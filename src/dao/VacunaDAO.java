package dao;

import conexion.Conexion;
import modelo.Vacuna;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;

public class VacunaDAO {

    /**
     * Inserta un dueno nuevo.
     *
     * @return el id que asigno MySQL, o -1 si no se inserto nada.
     */
    public int guardar(Vacuna vacuna) throws SQLException {
        String sql = "INSERT INTO vacuna (tipo, fecha, hora, vencimiento, id_mascota, id_consulta) "
                + "VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection cn = Conexion.getConexion();
             PreparedStatement ps = cn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, vacuna.getTipo());
            ps.setDate(2, Date.valueOf(vacuna.getFecha()));
            ps.setTime(3, Time.valueOf(vacuna.getHora()));
            ps.setDate(4, Date.valueOf(vacuna.getVencimiento()));
            ps.setInt(5, vacuna.getId_mascota());
            ps.setInt(6, vacuna.getId_consulta());

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
     * Devuelve todas las vacunas guardadas, ordenadas por tipo.
     */
    public ArrayList<Vacuna> listar() throws SQLException {
        String sql = "SELECT id, tipo, fecha, hora, vencimiento, id_mascota, id_consulta FROM vacuna ORDER BY tipo";
        ArrayList<Vacuna> lista = new ArrayList<>();

        try (Connection cn = Conexion.getConexion();
             PreparedStatement ps = cn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                lista.add(new Vacuna(
                        rs.getInt("id"),
                        rs.getString("tipo"),
                        rs.getDate("fecha").toLocalDate(),
                        rs.getTime("hora").toLocalTime(),
                        rs.getDate("vencimiento").toLocalDate(),
                        rs.getInt("id_mascota"),
                        rs.getInt("id_consulta")));
            }
        }
        return lista;
    }

    /**
     * Actualiza los datos de un dueno ya existente (usando su id).
     *
     * @return true si se actualizo algun registro.
     */
    public boolean actualizar(Vacuna vacuna) throws SQLException {
        String sql = "UPDATE vacuna SET tipo = ?, fecha = ?, hora = ?, vencimiento = ?, "
                + "id_mascota = ?, id_consulta = ? WHERE id = ?";

        try (Connection cn = Conexion.getConexion();
             PreparedStatement ps = cn.prepareStatement(sql)) {

            ps.setString(1, vacuna.getTipo());
            ps.setDate(2, Date.valueOf(vacuna.getFecha()));
            ps.setTime(3, Time.valueOf(vacuna.getHora()));
            ps.setDate(4, Date.valueOf(vacuna.getVencimiento()));
            ps.setInt(5, vacuna.getId_mascota());
            ps.setInt(6, vacuna.getId_consulta());
            ps.setInt(7, vacuna.getId());

            return ps.executeUpdate() > 0;
        }
    }

    /**
     * Elimina un dueno por su id.
     *
     * @return true si se elimino algun registro.
     */
    public boolean eliminar(int id) throws SQLException {
        String sql = "DELETE FROM vacuna WHERE id = ?";

        try (Connection cn = Conexion.getConexion();
             PreparedStatement ps = cn.prepareStatement(sql)) {

            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        }
    }
}