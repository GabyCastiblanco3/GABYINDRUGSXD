package com.example.Indrugs.controllers;

import com.example.Indrugs.entities.Usuario;
import com.example.Indrugs.services.UsuarioService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping
public class LoginController {

    private final UsuarioService usuarioService;

    public LoginController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    /**
     * Mostrar el formulario de inicio de sesión
     */
    @GetMapping("/login")
    public String mostrarLogin() {
        return "2.pagina_inicio_sesion";
    }

    /**
     * Procesar el inicio de sesión
     */
    @PostMapping("/login")
    public String procesoLogin(@RequestParam String correo,
                               @RequestParam String password,
                               RedirectAttributes redirectAttributes,
                               HttpSession session) {

        try {
            // Autenticación del usuario
            Usuario usuario = usuarioService.autenticar(correo, password);

            // Guardar el usuario logueado en sesión
            session.setAttribute("usuarioLogueado", usuario);

            // Redirección según rol
            String rol = usuario.getRol().getNombreRol();

            switch (rol) {
                case "Administrador":
                    return "redirect:/20.pagina_principal_administrador";
                case "Paciente":
                    return "redirect:/1.pagina_principal_paciente";
                case "Domiciliario":
                    return "redirect:/11.pagina_principal_domiciliario";
                default:
                    redirectAttributes.addFlashAttribute("error", "Rol no reconocido en el sistema.");
                    return "redirect:/login";
            }

        } catch (RuntimeException e) {
            // Captura de errores (correo o contraseña incorrectos)
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/login";
        }
    }

    /**
     * Cerrar sesión
     */
    @GetMapping("/cerrarSesion")
    public String cerrarSesion(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }
}
