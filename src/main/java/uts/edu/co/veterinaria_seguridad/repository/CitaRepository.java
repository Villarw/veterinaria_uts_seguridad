package uts.edu.co.veterinaria_seguridad.repository;

import uts.edu.co.veterinaria_seguridad.model.Cita;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface CitaRepository extends JpaRepository<Cita, Integer> {
    List<Cita> findByMascotaClienteIdCliente(Integer idCliente);

    @Query("SELECT c FROM Cita c WHERE c.medico.idMedico = :idMedico AND c.mascota.cliente.activo = true")
    List<Cita> findByMedicoIdMedicoAndClienteActivo(@Param("idMedico") Integer idMedico);

    @Query("SELECT c FROM Cita c WHERE c.mascota.cliente.idCliente = :idCliente AND c.mascota.cliente.activo = true")
    List<Cita> findByClienteIdAndActivoTrue(@Param("idCliente") Integer idCliente);
}