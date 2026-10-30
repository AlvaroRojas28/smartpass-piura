package pe.smartpass.dao;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import pe.smartpass.model.FiltroReporte;
import pe.smartpass.model.Transaccion;

@Repository
public class TransaccionDAO {

    private static final String DESDE = " FROM transaccion t "
            + "JOIN unidad un ON un.id_unidad = t.id_unidad "
            + "JOIN ruta r ON r.id_ruta = un.id_ruta ";

    private final JdbcTemplate jdbc;

    public TransaccionDAO(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    public Transaccion guardarTransaccion(Transaccion t) {
        Integer id = jdbc.queryForObject(
                "INSERT INTO transaccion (id_usuario, id_empleado, id_unidad, monto, estado) "
                        + "VALUES (?, ?, ?, ?, ?) RETURNING id_transaccion",
                Integer.class, t.getIdUsuario(), t.getIdEmpleado(), t.getIdUnidad(), t.getMonto(), t.getEstado());
        t.setIdTransaccion(id == null ? 0 : id);
        return t;
    }

    public List<Map<String, Object>> listarPorUsuario(int idUsuario) {
        return jdbc.queryForList(
                "SELECT t.id_transaccion AS \"idTransaccion\", t.monto, "
                        + "to_char(t.fec_transaccion, 'YYYY-MM-DD') AS fecha, "
                        + "to_char(t.hora_transaccion, 'HH24:MI:SS') AS hora, "
                        + "un.nom_unidad AS unidad, r.nom_ruta AS ruta" + DESDE
                        + "WHERE t.id_usuario = ? ORDER BY t.id_transaccion DESC LIMIT 50", idUsuario);
    }

    public BigDecimal sumarRecaudacion(FiltroReporte f) {
        List<Object> args = new ArrayList<>();
        String where = condiciones(f, args);
        return jdbc.queryForObject("SELECT COALESCE(SUM(t.monto), 0)" + DESDE + where,
                BigDecimal.class, args.toArray());
    }

    public int contarTransacciones(FiltroReporte f) {
        List<Object> args = new ArrayList<>();
        String where = condiciones(f, args);
        Integer n = jdbc.queryForObject("SELECT COUNT(*)" + DESDE + where, Integer.class, args.toArray());
        return n == null ? 0 : n;
    }

    public List<Map<String, Object>> recaudacionPorDia(FiltroReporte f) {
        List<Object> args = new ArrayList<>();
        String where = condiciones(f, args);
        return jdbc.queryForList(
                "SELECT to_char(t.fec_transaccion, 'YYYY-MM-DD') AS fecha, COUNT(*) AS transacciones, "
                        + "SUM(t.monto) AS monto" + DESDE + where
                        + "GROUP BY t.fec_transaccion ORDER BY t.fec_transaccion", args.toArray());
    }

    public List<Map<String, Object>> recaudacionPorUnidad(FiltroReporte f) {
        List<Object> args = new ArrayList<>();
        String where = condiciones(f, args);
        return jdbc.queryForList(
                "SELECT un.nom_unidad AS unidad, un.placa, COUNT(*) AS transacciones, SUM(t.monto) AS monto"
                        + DESDE + where + "GROUP BY un.id_unidad, un.nom_unidad, un.placa ORDER BY un.id_unidad",
                args.toArray());
    }

    public List<Map<String, Object>> recaudacionPorRuta(FiltroReporte f) {
        List<Object> args = new ArrayList<>();
        String where = condiciones(f, args);
        return jdbc.queryForList(
                "SELECT r.nom_ruta AS ruta, COUNT(*) AS transacciones, SUM(t.monto) AS monto"
                        + DESDE + where + "GROUP BY r.id_ruta, r.nom_ruta ORDER BY r.id_ruta", args.toArray());
    }

    /** Arma el WHERE comun a todos los reportes y llena la lista de parametros. */
    private String condiciones(FiltroReporte f, List<Object> args) {
        StringBuilder sb = new StringBuilder("WHERE t.estado = 1 AND t.fec_transaccion BETWEEN ? AND ? ");
        args.add(java.sql.Date.valueOf(f.getFechaInicio()));
        args.add(java.sql.Date.valueOf(f.getFechaFin()));
        if (f.getIdUnidad() != null) {
            sb.append("AND t.id_unidad = ? ");
            args.add(f.getIdUnidad());
        }
        if (f.getIdRuta() != null) {
            sb.append("AND un.id_ruta = ? ");
            args.add(f.getIdRuta());
        }
        return sb.toString();
    }
}
