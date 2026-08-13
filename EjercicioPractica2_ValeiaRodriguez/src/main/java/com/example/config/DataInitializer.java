package com.example.config;

import com.example.domain.Role;
import com.example.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public void run(String... args) throws Exception {
        crearRolSiNoExiste("ADMIN");
        crearRolSiNoExiste("MEDICO");
        crearRolSiNoExiste("PACIENTE");
    }

    private void crearRolSiNoExiste(String nombre) {
        if (!roleRepository.existsByNombre(nombre)) {
            roleRepository.save(new Role(nombre));
            System.out.println("Rol creado: " + nombre);
        }
    }
}
