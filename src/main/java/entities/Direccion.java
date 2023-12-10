package entities;


/**
 *
 * @author Zeben
 */
public class Direccion {
    int id;
    int idAlumno;
    String direccion;

    public Direccion(int idAlumno, String direccion) {

        this.idAlumno = idAlumno;
        this.direccion = direccion;
    }
    public int getIdAlumno() {
        return idAlumno;
    }
    public String getDireccion() {
        return direccion;
    }

    @Override
    public String toString() {
        return "Direccion = " + direccion;
    }

}
