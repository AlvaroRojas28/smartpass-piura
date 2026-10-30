package pe.smartpass.model;

public class Ruta {

    private int idRuta;
    private String nomRuta;
    private String origen;
    private String destino;
    private int estado;

    public Ruta() {
    }

    public int getIdRuta() { return idRuta; }
    public void setIdRuta(int idRuta) { this.idRuta = idRuta; }

    public String getNomRuta() { return nomRuta; }
    public void setNomRuta(String nomRuta) { this.nomRuta = nomRuta; }

    public String getOrigen() { return origen; }
    public void setOrigen(String origen) { this.origen = origen; }

    public String getDestino() { return destino; }
    public void setDestino(String destino) { this.destino = destino; }

    public int getEstado() { return estado; }
    public void setEstado(int estado) { this.estado = estado; }
}
