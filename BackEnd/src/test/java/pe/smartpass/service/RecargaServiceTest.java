package pe.smartpass.service;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;

import pe.smartpass.dao.RecargaDAO;
import pe.smartpass.dao.UsuarioDAO;
import pe.smartpass.model.Recarga;

/** Prueba funcional PF0002 (recargar saldo) a nivel de servicio. */
class RecargaServiceTest {

    @Test
    void registraLaRecargaYActualizaElSaldo() {
        RecargaDAO recargaDAO = mock(RecargaDAO.class);
        UsuarioDAO usuarioDAO = mock(UsuarioDAO.class);
        RecargaService servicio = new RecargaService(recargaDAO, usuarioDAO);

        servicio.procesarRecarga(3, new BigDecimal("10.00"), "Billetera digital");

        verify(recargaDAO).guardarRecarga(any(Recarga.class));
        verify(usuarioDAO).actualizarSaldoUsuario(3, new BigDecimal("10.00"));
    }

    @Test
    void rechazaUnMontoNoPositivo() {
        RecargaDAO recargaDAO = mock(RecargaDAO.class);
        UsuarioDAO usuarioDAO = mock(UsuarioDAO.class);
        RecargaService servicio = new RecargaService(recargaDAO, usuarioDAO);

        assertThrows(IllegalArgumentException.class,
                () -> servicio.procesarRecarga(3, BigDecimal.ZERO, "Efectivo"));
        verify(recargaDAO, never()).guardarRecarga(any(Recarga.class));
    }
}
