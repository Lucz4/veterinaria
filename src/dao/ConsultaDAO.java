package dao;

import conexion.Conexion;
import modelo.Dueno;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import modelo.Consulta;

/**
 * DAO (Data Access Object) de la tabla "duenos".
 * Recibe objetos Dueno (del paquete modelo) y los guarda,
 * actualiza, elimina o lee desde MySQL usando la clase Conexion.
 */
public class ConsultaDAO {

    /**
     * Inserta un dueno nuevo.
     *
     * @return el id que asigno MySQL, o -1 si no se inserto nada.
     */
    public int guardar(Consulta Consulta) throws SQLException {
        String sql = "INSERT INTO consulta (procedimiento, fecha, hora, id_mascota, id_veterinario) VALUES (?, ?, ?, ?, ?)";

        try (Connection cn = Conexion.getConexion();
             PreparedStatement ps = cn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, Consulta.getProcedimiento());
            ps.setObject(2, Consulta.getFecha());
            ps.setObject(3, Consulta.getHora());
            ps.setInt(4, Consulta.getId_mascota());
            ps.setInt(5, Consulta.getId_veterinario());

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
    public ArrayList<Consulta> listar() throws SQLException {
        String sql = "SELECT id, procedimiento, fecha, hora, id_mascota, id_veterinario FROM consulta ORDER BY procedimiento";
        ArrayList<Consulta> lista = new ArrayList<>();

        try (Connection cn = Conexion.getConexion();
             PreparedStatement ps = cn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                lista.add(new Consulta(
                        rs.getInt("id"),
                        rs.getString("procedimiento"),
                        rs.getDate("fecha").toLocalDate(),
                        rs.getTime("hora").toLocalTime(),
                        rs.getInt("id_mascota"),
                        rs.getInt("id_veterinario")
                ));
            }
        }
        return lista;
    }

    /**
     * Actualiza los datos de un dueno ya existente (usando su id).
     *
     * @return true si se actualizo algun registro.
     */
    public boolean actualizar(Consulta Consulta) throws SQLException {
        String sql = "UPDATE consulta SET procedimiento = ?, fecha = ?, hora = ?, id_mascota = ?, id_veterinario = ? WHERE id = ?";

        try (Connection cn = Conexion.getConexion();
             PreparedStatement ps = cn.prepareStatement(sql)) {

            ps.setString(1, Consulta.getProcedimiento());
            ps.setObject(2, Consulta.getFecha());
            ps.setObject(3, Consulta.getHora());
            ps.setInt(4, Consulta.getId_mascota());
            ps.setInt(5, Consulta.getId_veterinario());
            ps.setInt(6, Consulta.getId());

            return ps.executeUpdate() > 0;
        }
    }

    /**
     * Elimina un dueno por su id.
     *
     * @return true si se elimino algun registro.
     */
    public boolean eliminar(int id) throws SQLException {
        String sql = "DELETE FROM consulta WHERE id = ?";

        try (Connection cn = Conexion.getConexion();
             PreparedStatement ps = cn.prepareStatement(sql)) {

            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        }
    }
}
