package pe.smartpass.model;

public class Perfil {

    private int idPerfil;
    private String nomPerfil;
    private int estado;

    public Perfil() {
    }

    public int getIdPerfil() { return idPerfil; }
    public void setIdPerfil(int idPerfil) { this.idPerfil = idPerfil; }

    public String getNomPerfil() { return nomPerfil; }
    public void setNomPerfil(String nomPerfil) { this.nomPerfil = nomPerfil; }

    public int getEstado() { return estado; }
    public void setEstado(int estado) { this.estado = estado; }
}
