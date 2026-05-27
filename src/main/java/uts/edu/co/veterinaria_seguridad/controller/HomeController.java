package uts.edu.co.veterinaria_seguridad.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/home")
    public String home(Authentication auth) {
        for (GrantedAuthority authority : auth.getAuthorities()) {
            String role = authority.getAuthority();
            if (role.equals("ROLE_ADMIN")) return "redirect:/admin/dashboard";
            else if (role.equals("ROLE_VET")) return "redirect:/vet/dashboard";
            else if (role.equals("ROLE_CLIENTE")) return "redirect:/cliente/dashboard";
        }
        return "login";
    }

    @GetMapping("/")
    public String root() { return "redirect:/home"; }

    @GetMapping("/admin/dashboard")
    public String adminDashboard() { return "admin/dashboard"; }

    @GetMapping("/vet/dashboard")
    public String vetDashboard() { return "vet/dashboard"; }
}