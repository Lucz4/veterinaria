package modelo;

import java.sql.SQLException;
import java.util.ArrayList;

import dao.VeterinarioDAO;

public class GestionVeterinario {
    private VeterinarioDAO VeterinarioDAO = new VeterinarioDAO();

    public int guardar(Veterinario Veterinario) {
        try {
            return VeterinarioDAO.guardar(Veterinario);
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    public ArrayList<Veterinario> listar() {
        try {
            return VeterinarioDAO.listar();
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public boolean actualizar(Veterinario Veterinario) {
        try {
            return VeterinarioDAO.actualizar(Veterinario);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean eliminar(int id) {
        try {
            return VeterinarioDAO.eliminar(id);
        } catch (SQLException e) {
            String estadoSql = e.getSQLState();
            if (estadoSql != null && estadoSql.startsWith("23")) {
                throw new IllegalStateException(
                        "No se puede eliminar el veterinario porque tiene consultas activas asociadas.", e);
            }
            e.printStackTrace();
            return false;
        }
    }
}