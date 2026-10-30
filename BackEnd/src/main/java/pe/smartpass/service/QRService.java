package pe.smartpass.service;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Service;

import pe.smartpass.dao.UsuarioDAO;

@Service
public class QRService {

    private final UsuarioDAO usuarioDAO;

    public QRService(UsuarioDAO usuarioDAO) {
        this.usuarioDAO = usuarioDAO;
    }

    /** RF0003: genera un codigo QR unico; el anterior deja de ser valido. */
    public String generarOActualizarQR(int idUsuario) {
        String cadenaQR = idUsuario + "-" + System.currentTimeMillis();
        String codigoQR = DigestUtils.sha256Hex(cadenaQR);
        usuarioDAO.actualizarCodigoQR(idUsuario, codigoQR);
        return codigoQR;
    }
}
