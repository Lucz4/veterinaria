package modelo;

import java.time.LocalDate;
import java.time.LocalTime;

public class Consulta {
    private int id;
    private String Procedimiento;
    private LocalDate fecha;
    private LocalTime hora;
    private int id_mascota;
    private int id_veterinario;
    
	public Consulta(int id, String procedimiento, LocalDate fecha, LocalTime hora, int id_mascota, int id_veterinario) {
		this.id = id;
		Procedimiento = procedimiento;
		this.fecha = fecha;
		this.hora = hora;
		this.id_mascota = id_mascota;
		this.id_veterinario = id_veterinario;
	}
    
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getProcedimiento() {
		return Procedimiento;
	}
	public void setProcedimiento(String procedimiento) {
		Procedimiento = procedimiento;
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
	public int getId_mascota() {
		return id_mascota;
	}
	public void setId_mascota(int id_mascota) {
		this.id_mascota = id_mascota;
	}
	public int getId_veterinario() {
		return id_veterinario;
	}
	public void setId_veterinario(int id_veterinario) {
		this.id_veterinario = id_veterinario;
	}
}
