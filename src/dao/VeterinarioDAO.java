package dao;

import conexion.Conexion;
import modelo.Veterinario;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * DAO (Data Access Object) de la tabla "duenos".
 * Recibe objetos Dueno (del paquete modelo) y los guarda,
 * actualiza, elimina o lee desde MySQL usando la clase Conexion.
 */
public class VeterinarioDAO {

    /**
     * Inserta un dueno nuevo.
     *
     * @return el id que asigno MySQL, o -1 si no se inserto nada.
     */
    public int guardar(Veterinario Veterinario) throws SQLException {
        String sql = "INSERT INTO `veterinario`(`nombre`, `password`, `especialidad`) VALUES (?,?,?)";

        try (Connection cn = Conexion.getConexion();
             PreparedStatement ps = cn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, Veterinario.getNombre());
            ps.setString(2, Veterinario.getPassword());
            ps.setString(3, Veterinario.getEspecialidad());

            int filas = ps.executeUpdate();
            if (filas == 0) {
                return -1;
            }

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
            return -1;
        }
    }

    /**
     * Devuelve todos los duenos guardados, ordenados por nombre.
     */
    public ArrayList<Veterinario> listar() throws SQLException {
        String sql = "SELECT id, nombre, password, especialidad FROM veterinario ORDER BY nombre";
        ArrayList<Veterinario> lista = new ArrayList<>();

        try (Connection cn = Conexion.getConexion();
             PreparedStatement ps = cn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                lista.add(new Veterinario(
                        rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getString("password"),
                        rs.getString("especialidad")));
            }
        }
        return lista;
    }

    /**
     * Actualiza los datos de un dueno ya existente (usando su id).
     *
     * @return true si se actualizo algun registro.
     */
    public boolean actualizar(Veterinario veterinario) throws SQLException {
        boolean actualizarPassword = veterinario.getPassword() != null && !veterinario.getPassword().isEmpty();
        String sql = actualizarPassword
                ? "UPDATE veterinario SET nombre = ?, password = ?, especialidad = ? WHERE id = ?"
                : "UPDATE veterinario SET nombre = ?, especialidad = ? WHERE id = ?";

        try (Connection cn = Conexion.getConexion();
             PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setString(1, veterinario.getNombre());
            if (actualizarPassword) {
                ps.setString(2, veterinario.getPassword());
                ps.setString(3, veterinario.getEspecialidad());
                ps.setInt(4, veterinario.getId());
            } else {
                ps.setString(2, veterinario.getEspecialidad());
                ps.setInt(3, veterinario.getId());
            }

            return ps.executeUpdate() > 0;
        }
    }

    /**
     * Elimina un dueno por su id.
     *
     * @return true si se elimino algun registro.
     */
    public boolean eliminar(int id) throws SQLException {
        String sql = "DELETE FROM veterinario WHERE id = ?";

        try (Connection cn = Conexion.getConexion();
             PreparedStatement ps = cn.prepareStatement(sql)) {

            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        }
    }
}