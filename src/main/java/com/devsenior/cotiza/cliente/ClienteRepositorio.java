package com.devsenior.cotiza.cliente;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepositorio extends JpaRepository<Cliente, Long> {

    boolean existsByTelefono(String telefono);
}
