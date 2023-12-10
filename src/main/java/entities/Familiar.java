package entities;


public class Familiar {
    int id;
    int idAlumno;
    String nombre;
    String sexo;
    int telefono;
    Boolean custodia;

    public Familiar(int id, int idAlumno, String nombre, String sexo, int telefono, Boolean custodia) {
        this.id = id;
        this.idAlumno = idAlumno;
        this.nombre = nombre;
        this.sexo = sexo;
        this.telefono = telefono;
        this.custodia = custodia;
    }
    public Familiar(int idAlumno, String nombre, String sexo, int telefono, Boolean custodia) {
        this.idAlumno = idAlumno;
        this.nombre = nombre;
        this.sexo = sexo;
        this.telefono = telefono;
        this.custodia = custodia;
    }

    public Familiar(String nombre, int telefono) {
        this.nombre = nombre;
        this.telefono = telefono;
    }

    public int getIdAlumno() {
        return idAlumno;
    }

    public String getNombre() {
        return nombre;
    }

    public String getSexo() {
        return sexo;
    }

    public int getTelefono() {
        return telefono;
    }

    public Boolean getCustodia() {
        return custodia;
    }

    @Override
    public String toString() {
        return "Familiar\n"  + "Nombre = " + nombre + ", Telefono = " + telefono;
    }
}
