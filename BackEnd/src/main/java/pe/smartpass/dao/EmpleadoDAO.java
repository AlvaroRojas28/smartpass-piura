package pe.smartpass.dao;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import pe.smartpass.model.Empleado;

@Repository
public class EmpleadoDAO {

    private static final RowMapper<Empleado> MAPPER = (rs, i) -> {
        Empleado e = new Empleado();
        e.setIdEmpleado(rs.getInt("id_empleado"));
        e.setIdPerfil(rs.getInt("id_perfil"));
        e.setNomPerfil(rs.getString("nom_perfil"));
        e.setNom(rs.getString("nom"));
        e.setApePaterno(rs.getString("ape_paterno"));
        e.setApeMaterno(rs.getString("ape_materno"));
        e.setDni(rs.getString("dni"));
        e.setClave(rs.getString("clave"));
        e.setEstado(rs.getInt("estado"));
        return e;
    };

    private final JdbcTemplate jdbc;

    public EmpleadoDAO(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    public Empleado buscarPorDni(String dni) {
        List<Empleado> lista = jdbc.query(
                "SELECT e.*, p.nom_perfil FROM empleado e JOIN perfil p ON p.id_perfil = e.id_perfil "
                        + "WHERE e.dni = ?", MAPPER, dni);
        return lista.isEmpty() ? null : lista.get(0);
    }
}
