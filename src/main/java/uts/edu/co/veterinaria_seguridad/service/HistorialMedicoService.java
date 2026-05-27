package uts.edu.co.veterinaria_seguridad.service;

import uts.edu.co.veterinaria_seguridad.model.HistorialMedico;
import uts.edu.co.veterinaria_seguridad.repository.HistorialMedicoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class HistorialMedicoService {

    private final HistorialMedicoRepository historialRepository;

    public HistorialMedicoService(HistorialMedicoRepository historialRepository) {
        this.historialRepository = historialRepository;
    }

    public List<HistorialMedico> listarPorMascota(Integer idMascota) {
        return historialRepository.findByMascotaIdMascota(idMascota);
    }

    public Optional<HistorialMedico> buscarPorId(Integer id) {
        return historialRepository.findById(id);
    }

    @Transactional
    public HistorialMedico guardar(HistorialMedico historial) {
        return historialRepository.save(historial);
    }

    @Transactional
    public void eliminar(Integer id) {
        historialRepository.deleteById(id);
    }
}