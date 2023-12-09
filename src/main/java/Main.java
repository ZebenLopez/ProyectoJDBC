import connection.MySQLConnection;
import repositories.ManejoAlumnos;

import java.sql.Connection;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws Exception {
        Main app = new Main();
        app.run();
    }

    public void run() throws Exception {
        MySQLConnection mySQLConnection = new MySQLConnection();
        Connection connection = mySQLConnection.conexionBaseDatos();
        String url = mySQLConnection.getUrl();
        String bdName = mySQLConnection.getBdName();
        String username = mySQLConnection.getUsername();
        String password = mySQLConnection.getPassword();

        ManejoAlumnos manejoAlumnos = new ManejoAlumnos();
        manejoAlumnos.elegirOpcion(url, username, password);

    }
}


//public class Main {
//    public static void main(String[] args) throws Exception {
//        Main app = new Main();
//        app.run();
//    }

//    public void run() throws Exception {
//        String path = "src/main/resources/alumnos.sql";
//
//        MySQLConnection mySQLConnection = new MySQLConnection();
//        Connection connection = mySQLConnection.conexionBaseDatos();
//
//        TablaAlumnos tablaAlumnos = new TablaAlumnos();
//        CargaDatos cargaDatos = new CargaDatos();
//
//        tablaAlumnos.crearTablaAlumno("ProyectoJDBC", "root", "123456");
//        cargaDatos.loadData(connection, path);
//
////        ManejoAlumnos manejoAlumnos = new ManejoAlumnos();
////        manejoAlumnos.buscarAlumnos("ProyectoJDBC", "root", "123456");
////        tablaAlumnos.insertarDatos(bdName, username, password, "Alumno");
//    }
//}

