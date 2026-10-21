package modelo;

public class Mascota {
    private int id;
    private String nombre;
    private int edad;
    private String especie;
    private String raza;
    private String genero;
    private double peso;
    private int id_dueño;
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public int getEdad() {
        return edad;
    }
    public void setEdad(int edad) {
        this.edad = edad;
    }
    public String getEspecie() {
        return especie;
    }
    public void setEspecie(String especie) {
        this.especie = especie;
    }
    public String getRaza() {
        return raza;
    }
    public void setRaza(String raza) {
        this.raza = raza;
    }
    public String getGenero() {
        return genero;
    }
    public void setGenero(String genero) {
        this.genero = genero;
    }
    public double getPeso() {
        return peso;
    }
    public void setPeso(double peso) {
        this.peso = peso;
    }
    public int getId_dueño() {
        return id_dueño;
    }
    public void setId_dueño(int id_dueño) {
        this.id_dueño = id_dueño;
    }
    
    public Mascota(int id, String nombre, int edad, String especie, String raza, String genero, double peso,
            int id_dueño) {
        this.id = id;
        this.nombre = nombre;
        this.edad = edad;
        this.especie = especie;
        this.raza = raza;
        this.genero = genero;
        this.peso = peso;
        this.id_dueño = id_dueño;
    }
}