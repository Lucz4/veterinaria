package dao;

import conexion.Conexion;
import modelo.Mascota;

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
public class MascotaDAO {

    /**
     * Inserta un dueno nuevo.
     *
     * @return el id que asigno MySQL, o -1 si no se inserto nada.
     */
    public int guardar(Mascota mascota) throws SQLException {
        String sql = "INSERT INTO mascota (nombre, edad, especie, raza, genero, peso, id_dueño) VALUES (?, ?, ?, ?, ?, ?, ?)";
       // String sql2 = "INSERT INTO mascota (nombre, edad, especie, raza, genero, peso, id_dueño) VALUES (" + mascota.getNombre()+ ", " + mascota.getEdad()+ ", " + mascota.getEspecie()+", " + mascota.getRaza()+", " + mascota.getGenero()+", " + mascota.getPeso()+", "+ mascota.getId_dueño()+")";
        //System.out.println(sql2);

        try (Connection cn = Conexion.getConexion();
             PreparedStatement ps = cn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, mascota.getNombre());
            ps.setInt(2, mascota.getEdad());
            ps.setString(3, mascota.getEspecie());
            ps.setString(4, mascota.getRaza());
            ps.setString(5, mascota.getGenero());
            ps.setDouble(6, mascota.getPeso());
            ps.setInt(7, mascota.getId_dueño());

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
    public ArrayList<Mascota> listar() throws SQLException {
        String sql = "SELECT id, nombre, edad, especie, raza, genero, peso, id_dueño FROM mascota ORDER BY nombre";
        ArrayList<Mascota> lista = new ArrayList<>();

        try (Connection cn = Conexion.getConexion();
             PreparedStatement ps = cn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                lista.add(new Mascota(
                        rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getInt("edad"),
                        rs.getString("especie"),
                        rs.getString("raza"),
                        rs.getString("genero"),
                        rs.getDouble("peso"),
                        rs.getInt("id_dueño")));
            }
        }
        return lista;
    }

    /**
     * Actualiza los datos de un dueno ya existente (usando su id).
     *
     * @return true si se actualizo algun registro.
     */
    public boolean actualizar(Mascota Mascota) throws SQLException {
        String sql = "UPDATE dueño SET nombre = ?, edad = ?, especie = ?, raza = ?, genero = ?, peso = ?, id_dueño = ? WHERE id = ?";

        try (Connection cn = Conexion.getConexion();
             PreparedStatement ps = cn.prepareStatement(sql)) {

            ps.setString(1, Mascota.getNombre());
            ps.setInt(2, Mascota.getEdad());
            ps.setString(3, Mascota.getEspecie());
            ps.setString(4, Mascota.getRaza());
            ps.setString(5, Mascota.getGenero());
            ps.setDouble(6, Mascota.getPeso());
            ps.setInt(7, Mascota.getId_dueño());

            return ps.executeUpdate() > 0;
        }
    }

    /**
     * Elimina un dueno por su id.
     *
     * @return true si se elimino algun registro.
     */
    public boolean eliminar(int id) throws SQLException {
        String sql = "DELETE FROM mascota WHERE id = ?";

        try (Connection cn = Conexion.getConexion();
             PreparedStatement ps = cn.prepareStatement(sql)) {

            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        }
    }
}
