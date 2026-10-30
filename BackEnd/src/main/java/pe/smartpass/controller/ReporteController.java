package pe.smartpass.controller;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import pe.smartpass.model.FiltroReporte;
import pe.smartpass.service.ReporteService;

@RestController
@RequestMapping("/api/admin")
public class ReporteController {

    private final ReporteService reporteService;

    public ReporteController(ReporteService reporteService) {
        this.reporteService = reporteService;
    }

    /** RF0009 y RF0010: reporte de recaudacion para el panel administrativo. */
    @GetMapping("/reportes/recaudacion")
    public Map<String, Object> generarReporte(@RequestParam(required = false) String fechaInicio,
                                              @RequestParam(required = false) String fechaFin,
                                              @RequestParam(required = false) Integer idUnidad,
                                              @RequestParam(required = false) Integer idRuta) {
        FiltroReporte filtro = new FiltroReporte();
        try {
            if (fechaInicio != null && !fechaInicio.isBlank()) {
                filtro.setFechaInicio(LocalDate.parse(fechaInicio));
            }
            if (fechaFin != null && !fechaFin.isBlank()) {
                filtro.setFechaFin(LocalDate.parse(fechaFin));
            }
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("El formato de fecha no es válido");
        }
        filtro.setIdUnidad(idUnidad);
        filtro.setIdRuta(idRuta);
        return reporteService.construirReporteRecaudacion(filtro);
    }
}
