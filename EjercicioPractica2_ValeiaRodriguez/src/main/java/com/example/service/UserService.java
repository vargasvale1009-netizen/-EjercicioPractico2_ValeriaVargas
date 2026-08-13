package com.example.service;

import com.example.domain.User;
import com.example.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EmailService emailService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public List<User> listarTodos() {
        return userRepository.findAll();
    }

    public Optional<User> buscarPorId(Long id) {
        return userRepository.findById(id);
    }

    public User guardar(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        User guardado = userRepository.save(user);
        emailService.enviarCorreoBienvenida(guardado.getEmail(), guardado.getNombre());
        return guardado;
    }

    public User actualizar(User user) {
        // Si el password llega vacío, conservar el existente
        if (user.getPassword() == null || user.getPassword().isEmpty()) {
            userRepository.findById(user.getId())
                    .ifPresent(u -> user.setPassword(u.getPassword()));
        } else {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        }
        return userRepository.save(user);
    }

    public void eliminar(Long id) {
        userRepository.deleteById(id);
    }
}
