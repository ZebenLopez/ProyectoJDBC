package entities;


public class Asignatura {
    int id;
    int idAlumno;
    String nombreAsignatura;
    String curso;
    int notas;

    public Asignatura(int idAlumno, String nombreAsignatura, String curso, int notas) {
        this.idAlumno = idAlumno;
        this.nombreAsignatura = nombreAsignatura;
        this.curso = curso;
        this.notas = notas;
    }

    public int getIdAlumno() {
        return idAlumno;
    }

    public String getNombreAsignatura() {
        return nombreAsignatura;
    }

    public String getCurso() {
        return curso;
    }

    public int getNotas() {
        return notas;
    }
}
