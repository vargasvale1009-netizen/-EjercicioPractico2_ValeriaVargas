package com.example.controllers;

import com.example.domain.CitaMedica;
import com.example.domain.User;
import com.example.repository.UserRepository;
import com.example.service.CitaMedicaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequestMapping("/consultas")
public class ConsultasController {

    @Autowired
    private CitaMedicaService citaMedicaService;

    @Autowired
    private UserRepository userRepository;

    /** Página principal de consultas — sin resultados aún */
    @GetMapping
    public String index(Model model) {
        model.addAttribute("totalActivas", citaMedicaService.contarCitasActivas());
        return "consultas/busqueda";
    }

    /** Consulta 1 – citas por estado */
    @GetMapping("/por-estado")
    public String porEstado(@RequestParam(defaultValue = "true") Boolean activa,
                            Model model) {
        List<CitaMedica> resultados = citaMedicaService.buscarPorEstado(activa);
        model.addAttribute("resultadosCitas", resultados);
        model.addAttribute("totalActivas",    citaMedicaService.contarCitasActivas());
        model.addAttribute("consultaActiva",  "estado");
        model.addAttribute("filtroEstado",    activa);
        return "consultas/busqueda";
    }

    /** Consulta 2 – citas en rango de fechas */
    @GetMapping("/por-fechas")
    public String porFechas(
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm") LocalDateTime inicio,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm") LocalDateTime fin,
            Model model) {
        List<CitaMedica> resultados = citaMedicaService.buscarPorRangoFechas(inicio, fin);
        model.addAttribute("resultadosCitas", resultados);
        model.addAttribute("totalActivas",    citaMedicaService.contarCitasActivas());
        model.addAttribute("consultaActiva",  "fechas");
        model.addAttribute("filtroInicio",    inicio);
        model.addAttribute("filtroFin",       fin);
        return "consultas/busqueda";
    }

    /** Consulta 3 – citas por coincidencia parcial en especialidad */
    @GetMapping("/por-especialidad")
    public String porEspecialidad(@RequestParam String especialidad, Model model) {
        List<CitaMedica> resultados = citaMedicaService.buscarPorEspecialidad(especialidad);
        model.addAttribute("resultadosCitas",     resultados);
        model.addAttribute("totalActivas",        citaMedicaService.contarCitasActivas());
        model.addAttribute("consultaActiva",      "especialidad");
        model.addAttribute("filtroEspecialidad",  especialidad);
        return "consultas/busqueda";
    }

    /** Consulta 4+5 – usuarios por rol + costo máximo */
    @GetMapping("/por-rol")
    public String porRol(@RequestParam String nombreRol, Model model) {
        List<User> usuarios = userRepository.buscarPorNombreRol(nombreRol);
        model.addAttribute("resultadosUsuarios", usuarios);
        model.addAttribute("totalActivas",       citaMedicaService.contarCitasActivas());
        model.addAttribute("consultaActiva",     "rol");
        model.addAttribute("filtroRol",          nombreRol);
        return "consultas/busqueda";
    }

    /** Consulta 5 – citas con costo <= monto */
    @GetMapping("/por-costo")
    public String porCosto(@RequestParam BigDecimal monto, Model model) {
        List<CitaMedica> resultados = citaMedicaService.buscarPorCostoMaximo(monto);
        model.addAttribute("resultadosCitas",  resultados);
        model.addAttribute("totalActivas",     citaMedicaService.contarCitasActivas());
        model.addAttribute("consultaActiva",   "costo");
        model.addAttribute("filtroCosto",      monto);
        return "consultas/busqueda";
    }
}
