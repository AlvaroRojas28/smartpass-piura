package pe.smartpass.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Usuario {

    private int idUsuario;
    private String nomUsuario;
    private String apePaterno;
    private String apeMaterno;
    private String dni;
    private String celular;
    private String clave;
    private BigDecimal saldoActual;
    private String codigoQR;
    private int estado;
    private LocalDateTime fecCreacion;
    private LocalDateTime fecModificacion;

    public Usuario() {
    }

    public int getIdUsuario() { return idUsuario; }
    public void setIdUsuario(int idUsuario) { this.idUsuario = idUsuario; }

    public String getNomUsuario() { return nomUsuario; }
    public void setNomUsuario(String nomUsuario) { this.nomUsuario = nomUsuario; }

    public String getApePaterno() { return apePaterno; }
    public void setApePaterno(String apePaterno) { this.apePaterno = apePaterno; }

    public String getApeMaterno() { return apeMaterno; }
    public void setApeMaterno(String apeMaterno) { this.apeMaterno = apeMaterno; }

    public String getDni() { return dni; }
    public void setDni(String dni) { this.dni = dni; }

    public String getCelular() { return celular; }
    public void setCelular(String celular) { this.celular = celular; }

    public String getClave() { return clave; }
    public void setClave(String clave) { this.clave = clave; }

    public BigDecimal getSaldoActual() { return saldoActual; }
    public void setSaldoActual(BigDecimal saldoActual) { this.saldoActual = saldoActual; }

    public String getCodigoQR() { return codigoQR; }
    public void setCodigoQR(String codigoQR) { this.codigoQR = codigoQR; }

    public int getEstado() { return estado; }
    public void setEstado(int estado) { this.estado = estado; }

    public LocalDateTime getFecCreacion() { return fecCreacion; }
    public void setFecCreacion(LocalDateTime fecCreacion) { this.fecCreacion = fecCreacion; }

    public LocalDateTime getFecModificacion() { return fecModificacion; }
    public void setFecModificacion(LocalDateTime fecModificacion) { this.fecModificacion = fecModificacion; }
}
