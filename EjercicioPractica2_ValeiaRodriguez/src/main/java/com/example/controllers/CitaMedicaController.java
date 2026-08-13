package com.example.controllers;

import com.example.domain.CitaMedica;
import com.example.service.CitaMedicaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDateTime;

@Controller
@RequestMapping("/citas")
public class CitaMedicaController {

    @Autowired
    private CitaMedicaService citaMedicaService;

    // Listar todas las citas
    @GetMapping
    public String listar(Model model) {
        model.addAttribute("citas", citaMedicaService.listarTodas());
        return "citas/list";
    }

    // Ver detalle de una cita
    @GetMapping("/{id}")
    public String detalle(@PathVariable Long id, Model model) {
        CitaMedica cita = citaMedicaService.buscarPorId(id)
                .orElseThrow(() -> new IllegalArgumentException("Cita no encontrada: " + id));
        model.addAttribute("cita", cita);
        return "citas/detail";
    }

    // Mostrar formulario de creación
    @GetMapping("/nueva")
    public String mostrarFormularioCrear(Model model) {
        model.addAttribute("cita", new CitaMedica());
        return "citas/create";
    }

    // Guardar nueva cita
    @PostMapping("/nueva")
    public String crearCita(@ModelAttribute CitaMedica cita,
                            @RequestParam("fechaCita")
                            @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm") LocalDateTime fechaCita,
                            RedirectAttributes redirectAttributes) {
        cita.setFechaCita(fechaCita);
        citaMedicaService.guardar(cita);
        redirectAttributes.addFlashAttribute("exito", "Cita creada exitosamente.");
        return "redirect:/citas";
    }

    // Mostrar formulario de edición
    @GetMapping("/{id}/editar")
    public String mostrarFormularioEditar(@PathVariable Long id, Model model) {
        CitaMedica cita = citaMedicaService.buscarPorId(id)
                .orElseThrow(() -> new IllegalArgumentException("Cita no encontrada: " + id));
        model.addAttribute("cita", cita);
        return "citas/edit";
    }

    // Actualizar cita existente
    @PostMapping("/{id}/editar")
    public String actualizarCita(@PathVariable Long id,
                                 @ModelAttribute CitaMedica cita,
                                 @RequestParam("fechaCita")
                                 @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm") LocalDateTime fechaCita,
                                 RedirectAttributes redirectAttributes) {
        cita.setId(id);
        cita.setFechaCita(fechaCita);
        citaMedicaService.actualizar(cita);
        redirectAttributes.addFlashAttribute("exito", "Cita actualizada exitosamente.");
        return "redirect:/citas";
    }

    // Cambiar estado activa/inactiva
    @PostMapping("/{id}/estado")
    public String cambiarEstado(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        citaMedicaService.cambiarEstado(id);
        redirectAttributes.addFlashAttribute("exito", "Estado de la cita actualizado.");
        return "redirect:/citas";
    }

    // Eliminar cita
    @PostMapping("/{id}/eliminar")
    public String eliminarCita(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        citaMedicaService.eliminar(id);
        redirectAttributes.addFlashAttribute("exito", "Cita eliminada exitosamente.");
        return "redirect:/citas";
    }
}
