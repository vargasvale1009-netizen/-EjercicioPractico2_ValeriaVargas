package com.example.repository;

import com.example.domain.CitaMedica;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface CitaMedicaRepository extends JpaRepository<CitaMedica, Long> {

    // --- Consulta 1: por estado (activa/inactiva) — derivada ---
    List<CitaMedica> findByActiva(Boolean activa);

    // --- Consulta 2: dentro de un rango de fechas — @Query JPQL ---
    @Query("SELECT c FROM CitaMedica c WHERE c.fechaCita BETWEEN :inicio AND :fin ORDER BY c.fechaCita ASC")
    List<CitaMedica> buscarPorRangoFechas(@Param("inicio") LocalDateTime inicio,
                                          @Param("fin")    LocalDateTime fin);

    // --- Consulta 3: coincidencia parcial en especialidad — derivada ---
    List<CitaMedica> findByEspecialidadContainingIgnoreCase(String especialidad);

    // --- Consulta 4: contar citas activas — @Query JPQL ---
    @Query("SELECT COUNT(c) FROM CitaMedica c WHERE c.activa = true")
    Long contarCitasActivas();

    // --- Consulta 5: citas con costo menor o igual a un monto — @Query JPQL ---
    @Query("SELECT c FROM CitaMedica c WHERE c.costo <= :monto ORDER BY c.costo ASC")
    List<CitaMedica> buscarPorCostoMaximo(@Param("monto") BigDecimal monto);

    // Mantener consultas existentes
    List<CitaMedica> findByNombrePacienteContainingIgnoreCase(String nombrePaciente);
}
