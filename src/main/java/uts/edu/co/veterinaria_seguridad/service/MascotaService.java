package uts.edu.co.veterinaria_seguridad.service;

import uts.edu.co.veterinaria_seguridad.model.Mascota;
import uts.edu.co.veterinaria_seguridad.repository.MascotaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class MascotaService {

    private final MascotaRepository mascotaRepository;

    public MascotaService(MascotaRepository mascotaRepository) {
        this.mascotaRepository = mascotaRepository;
    }

    public List<Mascota> listarTodas() {
        return mascotaRepository.findAll();
    }

    public Optional<Mascota> buscarPorId(Integer id) {
        return mascotaRepository.findById(id);
    }

    @Transactional
    public Mascota guardar(Mascota mascota) {
        return mascotaRepository.save(mascota);
    }

    @Transactional
    public void eliminar(Integer id) {
        mascotaRepository.deleteById(id);
    }

    public List<Mascota> listarPorCliente(Integer idCliente) {
        return mascotaRepository.findByClienteIdCliente(idCliente);
    }

    public List<Mascota> listarPorMedico(Integer idMedico) {
        return mascotaRepository.findDistinctByMedicoId(idMedico);
    }
}