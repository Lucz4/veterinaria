package conexion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Se encarga de abrir la conexion con MySQL (XAMPP).
 */
public class Conexion {

    // Cambia estos datos si tu XAMPP usa otros valores.
    private static final String URL =
            "jdbc:mysql://localhost:3306/veterinaria"
            + "?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true&characterEncoding=utf8";
    private static final String USUARIO = "root";
    private static final String CLAVE = ""; // XAMPP trae root sin contrasena

    public static Connection getConexion() throws SQLException {
        return DriverManager.getConnection(URL, USUARIO, CLAVE);
    }

    // Prueba rapida de la conexion.
    // Se ejecuta con: java -cp "bin;lib\mysql-connector-j-8.0.33.jar" conexion.Conexion
    public static void main(String[] args) {
        try (Connection cn = getConexion()) {
            System.out.println("Conexion correcta a veterinaria_db");
        } catch (SQLException e) {
            System.out.println("Error de conexion: " + e.getMessage());
        }
    }
}
