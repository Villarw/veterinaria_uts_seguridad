package uts.edu.co.veterinaria_seguridad.controller;

import uts.edu.co.veterinaria_seguridad.model.DirectorioMedico;
import uts.edu.co.veterinaria_seguridad.model.Usuario;
import uts.edu.co.veterinaria_seguridad.service.DirectorioMedicoService;
import uts.edu.co.veterinaria_seguridad.service.UsuarioService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

@Controller
@RequestMapping("/admin/medicos")
public class DirectorioMedicoController {

    private final DirectorioMedicoService medicoService;
    private final UsuarioService usuarioService;

    public DirectorioMedicoController(DirectorioMedicoService medicoService,
                                      UsuarioService usuarioService) {
        this.medicoService = medicoService;
        this.usuarioService = usuarioService;
    }

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("medicos", medicoService.listarTodos());
        return "admin/medicos/lista";
    }

    @GetMapping("/nuevo")
    public String mostrarFormulario(Model model) {
        model.addAttribute("medico", new DirectorioMedico());
        return "admin/medicos/form";
    }

    @PostMapping
    public String guardar(@ModelAttribute DirectorioMedico medico,
                          @RequestParam String username,
                          @RequestParam String password,
                          RedirectAttributes redirect) {
        try {
            Usuario usuario = usuarioService.crearUsuarioConRol(username, password, "ROLE_VET");
            medico.setUsuario(usuario);
            medicoService.guardar(medico);
            redirect.addFlashAttribute("mensaje", "Médico creado exitosamente");
        } catch (RuntimeException e) {
            redirect.addFlashAttribute("error", "Error al crear el médico: " + e.getMessage());
        }
        return "redirect:/admin/medicos";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Integer id, Model model, RedirectAttributes redirect) {
        Optional<DirectorioMedico> medico = medicoService.buscarPorId(id);
        if (medico.isPresent()) {
            model.addAttribute("medico", medico.get());
            return "admin/medicos/form";
        } else {
            redirect.addFlashAttribute("error", "Médico no encontrado");
            return "redirect:/admin/medicos";
        }
    }

    @PostMapping("/actualizar")
    public String actualizar(@ModelAttribute DirectorioMedico medico, RedirectAttributes redirect) {
        try {
            Optional<DirectorioMedico> existente = medicoService.buscarPorId(medico.getIdMedico());
            if (existente.isPresent()) {
                DirectorioMedico med = existente.get();
                med.setNombre(medico.getNombre());
                med.setEspecialidad(medico.getEspecialidad());
                med.setTarjetaProfesional(medico.getTarjetaProfesional());
                medicoService.guardar(med);
                redirect.addFlashAttribute("mensaje", "Médico actualizado correctamente");
            } else {
                redirect.addFlashAttribute("error", "No se encontró el médico");
            }
        } catch (RuntimeException e) {
            redirect.addFlashAttribute("error", "Error al actualizar: " + e.getMessage());
        }
        return "redirect:/admin/medicos";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Integer id, RedirectAttributes redirect) {
        try {
            medicoService.eliminar(id);
            redirect.addFlashAttribute("mensaje", "Médico eliminado");
        } catch (Exception e) {
            redirect.addFlashAttribute("error", "No se pudo eliminar el médico");
        }
        return "redirect:/admin/medicos";
    }
}