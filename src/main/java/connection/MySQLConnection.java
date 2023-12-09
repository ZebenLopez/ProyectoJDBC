package connection;

import entities.Alumno;
import entities.Asignatura;
import entities.Direccion;
import entities.Familiar;
import repositories.CrearTablas;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySQLConnection {
    CrearTablas tablaAlumnos = new CrearTablas();
    String bdName = "ProyectoJDBC";

    public String getBdName() {
        return bdName;
    }

    public String getUrl() {
        return url;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    String url = "jdbc:mysql://localhost:3306/" + bdName;
    String username = "root";
    String password = "123456";
    Connection connection = null;

    private static String driver = "com.mysql.cj.jdbc.Driver";

    public MySQLConnection() {
    }

    public Connection getConnection() {
        return connection;
    }
    public Connection conexionBaseDatos() {
        try {
            Class.forName(driver);
            System.out.println("MySQL JDBC Driver registrado.");
        } catch (ClassNotFoundException e) {
            System.out.println("MySQL JDBC Driver no encontrado.");
//            System.out.println(e.getMessage());
            System.exit(-1);
        }
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(url, username, password);
            System.out.println("Connection exitosa.");
        } catch (SQLException e) {
            System.err.println("Connection NO exitosa.");
            System.exit(-1);
        } finally {
            try {
                if (connection != null) connection.close();
            } catch (SQLException e) {
                System.out.println("Cierre de conexion fallido.");
            }
        }
        tablaAlumnos.eliminarTablas(bdName, username, password);
        tablaAlumnos.crearTablas(bdName, username, password);
        for (Alumno alumno : alumnos) {
            Object[] datosAlumno = new Object[]{alumno.getNombre(), alumno.getTelefono(), alumno.getDireccion()};
            tablaAlumnos.insertarDatosGenerico(bdName, username, password, "Alumno", columnasAlumno, datosAlumno);
        }
        for (Direccion direccion : direcciones) {
            Object[] datosDireccion = new Object[]{direccion.getIdAlumno(), direccion.getDireccion()};
            tablaAlumnos.insertarDatosGenerico(bdName, username, password, "Direccion", columnasDireccion, datosDireccion);
        }
        for (Familiar familiar : familiares) {
            Object[] datosFamiliar = new Object[]{familiar.getIdAlumno(), familiar.getNombre(), familiar.getSexo(), familiar.getTelefono(), familiar.getCustodia()};
            tablaAlumnos.insertarDatosGenerico(bdName, username, password, "Familiar", columnasFamiliar, datosFamiliar);
        }
        for (Asignatura asignatura : asignaturas) {
            Object[] datosAsignatura = new Object[]{asignatura.getIdAlumno(), asignatura.getNombreAsignatura(), asignatura.getCurso(), asignatura.getNotas()};
            tablaAlumnos.insertarDatosGenerico(bdName, username, password, "Asignatura", columnasAsignatura, datosAsignatura);
        }
        return connection;
    }


    String[] columnasAlumno = new String[]{"nombre", "telefono", "direccion"};
    String[] columnasDireccion = new String[]{"idAlumno", "direccion"};
    String[] columnasFamiliar = new String[]{"idAlumno", "nombre", "sexo", "telefono", "custodia"};
    String[] columnasAsignatura = new String[]{"idAlumno", "nombreAsignatura", "curso", "notas"};

    Familiar[] familiares = new Familiar[]{
            new Familiar(1, "Familiar1", "Hombre", 123456789, true),
            new Familiar(2, "Familiar2", "Mujer", 456123456, false),
            new Familiar(3, "Familiar3", "Hombre", 789789789, true),
            new Familiar(4, "Familiar4", "Mujer", 987987987, false),
            new Familiar(5, "Familiar5", "Hombre", 654654654, true),
            new Familiar(6, "Familiar6", "Mujer", 321321321, false),
            new Familiar(7, "Familiar7", "Hombre", 147147147, true),
            new Familiar(8, "Familiar8", "Mujer", 258258258, false),
            new Familiar(9, "Familiar9", "Hombre", 369369369, true),
            new Familiar(10, "Familiar10", "Mujer", 741741741, false),
    };

    Asignatura[] asignaturas = new Asignatura[]{
            new Asignatura(1, "Asignatura1", "Primero", 5),
            new Asignatura(2, "Asignatura2", "Segundo", 6),
            new Asignatura(3, "Asignatura3", "Tercero", 7),
            new Asignatura(4, "Asignatura4", "Cuarto", 8),
            new Asignatura(5, "Asignatura5", "Quinto", 9),
            new Asignatura(6, "Asignatura6", "Sexto", 10),
            new Asignatura(7, "Asignatura7", "Septimo", 5),
            new Asignatura(8, "Asignatura8", "Octavo", 6),
            new Asignatura(9, "Asignatura9", "Noveno", 7),
            new Asignatura(10, "Asignatura10", "Decimo", 8),
    };


    Direccion[] direcciones = new Direccion[]{
            new Direccion(1, "Calle  1"),
            new Direccion(2, "Calle  2"),
            new Direccion(3, "Calle  3"),
            new Direccion(4, "Calle  4"),
            new Direccion(5, "Calle  5"),
            new Direccion(6, "Calle  6"),
            new Direccion(7, "Calle  7"),
            new Direccion(8, "Calle  8"),
            new Direccion(9, "Calle  9"),
            new Direccion(10, "Calle 10"),
    };

    Alumno[] alumnos = new Alumno[]{
            new Alumno("Juan", 123456789, "Calle 1"),
            new Alumno("Pedro", 987654321, "Calle 2"),
            new Alumno("Maria", 123456788, "Calle 3"),
            new Alumno("Jose", 987654326, "Calle 4"),
            new Alumno("Ana", 123456782, "Calle 5"),
            new Alumno("Luis", 987654329, "Calle 6"),
            new Alumno("Carlos", 123456799, "Calle 7"),
            new Alumno("Sofia", 987654399, "Calle 8"),
            new Alumno("Jorge", 123456722, "Calle 9"),
            new Alumno("Marta", 987654322, "Calle 10"),

    };
}
