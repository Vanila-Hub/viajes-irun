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

public class ReservaModelo extends GestorBBDD {
	public static void verReservas(int id_hotel) {
		ArrayList<Reserva> reservas = new ArrayList<Reserva>();

		try {
			String sql = "SELECT * FROM reservas WHERE id_cliente = ?";
			Connection conexion = Conector.conectar();
			PreparedStatement st = conexion.prepareStatement(sql);
			ResultSet rst = st.executeQuery();
			while (rst.next()) {
				Reserva reserva = new Reserva();
				reserva.setId(rst.getInt("id"));
				reserva.setDesde(rst.getDate("desde"));
				reserva.setHasta(rst.getDate("hasta"));
				reservas.add(reserva);
			}
			Conector.CERRAR();
		} catch (SQLException e) {
			System.out.println(e);
		}
	}

	
	public static void darAltaReserva(Scanner scan, String dni, String nombre_hotel, java.util.Date fechaAsta, java.util.Date fechaDesde) {
		try {
			Reserva reserva = new Reserva();
			if (dni.equalsIgnoreCase(ClienteModelo.getClienteByDni(dni).getDni())) {
			
				Hotel hotel = HotelModelo.getHotelByNombre(nombre_hotel);
				Cliente cliente = ClienteModelo.getClienteByDni(dni);
				reserva.setHabitaciones(HabitacionModelo.getHabitacionesHotel(scan,hotel.getId())); 
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
	
	public static void verReservasEndosFechas() {
		Scanner scan = new Scanner(System.in);
		Date fechaEntrada = Formulario.pedirFechaDesde(scan);
		Date fechaSalida = Formulario.pedirFechaHasta(scan);
		ArrayList<Reserva> reservas = new ArrayList<Reserva>();
		ArrayList<Cliente> clientes = new ArrayList<Cliente>();
		ArrayList<Habitacion> habitaciones = new ArrayList<Habitacion>();
		
		try {
			String sql = "SELECT * FROM reservas";
			PreparedStatement prst = Conector.conectar().prepareStatement(sql);
			ResultSet rst = prst.executeQuery();
			while(rst.next()) {
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
				Visor.mostrarReserva(reserva);
			}
			else {
				//system.out.println(reserva + " \n no validas");
			}
		}
	}
}
