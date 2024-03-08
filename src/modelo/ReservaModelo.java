package modelo;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

import vista.Formulario;
import vista.Visor;

public class ReservaModelo {
	public static ArrayList<Reserva> verReservas(int id_habitacion) {
		ArrayList<Reserva> reservas = new ArrayList<Reserva>();
		try {
			String sql = "SELECT * FROM reservas WHERE id_habitacion = ?";
			Connection conexion = Conector.conectar();
			PreparedStatement st = conexion.prepareStatement(sql);
			st.setInt(1, id_habitacion);
			ResultSet rst = st.executeQuery();
			while (rst.next()) {
				ArrayList<Habitacion> habitaciones = new ArrayList<Habitacion>();
				ArrayList<Cliente> clientes = new ArrayList<Cliente>();
				Reserva reserva = new Reserva();
				Habitacion habitacion = HabitacionModelo.getHabitacionID(rst.getInt("id_habitacion"));
				Cliente cliente = ClienteModelo.getClienteByDni(rst.getString("dni"));
				reserva.setId(rst.getInt("id"));
				reserva.setDesde(rst.getDate("desde"));
				reserva.setHasta(rst.getDate("hasta"));
				habitaciones.add(habitacion);
				clientes.add(cliente);
				reserva.setClientes(clientes);
				reserva.setHabitaciones(habitaciones);
				reservas.add(reserva);
			}
			Conector.CERRAR();
			return reservas;
		} catch (SQLException e) {
			System.out.println(e);
		}
		return null;
	}

	
	public static void darAltaReserva(Scanner scan, String dni, String nombre_hotel, java.util.Date fechaAsta, java.util.Date fechaDesde) {
		try {
			Reserva reserva = new Reserva();
			if (dni.equalsIgnoreCase(ClienteModelo.getClienteByDni(dni).getDni())) {
			
				Hotel hotel = HotelModelo.getHotelByNombre(nombre_hotel);
				Cliente cliente = ClienteModelo.getClienteByDni(dni);
				reserva.setHabitaciones(HabitacionModelo.getHabitacionesHotel(scan,hotel.getId())); 
				Visor.mostrarHabitaciones(reserva.getHabitaciones());
				int id_habitacion = Formulario.pedirIdHabitacion(scan);//cambiar
				reserva.setDesde(fechaDesde);
				reserva.setHasta(fechaAsta);

				String sql = "INSERT INTO reservas (id_habitacion, dni, desde, hasta) VALUES (?,?,?,?)";
				Connection con = Conector.conectar();
				PreparedStatement prst = con.prepareStatement(sql);
				prst.setInt(1, id_habitacion);//cambiar
				prst.setString(2, cliente.getDni());
				prst.setDate(3, (Date) reserva.getDesde());
				prst.setDate(4, (Date) reserva.getHasta());
				prst.executeUpdate();
				prst.close();
				Visor.mostrarResrvaAceptada(reserva);
				
			}else {
				Visor.mostrarError();
			}
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}
	

	public static void anularReserva(Scanner scan, int id_reserva) {
		
		try {
			String sql = "DELETE FROM reservas WHERE reservas.id = ?";
			PreparedStatement prst = Conector.conectar().prepareStatement(sql);
			prst.setInt(1, id_reserva);
			prst.executeUpdate();
			prst.close();
			Visor.mostrarStatementExitoso();
		} catch (Exception e) {
			System.out.println(e);
		}

	}
	
	public static Reserva verReservasEndosFechas(java.util.Date fechaEntrada, java.util.Date fechaSalida) {
		ArrayList<Reserva> reservas = new ArrayList<Reserva>();
		try {
			String sql = "SELECT * FROM reservas";
			PreparedStatement prst = Conector.conectar().prepareStatement(sql);
			ResultSet rst = prst.executeQuery();
			while(rst.next()) {
				ArrayList<Cliente> clientes = new ArrayList<Cliente>();
				ArrayList<Habitacion> habitaciones = new ArrayList<Habitacion>();
				Reserva reserva = new Reserva();
				clientes.add(ClienteModelo.getClienteByDni(rst.getString("dni")));
				reserva.setClientes(clientes);
				reserva.setDesde(rst.getDate("desde"));
				habitaciones.add(HabitacionModelo.getHabitacionID(rst.getInt("id_habitacion")));
				reserva.setHabitaciones(habitaciones);
				reserva.setId(rst.getInt("id"));
				reserva.setHasta(rst.getDate("hasta"));
				reservas.add(reserva);
			}
		} catch (Exception e) {
			System.err.println(e);
		}
		for (Reserva reserva : reservas) {
			int fchentrada = reserva.getDesde().compareTo(fechaEntrada);
			int fchSalida = reserva.getHasta().compareTo(fechaSalida);
			if(reserva.getDesde().before(fechaSalida) && reserva.getHasta().after(fechaEntrada) || reserva.getDesde().after(fechaEntrada) && reserva.getDesde().before(fechaSalida)) {
				return reserva;
			}
			else {
				//system.out.println(reserva + " \n no validas");
			}
		}
		return null;
	}
}
