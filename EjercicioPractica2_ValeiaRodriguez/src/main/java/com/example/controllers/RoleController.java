package com.example.controllers;

import com.example.domain.Role;
import com.example.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/roles")
public class RoleController {

    @Autowired
    private RoleService roleService;

    // Listar todos los roles
    @GetMapping
    public String listar(Model model) {
        model.addAttribute("roles", roleService.listarTodos());
        return "roles/list";
    }

    // Ver detalle de un rol
    @GetMapping("/{id}")
    public String detalle(@PathVariable Long id, Model model) {
        Role role = roleService.buscarPorId(id)
                .orElseThrow(() -> new IllegalArgumentException("Rol no encontrado: " + id));
        model.addAttribute("role", role);
        return "roles/detail";
    }

    // Mostrar formulario de creación
    @GetMapping("/nuevo")
    public String mostrarFormularioCrear(Model model) {
        model.addAttribute("role", new Role());
        return "roles/create";
    }

    // Guardar nuevo rol
    @PostMapping("/nuevo")
    public String crearRol(@ModelAttribute Role role, RedirectAttributes redirectAttributes) {
        if (roleService.existePorNombre(role.getNombre())) {
            redirectAttributes.addFlashAttribute("error", "Ya existe un rol con el nombre: " + role.getNombre());
            return "redirect:/roles/nuevo";
        }
        roleService.guardar(role);
        redirectAttributes.addFlashAttribute("exito", "Rol creado exitosamente.");
        return "redirect:/roles";
    }

    // Mostrar formulario de edición
    @GetMapping("/{id}/editar")
    public String mostrarFormularioEditar(@PathVariable Long id, Model model) {
        Role role = roleService.buscarPorId(id)
                .orElseThrow(() -> new IllegalArgumentException("Rol no encontrado: " + id));
        model.addAttribute("role", role);
        return "roles/edit";
    }

    // Actualizar rol existente
    @PostMapping("/{id}/editar")
    public String actualizarRol(@PathVariable Long id, @ModelAttribute Role role,
                                RedirectAttributes redirectAttributes) {
        role.setId(id);
        roleService.actualizar(role);
        redirectAttributes.addFlashAttribute("exito", "Rol actualizado exitosamente.");
        return "redirect:/roles";
    }

    // Eliminar rol
    @PostMapping("/{id}/eliminar")
    public String eliminarRol(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        roleService.eliminar(id);
        redirectAttributes.addFlashAttribute("exito", "Rol eliminado exitosamente.");
        return "redirect:/roles";
    }
}
