package com.example.Indrugs.controllers;


import com.example.Indrugs.DTO.OrdenDTO;
import com.example.Indrugs.DTO.Usuario.UsuarioDTO;
import com.example.Indrugs.DTO.Usuario.UsuarioUpdateDTO;
import com.example.Indrugs.entities.Usuario;
import com.example.Indrugs.mapper.UsuarioMapper;
import com.example.Indrugs.services.InventarioService;
import com.example.Indrugs.services.OrdenService;
import com.example.Indrugs.services.UsuarioService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping
public class AdminisradorController {

    private final UsuarioService usuarioService;
    private final InventarioService inventarioService;
    private final OrdenService ordenService;

    public AdminisradorController(UsuarioService usuarioService, InventarioService inventarioService,OrdenService ordenService){
        this.usuarioService = usuarioService;
        this.inventarioService = inventarioService;
        this.ordenService = ordenService;
    }


    @GetMapping("/export/usuarios_registrados")
    public void exportUsersToPdf(HttpServletResponse response) throws IOException, DocumentException {
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=usuarios.pdf");

        List<UsuarioDTO> usuarios = usuarioService.read();
        Document document = new Document(PageSize.A4.rotate());
        PdfWriter.getInstance(document, response.getOutputStream());
        document.open();

        Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18, new BaseColor(34,139,34));
        Paragraph title = new Paragraph("Lista de Usuarios Registrados ",titleFont);
        title.setAlignment(Element.ALIGN_CENTER);
        document.add(title);
        document.add(new Paragraph(" "));

        Paragraph fecha = new Paragraph("Fecha de generación: " + new Date().toString());
        fecha.setAlignment(Element.ALIGN_RIGHT);
        document.add(fecha);
        document.add(new Paragraph(" "));

        PdfPTable table = new PdfPTable(8);
        table.setWidthPercentage(100);

