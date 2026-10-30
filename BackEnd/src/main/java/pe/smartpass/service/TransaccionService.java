package pe.smartpass.service;

import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import pe.smartpass.dao.TransaccionDAO;
import pe.smartpass.dao.UsuarioDAO;
import pe.smartpass.model.Transaccion;

@Service
public class TransaccionService {

    private final TransaccionDAO transaccionDAO;
    private final UsuarioDAO usuarioDAO;

    public TransaccionService(TransaccionDAO transaccionDAO, UsuarioDAO usuarioDAO) {
        this.transaccionDAO = transaccionDAO;
        this.usuarioDAO = usuarioDAO;
    }

    /** RF0007: registra la transaccion (la fecha y la hora las asigna la base de datos). */
    public Transaccion registrarTransaccion(Transaccion transaccion) {
        return transaccionDAO.guardarTransaccion(transaccion);
    }

    /** RF0008: historial de viajes y saldo disponible del pasajero. */
    public Map<String, Object> listarHistorial(int idUsuario) {
        List<Map<String, Object>> viajes = transaccionDAO.listarPorUsuario(idUsuario);
        BigDecimal saldo = usuarioDAO.obtenerSaldoUsuario(idUsuario);
        Map<String, Object> respuesta = new LinkedHashMap<>();
        respuesta.put("saldoActual", saldo);
        respuesta.put("transacciones", viajes);
        return respuesta;
    }
}
