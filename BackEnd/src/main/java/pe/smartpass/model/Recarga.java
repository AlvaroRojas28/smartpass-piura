package pe.smartpass.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Recarga {

    private int idRecarga;
    private int idUsuario;
    private BigDecimal monto;
    private String metodoSimulado;
    private int estado;
    private LocalDateTime fecRecarga;

    public Recarga() {
    }

    public Recarga(int idUsuario, BigDecimal monto, String metodoSimulado) {
        this.idUsuario = idUsuario;
        this.monto = monto;
        this.metodoSimulado = metodoSimulado;
        this.estado = 1;
    }

    public int getIdRecarga() { return idRecarga; }
    public void setIdRecarga(int idRecarga) { this.idRecarga = idRecarga; }

    public int getIdUsuario() { return idUsuario; }
    public void setIdUsuario(int idUsuario) { this.idUsuario = idUsuario; }

    public BigDecimal getMonto() { return monto; }
    public void setMonto(BigDecimal monto) { this.monto = monto; }

    public String getMetodoSimulado() { return metodoSimulado; }
    public void setMetodoSimulado(String metodoSimulado) { this.metodoSimulado = metodoSimulado; }

    public int getEstado() { return estado; }
    public void setEstado(int estado) { this.estado = estado; }

    public LocalDateTime getFecRecarga() { return fecRecarga; }
    public void setFecRecarga(LocalDateTime fecRecarga) { this.fecRecarga = fecRecarga; }
}
