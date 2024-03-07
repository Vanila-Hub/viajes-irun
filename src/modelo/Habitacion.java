package modelo;

import java.util.ArrayList;

public class Habitacion {
	private int id;
	private int numero;
	private String descripcion;
	private int precio;
	private int id_hotel;
	
	public int getId_hotel() {
		return id_hotel;
	}
	public void setId_hotel(int id_hotel) {
		this.id_hotel = id_hotel;
	}

	private ArrayList<Hotel>hoteles=new ArrayList<Hotel>();
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getNumero() {
		return numero;
	}
	public void setNumero(int numero) {
		this.numero = numero;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public int getPrecio() {
		return precio;
	}
	public void setPrecio(int precio) {
		this.precio = precio;
	}
	public void setHoteles(ArrayList<Hotel> hoteles) {
		this.hoteles=hoteles;
	}
	@Override
	public String toString() {
		return "Habitacion [id=" + id + ", numero=" + numero + ", descripcion=" + descripcion + ", precio=" + precio
				+ ", id_hotel=" + id_hotel + ", hoteles=" + hoteles.toString() + "]";
	}


}
