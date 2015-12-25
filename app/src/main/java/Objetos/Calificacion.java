package Objetos;

/**
 * Created by john on 24/12/15.
 */
public class Calificacion {

    public String materia, paralelo, nota1, nota2, nota3, promedio, vez, estado;

    public Calificacion(){}
    public Calificacion(String estado, String materia, String nota1, String nota2, String nota3, String paralelo, String promedio, String vez) {
        this.estado = estado;
        this.materia = materia;
        this.nota1 = nota1;
        this.nota2 = nota2;
        this.nota3 = nota3;
        this.paralelo = paralelo;
        this.promedio = promedio;
        this.vez = vez;
    }


    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getMateria() {
        return materia;
    }

    public void setMateria(String materia) {
        this.materia = materia;
    }

    public String getNota1() {
        return nota1;
    }

    public void setNota1(String nota1) {
        this.nota1 = nota1;
    }

    public String getNota2() {
        return nota2;
    }

    public void setNota2(String nota2) {
        this.nota2 = nota2;
    }

    public String getNota3() {
        return nota3;
    }

    public void setNota3(String nota3) {
        this.nota3 = nota3;
    }

    public String getParalelo() {
        return paralelo;
    }

    public void setParalelo(String paralelo) {
        this.paralelo = paralelo;
    }

    public String getPromedio() {
        return promedio;
    }

    public void setPromedio(String promedio) {
        this.promedio = promedio;
    }

    public String getVez() {
        return vez;
    }

    public void setVez(String vez) {
        this.vez = vez;
    }
}
