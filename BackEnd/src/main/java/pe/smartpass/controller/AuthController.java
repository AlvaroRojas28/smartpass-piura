package pe.smartpass.controller;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import pe.smartpass.model.Empleado;
import pe.smartpass.model.Usuario;
import pe.smartpass.security.JwtUtil;
import pe.smartpass.service.EmpleadoService;
import pe.smartpass.service.UsuarioService;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UsuarioService usuarioService;
    private final EmpleadoService empleadoService;
    private final JwtUtil jwtUtil;

    public AuthController(UsuarioService usuarioService, EmpleadoService empleadoService, JwtUtil jwtUtil) {
        this.usuarioService = usuarioService;
        this.empleadoService = empleadoService;
        this.jwtUtil = jwtUtil;
    }

    /** RF0001: registro de pasajeros. */
    @PostMapping("/registro")
    public ResponseEntity<Map<String, Object>> registrar(@RequestBody Usuario usuario) {
        Usuario creado = usuarioService.registrarUsuario(usuario);
        Map<String, Object> cuerpo = new LinkedHashMap<>();
        cuerpo.put("idUsuario", creado.getIdUsuario());
        cuerpo.put("mensaje", "Registro exitoso");
        return ResponseEntity.status(HttpStatus.CREATED).body(cuerpo);
    }

    @PostMapping("/login")
    public Map<String, Object> loginPasajero(@RequestBody Map<String, String> datos) {
        Usuario u = usuarioService.autenticar(datos.get("dni"), datos.get("clave"));
        String nombre = u.getNomUsuario() + " " + u.getApePaterno();
        Map<String, Object> cuerpo = new LinkedHashMap<>();
        cuerpo.put("token", jwtUtil.generar(u.getIdUsuario(), "PASAJERO", nombre));
        cuerpo.put("rol", "PASAJERO");
        cuerpo.put("nombre", nombre);
        return cuerpo;
    }

    @PostMapping("/login-personal")
    public Map<String, Object> loginPersonal(@RequestBody Map<String, String> datos) {
        Empleado e = empleadoService.autenticar(datos.get("dni"), datos.get("clave"));
        String rol = e.getNomPerfil().toUpperCase();
        String nombre = e.getNom() + " " + e.getApePaterno();
        Map<String, Object> cuerpo = new LinkedHashMap<>();
        cuerpo.put("token", jwtUtil.generar(e.getIdEmpleado(), rol, nombre));
        cuerpo.put("rol", rol);
        cuerpo.put("nombre", nombre);
        return cuerpo;
    }
}
