package com.example.Indrugs.controllers;

import com.example.Indrugs.DTO.OrdenDTO;
import com.example.Indrugs.DTO.Usuario.UsuarioDTO;
import com.example.Indrugs.DTO.Usuario.UsuarioUpdateDTO;
import com.example.Indrugs.entities.Usuario;
import com.example.Indrugs.mapper.UsuarioMapper;
import com.example.Indrugs.services.UsuarioService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Map;
@Controller
@RequestMapping
public class ActualizarperfilController {


    private final UsuarioService usuarioService;

    public ActualizarperfilController(UsuarioService usuarioService){
        this.usuarioService = usuarioService;
    }

    @GetMapping("/actualizar_perfil")
    public String mostrarFormularioEdicion(HttpSession session, Model model) {
        Usuario usuario = (Usuario) session.getAttribute("usuarioLogueado");

        if (usuario == null) {
            return "redirect:/login"; // si no está logueado
        }

        try {
            // Obtener el usuario por ID

            UsuarioDTO usuariodto = usuarioService.findById(usuario.getIdUsuario());

            // Convertir a UsuarioUpdateDTO para el formulario
            UsuarioUpdateDTO usuarioUpdate = UsuarioMapper.toUpdateDTO(usuariodto);

            model.addAttribute("usuario", usuarioUpdate);

            model.addAttribute("estados", List.of("ACTIVO", "INACTIVO"));

            String rol = usuario.getRol().getNombreRol();
            return switch (rol) {
                case "Administrador" -> "administrador/actualizar_perfil";
                case "Pacientes" -> "paciente/actualizar_perfil";
                case "Domiciliario" -> "domiciliario/actualizar_perfil";
                default -> "redirect:/login";
            };

        } catch (Exception e) {
            return "redirect:/error=Usuario no encontrado";
        }
    }

    @PostMapping("/actualizar_perfil")
    public String actualizarUsuario(UsuarioUpdateDTO userUpdate,
                                    RedirectAttributes redirectAttributes,
                                    HttpSession session) {
        Usuario usuario = (Usuario) session.getAttribute("usuarioLogueado");
        try{
            usuarioService.actualizar(usuario.getIdUsuario(), userUpdate);
            redirectAttributes.addFlashAttribute("mensaje", "Usuario actualizado correctamente");
        }catch (Exception e){
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        String rol = usuario.getRol().getNombreRol();
        return switch (rol) {
            case "Administrador" -> "administrador/actualizar_perfil";
            case "Paciente" -> "pacientes/actualizar_perfil";
            case "Domiciliario" -> "domiciliario/actualizar_perfil";
            default -> "redirect:/login";
        };
    }
}
