package uts.edu.co.veterinaria_seguridad.service;

import uts.edu.co.veterinaria_seguridad.model.Cita;
import uts.edu.co.veterinaria_seguridad.repository.CitaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class CitaService {

    private final CitaRepository citaRepository;

    public CitaService(CitaRepository citaRepository) {
        this.citaRepository = citaRepository;
    }

    public List<Cita> listarTodas() {
        return citaRepository.findAll();
    }

    public Optional<Cita> buscarPorId(Integer id) {
        return citaRepository.findById(id);
    }

    @Transactional
    public Cita guardar(Cita cita) {
        return citaRepository.save(cita);
    }

    @Transactional
    public void eliminar(Integer id) {
        citaRepository.deleteById(id);
    }

    public List<Cita> listarPorCliente(Integer idCliente) {
        return citaRepository.findByClienteIdAndActivoTrue(idCliente);
    }

    public List<Cita> listarPorMedico(Integer idMedico) {
        return citaRepository.findByMedicoIdMedicoAndClienteActivo(idMedico);
    }
}