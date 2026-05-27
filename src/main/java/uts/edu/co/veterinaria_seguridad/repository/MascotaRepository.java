package uts.edu.co.veterinaria_seguridad.repository;

import uts.edu.co.veterinaria_seguridad.model.Mascota;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface MascotaRepository extends JpaRepository<Mascota, Integer> {
    List<Mascota> findByClienteIdCliente(Integer idCliente);

    @Query("SELECT DISTINCT m FROM Mascota m JOIN m.citas c WHERE c.medico.idMedico = :idMedico AND m.cliente.activo = true")
    List<Mascota> findDistinctByMedicoId(@Param("idMedico") Integer idMedico);
}