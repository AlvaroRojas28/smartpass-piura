package pe.smartpass.dao;

import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class CatalogoDAO {

    private final JdbcTemplate jdbc;

    public CatalogoDAO(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    public List<Map<String, Object>> listarUnidades() {
        return jdbc.queryForList(
                "SELECT u.id_unidad AS \"idUnidad\", u.nom_unidad AS \"nomUnidad\", u.placa, "
                        + "r.id_ruta AS \"idRuta\", r.nom_ruta AS \"nomRuta\" "
                        + "FROM unidad u JOIN ruta r ON r.id_ruta = u.id_ruta "
                        + "WHERE u.estado = 1 ORDER BY u.id_unidad");
    }

    public List<Map<String, Object>> listarRutas() {
        return jdbc.queryForList(
                "SELECT id_ruta AS \"idRuta\", nom_ruta AS \"nomRuta\" FROM ruta WHERE estado = 1 ORDER BY id_ruta");
    }
}
