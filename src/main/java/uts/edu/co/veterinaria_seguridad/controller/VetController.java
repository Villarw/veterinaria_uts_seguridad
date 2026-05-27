package uts.edu.co.veterinaria_seguridad.controller;

import uts.edu.co.veterinaria_seguridad.model.*;
import uts.edu.co.veterinaria_seguridad.service.*;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/vet")
public class VetController {

    private final DirectorioMedicoService medicoService;
    private final ClienteService clienteService;
    private final MascotaService mascotaService;
    private final CitaService citaService;
    private final HistorialMedicoService historialService;

    public VetController(DirectorioMedicoService medicoService,
                         ClienteService clienteService,
                         MascotaService mascotaService,
                         CitaService citaService,
                         HistorialMedicoService historialService) {
        this.medicoService = medicoService;
        this.clienteService = clienteService;
        this.mascotaService = mascotaService;
        this.citaService = citaService;
        this.historialService = historialService;
    }

    private DirectorioMedico getVetAuthenticated(Authentication auth) {
        String username = auth.getName();
        return medicoService.buscarPorUsername(username)
                .orElseThrow(() -> new RuntimeException("Veterinario no encontrado"));
    }

    @GetMapping("/clientes")
    public String listarClientes(Authentication auth, Model model) {
        DirectorioMedico vet = getVetAuthenticated(auth);
        model.addAttribute("clientes", clienteService.listarPorMedico(vet.getIdMedico()));
        return "clientes/lista";
    }

    @GetMapping("/mascotas")
    public String listarMascotas(Authentication auth, Model model) {
        DirectorioMedico vet = getVetAuthenticated(auth);
        model.addAttribute("mascotas", mascotaService.listarPorMedico(vet.getIdMedico()));
        return "mascotas/lista";
    }

    @GetMapping("/citas")
    public String listarCitas(Authentication auth, Model model) {
        DirectorioMedico vet = getVetAuthenticated(auth);
        model.addAttribute("citas", citaService.listarPorMedico(vet.getIdMedico()));
        return "citas/lista";
    }

    @GetMapping("/citas/nueva")
    public String nuevaCita(Authentication auth, Model model) {
        DirectorioMedico vet = getVetAuthenticated(auth);
        model.addAttribute("mascotas", mascotaService.listarPorMedico(vet.getIdMedico()));
        Cita cita = new Cita();
        cita.setMedico(vet);
        model.addAttribute("cita", cita);
        return "citas/form";
    }

    @PostMapping("/citas")
    public String guardarCita(@ModelAttribute Cita cita, Authentication auth) {
        DirectorioMedico vet = getVetAuthenticated(auth);
        cita.setMedico(vet);
        citaService.guardar(cita);
        return "redirect:/vet/citas";
    }

    @GetMapping("/citas/editar/{id}")
    public String editarCita(@PathVariable Integer id, Authentication auth, Model model) {
        Optional<Cita> citaOpt = citaService.buscarPorId(id);
        if (citaOpt.isPresent()) {
            Cita cita = citaOpt.get();
            DirectorioMedico vet = getVetAuthenticated(auth);
            if (!cita.getMedico().getIdMedico().equals(vet.getIdMedico())) {
                return "redirect:/vet/citas";
            }
            model.addAttribute("cita", cita);
            model.addAttribute("mascotas", mascotaService.listarPorMedico(vet.getIdMedico()));
            return "citas/form";
        }
        return "redirect:/vet/citas";
    }

    @GetMapping("/citas/eliminar/{id}")
    public String eliminarCita(@PathVariable Integer id, Authentication auth) {
        Optional<Cita> citaOpt = citaService.buscarPorId(id);
        if (citaOpt.isPresent()) {
            DirectorioMedico vet = getVetAuthenticated(auth);
            if (citaOpt.get().getMedico().getIdMedico().equals(vet.getIdMedico())) {
                citaService.eliminar(id);
            }
        }
        return "redirect:/vet/citas";
    }

    @GetMapping("/historial/mascota/{idMascota}")
    public String verHistorial(@PathVariable Integer idMascota, Authentication auth, Model model) {
        Optional<Mascota> mascotaOpt = mascotaService.buscarPorId(idMascota);
        if (mascotaOpt.isEmpty()) return "redirect:/vet/mascotas";
        DirectorioMedico vet = getVetAuthenticated(auth);
        boolean belongs = mascotaOpt.get().getCitas().stream()
                .anyMatch(c -> c.getMedico().getIdMedico().equals(vet.getIdMedico()));
        if (!belongs) return "redirect:/vet/mascotas";

        model.addAttribute("mascota", mascotaOpt.get());
        model.addAttribute("historiales", historialService.listarPorMascota(idMascota));
        return "historial/lista";
    }

    @GetMapping("/historial/nuevo/{idMascota}")
    public String nuevoHistorial(@PathVariable Integer idMascota, Authentication auth, Model model) {
        Optional<Mascota> mascotaOpt = mascotaService.buscarPorId(idMascota);
        if (mascotaOpt.isEmpty()) return "redirect:/vet/mascotas";
        DirectorioMedico vet = getVetAuthenticated(auth);
        boolean belongs = mascotaOpt.get().getCitas().stream()
                .anyMatch(c -> c.getMedico().getIdMedico().equals(vet.getIdMedico()));
        if (!belongs) return "redirect:/vet/mascotas";

        HistorialMedico historial = new HistorialMedico();
        historial.setMascota(mascotaOpt.get());
        model.addAttribute("historial", historial);
        return "historial/form";
    }

    @PostMapping("/historial")
    public String guardarHistorial(@ModelAttribute HistorialMedico historial, Authentication auth) {
        Optional<Mascota> mascotaOpt = mascotaService.buscarPorId(historial.getMascota().getIdMascota());
        if (mascotaOpt.isEmpty()) return "redirect:/vet/mascotas";
        DirectorioMedico vet = getVetAuthenticated(auth);
        boolean belongs = mascotaOpt.get().getCitas().stream()
                .anyMatch(c -> c.getMedico().getIdMedico().equals(vet.getIdMedico()));
        if (!belongs) return "redirect:/vet/mascotas";

        historialService.guardar(historial);
        return "redirect:/vet/historial/mascota/" + historial.getMascota().getIdMascota();
    }
}