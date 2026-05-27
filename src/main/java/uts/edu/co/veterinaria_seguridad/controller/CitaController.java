package uts.edu.co.veterinaria_seguridad.controller;

import uts.edu.co.veterinaria_seguridad.model.Cita;
import uts.edu.co.veterinaria_seguridad.service.CitaService;
import uts.edu.co.veterinaria_seguridad.service.MascotaService;
import uts.edu.co.veterinaria_seguridad.service.DirectorioMedicoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/citas")
public class CitaController {

    private final CitaService citaService;
    private final MascotaService mascotaService;
    private final DirectorioMedicoService medicoService;

    public CitaController(CitaService citaService, MascotaService mascotaService,
                          DirectorioMedicoService medicoService) {
        this.citaService = citaService;
        this.mascotaService = mascotaService;
        this.medicoService = medicoService;
    }

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("citas", citaService.listarTodas());
        return "citas/lista";
    }

    @GetMapping("/nuevo")
    public String mostrarFormulario(Model model) {
        model.addAttribute("cita", new Cita());
        model.addAttribute("mascotas", mascotaService.listarTodas());
        model.addAttribute("medicos", medicoService.listarTodos());
        return "citas/form";
    }

    @PostMapping
    public String guardar(@ModelAttribute Cita cita) {
        citaService.guardar(cita);
        return "redirect:/citas";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Integer id, Model model) {
        Optional<Cita> cita = citaService.buscarPorId(id);
        if (cita.isPresent()) {
            model.addAttribute("cita", cita.get());
            model.addAttribute("mascotas", mascotaService.listarTodas());
            model.addAttribute("medicos", medicoService.listarTodos());
            return "citas/form";
        }
        return "redirect:/citas";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Integer id) {
        citaService.eliminar(id);
        return "redirect:/citas";
    }
}