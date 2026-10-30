package pe.smartpass.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import pe.smartpass.dao.EmpleadoDAO;
import pe.smartpass.exception.CredencialesInvalidasException;
import pe.smartpass.model.Empleado;

@Service
public class EmpleadoService {

    private final EmpleadoDAO empleadoDAO;
    private final PasswordEncoder passwordEncoder;

    public EmpleadoService(EmpleadoDAO empleadoDAO, PasswordEncoder passwordEncoder) {
        this.empleadoDAO = empleadoDAO;
        this.passwordEncoder = passwordEncoder;
    }

    public Empleado autenticar(String dni, String clave) {
        Empleado e = dni == null ? null : empleadoDAO.buscarPorDni(dni);
        if (e == null || e.getEstado() != 1 || clave == null || !passwordEncoder.matches(clave, e.getClave())) {
            throw new CredencialesInvalidasException("DNI o clave incorrectos");
        }
        return e;
    }
}
