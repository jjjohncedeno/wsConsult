package Objetos;

import java.util.ArrayList;

/**
 * Created by john on 24/12/15.
 */
public class Datos {
    public String nombres, apellidos, matricula, cedula;

    public Datos(String apellidos, String nombres, String matricula, String cedula) {
        this.apellidos = apellidos;
        this.nombres = nombres;
        this.matricula = matricula;
        this.cedula = cedula;
    }

    public String getApellidos() {
        return apellidos;
    }

    public String getCedula() {
        return cedula;
    }

    public String getMatricula() {
        return matricula;
    }

    public String getNombres() {
        return nombres;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

}