        float[] columnWidths = {1f, 2f, 1.5f, 2f, 2.5f, 1.5f, 2.5f, 1f};
        table.setWidths(columnWidths);
        int rowIndex = 0;
        Font headerFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 10);
        addCellToTable(table, "Rol", headerFont, true, rowIndex);
        addCellToTable(table, "Nombre", headerFont, true, rowIndex);
        addCellToTable(table, "Tipo documento", headerFont, true, rowIndex);
        addCellToTable(table, "Documento", headerFont, true, rowIndex);
        addCellToTable(table, "Dirección", headerFont, true, rowIndex);
        addCellToTable(table, "Teléfono", headerFont, true, rowIndex);
        addCellToTable(table, "Correo", headerFont, true, rowIndex);
        addCellToTable(table, "Estado", headerFont, true, rowIndex);

        Font dataFont = FontFactory.getFont(FontFactory.HELVETICA, 8);

        for (UsuarioDTO usuario : usuarios) {
            addCellToTable(table, usuario.getNombreRol() != null ? usuario.getNombreRol() : "", dataFont, false, rowIndex);
            addCellToTable(table, usuario.getNombre() != null ? usuario.getNombre() : "", dataFont, false, rowIndex);
            addCellToTable(table, usuario.getTipoDoc() != null ? usuario.getTipoDoc() : "", dataFont, false, rowIndex);
            addCellToTable(table, usuario.getNumDoc() != null ? usuario.getNumDoc() : "", dataFont, false, rowIndex);
            addCellToTable(table, usuario.getDireccion() != null ? usuario.getDireccion() : "", dataFont, false, rowIndex);
            addCellToTable(table, usuario.getTelefono() != null ? usuario.getTelefono() : "", dataFont, false, rowIndex);
            addCellToTable(table, usuario.getCorreo() != null ? usuario.getCorreo() : "", dataFont, false, rowIndex);
            addCellToTable(table, usuario.getEstado() != null ? usuario.getEstado() : "", dataFont, false, rowIndex);
            rowIndex++;
        }

        document.add(table);

        document.add(new Paragraph(" "));
        Paragraph footer = new Paragraph("Total de usuarios: " + usuarios.size());
        footer.setAlignment(Element.ALIGN_CENTER);
        document.add(footer);

        document.close();
    }

    private void addCellToTable(PdfPTable table, String content, Font font, boolean isHeader, int rowIndex) {
        PdfPCell cell = new PdfPCell(new Phrase(content, font));
        if (isHeader) {
            cell.setBackgroundColor(new BaseColor(200, 225, 255)); // azul pastel muy claro para encabezados
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setBorderColor(new BaseColor(180, 210, 245)); // azul grisáceo claro para bordes
        } else {
            if (rowIndex % 2 == 0) {
                cell.setBackgroundColor(new BaseColor(225, 240, 255)); // azul muy claro para filas pares
            } else {
                cell.setBackgroundColor(new BaseColor(240, 248, 255)); // azul casi blanco (tono aireado)
            }
            cell.setBorderColor(new BaseColor(180, 210, 245)); // bordes más claros para datos
        }
        cell.setPadding(5);
        table.addCell(cell);
    }

    @GetMapping("/20.pagina_principal_administrador")
    public String mostrarPaginaAdmin(HttpSession session, Model model){
        Usuario usuario = (Usuario) session.getAttribute("usuarioLogueado");

        if (usuario == null) {
            return "redirect:/login"; // si no está logueado
        }

        //estadistica
        Map<String, Long> estadisticasUsuarios = usuarioService.obtenerResumenUsuarios();
        model.addAttribute("estadisticas", estadisticasUsuarios);
        // resumen
        List<UsuarioDTO> usuariosRecientes = usuarioService.obtenerUsuariosRecientes();
        model.addAttribute("usuariosRecientes", usuariosRecientes);

        model.addAttribute("cantidadInventario", inventarioService.totalUnidadesEnStock());
        
        List<OrdenDTO> ordenesRecientes = ordenService.ObtenerOrdenesRecientes();
        model.addAttribute("ordenesRecientes", ordenesRecientes);

        Map<String,Object> dashboard=ordenService.ObtenerResumenOrden();
//        model.addAttribute("ordenesRecientes",dashboard.get("ordenesRecientes"));
        model.addAttribute("cantidadOrdenes",dashboard.get("totalOrdenesActivos"));


        return "administrador/20.pagina_principal_administrador";
    }

    @GetMapping("/21.pagina_usuarios")
    public String gestionUsuarios(
            @RequestParam(required = false) String rol,
            @RequestParam(required = false) String estado,
            HttpSession session,
            Model model) {
        Usuario usuario = (Usuario) session.getAttribute("usuarioLogueado");

        if (usuario == null) {
            return "redirect:/login"; // si no está logueado
        }

        List<UsuarioDTO> usuarios;

        // aplicar filtros
        if (rol != null && !rol.isEmpty() && estado != null && !estado.isEmpty()) {
            usuarios = usuarioService.findByRolNombreAndEstado(rol, estado);
        } else if (rol != null && !rol.isEmpty()) {
            usuarios = usuarioService.findByRolNombre(rol);
        } else if (estado != null && !estado.isEmpty()) {
            usuarios = usuarioService.findByStatus(estado);
        } else {
            usuarios = usuarioService.read(); // Todos los usuarios
        }

        model.addAttribute("usuarios", usuarios);
        model.addAttribute("rolSeleccionado", rol);
        model.addAttribute("estadoSeleccionado", estado);

        return "administrador/21.pagina_usuarios";
    }

    @GetMapping("/actualizar")
    public String mostrarFormularioEdicion(@RequestParam Long idUsuario, HttpSession session, Model model) {
        Usuario usuario = (Usuario) session.getAttribute("usuarioLogueado");

        if (usuario == null) {
            return "redirect:/login"; // si no está logueado
        }

        try {
            // Obtener el usuario por ID
            UsuarioDTO usuariodto = usuarioService.findById(idUsuario);

            // Convertir a UsuarioUpdateDTO para el formulario
            UsuarioUpdateDTO usuarioUpdate = UsuarioMapper.toUpdateDTO(usuariodto);

            model.addAttribute("usuario", usuarioUpdate);

            model.addAttribute("estados", List.of("ACTIVO", "INACTIVO"));

            return "administrador/actualizar_usuario";

        } catch (Exception e) {
            return "redirect:/21.pagina_usuarios?error=Usuario no encontrado";
        }
    }

    @PostMapping("/actualizar")
    public String actualizarUsuario(@RequestParam Long idUsuario,
                                    UsuarioUpdateDTO userUpdate,
                                    RedirectAttributes redirectAttributes) {
        try{
            usuarioService.actualizar(idUsuario, userUpdate);
            redirectAttributes.addFlashAttribute("mensaje", "Usuario actualizado correctamente");
        }catch (Exception e){
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/21.pagina_usuarios";
    }
}
