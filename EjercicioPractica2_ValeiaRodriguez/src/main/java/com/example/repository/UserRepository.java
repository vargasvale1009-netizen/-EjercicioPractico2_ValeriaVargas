package com.example.repository;

import com.example.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    // --- Consulta 6: buscar usuarios por nombre de rol asignado — @Query JPQL ---
    @Query("SELECT u FROM User u WHERE u.role.nombre = :nombreRol")
    List<User> buscarPorNombreRol(@Param("nombreRol") String nombreRol);

    // --- Consulta derivada: buscar por nombre parcial ---
    List<User> findByNombreContainingIgnoreCase(String nombre);
}
