package uts.edu.co.veterinaria_seguridad.service;

import uts.edu.co.veterinaria_seguridad.model.Rol;
import uts.edu.co.veterinaria_seguridad.model.Usuario;
import uts.edu.co.veterinaria_seguridad.repository.RolRepository;
import uts.edu.co.veterinaria_seguridad.repository.UsuarioRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final RolRepository rolRepository;
    private final PasswordEncoder passwordEncoder;

    public UsuarioService(UsuarioRepository usuarioRepository,
                          RolRepository rolRepository,
                          PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.rolRepository = rolRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public Usuario crearUsuarioConRol(String username, String rawPassword, String nombreRol) {
        try {
            Rol rol = rolRepository.findByNombreRol(nombreRol)
                    .orElseThrow(() -> new RuntimeException("Rol no encontrado: " + nombreRol));

            Usuario usuario = new Usuario();
            usuario.setUsername(username);
            usuario.setPassword(passwordEncoder.encode(rawPassword));
            usuario.setRol(rol);
            return usuarioRepository.save(usuario);
        } catch (RuntimeException e) {
            System.err.println("ERROR AL CREAR USUARIO: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }

    public Optional<Usuario> buscarPorId(Integer id) {
        return usuarioRepository.findById(id);
    }

    public Optional<Usuario> buscarPorUsername(String username) {
        return usuarioRepository.findByUsername(username);
    }
}