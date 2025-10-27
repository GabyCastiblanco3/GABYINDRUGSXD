package com.example.Indrugs.controllers;

import com.example.Indrugs.DTO.MedicamentoDTO;
import com.example.Indrugs.DTO.Usuario.UsuarioDTO;
import com.example.Indrugs.entities.Usuario;
import com.example.Indrugs.services.ArchivosService;
import com.example.Indrugs.services.MedicamentosService;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping
public class MedicControllerPaciente {

    private final MedicamentosService medicService;
    private ArchivosService archivosService;

    public  MedicControllerPaciente(MedicamentosService medicService, ArchivosService archivosService){
        this.medicService = medicService;
        this.archivosService = archivosService;
    }

    @GetMapping("/1.pagina_principal_paciente")
    public String mostrarPaginaPaciente(HttpSession session){
        Usuario usuario = (Usuario) session.getAttribute("usuarioLogueado");

        if (usuario == null) {
            return "redirect:/login"; // si no está logueado
        }

        return "pacientes/1.pagina_principal_paciente";
    }

    @GetMapping("/8.pagina_med")
    public String mostrarMedic(HttpSession session, Model model){
        Usuario usuario = (Usuario) session.getAttribute("usuarioLogueado");

        if (usuario == null) {
            return "redirect:/login"; // si no está logueado
        }

        List<MedicamentoDTO> medicamentos = medicService.mostarEnPaciente();
        model.addAttribute("medicamentos", medicamentos);
        return "pacientes/8.pagina_med";
    }

    @GetMapping("/medicamentos/{idMedicamento}")
    public String mostrarDetalleMedicamento(@PathVariable Long idMedicamento, HttpSession session, Model model) {
        Usuario usuario = (Usuario) session.getAttribute("usuarioLogueado");

        if (usuario == null) {
            return "redirect:/login"; // si no está logueado
        }

        MedicamentoDTO medicamento = medicService.buscarPorIdMedicamento(idMedicamento);
        model.addAttribute("medicamento", medicamento);
        return "pacientes/9.pagina_detalle_med";
    }

    @GetMapping("/api/medicamentos/buscar")
    @ResponseBody
    public ResponseEntity<List<MedicamentoDTO>> buscarMedicamentos(
            @RequestParam(value = "nombre") String nombreMedicamento) {

        try {
            if (nombreMedicamento == null || nombreMedicamento.trim().isEmpty()) {
                return ResponseEntity.badRequest().build();
            }

            List<MedicamentoDTO> medicamentos = medicService.findByNombre(nombreMedicamento);
            return ResponseEntity.ok(medicamentos);

        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }

}
