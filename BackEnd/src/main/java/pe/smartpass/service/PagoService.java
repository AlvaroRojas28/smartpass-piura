package pe.smartpass.service;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pe.smartpass.dao.UsuarioDAO;
import pe.smartpass.model.ResultadoPago;
import pe.smartpass.model.Transaccion;
import pe.smartpass.model.Usuario;

@Service
public class PagoService {

    private final UsuarioDAO usuarioDAO;
    private final TransaccionService transaccionService;
    private final BigDecimal tarifa;

    public PagoService(UsuarioDAO usuarioDAO, TransaccionService transaccionService,
                       @Value("${smartpass.tarifa-pasaje}") BigDecimal tarifa) {
        this.usuarioDAO = usuarioDAO;
        this.transaccionService = transaccionService;
        this.tarifa = tarifa;
    }

    /** RF0004, RF0005, RF0006 y RF0007: valida el QR, descuenta el pasaje y registra la transaccion. */
    @Transactional
    public ResultadoPago procesarPago(String codigoQR, int idEmpleado, int idUnidad) {
        Usuario usuario = (codigoQR == null || codigoQR.isBlank()) ? null : usuarioDAO.buscarPorCodigoQR(codigoQR);
        if (usuario == null || usuario.getEstado() != 1) {
            return ResultadoPago.rechazado("Código QR no válido");
        }
        if (!tieneSaldoSuficiente(usuario, tarifa)) {
            return ResultadoPago.rechazado("Saldo insuficiente");
        }
        if (!usuarioDAO.descontarSaldo(usuario.getIdUsuario(), tarifa)) {
            return ResultadoPago.rechazado("Saldo insuficiente");
        }
        Transaccion transaccion = new Transaccion(usuario.getIdUsuario(), idEmpleado, idUnidad, tarifa);
        transaccionService.registrarTransaccion(transaccion);

        ResultadoPago resultado = new ResultadoPago();
        resultado.setAprobado(true);
        resultado.setMensaje("Pago aprobado");
        resultado.setPasajero(usuario.getNomUsuario() + " " + usuario.getApePaterno());
        resultado.setMonto(tarifa);
        resultado.setSaldoRestante(usuario.getSaldoActual().subtract(tarifa));
        return resultado;
    }

    public boolean tieneSaldoSuficiente(Usuario usuario, BigDecimal monto) {
        return usuario.getSaldoActual() != null && usuario.getSaldoActual().compareTo(monto) >= 0;
    }
}
