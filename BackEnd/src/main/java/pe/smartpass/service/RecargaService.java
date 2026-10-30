package pe.smartpass.service;

import java.math.BigDecimal;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pe.smartpass.dao.RecargaDAO;
import pe.smartpass.dao.UsuarioDAO;
import pe.smartpass.model.Recarga;

@Service
public class RecargaService {

    private final RecargaDAO recargaDAO;
    private final UsuarioDAO usuarioDAO;

    public RecargaService(RecargaDAO recargaDAO, UsuarioDAO usuarioDAO) {
        this.recargaDAO = recargaDAO;
        this.usuarioDAO = usuarioDAO;
    }

    /** RF0002: registra la recarga simulada y actualiza el saldo en una sola transaccion. */
    @Transactional
    public void procesarRecarga(int idUsuario, BigDecimal monto, String metodoSimulado) {
        if (monto == null || monto.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("El monto debe ser mayor a cero");
        }
        Recarga recarga = new Recarga(idUsuario, monto, metodoSimulado);
        recargaDAO.guardarRecarga(recarga);
        usuarioDAO.actualizarSaldoUsuario(idUsuario, monto);
    }
}
