package com.devsenior.cotiza.cliente;

import java.util.Map;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.devsenior.cotiza.cliente.dto.ClienteRequest;
import com.devsenior.cotiza.cliente.dto.ClienteResponse;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api")
public class ClienteControlador {

    private final ClienteServicio clienteServicio;

    public ClienteControlador(ClienteServicio clienteServicio) {
        this.clienteServicio = clienteServicio;
    }

    @PostMapping("/clientes")
    public ResponseEntity<ClienteResponse> crearCliente(@Valid @RequestBody ClienteRequest clienteRequest) {
        ClienteResponse clienteCreado = clienteServicio.crearCliente(clienteRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(clienteCreado);
    }

    @GetMapping("/clientes")
    public List<ClienteResponse> obtenerClientes() {
        return clienteServicio.obtenerClientes();
    }

    @GetMapping("/clientes/{id}")
    public ClienteResponse obtenerClientePorId(@PathVariable Long id) {
        return clienteServicio.obtenerClientePorId(id);
    }

    @ExceptionHandler(ClienteTelefonoDuplicadoExcepcion.class)
    public ResponseEntity<Map<String, String>> manejarTelefonoDuplicado(ClienteTelefonoDuplicadoExcepcion excepcion) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(Map.of("mensaje", excepcion.getMessage()));
    }

    @ExceptionHandler(ClienteNoEncontradoExcepcion.class)
    public ResponseEntity<Map<String, String>> manejarClienteNoEncontrado(ClienteNoEncontradoExcepcion excepcion) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Map.of("mensaje", excepcion.getMessage()));
    }
}
