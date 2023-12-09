package validacion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Validacion {

    public boolean validarString(String nombre){
        if (nombre.length() < 4) {
            System.out.println("El nombre debe tener al menos 4 caracteres.");
            return false;
        } else if (nombre.length() > 50) {
            System.out.println("El nombre debe tener menos de 50 caracteres.");
            return false;
        } else if (!nombre.matches("^[a-zA-Z0-9 ]*$")) {
            System.out.println("El nombre solo puede contener letras, números y espacios.");
            return false;
        } else {
            return true;
        }
    }

    public boolean validarTelefono(int telefono) throws Exception {
        try {
            if (telefono < 100000000 || telefono > 999999999) {
                System.out.println("El telefono debe tener 9 digitos.");
                return false;
            } else {
                return true;
            }
        } catch (Exception e) {
            System.out.println("El telefono debe tener 9 digitos.");
            return false;
        }
    }

    public boolean telefonoExiste(int telefono, String url, String username, String password) throws Exception {
        String sql = "SELECT COUNT(*) FROM Alumno WHERE telefono = ?";
        try (Connection con = DriverManager.getConnection(url, username, password);
             PreparedStatement pstmt = con.prepareStatement(sql)) {
            ((PreparedStatement) pstmt).setInt(1, telefono);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        }
        return false;
    }

    public boolean validarSexo(String sexo) throws Exception {
        if (sexo.equalsIgnoreCase("Hombre") || sexo.equalsIgnoreCase("Mujer")) {
            return true;
        } else {
            System.out.println("El sexo debe ser Hombre o Mujer.");
            return false;
        }
    }

    public boolean validarNotas(int notas) throws Exception {
        try {
            if (notas < 0 || notas > 10) {
                System.out.println("Las notas deben estar entre 0 y 10.");
                return false;
            } else {
                return true;
            }
        } catch (Exception e) {
            System.out.println("Las notas deben ser un número entre 0 y 10.");
            return false;
        }
    }
}
