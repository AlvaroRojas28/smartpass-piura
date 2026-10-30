package pe.smartpass.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

public class Transaccion {

    private int idTransaccion;
    private int idUsuario;
    private int idEmpleado;
    private int idUnidad;
    private BigDecimal monto;
    private LocalDate fecTransaccion;
    private LocalTime horaTransaccion;
    private int estado;

    public Transaccion() {
    }

    public Transaccion(int idUsuario, int idEmpleado, int idUnidad, BigDecimal monto) {
        this.idUsuario = idUsuario;
        this.idEmpleado = idEmpleado;
        this.idUnidad = idUnidad;
        this.monto = monto;
        this.estado = 1;
    }

    public int getIdTransaccion() { return idTransaccion; }
    public void setIdTransaccion(int idTransaccion) { this.idTransaccion = idTransaccion; }

    public int getIdUsuario() { return idUsuario; }
    public void setIdUsuario(int idUsuario) { this.idUsuario = idUsuario; }

    public int getIdEmpleado() { return idEmpleado; }
    public void setIdEmpleado(int idEmpleado) { this.idEmpleado = idEmpleado; }

    public int getIdUnidad() { return idUnidad; }
    public void setIdUnidad(int idUnidad) { this.idUnidad = idUnidad; }

    public BigDecimal getMonto() { return monto; }
    public void setMonto(BigDecimal monto) { this.monto = monto; }

    public LocalDate getFecTransaccion() { return fecTransaccion; }
    public void setFecTransaccion(LocalDate fecTransaccion) { this.fecTransaccion = fecTransaccion; }

    public LocalTime getHoraTransaccion() { return horaTransaccion; }
    public void setHoraTransaccion(LocalTime horaTransaccion) { this.horaTransaccion = horaTransaccion; }

    public int getEstado() { return estado; }
    public void setEstado(int estado) { this.estado = estado; }
}
