package pe.smartpass.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import pe.smartpass.model.Recarga;

@Repository
public class RecargaDAO {

    private final JdbcTemplate jdbc;

    public RecargaDAO(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    public Recarga guardarRecarga(Recarga r) {
        Integer id = jdbc.queryForObject(
                "INSERT INTO recarga (id_usuario, monto, metodo_simulado, estado) "
                        + "VALUES (?, ?, ?, ?) RETURNING id_recarga",
                Integer.class, r.getIdUsuario(), r.getMonto(), r.getMetodoSimulado(), r.getEstado());
        r.setIdRecarga(id == null ? 0 : id);
        return r;
    }
}
