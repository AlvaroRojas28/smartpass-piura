package pe.smartpass.service;

import java.math.BigDecimal;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import pe.smartpass.dao.UsuarioDAO;
import pe.smartpass.exception.CredencialesInvalidasException;
import pe.smartpass.model.Usuario;

@Service
public class UsuarioService {

    private final UsuarioDAO usuarioDAO;
    private final PasswordEncoder passwordEncoder;

    public UsuarioService(UsuarioDAO usuarioDAO, PasswordEncoder passwordEncoder) {
        this.usuarioDAO = usuarioDAO;
        this.passwordEncoder = passwordEncoder;
    }

    /** RF0001: registra un nuevo pasajero. */
    public Usuario registrarUsuario(Usuario usuario) {
        validarDatos(usuario);
        if (usuarioDAO.existeDNI(usuario.getDni())) {
            throw new IllegalStateException("El DNI ya se encuentra registrado");
        }
        usuario.setClave(passwordEncoder.encode(usuario.getClave()));
        usuario.setSaldoActual(BigDecimal.ZERO);
        usuario.setEstado(1);
        return usuarioDAO.guardarUsuario(usuario);
    }

    public Usuario autenticar(String dni, String clave) {
        Usuario u = dni == null ? null : usuarioDAO.buscarPorDni(dni);
        if (u == null || u.getEstado() != 1 || clave == null || !passwordEncoder.matches(clave, u.getClave())) {
            throw new CredencialesInvalidasException("DNI o clave incorrectos");
        }
        return u;
    }

    public BigDecimal consultarSaldo(int idUsuario) {
        return usuarioDAO.obtenerSaldoUsuario(idUsuario);
    }

    private void validarDatos(Usuario u) {
        if (u.getDni() == null || !u.getDni().matches("\\d{8}")) {
            throw new IllegalArgumentException("El DNI debe tener 8 dígitos");
        }
        if (u.getCelular() == null || !u.getCelular().matches("\\d{9}")) {
            throw new IllegalArgumentException("El celular debe tener 9 dígitos");
        }
        if (u.getNomUsuario() == null || u.getNomUsuario().isBlank()
                || u.getApePaterno() == null || u.getApePaterno().isBlank()
                || u.getApeMaterno() == null || u.getApeMaterno().isBlank()) {
            throw new IllegalArgumentException("Nombre y apellidos son obligatorios");
        }
        if (u.getClave() == null || u.getClave().length() < 6) {
            throw new IllegalArgumentException("La clave debe tener al menos 6 caracteres");
        }
    }
}
