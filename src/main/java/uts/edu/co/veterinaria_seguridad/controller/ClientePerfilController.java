package uts.edu.co.veterinaria_seguridad.controller;

import uts.edu.co.veterinaria_seguridad.model.*;
import uts.edu.co.veterinaria_seguridad.service.*;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;

@Controller
@RequestMapping("/cliente")
public class ClientePerfilController {

    private final ClienteService clienteService;
    private final MascotaService mascotaService;
    private final CitaService citaService;
    private final HistorialMedicoService historialService;
    private final DirectorioMedicoService medicoService;

    public ClientePerfilController(ClienteService clienteService, MascotaService mascotaService,
                                   CitaService citaService, HistorialMedicoService historialService,
                                   DirectorioMedicoService medicoService) {
        this.clienteService = clienteService;
        this.mascotaService = mascotaService;
        this.citaService = citaService;
        this.historialService = historialService;
        this.medicoService = medicoService;
    }

    private Cliente getClienteActivo(Authentication auth) {
        String username = auth.getName();
        Optional<Cliente> clienteOpt = clienteService.buscarPorUsername(username);
        if (clienteOpt.isEmpty() || !clienteOpt.get().getActivo()) {
            throw new RuntimeException("Acceso denegado");
        }
        return clienteOpt.get();
    }

    @GetMapping("/dashboard")
    public String dashboard(Authentication auth, Model model) {
        Cliente cliente = getClienteActivo(auth);
        model.addAttribute("cliente", cliente);
        return "cliente/dashboard";
    }

    @GetMapping("/mascotas")
    public String misMascotas(Authentication auth, Model model) {
        Cliente cliente = getClienteActivo(auth);
        model.addAttribute("mascotas", mascotaService.listarPorCliente(cliente.getIdCliente()));
        return "cliente/mascotas/lista";
    }

    @GetMapping("/mascotas/nueva")
    public String nuevaMascota(Model model) {
        model.addAttribute("mascota", new Mascota());
        return "cliente/mascotas/form";
    }

    @PostMapping("/mascotas")
    public String guardarMascota(@ModelAttribute Mascota mascota, Authentication auth) {
        Cliente cliente = getClienteActivo(auth);
        mascota.setCliente(cliente);
        mascotaService.guardar(mascota);
        return "redirect:/cliente/mascotas";
    }

    @GetMapping("/citas")
    public String misCitas(Authentication auth, Model model) {
        Cliente cliente = getClienteActivo(auth);
        model.addAttribute("citas", citaService.listarPorCliente(cliente.getIdCliente()));
        return "cliente/citas/lista";
    }

    @GetMapping("/citas/nueva")
    public String nuevaCita(Model model, Authentication auth) {
        Cliente cliente = getClienteActivo(auth);
        model.addAttribute("mascotas", mascotaService.listarPorCliente(cliente.getIdCliente()));
        model.addAttribute("medicos", medicoService.listarTodos());
        model.addAttribute("cita", new Cita());
        return "cliente/citas/form";
    }

    @PostMapping("/citas")
    public String guardarCita(@ModelAttribute Cita cita, Authentication auth) {
        getClienteActivo(auth);
        citaService.guardar(cita);
        return "redirect:/cliente/citas";
    }

    @GetMapping("/historial/{idMascota}")
    public String verHistorial(@PathVariable Integer idMascota, Authentication auth, Model model) {
        Cliente cliente = getClienteActivo(auth);
        Optional<Mascota> mascotaOpt = mascotaService.buscarPorId(idMascota);
        if (mascotaOpt.isEmpty() || !mascotaOpt.get().getCliente().getIdCliente().equals(cliente.getIdCliente())) {
            return "redirect:/cliente/mascotas";
        }
        model.addAttribute("mascota", mascotaOpt.get());
        model.addAttribute("historiales", historialService.listarPorMascota(idMascota));
        return "cliente/historial/lista";
    }
}