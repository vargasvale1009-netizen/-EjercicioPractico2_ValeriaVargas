package com.example.service;

import com.example.domain.CitaMedica;
import com.example.repository.CitaMedicaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class CitaMedicaService {

    @Autowired
    private CitaMedicaRepository citaMedicaRepository;

    // ── CRUD base ──────────────────────────────────────────────────────────────

    public List<CitaMedica> listarTodas() {
        return citaMedicaRepository.findAll();
    }

    public List<CitaMedica> listarActivas() {
        return citaMedicaRepository.findByActiva(true);
    }

    public List<CitaMedica> listarInactivas() {
        return citaMedicaRepository.findByActiva(false);
    }

    public Optional<CitaMedica> buscarPorId(Long id) {
        return citaMedicaRepository.findById(id);
    }

    public CitaMedica guardar(CitaMedica cita) {
        return citaMedicaRepository.save(cita);
    }

    public CitaMedica actualizar(CitaMedica cita) {
        return citaMedicaRepository.save(cita);
    }

    public void eliminar(Long id) {
        citaMedicaRepository.deleteById(id);
    }

    public void cambiarEstado(Long id) {
        citaMedicaRepository.findById(id).ifPresent(cita -> {
            cita.setActiva(!cita.getActiva());
            citaMedicaRepository.save(cita);
        });
    }

    // ── Consultas avanzadas ────────────────────────────────────────────────────

    /** Consulta 1 – por estado (activa/inactiva) */
    public List<CitaMedica> buscarPorEstado(Boolean activa) {
        return citaMedicaRepository.findByActiva(activa);
    }

    /** Consulta 2 – rango de fechas */
    public List<CitaMedica> buscarPorRangoFechas(LocalDateTime inicio, LocalDateTime fin) {
        return citaMedicaRepository.buscarPorRangoFechas(inicio, fin);
    }

    /** Consulta 3 – coincidencia parcial en especialidad */
    public List<CitaMedica> buscarPorEspecialidad(String especialidad) {
        return citaMedicaRepository.findByEspecialidadContainingIgnoreCase(especialidad);
    }

    /** Consulta 4 – contar citas activas */
    public Long contarCitasActivas() {
        return citaMedicaRepository.contarCitasActivas();
    }

    /** Consulta 5 – citas con costo <= monto */
    public List<CitaMedica> buscarPorCostoMaximo(BigDecimal monto) {
        return citaMedicaRepository.buscarPorCostoMaximo(monto);
    }
}
