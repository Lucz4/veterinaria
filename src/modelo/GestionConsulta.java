package modelo;

import java.util.ArrayList;
import dao.ConsultaDAO;

public class GestionConsulta {
    private ConsultaDAO ConsultaDAO = new ConsultaDAO();

    public int guardar(Consulta consulta) {
        try {
            return ConsultaDAO.guardar(consulta);
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    public ArrayList<Consulta> listar() {
        try {
            return ConsultaDAO.listar();
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public boolean actualizar(Consulta consulta) {
        try {
            return ConsultaDAO.actualizar(consulta);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean eliminar(int id) {
        try {
            return ConsultaDAO.eliminar(id);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}