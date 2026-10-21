package modelo;

import java.util.ArrayList;
import dao.VacunaDAO;

public class GestionVacuna {
    private VacunaDAO VacunaDAO = new VacunaDAO();

    public int guardar(Vacuna vacuna) {
        try {
            return VacunaDAO.guardar(vacuna);
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    public ArrayList<Vacuna> listar() {
        try {
            return VacunaDAO.listar();
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public boolean actualizar(Vacuna vacuna) {
        try {
            return VacunaDAO.actualizar(vacuna);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean eliminar(int id) {
        try {
            return VacunaDAO.eliminar(id);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}