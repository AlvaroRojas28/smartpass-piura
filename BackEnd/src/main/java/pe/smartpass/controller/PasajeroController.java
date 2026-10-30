package pe.smartpass.controller;

import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import pe.smartpass.service.QRService;
import pe.smartpass.service.RecargaService;
import pe.smartpass.service.TransaccionService;
import pe.smartpass.service.UsuarioService;

@RestController
@RequestMapping("/api/pasajero")
public class PasajeroController {

    private final UsuarioService usuarioService;
    private final RecargaService recargaService;
    private final QRService qrService;
    private final TransaccionService transaccionService;

    public PasajeroController(UsuarioService usuarioService, RecargaService recargaService,
                              QRService qrService, TransaccionService transaccionService) {
        this.usuarioService = usuarioService;
        this.recargaService = recargaService;
        this.qrService = qrService;
        this.transaccionService = transaccionService;
    }

    @GetMapping("/saldo")
    public Map<String, Object> saldo(@RequestAttribute("idActor") int idUsuario) {
        return Map.of("saldoActual", usuarioService.consultarSaldo(idUsuario));
    }

    /** RF0002: recarga virtual de saldo. */
    @PostMapping("/recargas")
    public Map<String, Object> recargar(@RequestAttribute("idActor") int idUsuario,
                                        @RequestBody Map<String, Object> datos) {
        BigDecimal monto;
        try {
            monto = new BigDecimal(String.valueOf(datos.get("monto")));
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("El monto ingresado no es válido");
        }
        String metodo = String.valueOf(datos.getOrDefault("metodoSimulado", "Billetera digital"));
        recargaService.procesarRecarga(idUsuario, monto, metodo);
        Map<String, Object> cuerpo = new LinkedHashMap<>();
        cuerpo.put("mensaje", "Recarga exitosa");
        cuerpo.put("saldoActual", usuarioService.consultarSaldo(idUsuario));
        return cuerpo;
    }

    /** RF0003: genera (o renueva) el codigo QR del pasajero. */
    @GetMapping("/qr")
    public Map<String, Object> qr(@RequestAttribute("idActor") int idUsuario) {
        String codigoQR = qrService.generarOActualizarQR(idUsuario);
        Map<String, Object> cuerpo = new LinkedHashMap<>();
        cuerpo.put("codigoQR", codigoQR);
        cuerpo.put("saldoActual", usuarioService.consultarSaldo(idUsuario));
        return cuerpo;
    }

    /** RF0008: historial de viajes y saldo. */
    @GetMapping("/historial")
    public Map<String, Object> historial(@RequestAttribute("idActor") int idUsuario) {
        return transaccionService.listarHistorial(idUsuario);
    }
}
