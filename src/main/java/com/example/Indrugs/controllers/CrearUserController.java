package com.example.Indrugs.controllers;


import com.example.Indrugs.DTO.Usuario.UsuarioCreateDTO;
import com.example.Indrugs.services.EmailService;
import com.example.Indrugs.services.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping
public class CrearUserController {

    private UsuarioService userService;
    private EmailService emailService;

    public CrearUserController(UsuarioService userService, EmailService emailService){

        this.emailService = emailService;
        this.userService = userService;
    }

    @GetMapping({"/registrarse"})
    public String mostrarRegistrar(Model model){

        model.addAttribute("usuarioNuevo", new UsuarioCreateDTO());
        return "6.pagina_registrar_usuario.html";
    }

    @PostMapping({"/registrar"})
    public String crearUsuario(@Valid @ModelAttribute("usuarioNuevo")
                                   UsuarioCreateDTO userCreate,
                               BindingResult bindingResult,
                               Model model,
                               RedirectAttributes redirectAttributes){

      if (bindingResult.hasErrors()){
          return "6.pagina_registrar_usuario";
      }

        try {
            if (userService.existsByCorreo(userCreate.getCorreo())) {
                model.addAttribute("error", "El correo ya está registrado");
                return "6.pagina_registrar_usuario";
            }

            userService.crear(userCreate);
            emailService.enviarCorreoRegistro(userCreate.getCorreo(), userCreate.getNombre());
            redirectAttributes.addFlashAttribute("mensaje", "Usuario registrado exitosamente");
            return "redirect:/login";

        } catch (Exception e) {

            model.addAttribute("error", "Error al registrar usuario: " + e.getMessage());
            return "6.pagina_registrar_usuario";
        }

    }

}
