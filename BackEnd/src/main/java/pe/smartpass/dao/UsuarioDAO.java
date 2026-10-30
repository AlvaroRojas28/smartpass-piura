package pe.smartpass.dao;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import pe.smartpass.model.Usuario;

@Repository
public class UsuarioDAO {

    private static final RowMapper<Usuario> MAPPER = (rs, i) -> {
        Usuario u = new Usuario();
        u.setIdUsuario(rs.getInt("id_usuario"));
        u.setNomUsuario(rs.getString("nom_usuario"));
        u.setApePaterno(rs.getString("ape_paterno"));
        u.setApeMaterno(rs.getString("ape_materno"));
        u.setDni(rs.getString("dni"));
        u.setCelular(rs.getString("celular"));
        u.setClave(rs.getString("clave"));
        u.setSaldoActual(rs.getBigDecimal("saldo_actual"));
        u.setCodigoQR(rs.getString("codigo_qr"));
        u.setEstado(rs.getInt("estado"));
        return u;
    };

    private final JdbcTemplate jdbc;

    public UsuarioDAO(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    public boolean existeDNI(String dni) {
        Integer n = jdbc.queryForObject("SELECT COUNT(*) FROM usuario WHERE dni = ?", Integer.class, dni);
        return n != null && n > 0;
    }

    public Usuario guardarUsuario(Usuario u) {
        Integer id = jdbc.queryForObject(
                "INSERT INTO usuario (nom_usuario, ape_paterno, ape_materno, dni, celular, clave, saldo_actual, estado) "
                        + "VALUES (?, ?, ?, ?, ?, ?, ?, ?) RETURNING id_usuario",
                Integer.class, u.getNomUsuario(), u.getApePaterno(), u.getApeMaterno(), u.getDni(),
                u.getCelular(), u.getClave(), u.getSaldoActual(), u.getEstado());
        u.setIdUsuario(id == null ? 0 : id);
        return u;
    }

    public Usuario buscarPorDni(String dni) {
        return primero(jdbc.query("SELECT * FROM usuario WHERE dni = ?", MAPPER, dni));
    }

    public Usuario buscarPorId(int idUsuario) {
        return primero(jdbc.query("SELECT * FROM usuario WHERE id_usuario = ?", MAPPER, idUsuario));
    }

    public Usuario buscarPorCodigoQR(String codigoQR) {
        return primero(jdbc.query("SELECT * FROM usuario WHERE codigo_qr = ?", MAPPER, codigoQR));
    }

    public BigDecimal obtenerSaldoUsuario(int idUsuario) {
        return jdbc.queryForObject("SELECT saldo_actual FROM usuario WHERE id_usuario = ?",
                BigDecimal.class, idUsuario);
    }

    /** Suma el monto recargado al saldo actual del usuario. */
    public void actualizarSaldoUsuario(int idUsuario, BigDecimal monto) {
        jdbc.update("UPDATE usuario SET saldo_actual = saldo_actual + ?, fec_modificacion = now() "
                + "WHERE id_usuario = ?", monto, idUsuario);
    }

    /** Descuenta la tarifa solo si el saldo alcanza (operacion atomica). */
    public boolean descontarSaldo(int idUsuario, BigDecimal tarifa) {
        int filas = jdbc.update("UPDATE usuario SET saldo_actual = saldo_actual - ?, fec_modificacion = now() "
                + "WHERE id_usuario = ? AND saldo_actual >= ?", tarifa, idUsuario, tarifa);
        return filas == 1;
    }

    public void actualizarCodigoQR(int idUsuario, String codigoQR) {
        jdbc.update("UPDATE usuario SET codigo_qr = ?, fec_modificacion = now() WHERE id_usuario = ?",
                codigoQR, idUsuario);
    }

    public int contarPasajerosRegistrados() {
        Integer n = jdbc.queryForObject("SELECT COUNT(*) FROM usuario WHERE estado = 1", Integer.class);
        return n == null ? 0 : n;
    }

    private static Usuario primero(List<Usuario> lista) {
        return lista.isEmpty() ? null : lista.get(0);
    }
}
