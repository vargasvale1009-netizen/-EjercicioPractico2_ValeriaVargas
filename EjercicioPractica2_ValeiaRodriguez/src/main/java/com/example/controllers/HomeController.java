package com.example.controllers;

import com.example.service.CitaMedicaService;
import com.example.service.RoleService;
import com.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private CitaMedicaService citaMedicaService;

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("totalUsuarios", userService.listarTodos().size());
        model.addAttribute("totalRoles",    roleService.listarTodos().size());
        model.addAttribute("totalCitas",    citaMedicaService.listarTodas().size());
        model.addAttribute("citasActivas",  citaMedicaService.contarCitasActivas());
        return "index";
    }
}
