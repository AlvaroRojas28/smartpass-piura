package pe.smartpass.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Empleado {

    private int idEmpleado;
    private int idPerfil;
    private String nomPerfil;
    private String nom;
    private String apePaterno;
    private String apeMaterno;
    private String dni;
    private String clave;
    private LocalDate fecNac;
    private String direccion;
    private int estado;
    private LocalDateTime fecCreacion;
    private LocalDateTime fecModificacion;

    public Empleado() {
    }

    public int getIdEmpleado() { return idEmpleado; }
    public void setIdEmpleado(int idEmpleado) { this.idEmpleado = idEmpleado; }

    public int getIdPerfil() { return idPerfil; }
    public void setIdPerfil(int idPerfil) { this.idPerfil = idPerfil; }

    public String getNomPerfil() { return nomPerfil; }
    public void setNomPerfil(String nomPerfil) { this.nomPerfil = nomPerfil; }

    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }

    public String getApePaterno() { return apePaterno; }
    public void setApePaterno(String apePaterno) { this.apePaterno = apePaterno; }

    public String getApeMaterno() { return apeMaterno; }
    public void setApeMaterno(String apeMaterno) { this.apeMaterno = apeMaterno; }

    public String getDni() { return dni; }
    public void setDni(String dni) { this.dni = dni; }

    public String getClave() { return clave; }
    public void setClave(String clave) { this.clave = clave; }

    public LocalDate getFecNac() { return fecNac; }
    public void setFecNac(LocalDate fecNac) { this.fecNac = fecNac; }

    public String getDireccion() { return direccion; }
    public void setDireccion(String direccion) { this.direccion = direccion; }

    public int getEstado() { return estado; }
    public void setEstado(int estado) { this.estado = estado; }

    public LocalDateTime getFecCreacion() { return fecCreacion; }
    public void setFecCreacion(LocalDateTime fecCreacion) { this.fecCreacion = fecCreacion; }

    public LocalDateTime getFecModificacion() { return fecModificacion; }
    public void setFecModificacion(LocalDateTime fecModificacion) { this.fecModificacion = fecModificacion; }
}
