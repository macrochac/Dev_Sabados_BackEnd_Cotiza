package com.devsenior.cotiza.cliente;

public class ClienteNoEncontradoExcepcion extends RuntimeException {

    public ClienteNoEncontradoExcepcion(String mensaje) {
        super(mensaje);
    }
}
