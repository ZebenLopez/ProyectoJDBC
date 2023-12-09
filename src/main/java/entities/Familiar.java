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

    public void setId(int id) {
        this.id = id;
    }

    public void setIdAlumno(int idAlumno) {
        this.idAlumno = idAlumno;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public void setTelefono(int telefono) {
        this.telefono = telefono;
    }

    public void setCustodia(Boolean custodia) {
        this.custodia = custodia;
    }

    public int getId() {
        return id;
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
