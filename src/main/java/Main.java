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
        mySQLConnection.conexionBaseDatos();
        String url = mySQLConnection.getUrl();
        String username = mySQLConnection.getUsername();
        String password = mySQLConnection.getPassword();

        ManejoAlumnos manejoAlumnos = new ManejoAlumnos();
        manejoAlumnos.elegirOpcion(url, username, password);

    }
}

