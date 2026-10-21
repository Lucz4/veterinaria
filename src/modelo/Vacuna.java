package modelo;

import java.time.LocalDate;
import java.time.LocalTime;

public class Vacuna {
    private int id;
    private String tipo;
    private LocalDate fecha;
    private LocalTime hora;
    private LocalDate vencimiento;
    private int id_mascota;
    private int id_consulta;

    public Vacuna(int id, String tipo, LocalDate fecha, LocalTime hora, LocalDate vencimiento,
            int id_mascota, int id_consulta) {
        this.id = id;
        this.tipo = tipo;
        this.fecha = fecha;
        this.hora = hora;
        this.vencimiento = vencimiento;
        this.id_mascota = id_mascota;
        this.id_consulta = id_consulta;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public LocalTime getHora() {
        return hora;
    }

    public void setHora(LocalTime hora) {
        this.hora = hora;
    }

    public LocalDate getVencimiento() {
        return vencimiento;
    }

    public void setVencimiento(LocalDate vencimiento) {
        this.vencimiento = vencimiento;
    }

    public int getId_mascota() {
        return id_mascota;
    }

    public void setId_mascota(int id_mascota) {
        this.id_mascota = id_mascota;
    }

    public int getId_consulta() {
        return id_consulta;
    }

    public void setId_consulta(int id_consulta) {
        this.id_consulta = id_consulta;
    }
}
