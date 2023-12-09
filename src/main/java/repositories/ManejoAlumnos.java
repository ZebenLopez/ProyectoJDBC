package repositories;

import entities.Alumno;
import entities.Familiar;
import validacion.Validacion;

import java.sql.*;
import java.util.InputMismatchException;
import java.util.Scanner;

public class ManejoAlumnos {
    Validacion validacion = new Validacion();

    public void elegirOpcion(String url, String username, String password) throws Exception {
        Scanner scanner = new Scanner(System.in);
        int opcion = 0;

        do {
            System.out.println("Elija una opción: ");
            System.out.println("1. Buscar alumno por nombre");
            System.out.println("2. Consultar todos los alumnos");
            System.out.println("3. Custodia de alumno por su nombre");
            System.out.println("4. Insertar alumno");
            System.out.println("5. Insertar dirección");
            System.out.println("6. Insertar familiar");
            System.out.println("7. Insertar asignatura");
            System.out.println("8. Actualizar custodia de familiar");
            System.out.println("9. Borrar alumno por nombre");
            System.out.println("10. Actualizar asignatura por nombre");
            System.out.println("11. Actualizar alumno por nombre");
            System.out.println("12. Borrar dirección");
            System.out.println("13. Borrar familiar");
            System.out.println("14. Eliminar asignatura");
            System.out.println("15. Salir");

            try {
                opcion = scanner.nextInt();
            } catch (InputMismatchException ex) {
                System.out.println("[i] Por favor, ingrese un número válido");
                System.out.println("--> Pulsa enter para continuar");
                scanner.nextLine();
                scanner.nextLine(); // Consumir la línea de entrada restante
                continue;
            }
            if (opcion < 1 || opcion > 15) {
                System.out.println("[i] Por favor, ingrese un valor válido");
                System.out.println("--> Pulsa enter para continuar");
                scanner.nextLine();
                scanner.nextLine();
            } else {
                switch (opcion) {
                    case 1:
                        buscarAlumnos(url, username, password);
                        break;
                    case 2:
                        consultarTodosAlumnos(url, username, password);
                        break;
                    case 3:
                        custodiaAlumno(url, username, password);
                        break;
                    case 4:
                        insertarAlumno(url, username, password);
                        break;
                    case 5:
                        insertarDireccion(url, username, password);
                        break;
                    case 6:
                        insertarFamiliar(url, username, password);
                        break;
                    case 7:
                        insertarAsignatura(url, username, password);
                        break;
                    case 8:
                        actualizarFamiliar(url, username, password);
                        break;
                    case 9:
                        borrarAlumno(url, username, password);
                        break;
                    case 10:
                        actualizarAsignatura(url, username, password);
                        break;
                    case 11:
                        actualizarAlumno(url, username, password);
                        break;
                    case 12:
                        borrarDireccion(url, username, password);
                        break;
                    case 13:
                        borrarFamiliar(url, username, password);
                        break;
                    case 14:
                        eliminarAsignatura(url, username, password);
                        break;
                    case 15:
                        System.out.println("Saliendo...");
                        break;
                    default:
                        System.out.println("Opción no válida");
                        break;
                }
            }
        } while (opcion != 15);
    }

