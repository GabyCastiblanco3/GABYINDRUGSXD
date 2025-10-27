package com.example.Indrugs.controllers;

import com.example.Indrugs.DTO.InventarioDTO;
import com.example.Indrugs.entities.Inventario;
import com.example.Indrugs.entities.Usuario;
import com.example.Indrugs.services.InventarioService;
import com.example.Indrugs.services.MedicamentosService;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping
public class InventarioController {

    private InventarioService inventarioService;
    private MedicamentosService medicamentosService;

    public InventarioController(InventarioService inventarioService, MedicamentosService medicamentosService){
        this.inventarioService = inventarioService;
        this.medicamentosService = medicamentosService;
    }

    @GetMapping("/export/inventario")
    public void exportUsersToPdf(HttpServletResponse response) throws IOException, DocumentException {
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=inventario.pdf");

        List<InventarioDTO> inventarios = inventarioService.read();
        Document document = new Document(PageSize.A4.rotate());
        PdfWriter.getInstance(document, response.getOutputStream());
        document.open();

        Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18, new BaseColor(22,17,153));
        Paragraph title = new Paragraph("Lista de Inventario",titleFont);
        title.setAlignment(Element.ALIGN_CENTER);
        document.add(title);
        document.add(new Paragraph(" "));

        Paragraph fecha = new Paragraph("Fecha de generación: " + new Date().toString());
        fecha.setAlignment(Element.ALIGN_RIGHT);
        document.add(fecha);
        document.add(new Paragraph(" "));

        PdfPTable table = new PdfPTable(7);
        table.setWidthPercentage(100);

        float[] columnWidths = {0.5f, 1f, 1f, 0.5f, 1f, 1f, 0.5f};
        table.setWidths(columnWidths);
        int rowIndex = 0;
        Font headerFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 10);
        addCellToTable(table, "ID", headerFont, true, rowIndex);
        addCellToTable(table, "Medicamento", headerFont, true, rowIndex);
        addCellToTable(table, "Entrada", headerFont, true, rowIndex);
        addCellToTable(table, "Stock", headerFont, true, rowIndex);
        addCellToTable(table, "Salida", headerFont, true, rowIndex);
        addCellToTable(table, "Vencimiento", headerFont, true, rowIndex);
        addCellToTable(table, "Estado", headerFont, true, rowIndex);

        Font dataFont = FontFactory.getFont(FontFactory.HELVETICA, 7);

        for (InventarioDTO inventario : inventarios) {
            addCellToTable(table, String.valueOf(inventario.getIdInventario()), dataFont, false, rowIndex);
            addCellToTable(table, inventario.getNombreMedicamento() != null ? inventario.getNombreMedicamento() : "", dataFont, false, rowIndex);
            addCellToTable(table, inventario.getFechaEntrada() != null ? String.valueOf(inventario.getFechaEntrada())  : "", dataFont, false, rowIndex);
            addCellToTable(table, inventario.getStock() != null ? String.valueOf(inventario.getStock())  : "", dataFont, false, rowIndex);
            addCellToTable(table, inventario.getFechaSalida() != null ? String.valueOf(inventario.getFechaSalida()) : "", dataFont, false, rowIndex);
            addCellToTable(table, inventario.getVencimiento() != null ? String.valueOf(inventario.getVencimiento()) : "", dataFont, false, rowIndex);
            addCellToTable(table, inventario.getEstadoMed() != null ? inventario.getEstadoMed() : "", dataFont, false, rowIndex);
            rowIndex++;
        }

        document.add(table);

        document.add(new Paragraph(" "));
        Paragraph footer = new Paragraph("Total inventario: " + inventarios.size());
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
            cell.setBorderColor(new BaseColor(180, 210, 245)); // bordes azul pastel
        }
        cell.setPadding(5);
        table.addCell(cell);
    }

    @GetMapping("/17.pagina_inventario")
    public String mostrarInventario(@RequestParam(required = false) String estadoMed,
                                    HttpSession session,
                                    Model model){
        Usuario usuario = (Usuario) session.getAttribute("usuarioLogueado");

        if (usuario == null) {
            return "redirect:/login"; // si no está logueado
        }
        List<InventarioDTO> inventarios;

        //filtros
        if (estadoMed != null && !estadoMed.isEmpty()){
            inventarios = inventarioService.findByEstado(estadoMed);
        }else {
            inventarios = inventarioService.read();
        }

        model.addAttribute("inventarios", inventarios);
        model.addAttribute("estadoSelecionado", estadoMed);

        return "administrador/17.pagina_inventario";
    }

    @GetMapping("/19.pagina_agregar_med")
    public String mostrarFormulario(HttpSession session, Model model){
        Usuario usuario = (Usuario) session.getAttribute("usuarioLogueado");

        if (usuario == null) {
            return "redirect:/login"; // si no está logueado
        }

        model.addAttribute("invetarioDTO", new InventarioDTO());
        model.addAttribute("medicamentos", medicamentosService.readAdmin());
        return "administrador/19.pagina_agregar_med";
    }

    @PostMapping("/procesar_inventario")
    public String agregarInventario(@ModelAttribute InventarioDTO inventarioDTO,
                                    RedirectAttributes redirectAttribute){
        try{
            inventarioService.crear(inventarioDTO);
            redirectAttribute.addFlashAttribute("mensaje", "Medicamento agregado exitosamente");
        } catch (Exception e) {
            redirectAttribute.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/17.pagina_inventario";
    }
}
