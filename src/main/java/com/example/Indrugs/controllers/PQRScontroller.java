package com.example.Indrugs.controllers;

import com.example.Indrugs.DTO.PQRSDTO;
import com.example.Indrugs.entities.Usuario;
import com.example.Indrugs.services.PQRSservice;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping
public class PQRScontroller {

    @Autowired
    private PQRSservice pqrsService;

    @GetMapping("/5.pagina_pqrs")
    public String mostrarFormulario(Model model, HttpSession session) {
            Usuario usuario = (Usuario) session.getAttribute("usuarioLogueado");
            if (usuario == null) {
                return "redirect:/login";
            }
            model.addAttribute("pqrs", new PQRSDTO());
        model.addAttribute("usuarioLogueado", session.getAttribute("usuarioLogueado"));
        return "pacientes/5.pagina_pqrs";
    }


    @PostMapping("/guardar-pqrs")
    public String guardarPQRS(@ModelAttribute("pqrs") PQRSDTO dto,
                              HttpSession session,
                              RedirectAttributes redirectAttributes) {
        Usuario usuario = (Usuario) session.getAttribute("usuarioLogueado");
        if (usuario == null) {
            return "redirect:/login";
        }
        pqrsService.crear(dto, usuario.getIdUsuario());
        redirectAttributes.addFlashAttribute("mensaje", "¡PQRS enviada correctamente!");
        return "redirect:/5.pagina_pqrs";
    }
}
