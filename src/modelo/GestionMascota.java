package modelo;

import java.util.ArrayList;
import dao.MascotaDAO;

public class GestionMascota {
    private MascotaDAO MascotaDAO = new MascotaDAO();

    public int guardar(Mascota mascota) {
        try {
            return MascotaDAO.guardar(mascota);
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    public ArrayList<Mascota> listar() {
        try {
            return MascotaDAO.listar();
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public boolean actualizar(Mascota mascota) {
        try {
            return MascotaDAO.actualizar(mascota);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean eliminar(int id) {
        try {
            return MascotaDAO.eliminar(id);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}