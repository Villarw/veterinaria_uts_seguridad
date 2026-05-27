package uts.edu.co.veterinaria_seguridad.controller;

import uts.edu.co.veterinaria_seguridad.model.Mascota;
import uts.edu.co.veterinaria_seguridad.service.ClienteService;
import uts.edu.co.veterinaria_seguridad.service.MascotaService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/mascotas")
public class MascotaController {

    private final MascotaService mascotaService;
    private final ClienteService clienteService;

    public MascotaController(MascotaService mascotaService, ClienteService clienteService) {
        this.mascotaService = mascotaService;
        this.clienteService = clienteService;
    }

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("mascotas", mascotaService.listarTodas());
        return "mascotas/lista";
    }

    @GetMapping("/nuevo")
    public String mostrarFormulario(Model model) {
        model.addAttribute("mascota", new Mascota());
        model.addAttribute("clientes", clienteService.listarTodos());
        return "mascotas/form";
    }

    @PostMapping
    public String guardar(@ModelAttribute Mascota mascota) {
        mascotaService.guardar(mascota);
        return "redirect:/mascotas";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Integer id, Model model) {
        Optional<Mascota> mascota = mascotaService.buscarPorId(id);
        if (mascota.isPresent()) {
            model.addAttribute("mascota", mascota.get());
            model.addAttribute("clientes", clienteService.listarTodos());
            return "mascotas/form";
        }
        return "redirect:/mascotas";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Integer id) {
        mascotaService.eliminar(id);
        return "redirect:/mascotas";
    }
}