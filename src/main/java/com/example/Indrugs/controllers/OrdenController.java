package com.example.Indrugs.controllers;

import com.example.Indrugs.DTO.MedicamentoDTO;
import com.example.Indrugs.DTO.OrdenDTO;
import com.example.Indrugs.entities.Medicamentos;
import com.example.Indrugs.entities.Orden;
import com.example.Indrugs.entities.Usuario;
import com.example.Indrugs.services.ArchivosService;
import com.example.Indrugs.services.MedicamentosService;
import com.example.Indrugs.services.OrdenService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDateTime;

@Controller
public class OrdenController {

    @Autowired
    private OrdenService ordenService;
    @Autowired
    private MedicamentosService medicamentosService;
    @Autowired
    private ArchivosService archivosService;

    @GetMapping("/14.pagina_ordenes")
    public String verOrdenesDirecto(Model model, HttpSession session) {
        Usuario usuario = (Usuario) session.getAttribute("usuarioLogueado");
        if (usuario == null) {
            return "redirect:/login"; // Si no está logueado
        }

        model.addAttribute("ordenes", ordenService.listarOrdenes());
        return "domiciliario/14.pagina_ordenes";
    }
    @GetMapping("/16.pagina_carrito_med")
    public String verOrdenesPaciente(Model model, HttpSession session) {
        Usuario usuario = (Usuario) session.getAttribute("usuarioLogueado");
        if (usuario == null) {
            return "redirect:/login"; // Si no está logueado
        }

        model.addAttribute("ordenes", ordenService.listarOrdenesP(usuario.getIdUsuario()));
        return "pacientes/16.pagina_carrito_med";
    }

    // Vista del administrador
    @GetMapping("/18.pagina_orden_admin")
    public String verOrdenesAdmin(Model model, HttpSession session) {
        Usuario usuario = (Usuario) session.getAttribute("usuarioLogueado");
        if (usuario == null) {
            return "redirect:/login"; // Si no está logueado
        }

        model.addAttribute("ordenes", ordenService.listarOrdenes());
        return "administrador/18.pagina_orden_admin";
    }

    @GetMapping("/nuevo")
    public String mostrarformulario(@RequestParam("idMedicamento") Long idMedicamento,
                                    @RequestParam("cantidad") Integer cantidad,
                                    HttpSession session, Model model)
    {try {
        // Obtener usuario logueado usando tu método
        Usuario usuario = (Usuario) session.getAttribute("usuarioLogueado");
        if (usuario == null) {
            return "redirect:/login"; // Si no está logueado
        }


        // Obtener medicamento
        MedicamentoDTO medicamento = medicamentosService.buscarPorIdMedicamento(idMedicamento);

        if (medicamento == null) {
            model.addAttribute("error", "Medicamento no encontrado");
            return "error";
        }

        // Crear nueva orden DTO
        OrdenDTO ordenDTO = new OrdenDTO();
        ordenDTO.setPacienteNombre(usuario.getNombre());
        ordenDTO.setCantidad(cantidad);
        ordenDTO.setNombreMedicamento(medicamento.getNombreMedicamento());
        ordenDTO.setEstadoOrden("ACTIVO");

        // Agregar al modelo
        model.addAttribute("orden", ordenDTO);
        model.addAttribute("usuarioLogueado", usuario);
        model.addAttribute("medicamento", medicamento);
        model.addAttribute("idMedicamento", idMedicamento);
        model.addAttribute("cantidad", cantidad);

        return "pacientes/4.pagina_domicilio";

    } catch (Exception e) {
        model.addAttribute("error", "Error al cargar el formulario: " + e.getMessage());
        return "error";
    }
    }

    @PostMapping("/guardar")
    public String guardarOrden(@ModelAttribute OrdenDTO ordenDTO,
//                               @RequestParam("formulaFile") MultipartFile formulaFile,
                               @RequestParam("idMedicamento") Long idMedicamento,
                               HttpSession session, Model model,
                               RedirectAttributes redirectAttributes) {
        try {
            Usuario usuario = (Usuario) session.getAttribute("usuarioLogueado");
            if (usuario == null) {
                return "redirect:/login";
            }

            MedicamentoDTO medicamento = medicamentosService.buscarPorIdMedicamento(idMedicamento);

            if (medicamento == null) {
                model.addAttribute("error", "Medicamento no encontrado");
                return "error";
            }

            ordenDTO.setPacienteNombre(usuario.getNombre());
            ordenDTO.setNombreMedicamento(medicamento.getNombreMedicamento());
            ordenDTO.setEstadoOrden("ACTIVO");

            if (ordenDTO.getFechaEntrega() == null) {
                ordenDTO.setFechaEntrega(LocalDateTime.now().plusDays(1));
            }
            ordenService.crear(ordenDTO,usuario.getIdUsuario(),idMedicamento);
            redirectAttributes.addFlashAttribute("mensaje", "Orden creada exitosamente");
            return "redirect:/16.pagina_carrito_med";

        } catch (Exception e) {
            model.addAttribute("error", "Error al guardar la orden: " + e.getMessage());
            model.addAttribute("orden", ordenDTO);
            model.addAttribute("usuarioLogueado", session.getAttribute("usuarioLogueado"));
            return "pacientes/4.pagina_domicilio";
        }
    }

    // Marcar orden como entregada desde domiciliario
//    @GetMapping("/domiciliario/ordenes/entregar/{id}")
//    public String entregarOrdenDesdeDomiciliario(@PathVariable Long id) {
//        ordenService.marcarComoEntregada(id);
//        return "redirect:/domiciliario/14.pagina_ordenes";
//    }

    // Marcar orden como entregada desde administrador
    @GetMapping("/ordenes/eliminar/{idOrden}")
    public String entregarOrdenDesdeAdmin(@PathVariable Long idOrden) {
        ordenService.eliminar(idOrden);
        return "redirect:/18.pagina_orden_admin";
    }
}