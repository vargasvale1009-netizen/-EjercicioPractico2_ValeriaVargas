package com.example.service;

import com.example.domain.Role;
import com.example.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RoleService {

    @Autowired
    private RoleRepository roleRepository;

    public List<Role> listarTodos() {
        return roleRepository.findAll();
    }

    public Optional<Role> buscarPorId(Long id) {
        return roleRepository.findById(id);
    }

    public Role guardar(Role role) {
        return roleRepository.save(role);
    }

    public Role actualizar(Role role) {
        return roleRepository.save(role);
    }

    public void eliminar(Long id) {
        roleRepository.deleteById(id);
    }

    public boolean existePorNombre(String nombre) {
        return roleRepository.existsByNombre(nombre);
    }
}
