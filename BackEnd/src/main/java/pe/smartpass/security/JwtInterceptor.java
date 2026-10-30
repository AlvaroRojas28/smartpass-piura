package pe.smartpass.security;

import java.io.IOException;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/** Valida el token JWT y restringe cada grupo de rutas al rol que le corresponde. */
@Component
public class JwtInterceptor implements HandlerInterceptor {

    private final JwtUtil jwtUtil;

    public JwtInterceptor(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws IOException {
        if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
            return true;
        }
        String cabecera = request.getHeader("Authorization");
        if (cabecera == null || !cabecera.startsWith("Bearer ")) {
            return rechazar(response, 401, "Debe iniciar sesión");
        }
        Claims claims;
        try {
            claims = jwtUtil.validar(cabecera.substring(7));
        } catch (Exception e) {
            return rechazar(response, 401, "Sesión inválida o vencida");
        }
        String rol = claims.get("rol", String.class);
        String ruta = request.getRequestURI();
        String requerido = null;
        if (ruta.startsWith("/api/pasajero")) {
            requerido = "PASAJERO";
        } else if (ruta.startsWith("/api/pagos")) {
            requerido = "COBRADOR";
        } else if (ruta.startsWith("/api/admin")) {
            requerido = "ADMINISTRADOR";
        }
        if (requerido != null && !requerido.equals(rol)) {
            return rechazar(response, 403, "No tiene permisos para esta operación");
        }
        request.setAttribute("idActor", Integer.parseInt(claims.getSubject()));
        request.setAttribute("rol", rol);
        return true;
    }

    private boolean rechazar(HttpServletResponse response, int estado, String mensaje) throws IOException {
        response.setStatus(estado);
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write("{\"error\":\"" + mensaje + "\"}");
        return false;
    }
}
