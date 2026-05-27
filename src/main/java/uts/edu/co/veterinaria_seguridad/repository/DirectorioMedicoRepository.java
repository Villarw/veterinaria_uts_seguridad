package uts.edu.co.veterinaria_seguridad.repository;

import uts.edu.co.veterinaria_seguridad.model.DirectorioMedico;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface DirectorioMedicoRepository extends JpaRepository<DirectorioMedico, Integer> {
    Optional<DirectorioMedico> findByUsuarioUsername(String username);
}