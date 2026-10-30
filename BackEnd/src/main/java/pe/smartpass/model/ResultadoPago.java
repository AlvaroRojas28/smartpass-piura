package pe.smartpass.model;

import java.math.BigDecimal;

public class ResultadoPago {

    private boolean aprobado;
    private String mensaje;
    private String pasajero;
    private BigDecimal monto;
    private BigDecimal saldoRestante;

    public ResultadoPago() {
    }

    public boolean isAprobado() { return aprobado; }
    public void setAprobado(boolean aprobado) { this.aprobado = aprobado; }

    public String getMensaje() { return mensaje; }
    public void setMensaje(String mensaje) { this.mensaje = mensaje; }

    public String getPasajero() { return pasajero; }
    public void setPasajero(String pasajero) { this.pasajero = pasajero; }

    public BigDecimal getMonto() { return monto; }
    public void setMonto(BigDecimal monto) { this.monto = monto; }

    public BigDecimal getSaldoRestante() { return saldoRestante; }
    public void setSaldoRestante(BigDecimal saldoRestante) { this.saldoRestante = saldoRestante; }

    public static ResultadoPago rechazado(String mensaje) {
        ResultadoPago r = new ResultadoPago();
        r.setAprobado(false);
        r.setMensaje(mensaje);
        return r;
    }
}
