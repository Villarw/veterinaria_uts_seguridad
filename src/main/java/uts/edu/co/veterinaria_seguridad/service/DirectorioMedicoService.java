package uts.edu.co.veterinaria_seguridad.service;

import uts.edu.co.veterinaria_seguridad.model.DirectorioMedico;
import uts.edu.co.veterinaria_seguridad.repository.DirectorioMedicoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class DirectorioMedicoService {

    private final DirectorioMedicoRepository repository;

    public DirectorioMedicoService(DirectorioMedicoRepository repository) {
        this.repository = repository;
    }

    public List<DirectorioMedico> listarTodos() {
        return repository.findAll();
    }

    public Optional<DirectorioMedico> buscarPorId(Integer id) {
        return repository.findById(id);
    }

    public Optional<DirectorioMedico> buscarPorUsername(String username) {
        return repository.findByUsuarioUsername(username);
    }

    @Transactional
    public DirectorioMedico guardar(DirectorioMedico medico) {
        return repository.save(medico);
    }

    @Transactional
    public void eliminar(Integer id) {
        repository.deleteById(id);
    }
}