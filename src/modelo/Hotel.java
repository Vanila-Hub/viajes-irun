package modelo;

import java.util.ArrayList;

public class Hotel {
	private int id;
	private String cif;
	private String nombre;
	private String gerente;
	private int estrella;
	private String compania;
	private ArrayList<Habitacion> habitaciones = new ArrayList<Habitacion>();
	
	public ArrayList<Habitacion> getHabitaciones(){
		
		return habitaciones;
	}
	
	public void setHabitaciones(ArrayList<Habitacion> habitaciones) {
		this.habitaciones=habitaciones;
	}
	public int getId() {
		return id;
	}
	public int getEstrella() {
		return estrella;
	}
	public void setEstrella(int estrella) {
		this.estrella = estrella;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getCif() {
		return cif;
	}
	public void setCif(String cif) {
		this.cif = cif;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getGerente() {
		return gerente;
	}
	public void setGerente(String gerente) {
		this.gerente = gerente;
	}
	public String getCompania() {
		return compania;
	}
	public void setCompania(String compania) {
		this.compania = compania;
	}

//
	@Override
	public String toString() {
		return "Hotel [id=" + id + ", cif=" + cif + ", nombre=" + nombre + ", gerente=" + gerente + ", entrella="
				+ estrella + ", compania=" + compania + "Habitaciones" + habitaciones.toString();
	}
	
}
