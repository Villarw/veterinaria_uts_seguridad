package uts.edu.co.veterinaria_seguridad.service;

import uts.edu.co.veterinaria_seguridad.model.Cliente;
import uts.edu.co.veterinaria_seguridad.repository.ClienteRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ClienteService {

    private final ClienteRepository clienteRepository;

    public ClienteService(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    public List<Cliente> listarTodos() {
        return clienteRepository.findAll();
    }

    public List<Cliente> listarActivos() {
        return clienteRepository.findByActivoTrue();
    }

    public Optional<Cliente> buscarPorId(Integer id) {
        return clienteRepository.findById(id);
    }

    public Optional<Cliente> buscarPorUsername(String username) {
        return clienteRepository.findByUsuarioUsername(username);
    }

    public List<Cliente> listarPorMedico(Integer idMedico) {
        return clienteRepository.findDistinctByMedicoIdAndActivoTrue(idMedico);
    }

    @Transactional
    public Cliente guardar(Cliente cliente) {
        return clienteRepository.save(cliente);
    }

    @Transactional
    public void toggleActivo(Integer id) {
        Optional<Cliente> opt = clienteRepository.findById(id);
        if (opt.isPresent()) {
            Cliente c = opt.get();
            c.setActivo(!c.getActivo());
            clienteRepository.save(c);
        }
    }
}