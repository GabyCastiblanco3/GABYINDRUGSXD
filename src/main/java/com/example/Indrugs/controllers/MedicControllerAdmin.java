package com.example.Indrugs.controllers;

import com.example.Indrugs.DTO.MedicamentoDTO;
import com.example.Indrugs.entities.Usuario;
import com.example.Indrugs.services.ArchivosService;
import com.example.Indrugs.services.MedicamentosService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping
public class MedicControllerAdmin {

    private final MedicamentosService medicService;
    private ArchivosService archivosService;

    public  MedicControllerAdmin(MedicamentosService medicService, ArchivosService archivosService){
        this.medicService = medicService;
        this.archivosService = archivosService;
    }

    @GetMapping("/22.pagina_medicamento")
    public String mostrarMedic(HttpSession session, Model model){
        Usuario usuario = (Usuario) session.getAttribute("usuarioLogueado");

        if (usuario == null) {
            return "redirect:/login"; // si no está logueado
        }

        List<MedicamentoDTO> medicamentos = medicService.readAdmin();
        model.addAttribute("medicamentos", medicamentos);
        return "administrador/22.pagina_medicamento";
    }

    @GetMapping("23.agregar_medicamentos")
    public String formularioCrear(HttpSession session, Model model){
        Usuario usuario = (Usuario) session.getAttribute("usuarioLogueado");

        if (usuario == null) {
            return "redirect:/login"; // si no está logueado
        }
        model.addAttribute("medicamentoDTO", new MedicamentoDTO());
        return "administrador/23.agregar_medicamentos";
    }

    @PostMapping("/guardar-medicamento")
    public String crearMedicamento(
            @ModelAttribute @Valid MedicamentoDTO medicamentoDTO,
            HttpSession session,
            BindingResult result,
            @RequestParam("imagenFile") MultipartFile imagenFile,
            RedirectAttributes redirectAttributes) {
        Usuario usuario = (Usuario) session.getAttribute("usuarioLogueado");

        if (usuario == null) {
            return "redirect:/login"; // si no está logueado
        }

        if (result.hasErrors()) {
            return "administrador/23.agregar_medicamentos";
        }

        try {
            if (imagenFile.isEmpty()) {
                redirectAttributes.addFlashAttribute("error", "Debes subir una imagen");
                return "redirect:/23.agregar_medicamentos";
            }

            String urlImagen = archivosService.guardarImagenMedicamento(imagenFile);
            medicamentoDTO.setImagenMedicamento(urlImagen);

            medicService.crear(medicamentoDTO);
            redirectAttributes.addFlashAttribute("mensaje", "Medicamento creado exitosamente");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }

        System.out.println("Medicamento guardado: " + medicamentoDTO.getNombreMedicamento());


        return "redirect:/22.pagina_medicamento";
    }
}
