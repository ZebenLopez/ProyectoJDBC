package repositories;

import java.io.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class CargaDatos {
    private static Scanner sc = new Scanner(System.in);

    public static void loadData(Connection connection, String path) throws SQLException, IOException, Exception {

        try {
            File file = new File(path);
            Statement statement = connection.createStatement();
            BufferedReader br = new BufferedReader(new FileReader(file));

            StringBuilder sb = new StringBuilder();
            String line;

            while ((line = br.readLine()) != null) {

                sb.append(line.trim());

                if (line.endsWith(";")) {
                    String order = sb.toString();

                    statement.executeUpdate(order);
                    sb.setLength(0); // Limpiar el StringBuilder para la siguiente orden
                }
            }

            connection.commit();
            System.out.println("Datos cargados correctamente.");
        } catch (FileNotFoundException e) {
            System.out.println("No se ha encontrado el archivo");
        } catch (SQLException | IOException e) {
            System.out.println("Ha ocurrido un error cargando los datos");
        }
        sc.close();
    }

}
