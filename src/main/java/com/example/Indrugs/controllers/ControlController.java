package com.example.Indrugs.controllers;

import com.example.Indrugs.DTO.ControlDTO;
import com.example.Indrugs.entities.Usuario;
import com.example.Indrugs.services.ControlService;
import com.example.Indrugs.services.MedicamentosService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping
public class ControlController {

    private final ControlService controlService;
    private final MedicamentosService medicamentosService;


    public ControlController(ControlService controlService, MedicamentosService medicamentosService) {
        this.controlService = controlService;
        this.medicamentosService=medicamentosService;
    }

    @GetMapping("/24.pagina_control")
    public String mostrarPaginaControl(Model model, HttpSession session) {
        // Obtener todos los controles para mostrar
        Usuario usuario = (Usuario) session.getAttribute("usuarioLogueado");

        if (usuario == null) {
            return "redirect:/login"; // Si no está logueado
        }

        List<ControlDTO> controles = controlService.obtenerTodosLosControlesporUsuario(usuario.getIdUsuario());
        model.addAttribute("controles", controles);

        return "pacientes/24.pagina_control";
    }

    @GetMapping("/3.pagina_de_control")
    public String mostrarFormularioAgregar(Model model, HttpSession session) {
        Usuario usuario = (Usuario) session.getAttribute("usuarioLogueado");

        if (usuario == null) {
            return "redirect:/login"; // Si no está logueado
        }
        model.addAttribute("usuarioLogueado",  usuario);
        model.addAttribute("medicamentos", medicamentosService.readAdmin());
        model.addAttribute("control", new ControlDTO());
        return "pacientes/3.pagina_de_control";
    }

    @PostMapping("/agregar_control")
    public String agregarControl(@ModelAttribute ControlDTO controlDTO, RedirectAttributes redirectAttributes) {
        try {
            controlService.guardarControl(controlDTO);
            redirectAttributes.addFlashAttribute("mensaje", "Control agregado correctamente");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/24.pagina_control";
    }

    @GetMapping("/actualizar_control")
    public String mostrarFormularioEdicion(@RequestParam Long idControl, Model model) {
        try {
            ControlDTO control = controlService.findById(idControl);
            model.addAttribute("control", control);
            return "pacientes/actualizar_control";
        } catch (Exception e) {
            return "redirect:/17.pagina_control?error=Control no encontrado";
        }
    }

    @PostMapping("/actualizar_control")
    public String actualizarControl(@RequestParam Long idControl,
                                    ControlDTO controlDTO,
                                    RedirectAttributes redirectAttributes) {
        try {
            controlService.actualizar(idControl, controlDTO);
            redirectAttributes.addFlashAttribute("mensaje", "Control actualizado correctamente");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/17.pagina_control";
    }
}

