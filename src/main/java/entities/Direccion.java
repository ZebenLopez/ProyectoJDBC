package entities;


/**
 *
 * @author Zeben
 */
public class Direccion {
    int id;
    int idAlumno;
    String direccion;

    public Direccion(int id, int idAlumno, String direccion) {
        this.id = id;
        this.idAlumno = idAlumno;
        this.direccion = direccion;
    }

    public Direccion(int idAlumno, String direccion) {

        this.idAlumno = idAlumno;
        this.direccion = direccion;
    }
    public int getId() {
        return id;
    }

    public int getIdAlumno() {
        return idAlumno;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setIdAlumno(int idAlumno) {
        this.idAlumno = idAlumno;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }
}
