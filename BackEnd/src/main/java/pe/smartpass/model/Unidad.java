package pe.smartpass.model;

public class Unidad {

    private int idUnidad;
    private int idRuta;
    private String placa;
    private String nomUnidad;
    private int capacidad;
    private int estado;

    public Unidad() {
    }

    public int getIdUnidad() { return idUnidad; }
    public void setIdUnidad(int idUnidad) { this.idUnidad = idUnidad; }

    public int getIdRuta() { return idRuta; }
    public void setIdRuta(int idRuta) { this.idRuta = idRuta; }

    public String getPlaca() { return placa; }
    public void setPlaca(String placa) { this.placa = placa; }

    public String getNomUnidad() { return nomUnidad; }
    public void setNomUnidad(String nomUnidad) { this.nomUnidad = nomUnidad; }

    public int getCapacidad() { return capacidad; }
    public void setCapacidad(int capacidad) { this.capacidad = capacidad; }

    public int getEstado() { return estado; }
    public void setEstado(int estado) { this.estado = estado; }
}
