package pe.smartpass.controller;

import java.util.Map;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import pe.smartpass.model.ResultadoPago;
import pe.smartpass.service.PagoService;

@RestController
@RequestMapping("/api/pagos")
public class PagoController {

    private final PagoService pagoService;

    public PagoController(PagoService pagoService) {
        this.pagoService = pagoService;
    }

    /** RF0004 a RF0007: el cobrador envia el QR escaneado y la unidad en la que viaja. */
    @PostMapping("/validar")
    public ResultadoPago validarPago(@RequestAttribute("idActor") int idEmpleado,
                                     @RequestBody Map<String, Object> datos) {
        String codigoQR = String.valueOf(datos.get("codigoQR"));
        int idUnidad;
        try {
            idUnidad = Integer.parseInt(String.valueOf(datos.get("idUnidad")));
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Debe seleccionar la unidad");
        }
        return pagoService.procesarPago(codigoQR, idEmpleado, idUnidad);
    }
}
