package com.example.Indrugs.services;

import com.example.Indrugs.DTO.OrdenDTO;
import com.example.Indrugs.entities.Domicilio;
import com.example.Indrugs.entities.Medicamentos;
import com.example.Indrugs.entities.Orden;
import com.example.Indrugs.entities.Usuario;
import com.example.Indrugs.mapper.OrdenMapper;
import com.example.Indrugs.repositorios.DomicilioRepository;
import com.example.Indrugs.repositorios.MedicamentoRepository;
import com.example.Indrugs.repositorios.OrdenRepository;
import com.example.Indrugs.repositorios.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class OrdenServiceImpl implements OrdenService {

    private final OrdenRepository ordenRepository;
    private final MedicamentoRepository medicamentoRepository;
    private final UsuarioRepository usuarioRepository;
    private final DomicilioRepository domicilioRepository;
    private final EmailService emailService; // ✉️ Servicio de correo

    // 📁 Directorio donde se guardan las fórmulas
    private static final String UPLOAD_DIR = "uploads";

    @Autowired
    public OrdenServiceImpl(OrdenRepository ordenRepository,
                            MedicamentoRepository medicamentoRepository,
                            UsuarioRepository usuarioRepository,
                            DomicilioRepository domicilioRepository,
                            EmailService emailService) {
        this.ordenRepository = ordenRepository;
        this.medicamentoRepository = medicamentoRepository;
        this.usuarioRepository = usuarioRepository;
        this.domicilioRepository = domicilioRepository;
        this.emailService = emailService;
    }

    @Override
    public List<OrdenDTO> listarOrdenes() {
        List<Orden> ordenes = ordenRepository.findAll();
        return OrdenMapper.toDTOList(ordenes);
    }

    @Override
    public List<OrdenDTO> listarOrdenesP(Long idUsuario) {
        List<Orden> ordenes = ordenRepository.findByPaciente_IdUsuario(idUsuario);
        return ordenes.stream().map(OrdenMapper::toDTO).collect(Collectors.toList());
    }

    @Override
    public void marcarComoEntregada(Long idOrden) {
        Orden orden = ordenRepository.findById(idOrden)
                .orElseThrow(() -> new RuntimeException("Orden no encontrada con ID: " + idOrden));
        orden.setEstadoOrden("Entregada");
        ordenRepository.save(orden);
    }

    @Override
    public void crear(OrdenDTO ordenDTO, Long idUsuario, Long idMedicamento) {
        Orden orden = OrdenMapper.toEntity(ordenDTO);

        Usuario usuario = usuarioRepository.findByIdUsuario(idUsuario)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        orden.setPaciente(usuario);

        Medicamentos medicamento = medicamentoRepository.findById(idMedicamento)
                .orElseThrow(() -> new RuntimeException("Medicamento no encontrado"));
        orden.setMedicamentos(List.of(medicamento));

        // ✅ Guardar la ruta de la fórmula médica
        orden.setFormulaMedica(ordenDTO.getFotoFormula());

        // ✅ Estado por defecto
        if (orden.getEstadoOrden() == null || orden.getEstadoOrden().isEmpty()) {
            orden.setEstadoOrden("ACTIVO");
        }

        ordenRepository.save(orden);
        crearDomicilioConOrden(orden);
    }

    @Override
    public void crearDomicilioConOrden(Orden orden) {
        Domicilio domicilio = new Domicilio();
        domicilio.setOrden(orden);
        domicilio.setUbicacionDomicilio(orden.getDireccionOrden());
        domicilio.setEstadoDomicilio("EN ESPERA");
        domicilio.setFechaEntregaDomicilio(orden.getFechaEntrega());
        domicilioRepository.save(domicilio);
    }

    /**
     * 🔥 Elimina una orden, borra su fórmula médica del servidor
     * y notifica al paciente por correo.
     */
    @Override
    public void eliminar(Long idOrden) {
        Orden orden = ordenRepository.findById(idOrden)
                .orElseThrow(() -> new RuntimeException("Orden no encontrada con ID: " + idOrden));

        // 📩 Datos del paciente
        String correoPaciente = null;
        String nombrePaciente = "Paciente";

        if (orden.getPaciente() != null) {
            correoPaciente = orden.getPaciente().getCorreo();
            nombrePaciente = orden.getPaciente().getNombre();
        }

        // 🗑️ Eliminar archivo de fórmula médica
        String nombreArchivo = orden.getFotoFormula();
        if (nombreArchivo != null && !nombreArchivo.isEmpty()) {
            try {
                Path rutaArchivo = Paths.get(UPLOAD_DIR).resolve(nombreArchivo).toAbsolutePath();
                File archivo = rutaArchivo.toFile();
                if (archivo.exists()) {
                    Files.delete(rutaArchivo);
                    System.out.println("✅ Archivo eliminado: " + rutaArchivo);
                } else {
                    System.out.println("⚠️ Archivo no encontrado: " + rutaArchivo);
                }
            } catch (Exception e) {
                System.err.println("❌ Error al eliminar el archivo: " + e.getMessage());
            }
        }

        // 🗑️ Eliminar orden
        ordenRepository.deleteById(idOrden);

        // ✉️ Enviar correo si el paciente tiene correo registrado
        if (correoPaciente != null && !correoPaciente.isEmpty()) {
            try {
                String asunto = "Notificación: Orden eliminada";
                String mensajeHtml = "<html><body style='font-family: Arial, sans-serif;'>" +
                        "<h2 style='color: #d32f2f;'>Estimado(a) " + nombrePaciente + ",</h2>" +
                        "<p>Tu orden con ID <strong>" + idOrden + "</strong> ha sido eliminada del sistema de INDRUGS por motivo de posible fraude o formula vencida.</p>" +
                        "<p>Si consideras que esto fue un error, por favor comunícate con el área de atención indrugsmedica@gmail.com.</p>" +
                        "<br><p style='color: #777;'>Atentamente,<br><strong>Equipo INDRUGS MÉDICA</strong></p>" +
                        "</body></html>";

                emailService.enviarCorreo(correoPaciente, asunto, mensajeHtml);
                System.out.println("📧 Correo enviado a: " + correoPaciente);
            } catch (Exception e) {
                System.err.println("⚠️ Error al enviar correo: " + e.getMessage());
            }
        } else {
            System.out.println("⚠️ No se envió correo: el paciente no tiene correo registrado.");
        }
    }

    @Override
    public long countOrdenActivo() {
        return ordenRepository.countByEstadoOrden("ACTIVO");
    }

    @Override
    public List<OrdenDTO> ObtenerOrdenesRecientes() {
        List<Orden> domicilio = ordenRepository.findTop4ByOrderByIdOrdenDesc();
        return domicilio.stream().map(OrdenMapper::toDTO).collect(Collectors.toList());
    }

    @Override
    public Map<String, Object> ObtenerResumenOrden() {
        Map<String, Object> dashboard = new HashMap<>();
        long ordenesActivos = ordenRepository.countByEstadoOrden("ACTIVO");
        dashboard.put("totalOrdenesActivos", ordenesActivos);
        List<Orden> top4Orden = ordenRepository.findTop4ByOrderByIdOrdenDesc();
        dashboard.put("ordenesRecientes", top4Orden);
        return dashboard;
    }

    @Override
    public OrdenDTO obtenerOrdenPorId(Long id) {
        Orden orden = ordenRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Orden no encontrada con ID: " + id));
        return OrdenMapper.toDTO(orden);
    }
}
