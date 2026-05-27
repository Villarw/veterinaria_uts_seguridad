package uts.edu.co.veterinaria_seguridad.controller;

import uts.edu.co.veterinaria_seguridad.model.HistorialMedico;
import uts.edu.co.veterinaria_seguridad.model.Mascota;
import uts.edu.co.veterinaria_seguridad.service.HistorialMedicoService;
import uts.edu.co.veterinaria_seguridad.service.MascotaService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/historial")
public class HistorialMedicoController {

    private final HistorialMedicoService historialService;
    private final MascotaService mascotaService;

    public HistorialMedicoController(HistorialMedicoService historialService, MascotaService mascotaService) {
        this.historialService = historialService;
        this.mascotaService = mascotaService;
    }

    @GetMapping("/mascota/{idMascota}")
    public String verHistorial(@PathVariable Integer idMascota, Model model) {
        Optional<Mascota> mascotaOpt = mascotaService.buscarPorId(idMascota);
        if (mascotaOpt.isPresent()) {
            model.addAttribute("mascota", mascotaOpt.get());
            model.addAttribute("historiales", historialService.listarPorMascota(idMascota));
            return "historial/lista";
        }
        return "redirect:/mascotas";
    }

    @GetMapping("/nuevo/{idMascota}")
    public String nuevo(@PathVariable Integer idMascota, Model model) {
        Optional<Mascota> mascotaOpt = mascotaService.buscarPorId(idMascota);
        if (mascotaOpt.isPresent()) {
            HistorialMedico historial = new HistorialMedico();
            historial.setMascota(mascotaOpt.get());
            model.addAttribute("historial", historial);
            return "historial/form";
        }
        return "redirect:/mascotas";
    }

    @PostMapping
    public String guardar(@ModelAttribute HistorialMedico historial) {
        historialService.guardar(historial);
        return "redirect:/historial/mascota/" + historial.getMascota().getIdMascota();
    }

    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Integer id) {
        Optional<HistorialMedico> opt = historialService.buscarPorId(id);
        if (opt.isPresent()) {
            Integer idMascota = opt.get().getMascota().getIdMascota();
            historialService.eliminar(id);
            return "redirect:/historial/mascota/" + idMascota;
        }
        return "redirect:/mascotas";
    }
}