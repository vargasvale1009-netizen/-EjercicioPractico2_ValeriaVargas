package com.example.controllers;

import com.example.domain.User;
import com.example.repository.RoleRepository;
import com.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/usuarios")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleRepository roleRepository;

    // Listar todos los usuarios
    @GetMapping
    public String listar(Model model) {
        model.addAttribute("usuarios", userService.listarTodos());
        return "usuarios/list";
    }

    // Ver detalle de un usuario
    @GetMapping("/{id}")
    public String detalle(@PathVariable Long id, Model model) {
        User user = userService.buscarPorId(id)
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado: " + id));
        model.addAttribute("usuario", user);
        return "usuarios/detail";
    }

    // Mostrar formulario de creación
    @GetMapping("/nuevo")
    public String mostrarFormularioCrear(Model model) {
        model.addAttribute("usuario", new User());
        model.addAttribute("roles", roleRepository.findAll());
        return "usuarios/create";
    }

    // Guardar nuevo usuario
    @PostMapping("/nuevo")
    public String crearUsuario(@ModelAttribute User usuario) {
        userService.guardar(usuario);
        return "redirect:/usuarios";
    }

    // Mostrar formulario de edición
    @GetMapping("/{id}/editar")
    public String mostrarFormularioEditar(@PathVariable Long id, Model model) {
        User user = userService.buscarPorId(id)
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado: " + id));
        model.addAttribute("usuario", user);
        model.addAttribute("roles", roleRepository.findAll());
        return "usuarios/edit";
    }

    // Actualizar usuario existente
    @PostMapping("/{id}/editar")
    public String actualizarUsuario(@PathVariable Long id, @ModelAttribute User usuario) {
        usuario.setId(id);
        userService.actualizar(usuario);
        return "redirect:/usuarios";
    }

    // Eliminar usuario
    @PostMapping("/{id}/eliminar")
    public String eliminarUsuario(@PathVariable Long id) {
        userService.eliminar(id);
        return "redirect:/usuarios";
    }
}
