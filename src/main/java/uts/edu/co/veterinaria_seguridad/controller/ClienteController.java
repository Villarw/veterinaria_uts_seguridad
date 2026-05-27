package uts.edu.co.veterinaria_seguridad.controller;

import uts.edu.co.veterinaria_seguridad.model.Cliente;
import uts.edu.co.veterinaria_seguridad.model.Usuario;
import uts.edu.co.veterinaria_seguridad.service.ClienteService;
import uts.edu.co.veterinaria_seguridad.service.UsuarioService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

@Controller
@RequestMapping("/clientes")
public class ClienteController {

    private final ClienteService clienteService;
    private final UsuarioService usuarioService;

    public ClienteController(ClienteService clienteService, UsuarioService usuarioService) {
        this.clienteService = clienteService;
        this.usuarioService = usuarioService;
    }

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("clientes", clienteService.listarTodos());
        return "clientes/lista";
    }

    @GetMapping("/nuevo")
    public String mostrarFormulario(Model model) {
        model.addAttribute("cliente", new Cliente());
        return "clientes/form";
    }

    @PostMapping
    public String guardar(@ModelAttribute Cliente cliente,
                          @RequestParam(required = false) String username,
                          @RequestParam(required = false) String password,
                          RedirectAttributes redirect) {
        if (username != null && !username.isBlank() && password != null && !password.isBlank()) {
            try {
                Usuario usuario = usuarioService.crearUsuarioConRol(username, password, "ROLE_CLIENTE");
                cliente.setUsuario(usuario);
            } catch (Exception e) {
                redirect.addFlashAttribute("error", "Error al crear usuario: " + e.getMessage());
                return "redirect:/clientes/nuevo";
            }
        }
        clienteService.guardar(cliente);
        redirect.addFlashAttribute("mensaje", "Cliente guardado correctamente");
        return "redirect:/clientes";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Integer id, Model model, RedirectAttributes redirect) {
        Optional<Cliente> clienteOpt = clienteService.buscarPorId(id);
        if (clienteOpt.isPresent()) {
            model.addAttribute("cliente", clienteOpt.get());
            return "clientes/form";
        } else {
            redirect.addFlashAttribute("error", "Cliente no encontrado");
            return "redirect:/clientes";
        }
    }

    @GetMapping("/toggle/{id}")
    public String toggleActivo(@PathVariable Integer id, RedirectAttributes redirect) {
        Optional<Cliente> opt = clienteService.buscarPorId(id);
        if (opt.isPresent()) {
            boolean estabaActivo = opt.get().getActivo();
            clienteService.toggleActivo(id);
            String estado = estabaActivo ? "desactivado" : "activado";
            redirect.addFlashAttribute("mensaje", "Cliente " + estado + " correctamente");
        } else {
            redirect.addFlashAttribute("error", "Cliente no encontrado");
        }
        return "redirect:/clientes";
    }
}