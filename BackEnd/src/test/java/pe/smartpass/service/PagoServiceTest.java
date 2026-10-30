package pe.smartpass.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import pe.smartpass.dao.UsuarioDAO;
import pe.smartpass.model.ResultadoPago;
import pe.smartpass.model.Transaccion;
import pe.smartpass.model.Usuario;

/** Pruebas funcionales PF0004 (validar pago) y PF0005 (registrar transaccion) a nivel de servicio. */
class PagoServiceTest {

    private static final BigDecimal TARIFA = new BigDecimal("1.80");

    private UsuarioDAO usuarioDAO;
    private TransaccionService transaccionService;
    private PagoService pagoService;

    @BeforeEach
    void preparar() {
        usuarioDAO = mock(UsuarioDAO.class);
        transaccionService = mock(TransaccionService.class);
        pagoService = new PagoService(usuarioDAO, transaccionService, TARIFA);
    }

    private Usuario usuarioConSaldo(String saldo) {
        Usuario u = new Usuario();
        u.setIdUsuario(7);
        u.setNomUsuario("Ana");
        u.setApePaterno("Torres");
        u.setEstado(1);
        u.setSaldoActual(new BigDecimal(saldo));
        return u;
    }

    @Test
    void apruebaElPagoYRegistraLaTransaccion() {
        when(usuarioDAO.buscarPorCodigoQR("QR1")).thenReturn(usuarioConSaldo("5.00"));
        when(usuarioDAO.descontarSaldo(7, TARIFA)).thenReturn(true);

        ResultadoPago r = pagoService.procesarPago("QR1", 2, 1);

        assertTrue(r.isAprobado());
        assertEquals(new BigDecimal("3.20"), r.getSaldoRestante());
        verify(transaccionService).registrarTransaccion(any(Transaccion.class));
    }

    @Test
    void rechazaElPagoPorSaldoInsuficiente() {
        when(usuarioDAO.buscarPorCodigoQR("QR2")).thenReturn(usuarioConSaldo("1.00"));

        ResultadoPago r = pagoService.procesarPago("QR2", 2, 1);

        assertFalse(r.isAprobado());
        assertEquals("Saldo insuficiente", r.getMensaje());
        verify(usuarioDAO, never()).descontarSaldo(anyInt(), any(BigDecimal.class));
        verify(transaccionService, never()).registrarTransaccion(any(Transaccion.class));
    }

    @Test
    void rechazaUnCodigoQrInexistente() {
        when(usuarioDAO.buscarPorCodigoQR("XXX")).thenReturn(null);

        ResultadoPago r = pagoService.procesarPago("XXX", 2, 1);

        assertFalse(r.isAprobado());
        assertEquals("Código QR no válido", r.getMensaje());
    }
}
