package entities;


public class Asignatura {
    int id;
    int idAlumno;
    String nombreAsignatura;
    String curso;
    int notas;

    public Asignatura(int id, int idAlumno, String nombreAsignatura, String curso, int notas) {
        this.id = id;
        this.idAlumno = idAlumno;
        this.nombreAsignatura = nombreAsignatura;
        this.curso = curso;
        this.notas = notas;
    }
    public Asignatura(int idAlumno, String nombreAsignatura, String curso, int notas) {
        this.idAlumno = idAlumno;
        this.nombreAsignatura = nombreAsignatura;
        this.curso = curso;
        this.notas = notas;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setIdAlumno(int idAlumno) {
        this.idAlumno = idAlumno;
    }

    public void setNombreAsignatura(String nombreAsignatura) {
        this.nombreAsignatura = nombreAsignatura;
    }

    public void setCurso(String curso) {
        this.curso = curso;
    }

    public void setNotas(int notas) {
        this.notas = notas;
    }

    public int getId() {
        return id;
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
