package modelo;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class Reserva {
	private int id;
	private ArrayList<Habitacion> habitaciones = new ArrayList<Habitacion>();
	private ArrayList<Cliente> clientes = new ArrayList<Cliente>();
	private java.util.Date desde;
	private java.util.Date hasta;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public java.util.Date getDesde() {
		return desde;
	}
	public void setDesde(java.util.Date fechaDesde) {
		this.desde = fechaDesde;
	}
	public java.util.Date getHasta() {
		return hasta;
	}
	public void setHasta(java.util.Date fechaAsta) {
		this.hasta = fechaAsta;
	}
	public void setClientes(ArrayList<Cliente> clientes) {
		this.clientes=clientes;
	}
	public void setHabitaciones(ArrayList<Habitacion> habitaciones) {
		this.habitaciones=habitaciones;
	}
	public ArrayList<Habitacion> getHabitaciones(){
		return this.habitaciones;
	}
	@Override
	public String toString() {
		return "Reserva [id=" + id + ", \n habitacion=" + habitaciones.toString() +  "\n Cliente=" + clientes.toString() + ",\n desde=" + desde
				+ ", hasta=" + hasta + "]";
	}
	
	
}
