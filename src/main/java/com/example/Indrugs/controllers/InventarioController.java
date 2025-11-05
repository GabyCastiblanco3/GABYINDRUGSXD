package com.example.Indrugs.controllers;

import com.example.Indrugs.DTO.InventarioDTO;
import com.example.Indrugs.entities.Inventario;
import com.example.Indrugs.entities.Usuario;
import com.example.Indrugs.services.InventarioService;
import com.example.Indrugs.services.MedicamentosService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
