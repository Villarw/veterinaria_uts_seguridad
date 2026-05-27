package uts.edu.co.veterinaria_seguridad;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class GeneradorHash {
    public static void main(String[] args) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        System.out.println("Hash para cliente123: " + encoder.encode("cliente123"));
    }
}