    public void buscarAlumnos(String url, String username, String password) {
        Scanner scanner = new Scanner(System.in);
        Connection con = null;
        PreparedStatement pstmt = null;
        String sql = "SELECT * FROM Alumno where id = ?";
        try {
            con = DriverManager.getConnection(url, username, password);
            int idAlumno = obtenerIdAlumno(con, scanner);
            if (idAlumno == -1) {
                return; // Si el método retorna -1, salimos del método
            }
            pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, idAlumno);
            ResultSet rs = pstmt.executeQuery();
            if (!rs.next()) {
                System.out.println("[i] No se encontró ningún alumno con el ID ingresado");
                System.out.println("--> Pulsa enter para continuar");
                scanner.nextLine();
            } else {
                do {
                    int id = rs.getInt("id");
                    String nombre = rs.getString("nombre");
                    int telefono = rs.getInt("telefono");
                    String direccion = rs.getString("direccion");
                    Alumno alumno = new Alumno(id, nombre, telefono, direccion);
                    System.out.println(alumno);
                    System.out.println("--> Pulsa enter para continuar");
                    scanner.nextLine();
                } while (rs.next());
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void consultarTodosAlumnos(String url, String username, String password) {
        Scanner scanner = new Scanner(System.in);
        Connection con = null;
        PreparedStatement pstmt = null;
        String sql = "SELECT * FROM Alumno";
        try {
            con = DriverManager.getConnection(url, username, password);
            pstmt = con.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                String nombre = rs.getString("nombre");
                int telefono = rs.getInt("telefono");
                String direccion = rs.getString("direccion");
                Alumno alumno = new Alumno(id, nombre, telefono, direccion);
                System.out.println(alumno);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        System.out.println();
        System.out.println("-->Pulsa enter para continuar");
        scanner.nextLine();
    }

    public void custodiaAlumno(String url, String username, String password) {
        Scanner scanner = new Scanner(System.in);
        Connection con = null;
        PreparedStatement pstmt = null;
        String sql = "SELECT Alumno.*, Familiar.nombre, Familiar.telefono " + "FROM Alumno " + "JOIN Familiar ON Alumno.id = Familiar.idAlumno " + "WHERE Alumno.nombre = ? AND Familiar.custodia = true";
        try {
            con = DriverManager.getConnection(url, username, password);
            pstmt = con.prepareStatement(sql);
            System.out.println("--> Ingrese el nombre del alumno a buscar: ");
            pstmt.setString(1, scanner.nextLine());
            ResultSet rs = pstmt.executeQuery();
            if (!rs.next()) {
                System.out.println("[i] Esa persona no tiene familiares que lo custodien o no es alumno del centro");
                System.out.println("--> Pulsa enter para continuar");
                scanner.nextLine();
            } else {
                do {
                    int id = rs.getInt("id");
                    String nombre = rs.getString("nombre");
                    int telefono = rs.getInt("telefono");
                    String direccion = rs.getString("direccion");
                    String nombreFamiliar = rs.getString("Familiar.nombre");
                    int telefonoFamiliar = rs.getInt("Familiar.telefono");
                    Alumno alumno = new Alumno(id, nombre, telefono, direccion);
                    Familiar familiar = new Familiar(nombreFamiliar, telefonoFamiliar);
                    System.out.println(alumno);
                    System.out.println(familiar);
                    System.out.println("--> Pulsa enter para continuar");
                    scanner.nextLine();

                } while (rs.next());
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void insertarAlumno(String url, String username, String password) throws Exception {
        Scanner scanner = new Scanner(System.in);
        String nombre;
        int telefono = 0;
        String direccion;
        boolean isValid;
        do {
            System.out.println("--> Ingrese el nombre del alumno: ");
            nombre = scanner.nextLine();
            isValid = validacion.validarString(nombre);
        } while (!isValid);
        isValid = false;
        do {
            System.out.println("--> Ingrese el teléfono del alumno: ");
            try {
                telefono = scanner.nextInt();
                if (validacion.telefonoExiste(telefono, url, username, password)) {
                    System.out.println("[i] El teléfono ya existe en la base de datos");
                    continue;
                }
                validacion.validarTelefono(telefono);
                isValid = validacion.validarTelefono(telefono);
            } catch (InputMismatchException ex) {
                System.out.println("[i] Por favor, ingrese un número válido para el teléfono");
                scanner.nextLine(); // Consumir la línea de entrada restante
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        } while (!isValid);
        do {
            System.out.println("--> Ingrese la dirección del alumno: ");
            direccion = scanner.nextLine();
            isValid = validacion.validarString(direccion);
        } while (!isValid);


        // Insertar el alumno en la tabla Alumno
        String sqlAlumno = "INSERT INTO Alumno (nombre, telefono, direccion) VALUES (?, ?, ?)";
        int idAlumno;
        try (Connection con = DriverManager.getConnection(url, username, password); PreparedStatement pstmt = con.prepareStatement(sqlAlumno, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, nombre);
            pstmt.setInt(2, telefono);
            pstmt.setString(3, direccion);
            pstmt.executeUpdate();
            // Obtener el ID del alumno recién insertado
            try (ResultSet rs = pstmt.getGeneratedKeys()) {
                if (rs.next()) {
                    idAlumno = rs.getInt(1);
                } else {
                    throw new RuntimeException("[Error] No se pudo obtener el ID del alumno recién insertado");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        // Desactivar las comprobaciones de clave foránea
        String sqlCheck = "SET FOREIGN_KEY_CHECKS = 0";
        try (Connection con = DriverManager.getConnection(url, username, password); PreparedStatement pstmt = con.prepareStatement(sqlCheck)) {
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        // Insertar la dirección en la tabla Direccion
        String sqlDireccion = "INSERT INTO Direccion (idAlumno, direccion) VALUES (?, ?)";
        try (Connection con = DriverManager.getConnection(url, username, password); PreparedStatement pstmt = con.prepareStatement(sqlDireccion)) {
            pstmt.setInt(1, idAlumno);
            pstmt.setString(2, direccion);
            pstmt.executeUpdate();
            System.out.println("[i] Alumno insertado correctamente");
            System.out.println("--> Pulsa enter para continuar");
            scanner.nextLine();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        // Reactivar las comprobaciones de clave foránea
        sqlCheck = "SET FOREIGN_KEY_CHECKS = 1";
        try (Connection con = DriverManager.getConnection(url, username, password); PreparedStatement pstmt = con.prepareStatement(sqlCheck)) {
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void insertarDireccion(String url, String username, String password) {
        Scanner scanner = new Scanner(System.in);
        Connection con = null;
        PreparedStatement pstmt = null;
        String sql = "INSERT INTO Direccion (idAlumno, direccion) VALUES (?, ?)";
        boolean isValid = false;
        String direccion = null;
        try {
            con = DriverManager.getConnection(url, username, password);

            int idAlumno = obtenerIdAlumno(con, scanner);
            if (idAlumno == -1) {
                return;
            }
            // Insertar la dirección
            do {
                System.out.println("--> Ingrese la dirección del alumno: ");
                try {
                    direccion = scanner.nextLine();
                    validacion.validarString(direccion);
                    isValid = validacion.validarString(direccion);
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            } while (!isValid);

            try (PreparedStatement pstmtInsert = con.prepareStatement(sql)) {
                pstmtInsert.setInt(1, idAlumno);
                pstmtInsert.setString(2, direccion);
                pstmtInsert.executeUpdate();
                System.out.println("[i] Dirección insertada correctamente");
                System.out.println("--> Pulsa enter para continuar");
                scanner.nextLine();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void insertarFamiliar(String url, String username, String password) {
        Scanner scanner = new Scanner(System.in);
        Connection con = null;
        int telefono = 0;
        boolean isValid = false;
        PreparedStatement pstmt = null;
        String sql = "INSERT INTO Familiar (idAlumno, nombre, sexo, telefono, custodia) VALUES (?, ?, ?, ?, ?)";
        Validacion validacion = new Validacion();
        try {
            con = DriverManager.getConnection(url, username, password);

            int idAlumno = obtenerIdAlumno(con, scanner);

            // Insertar el familiar
            try (PreparedStatement pstmtInsert = con.prepareStatement(sql)) {
                pstmtInsert.setInt(1, idAlumno);
                System.out.println("Ingrese el nombre del familiar: ");
                String nombre = scanner.nextLine();
                isValid = validacion.validarString(nombre);
                while (!isValid) {
                    System.out.println("[i] Por favor, ingrese un nombre válido para el familiar");
                    nombre = scanner.nextLine();
                    isValid = validacion.validarString(nombre);
                }
                pstmtInsert.setString(2, nombre);

                System.out.println("Ingrese el sexo del familiar (Hombre/Mujer): ");
                String sexo = scanner.nextLine();
                isValid = validacion.validarSexo(sexo);
                while (!isValid) {
                    System.out.println("[i] Por favor, ingrese un sexo válido para el familiar");
                    sexo = scanner.nextLine();
                    isValid = validacion.validarSexo(sexo);
                }
                pstmtInsert.setString(3, sexo);

                do {
                    System.out.println("--> Ingrese el teléfono del familiar: ");
                    try {
                        telefono = scanner.nextInt();
                        isValid = validacion.validarTelefono(telefono);
                        if (!isValid) {
                            System.out.println("[i] Por favor, ingrese un teléfono válido");
                            continue;
                        }
                        if (validacion.telefonoExiste(telefono, url, username, password)) {
                            System.out.println("[i] El teléfono ya existe en la base de datos");
                            isValid = false;
                        }
                    } catch (InputMismatchException ex) {
                        System.out.println("[i] Por favor, ingrese un número válido para el teléfono");
                        scanner.nextLine();
                        isValid = false;
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                        isValid = false;
                    }
                    scanner.nextLine();
                } while (!isValid);

                pstmtInsert.setInt(4, telefono);

                System.out.println("Ingrese si es custodio (Si/No): ");
                String custodia = scanner.nextLine();
                while (true) {
                    if (custodia.equalsIgnoreCase("si")) {
                        pstmtInsert.setBoolean(5, true);
                        break;
                    } else if (custodia.equalsIgnoreCase("no")) {
                        pstmtInsert.setBoolean(5, false);
                        break;
                    } else {
                        System.out.println("[i] Por favor, ingrese un valor válido para la custodia (Si/No)");
                        custodia = scanner.nextLine();
                    }
                }
                pstmtInsert.executeUpdate();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void insertarAsignatura(String url, String username, String password) {
        Scanner scanner = new Scanner(System.in);
        Connection con = null;
        String asignatura;
        int nota = 0;
        String curso;
        boolean isValid = false;
        PreparedStatement pstmt = null;
        String sql = "INSERT INTO Asignatura (idAlumno, nombreAsignatura, curso, notas) VALUES (?, ?, ?, ?)";
        Validacion validacion = new Validacion();
        try {
            con = DriverManager.getConnection(url, username, password);

            int idAlumno = obtenerIdAlumno(con, scanner);

            // Insertar la asignatura
            try (PreparedStatement pstmtInsert = con.prepareStatement(sql)) {
                pstmtInsert.setInt(1, idAlumno);
                do {
                    System.out.println("--> Ingrese el nombre de la asignatura: ");
                    asignatura = scanner.nextLine();
                    isValid = validacion.validarString(asignatura);
                } while (!isValid);
                do {
                    System.out.println("--> Ingrese el curso: ");
                    curso = scanner.nextLine();
                    isValid = validacion.validarString(curso);
                } while (!isValid);
                do {
                    try {
                        System.out.println("--> Ingrese la nota: ");
                        nota = scanner.nextInt();
                        isValid = validacion.validarNotas(nota);
                        if (!isValid) {
                            System.out.println("[i] Por favor, ingrese una nota válida");
                        }
                    } catch (InputMismatchException ex) {
                        System.out.println("[i] Por favor, ingrese un número válido para la nota");
                        isValid = false;
                        scanner.nextLine();
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                } while (!isValid);

                pstmtInsert.setString(2, asignatura);
                pstmtInsert.setString(3, curso);
                pstmtInsert.setInt(4, nota);
                pstmtInsert.executeUpdate();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void actualizarFamiliar(String url, String username, String password) {
        Scanner scanner = new Scanner(System.in);
        String sql = "UPDATE Familiar SET custodia = ? WHERE nombre = ?";
        String sqlCheck = "SELECT * FROM Familiar WHERE nombre = ?";
        try (Connection con = DriverManager.getConnection(url, username, password); PreparedStatement pstmtInsert = con.prepareStatement(sql); PreparedStatement pstmtCheck = con.prepareStatement(sqlCheck)) {
            System.out.println("Ingrese el nombre del familiar a actualizar: ");
            String nombreFamiliar = scanner.nextLine();
            pstmtCheck.setString(1, nombreFamiliar);
            ResultSet rsCheck = pstmtCheck.executeQuery();
            if (!rsCheck.next()) {
                System.out.println("[i] No se encontró ningún familiar con el nombre ingresado");
                System.out.println("--> Pulsa enter para continuar");
                scanner.nextLine();
                return; // Si no se encontró ningún familiar, terminamos el método
            }
            System.out.println("Ingrese si es custodio (Si/No): ");
            String custodia = scanner.nextLine();
            while (true) {
                if (custodia.equalsIgnoreCase("si")) {
                    pstmtInsert.setBoolean(1, true);
                    break;
                } else if (custodia.equalsIgnoreCase("no")) {
                    pstmtInsert.setBoolean(1, false);
                    break;
                } else {
                    System.out.println("[i] Por favor, ingrese un valor válido para la custodia (Si/No)");
                    custodia = scanner.nextLine();
                }
            }
            pstmtInsert.setString(2, nombreFamiliar);
            pstmtInsert.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void borrarAlumno(String url, String username, String password) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Ingrese el nombre del alumno a borrar: ");
        String nombre = scanner.nextLine();

        // Obtener el ID del alumno a borrar
        String sqlId = "SELECT id FROM Alumno WHERE nombre = ?";
        int idAlumno;
        try (Connection con = DriverManager.getConnection(url, username, password); PreparedStatement pstmt = con.prepareStatement(sqlId)) {
            pstmt.setString(1, nombre);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                idAlumno = rs.getInt(1);
            } else {
                throw new RuntimeException("No se encontró el alumno con el nombre proporcionado");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        // Borrar las entradas relacionadas en las otras tablas
        String sqlBorrarDireccion = "DELETE FROM Direccion WHERE idAlumno = ?";
        String sqlBorrarFamiliar = "DELETE FROM Familiar WHERE idAlumno = ?";
        String sqlBorrarAsignatura = "DELETE FROM Asignatura WHERE idAlumno = ?";
        // Agrega aquí las operaciones de borrado para las otras tablas relacionadas

        // Realizar las operaciones de borrado en las tablas
        try (Connection con = DriverManager.getConnection(url, username, password)) {
            try (PreparedStatement pstmt = con.prepareStatement(sqlBorrarDireccion)) {
                pstmt.setInt(1, idAlumno);
                pstmt.executeUpdate();
            }
            try (PreparedStatement pstmt = con.prepareStatement(sqlBorrarFamiliar)) {
                pstmt.setInt(1, idAlumno);
                pstmt.executeUpdate();
            }
            try (PreparedStatement pstmt = con.prepareStatement(sqlBorrarAsignatura)) {
                pstmt.setInt(1, idAlumno);
                pstmt.executeUpdate();
            }
            // Ejecuta aquí las operaciones de borrado para las otras tablas relacionadas
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        // Borrar el alumno de la tabla Alumno
        String sqlBorrarAlumno = "DELETE FROM Alumno WHERE nombre = ?";
        try (Connection con = DriverManager.getConnection(url, username, password); PreparedStatement pstmt = con.prepareStatement(sqlBorrarAlumno)) {
            pstmt.setString(1, nombre);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void actualizarAsignatura(String url, String username, String password) {
        Scanner scanner = new Scanner(System.in);
        Connection con = null;
        PreparedStatement pstmt = null;
        String sql = "UPDATE Asignatura SET nombreAsignatura = ?, curso = ?, notas = ? WHERE idAlumno = ?";
        try {
            con = DriverManager.getConnection(url, username, password);

            int idAlumno = obtenerIdAlumno(con, scanner);
            if (idAlumno == -1) {
                return; // Si el método retorna -1, salimos del método
            }

            // Actualizar la asignatura
            System.out.println("--> Ingrese el nuevo nombre de la asignatura: ");
            String nombreAsignatura = scanner.nextLine();
            System.out.println("--> Ingrese curso: ");
            String curso = scanner.nextLine();
            System.out.println("--> Ingrese la nota: ");
            int nota = scanner.nextInt();

            try (PreparedStatement pstmtUpdate = con.prepareStatement(sql)) {
                pstmtUpdate.setString(1, nombreAsignatura);
                pstmtUpdate.setString(2, curso);
                pstmtUpdate.setInt(3, nota);
                pstmtUpdate.setInt(4, idAlumno);
                pstmtUpdate.executeUpdate();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void actualizarAlumno(String url, String username, String password) throws SQLException {
        Scanner scanner = new Scanner(System.in);


        // Obtener el ID del alumno a actualizar
        Connection con = DriverManager.getConnection(url, username, password);
        int idAlumno;
        try {
            idAlumno = obtenerIdAlumno(con, scanner);
            if (idAlumno == -1) {
                System.out.println("No se encontró el alumno con el nombre proporcionado");
                return;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        System.out.println("Ingrese el nuevo nombre del alumno: ");
        String nuevoNombre = scanner.nextLine();
        System.out.println("Ingrese el nuevo teléfono del alumno: ");
        int telefono = scanner.nextInt();
        System.out.println("Ingrese la nueva dirección del alumno: ");
        scanner.nextLine(); // Consumir el salto de línea restante
        String direccion = scanner.nextLine();

        // Insertar la nueva dirección en la tabla Direccion
        String sqlInsertarDireccion = "INSERT INTO Direccion (idAlumno, direccion) VALUES (?, ?)";
        try (PreparedStatement pstmt = con.prepareStatement(sqlInsertarDireccion)) {
            pstmt.setInt(1, idAlumno);
            pstmt.setString(2, direccion);
            pstmt.executeUpdate();
        }

        // Actualizar la dirección en la tabla Alumno
        String sqlAlumno = "UPDATE Alumno SET nombre = ?, telefono = ?, direccion = ? WHERE id = ?";
        try (PreparedStatement pstmt = con.prepareStatement(sqlAlumno)) {
            pstmt.setString(1, nuevoNombre);
            pstmt.setInt(2, telefono);
            pstmt.setString(3, direccion);
            pstmt.setInt(4, idAlumno);
            pstmt.executeUpdate();
        }
    }

    public void borrarDireccion(String url, String username, String password) {
        Scanner scanner = new Scanner(System.in);
        Connection con = null;
        PreparedStatement pstmt = null;
        String sqlCheck = "SELECT * FROM Direccion WHERE direccion = ?";
        String sqlDelete = "DELETE FROM Direccion WHERE direccion = ?";
        int opcion;
        do {
            try {
                con = DriverManager.getConnection(url, username, password);
                pstmt = con.prepareStatement(sqlCheck);
                System.out.println("Ingrese la dirección a borrar: ");
                String direccion = scanner.nextLine();
                pstmt.setString(1, direccion);
                ResultSet rs = pstmt.executeQuery();
                if (!rs.next()) {
                    System.out.println("No se encontró la dirección a borrar");
                    System.out.println("¿Desea volver a intentarlo? (1 para sí, 2 para no)");
                    do {
                        while (!scanner.hasNextInt()) {
                            System.out.println("[i] Por favor, ingrese un valor válido (1 para sí, 2 para no)");
                            scanner.next();
                        }
                        opcion = (scanner.nextInt());
                    } while (opcion != 1 && opcion != 2);
                    if (opcion == 2) {
                        return;
                    }
                } else {
                    pstmt = con.prepareStatement(sqlDelete);
                    pstmt.setString(1, direccion);
                    pstmt.executeUpdate();
                    System.out.println("[i] Dirección borrada correctamente");
                    System.out.println("¿Desea borrar otra dirección? (1 para sí, 2 para no)");
                    do {
                        while (!scanner.hasNextInt()) {
                            System.out.println("[i] Por favor, ingrese un valor válido (1 para sí, 2 para no)");
                            scanner.next();
                        }
                        opcion = (scanner.nextInt());
                    } while (opcion != 1 && opcion != 2);
                    if (opcion == 2) {
                        return;
                    }
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            scanner.nextLine();
        } while (true);
    }

    public void borrarFamiliar(String url, String username, String password) {
        Scanner scanner = new Scanner(System.in);
        Connection con = null;
        PreparedStatement pstmt = null;
        String sqlCheck = "SELECT * FROM Familiar WHERE nombre = ?";
        String sqlDelete = "DELETE FROM Familiar WHERE nombre = ?";
        int opcion;
        do {
            try {
                con = DriverManager.getConnection(url, username, password);
                pstmt = con.prepareStatement(sqlCheck);
                System.out.println("Ingrese el nombre del familiar a borrar: ");
                String nombre = scanner.nextLine();
                pstmt.setString(1, nombre);
                ResultSet rs = pstmt.executeQuery();
                if (!rs.next()) {
                    System.out.println("No se encontró el familiar a borrar");
                    System.out.println("¿Desea volver a intentarlo? (1 para sí, 2 para no)");
                    do {
                        while (!scanner.hasNextInt()) {
                            System.out.println("[i] Por favor, ingrese un valor válido (1 para sí, 2 para no)");
                            scanner.next();
                        }
                        opcion = (scanner.nextInt());
                        scanner.nextLine(); // Consumir el salto de línea restante
                    } while (opcion != 1 && opcion != 2);
                    if (opcion == 2) {
                        return;
                    }
                } else {
                    pstmt = con.prepareStatement(sqlDelete);
                    pstmt.setString(1, nombre);
                    pstmt.executeUpdate();
                    System.out.println("[i] Familiar borrado correctamente");
                    System.out.println("¿Desea borrar otro familiar? (1 para sí, 2 para no)");
                    do {
                        while (!scanner.hasNextInt()) {
                            System.out.println("[i] Por favor, ingrese un valor válido (1 para sí, 2 para no)");
                            scanner.next();
                        }
                        opcion = (scanner.nextInt());
                    } while (opcion != 1 && opcion != 2);
                    if (opcion == 2) {
                        return;
                    }
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            scanner.nextLine();
        } while (true);
    }

    public void eliminarAsignatura(String url, String username, String password) {
        Scanner scanner = new Scanner(System.in);
        Connection con = null;
        PreparedStatement pstmt = null;
        String sqlCheck = "SELECT * FROM Asignatura WHERE nombreAsignatura = ?";
        String sqlDelete = "DELETE FROM Asignatura WHERE nombreAsignatura = ?";
        int opcion;
        do {
            try {
                con = DriverManager.getConnection(url, username, password);
                pstmt = con.prepareStatement(sqlCheck);
                System.out.println("Ingrese el nombre de la asignatura a borrar: ");
                String nombreAsignatura = scanner.nextLine();
                pstmt.setString(1, nombreAsignatura);
                ResultSet rs = pstmt.executeQuery();
                if (!rs.next()) {
                    System.out.println("No se encontró la asignatura a borrar");
                    System.out.println("¿Desea volver a intentarlo? (1 para sí, 2 para no)");
                    do {
                        while (!scanner.hasNextInt()) {
                            System.out.println("[i] Por favor, ingrese un valor válido (1 para sí, 2 para no)");
                            scanner.next();
                        }
                        opcion = (scanner.nextInt());
                    } while (opcion != 1 && opcion != 2);
                    if (opcion == 2) {
                        return;
                    }
                } else {
                    pstmt = con.prepareStatement(sqlDelete);
                    pstmt.setString(1, nombreAsignatura);
                    pstmt.executeUpdate();
                    System.out.println("[i] Asignatura borrada correctamente");
                    System.out.println("¿Desea borrar otra asignatura? (1 para sí, 2 para no)");
                    do {
                        while (!scanner.hasNextInt()) {
                            System.out.println("[i] Por favor, ingrese un valor válido (1 para sí, 2 para no)");
                            scanner.next();
                        }
                        opcion = (scanner.nextInt());
                    } while (opcion != 1 && opcion != 2);
                    if (opcion == 2) {
                        return;
                    }
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            scanner.nextLine();
        } while (true);
    }

    public int obtenerIdAlumno(Connection con, Scanner scanner) throws SQLException {
        String nombreAlumno;
        int idAlumno;
        do {
            System.out.println("Ingrese el nombre del alumno: ");
            nombreAlumno = scanner.nextLine();

            // Obtener el idAlumno a partir del nombre del alumno
            String sqlId = "SELECT id FROM Alumno WHERE nombre = ?";
            try (PreparedStatement pstmtId = con.prepareStatement(sqlId)) {
                pstmtId.setString(1, nombreAlumno);
                ResultSet rs = pstmtId.executeQuery();
                if (rs.next()) {
                    idAlumno = rs.getInt(1);
                    break;
                } else {
                    System.out.println("No se encontró el alumno con el nombre proporcionado");
                    System.out.println("¿Desea volver a intentarlo? (1 para sí, 2 para no)");
                    int opcion;
                    do {
                        while (!scanner.hasNextInt()) {
                            System.out.println("[i] Por favor, ingrese un valor válido (1 para sí, 2 para no)");
                            scanner.next();
                        }
                        opcion = scanner.nextInt();
                        scanner.nextLine(); // Consumir el salto de línea restante
                    } while (opcion != 1 && opcion != 2);
                    if (opcion == 2) {
                        return -1;
                    }
                }
            }
        } while (true);
        return idAlumno;
    }

}