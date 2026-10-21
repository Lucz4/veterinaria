package modelo;

import java.util.ArrayList;
import java.sql.SQLException;
import dao.DuenoDAO;

public class GestionDueño {
    private DuenoDAO duenoDAO = new DuenoDAO();

    public int guardar(Dueno dueno) {
        try {
            return duenoDAO.guardar(dueno);
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    public ArrayList<Dueno> listar() {
        try {
            return duenoDAO.listar();
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public boolean actualizar(Dueno dueno) {
        try {
            return duenoDAO.actualizar(dueno);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean eliminar(int id) throws SQLException {
        return duenoDAO.eliminar(id);
    }
}