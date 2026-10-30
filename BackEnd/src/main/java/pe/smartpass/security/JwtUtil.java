package pe.smartpass.security;

import java.nio.charset.StandardCharsets;
import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtil {

    private final SecretKey clave;
    private final long expiracionMs;

    public JwtUtil(@Value("${smartpass.jwt.secret}") String secreto,
                   @Value("${smartpass.jwt.expiracion-minutos}") long minutos) {
        this.clave = Keys.hmacShaKeyFor(secreto.getBytes(StandardCharsets.UTF_8));
        this.expiracionMs = minutos * 60_000L;
    }

    public String generar(int idActor, String rol, String nombre) {
        long ahora = System.currentTimeMillis();
        return Jwts.builder()
                .subject(String.valueOf(idActor))
                .claim("rol", rol)
                .claim("nombre", nombre)
                .issuedAt(new Date(ahora))
                .expiration(new Date(ahora + expiracionMs))
                .signWith(clave)
                .compact();
    }

    /** Devuelve los claims si el token es valido; lanza excepcion si esta vencido o alterado. */
    public Claims validar(String token) {
        return Jwts.parser().verifyWith(clave).build().parseSignedClaims(token).getPayload();
    }
}
