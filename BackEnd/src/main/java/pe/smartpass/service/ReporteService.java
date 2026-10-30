package pe.smartpass.service;

import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import pe.smartpass.dao.TransaccionDAO;
import pe.smartpass.dao.UsuarioDAO;
import pe.smartpass.model.FiltroReporte;

@Service
public class ReporteService {

    private final TransaccionDAO transaccionDAO;
    private final UsuarioDAO usuarioDAO;

    public ReporteService(TransaccionDAO transaccionDAO, UsuarioDAO usuarioDAO) {
        this.transaccionDAO = transaccionDAO;
        this.usuarioDAO = usuarioDAO;
    }

    /** RF0009 y RF0010: reporte de recaudacion por dia, unidad y ruta, con totales del periodo. */
    public Map<String, Object> construirReporteRecaudacion(FiltroReporte filtro) {
        if (filtro.getFechaFin() == null) {
            filtro.setFechaFin(LocalDate.now());
        }
        if (filtro.getFechaInicio() == null) {
            filtro.setFechaInicio(filtro.getFechaFin().minusDays(6));
        }
        if (filtro.getFechaInicio().isAfter(filtro.getFechaFin())) {
            throw new IllegalArgumentException("La fecha de inicio no puede ser posterior a la fecha de fin");
        }
        Map<String, Object> reporte = new LinkedHashMap<>();
        reporte.put("fechaInicio", filtro.getFechaInicio().toString());
        reporte.put("fechaFin", filtro.getFechaFin().toString());
        reporte.put("recaudacionTotal", transaccionDAO.sumarRecaudacion(filtro));
        reporte.put("totalTransacciones", transaccionDAO.contarTransacciones(filtro));
        reporte.put("totalPasajeros", usuarioDAO.contarPasajerosRegistrados());
        reporte.put("porDia", transaccionDAO.recaudacionPorDia(filtro));
        reporte.put("porUnidad", transaccionDAO.recaudacionPorUnidad(filtro));
        reporte.put("porRuta", transaccionDAO.recaudacionPorRuta(filtro));
        return reporte;
    }
}
