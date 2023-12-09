package entities;

/**
 *
 * @author Zeben
 */
public class Alumno {
    int id;
    String nombre;
    int telefono;
    String direccion;

    public Alumno(String nombre, int telefono, String direccion) {
        this.nombre = nombre;
        this.telefono = telefono;
        this.direccion = direccion;
    }

    public Alumno(int id, String nombre, int telefono, String direccion) {
        this.id = id;
        this.nombre = nombre;
        this.telefono = telefono;
        this.direccion = direccion;
    }

    public Alumno() {
    }


    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public int getTelefono() {
        return telefono;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setTelefono(int telefono) {
        this.telefono = telefono;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    @Override
    public String toString() {
        return "Alumno\n" + "Id = " + getId() + ", Nombre = " + nombre + ", Telefono = " + telefono + ", Direccion = " + direccion;
    }

}
