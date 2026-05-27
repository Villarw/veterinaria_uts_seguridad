package uts.edu.co.veterinaria_seguridad.repository;

import uts.edu.co.veterinaria_seguridad.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;
import java.util.Optional;

public interface ClienteRepository extends JpaRepository<Cliente, Integer> {
    Optional<Cliente> findByUsuarioUsername(String username);

    @Query("SELECT DISTINCT cl FROM Cliente cl JOIN cl.mascotas m JOIN m.citas c WHERE c.medico.idMedico = :idMedico")
    List<Cliente> findDistinctByMedicoId(@Param("idMedico") Integer idMedico);

    List<Cliente> findByActivoTrue();

    @Query("SELECT DISTINCT cl FROM Cliente cl JOIN cl.mascotas m JOIN m.citas c WHERE c.medico.idMedico = :idMedico AND cl.activo = true")
    List<Cliente> findDistinctByMedicoIdAndActivoTrue(@Param("idMedico") Integer idMedico);
}