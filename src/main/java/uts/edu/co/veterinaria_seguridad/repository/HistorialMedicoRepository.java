package uts.edu.co.veterinaria_seguridad.repository;

import uts.edu.co.veterinaria_seguridad.model.HistorialMedico;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface HistorialMedicoRepository extends JpaRepository<HistorialMedico, Integer> {
    List<HistorialMedico> findByMascotaIdMascota(Integer idMascota);
}