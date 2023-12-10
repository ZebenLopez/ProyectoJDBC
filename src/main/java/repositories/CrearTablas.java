package repositories;


import java.io.IOException;
import java.sql.*;

public class CrearTablas {
    public CrearTablas() {
    }

    public void crearTablas(String bdName, String username, String password) {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        String sql = null;
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/" + bdName, username, password);
            statement = connection.createStatement();
                sql = "CREATE TABLE Alumno ( " +
                        "id INT AUTO_INCREMENT PRIMARY KEY, " +
                        "nombre VARCHAR(50) UNIQUE NOT NULL, " +
                        "telefono INT UNIQUE, " +
                        "direccion VARCHAR(50))";
                statement.executeUpdate(sql);
                System.out.println("[i] La tabla Alumno ha sido creada.");
// Crear la tabla Direccion
            statement.executeUpdate("DROP TABLE IF EXISTS Direccion");
            sql = "CREATE TABLE Direccion (" +
                    "id INT AUTO_INCREMENT PRIMARY KEY, " +
                    "idAlumno INT, " +
                    "direccion VARCHAR(50), " +
                    "FOREIGN KEY (idAlumno) REFERENCES Alumno(id))";
            statement.executeUpdate(sql);
            System.out.println("[i] La tabla Direccion ha sido creada.");

// Crear la tabla Familiar
            statement.executeUpdate("DROP TABLE IF EXISTS Familiar");
            sql = "CREATE TABLE Familiar (" +
                    "id INT AUTO_INCREMENT PRIMARY KEY, " +
                    "idAlumno INT, " +
                    "nombre VARCHAR(50), " +
                    "sexo VARCHAR(50), " +
                    "telefono INT, " +
                    "custodia BOOLEAN, " +
                    "FOREIGN KEY (idAlumno) REFERENCES Alumno(id))";
            statement.executeUpdate(sql);
            System.out.println("[i] La tabla Familiar ha sido creada.");

// Crear la tabla Asignatura
            statement.executeUpdate("DROP TABLE IF EXISTS Asignatura");
            sql = "CREATE TABLE Asignatura (" +
                    "id INT AUTO_INCREMENT PRIMARY KEY, " +
                    "idAlumno INT, " +
                    "nombreAsignatura VARCHAR(50), " +
                    "curso VARCHAR(50), " +
                    "notas INT, " +
                    "FOREIGN KEY (idAlumno) REFERENCES Alumno(id))";
            statement.executeUpdate(sql);
            System.out.println("[i] La tabla Asignatura ha sido creada.");
            System.out.println("--> Pulse enter para continuar...");
            System.in.read();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                if (resultSet != null) resultSet.close();
                if (statement != null) statement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    public void eliminarTablas(String bdName, String username, String password) {
        Connection connection = null;
        Statement statement = null;
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/" + bdName, username, password);
            statement = connection.createStatement();

            // Desactivar las comprobaciones de claves foráneas
            statement.executeUpdate("SET FOREIGN_KEY_CHECKS = 0");

            // Eliminar las tablas
            statement.executeUpdate("DROP TABLE IF EXISTS Alumno");
            System.out.println("[i] La tabla Alumno ha sido eliminada.");
            statement.executeUpdate("DROP TABLE IF EXISTS Direccion");
            System.out.println("[i] La tabla Direccion ha sido eliminada.");
            statement.executeUpdate("DROP TABLE IF EXISTS Familiar");
            System.out.println("[i] La tabla Familiar ha sido eliminada.");
            statement.executeUpdate("DROP TABLE IF EXISTS Asignatura");
            System.out.println("[i] La tabla Asignatura ha sido eliminada.");

            // Reactivar las comprobaciones de claves foráneas
            statement.executeUpdate("SET FOREIGN_KEY_CHECKS = 1");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (statement != null) statement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }


    public void insertarDatosGenerico(String bdName, String username, String password, String tabla, String[] columnas, Object[] datos) {
        try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/" + bdName, username, password)) {
            StringBuilder sqlBuilder = new StringBuilder("INSERT INTO ");
            sqlBuilder.append(tabla);
            sqlBuilder.append(" (");
            for (int i = 0; i < columnas.length; i++) {
                sqlBuilder.append(columnas[i]);
                if (i < columnas.length - 1) {
                    sqlBuilder.append(", ");
                }
            }
            sqlBuilder.append(") VALUES (");
            for (int i = 0; i < columnas.length; i++) {
                sqlBuilder.append("?");
                if (i < columnas.length - 1) {
                    sqlBuilder.append(", ");
                }
            }
            sqlBuilder.append(")");

            String sql = sqlBuilder.toString();

            try (PreparedStatement pstmt = con.prepareStatement(sql)) {
                for (int i = 0; i < columnas.length; i++) {
                    if (datos[i] instanceof String) {
                        pstmt.setString(i + 1, (String) datos[i]);
                    } else if (datos[i] instanceof Integer) {
                        pstmt.setInt(i + 1, (Integer) datos[i]);
                    } else if (datos[i] instanceof Boolean) {
                        pstmt.setBoolean(i + 1, (Boolean) datos[i]);
                    }
                    // Agrega más tipos de datos según sea necesario
                }
                pstmt.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
