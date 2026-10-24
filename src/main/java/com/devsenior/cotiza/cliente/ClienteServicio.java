package com.devsenior.cotiza.cliente;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devsenior.cotiza.cliente.dto.ClienteRequest;
import com.devsenior.cotiza.cliente.dto.ClienteResponse;

@Service
public class ClienteServicio {

    private final ClienteRepositorio clienteRepositorio;

    public ClienteServicio(ClienteRepositorio clienteRepositorio) {
        this.clienteRepositorio = clienteRepositorio;
    }

    @Transactional
    public ClienteResponse crearCliente(ClienteRequest clienteRequest) {
        String telefono = clienteRequest.telefono().trim();
        String nombre = clienteRequest.nombre().trim();
        String correo = clienteRequest.correo() == null ? null : clienteRequest.correo().trim();

        if (clienteRepositorio.existsByTelefono(telefono)) {
            throw new ClienteTelefonoDuplicadoExcepcion("El teléfono ya está registrado");
        }

        Cliente cliente = new Cliente();
        cliente.setNombre(nombre);
        cliente.setTelefono(telefono);
        cliente.setCorreo(correo.isBlank() ? null : correo);

        Cliente clienteGuardado = clienteRepositorio.save(cliente);
        return convertirARespuesta(clienteGuardado);
    }

    @Transactional(readOnly = true)
    public List<ClienteResponse> obtenerClientes() {
        return clienteRepositorio.findAll().stream()
                .map(this::convertirARespuesta)
                .toList();
    }

    @Transactional(readOnly = true)
    public ClienteResponse obtenerClientePorId(Long id) {
        Cliente cliente = clienteRepositorio.findById(id)
                .orElseThrow(() -> new ClienteNoEncontradoExcepcion("Cliente no encontrado"));
        return convertirARespuesta(cliente);
    }

    private ClienteResponse convertirARespuesta(Cliente cliente) {
        return new ClienteResponse(
                cliente.getId(),
                cliente.getNombre(),
                cliente.getTelefono(),
                cliente.getCorreo(),
                cliente.getFechaCreacion()
        );
    }
}
