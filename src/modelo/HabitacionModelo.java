package modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Scanner;

import vista.Formulario;
import vista.Visor;

public class HabitacionModelo {
	
	public static Habitacion getHabitacionID(int id_habitacion) {
		ArrayList<Hotel> hoteles = new ArrayList<Hotel>();
		Habitacion habitacion = new Habitacion();
		try {
			String sql = "SELECT * FROM habitaciones WHERE habitaciones.id = " + id_habitacion;
			Connection conexion = Conector.conectar();
			Statement st = conexion.createStatement();
			ResultSet rst = st.executeQuery(sql);
			while(rst.next()) {
				Hotel hotel = HotelModelo.getHotelById(rst.getInt("id_hotel"));
				hoteles.add(hotel);
				habitacion.setId(rst.getInt("id"));
				habitacion.setNumero(rst.getInt("numero"));
				habitacion.setDescripcion(rst.getString("descripcion"));
				habitacion.setPrecio(rst.getInt("precio"));
				habitacion.setId_hotel(rst.getInt("id_hotel"));
				habitacion.setHoteles(hoteles);
			}
			//aqui
			Conector.CERRAR();
			return habitacion;
		} catch (SQLException e) {
			System.out.println(e);
		}
		return null;
	}
	
	public static ArrayList<Habitacion> getHabitacionesHotel(Scanner scan, int id_hotel) {
		ArrayList<Habitacion> habitaciones = new ArrayList<Habitacion>();
		try {
			String sql = "SELECT * FROM habitaciones WHERE id_hotel = ?";
			PreparedStatement prst = Conector.conectar().prepareStatement(sql);
			prst.setInt(1, id_hotel);
			ResultSet rst = prst.executeQuery();
			while(rst.next()) {
				Habitacion habitacion = new Habitacion();
				habitacion.setDescripcion(rst.getString("descripcion"));
				habitacion.setId(rst.getInt("id"));
				habitacion.setNumero(rst.getInt("numero"));
				habitacion.setPrecio(rst.getInt("precio"));
				habitaciones.add(habitacion);
			}
			return habitaciones;
		} catch (Exception e) {
		System.err.println(e);
		}
		return null;
	}
	
	public static Hotel getHabitaciones(Hotel hotel) {
		ArrayList<Habitacion> habitaciones = new ArrayList<Habitacion>();
		try {
			String sql = "SELECT * FROM habitaciones WHERE id_hotel = ?";
			PreparedStatement prst = Conector.conectar().prepareStatement(sql);
			prst.setInt(1, hotel.getId());
			ResultSet rs = prst.executeQuery();
			while(rs.next()) {
				Habitacion habitacion = new Habitacion();
				habitacion.setDescripcion(rs.getString("descripcion"));
				habitacion.setId(rs.getInt("id"));
				habitacion.setNumero(rs.getInt("numero"));
				habitacion.setPrecio(rs.getInt("precio"));
				habitaciones.add(habitacion);
			}
//			hotel.setHabitaciones(habitaciones);
			return hotel;
		} catch (Exception e) {
			System.out.println(e);
		}
		return null;
	}

	public static void editarHabitacion(Habitacion habitacion) {
		try {
			String sql = "UPDATE habitaciones SET numero=?,descripcion=?,precio=? WHERE id=?";
			PreparedStatement prst = Conector.conectar().prepareStatement(sql);
			prst.setInt(1, habitacion.getNumero());
			prst.setString(2, habitacion.getDescripcion());
			prst.setFloat(3, habitacion.getPrecio());
			prst.setInt(4, habitacion.getId());
			prst.executeUpdate();
			prst.close();
			Visor.mostrHabitacion(habitacion);
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	
	public static void darAltaHabitacion(Hotel hotel, Scanner scan) {
		Habitacion habitacion = new Habitacion();
		habitacion = Formulario.pedirNuevosDatosHabitacion(scan,habitacion);
		try {
			String sql = "INSERT INTO habitaciones(id_hotel,numero,descripcion,precio) VALUES (?,?,?,?)";
			PreparedStatement prst = Conector.conectar().prepareStatement(sql);
			prst.setInt(1, hotel.getId());
			prst.setInt(2, habitacion.getNumero());
			prst.setString(3,  habitacion.getDescripcion());
			prst.setFloat(4,  habitacion.getPrecio());
		} catch (Exception e) {
			System.err.println(e);
		}
	}
	

	public static void borrarHabitacion(Scanner scan, int id_habitacion) {
		
		try {
			String sql = "DELETE FROM habitaciones WHERE habitaciones.id = ?";
			PreparedStatement prst = Conector.conectar().prepareStatement(sql);
			prst.setInt(1, id_habitacion);
			prst.executeUpdate();
			prst.close();
			System.out.println("Habitacion Borrada Exitosamente");
		} catch (Exception e) {
			System.out.println(e);
		}

	}


}
