package modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Scanner;

import vista.Formulario;
import vista.Menu;
import vista.Visor;

public class HotelModelo{
	public static Hotel getHotelById(int id) {
		Hotel hotel = new Hotel();
		try {
			String sql = "SELECT * FROM hoteles WHERE hoteles.id = " + id;
			Connection conexion = Conector.conectar();
			Statement st = conexion.createStatement();
			ResultSet rst = st.executeQuery(sql);
			while(rst.next()) {
				hotel.setId(rst.getInt("id"));
				hotel.setCif(rst.getString("cif"));
				hotel.setNombre(rst.getString("nombre"));
				hotel.setGerente(rst.getString("gerente"));
				hotel.setEstrella(rst.getInt("estrellas"));
				hotel.setCompania(rst.getString("compania"));
			}
			Conector.CERRAR();
			return hotel;
		} catch (SQLException e) {
			System.out.println(e);
		}
		return null;
	}

	public static Hotel getHotelByNombre(String nombreHotel) {
		try {
			String sql = "SELECT * FROM hoteles WHERE hoteles.nombre = ?";
			PreparedStatement psrt = Conector.conectar().prepareStatement(sql);
			psrt.setString(1, nombreHotel);
			ResultSet rst = psrt.executeQuery();
			Hotel hotel = new Hotel();
			if (rst.next()) {
				hotel.setCif(rst.getString("cif"));
				hotel.setCompania(rst.getString("compania"));
				hotel.setEstrella(rst.getInt("estrella"));
				hotel.setGerente(rst.getString("gerente"));
				hotel.setId(rst.getInt("id"));
				hotel.setNombre(rst.getString("nombre"));
				return hotel;
			}else {
//				Visor.mostrarError();
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return null;
	}
	
	public static Hotel verHotelHabitaciones(Scanner scan, String hotel_cif) {
		Hotel hotel = new Hotel();
		try {
			String sql = "SELECT * FROM hoteles WHERE hoteles.cif = ?";
			Connection con = Conector.conectar();
			PreparedStatement st = con.prepareStatement(sql);
			st.setString(1, hotel_cif);
			ResultSet rst = st.executeQuery();
			if (rst.next()) {
				hotel.setCif(rst.getString("cif"));
				hotel.setCompania(rst.getString("compania"));
				hotel.setEstrella(rst.getInt("estrellas"));
				hotel.setGerente(rst.getString("gerente"));
				hotel.setId(rst.getInt("id"));
				hotel.setNombre(rst.getString("nombre"));
			}else {
				Visor.mostrarError();
			}
			con.close();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		hotel = HabitacionModelo.getHabitaciones(hotel);
		return hotel;
	}
	
public static void darAltaHotel(Scanner scan, Hotel hotel) {
		
		try {
			String sql = "INSERT INTO hoteles(cif,nombre,gerente,estrellas,compania) VALUES (?,?,?,?,?)";
			PreparedStatement prst = Conector.conectar().prepareStatement(sql);
			prst.setString(1, hotel.getCif());
			prst.setString(2, hotel.getNombre());
			prst.setString(3, hotel.getGerente());
			prst.setInt(4, hotel.getEstrella());
			prst.setString(5, hotel.getCompania() );
			prst.executeUpdate();
			prst.close();
		} catch (Exception e) {
			System.err.println(e);
		}
		int opciones = 0;
		do {
			Visor.mostrarOopcionesHotel();
			opciones = Formulario.pedirOpciones(scan);
			switch (opciones) {
			case 1:
				HabitacionModelo.darAltaHabitacion(hotel,scan);
				break;
			default:
				break;
			}
		} while (opciones!=Menu.SALIR);
	}
}
