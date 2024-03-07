package modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Scanner;

import vista.Visor;

public class ClienteModelo extends GestorBBDD {
	
	public static void verClientes(String cadenaIntroducida){
		ArrayList<Cliente> clientes = new ArrayList<Cliente>();
		try {
			String sql = "SELECT * FROM clientes";
			Connection conexion = Conector.conectar();
			Statement st = conexion.createStatement();
			ResultSet rst = st.executeQuery(sql);
			while(rst.next()) {
				Cliente cliente = new Cliente();
				cliente.setDni(rst.getString("dni"));
				cliente.setApellido(rst.getString("apellidos"));
				cliente.setDireccion(rst.getString("direccion"));
				cliente.setLocalidad(rst.getString("localidad"));
				cliente.setNombre(rst.getString("nombre"));
				clientes.add(cliente);
			}
			
			Visor.mostrarClientesqueContengan(clientes,cadenaIntroducida);
			Conector.CERRAR();
		} catch (SQLException e) {
			System.out.println(e);
		}
	}
	
	public static void updateCliente(Cliente cliente) {
		try {
			String sql = "UPDATE clientes SET nombre=?, apellidos=?, direccion=?, localidad=? WHERE clientes.dni=?";
			Connection con =  Conector.conectar();
			PreparedStatement prts = con.prepareStatement(sql);
			prts.setString(1, cliente.getNombre());
			prts.setString(2, cliente.getApellido());
			prts.setString(3, cliente.getDireccion());
			prts.setString(4, cliente.getLocalidad());
			prts.setString(5, cliente.getDni());
			prts.executeUpdate();
			prts.close();
			System.out.println("Cliente con DNi" + cliente.getDni()  + "Actualizado");
		} catch (Exception e) {
			System.out.println(e);
		}
	}



	public static void darAtltaCliente(Scanner scan, Cliente cliente) {
		try {
			String sql = "INSERT INTO clientes (dni,nombre,apellidos,direccion,localidad) VALUES (?,?,?,?,?)";
			Connection con = Conector.conectar();
			PreparedStatement prts = con.prepareStatement(sql);	
			prts.setString(1, cliente.getDni());
			prts.setString(2, cliente.getNombre());
			prts.setString(3, cliente.getApellido());
			prts.setString(4, cliente.getDireccion());
			prts.setString(5, cliente.getLocalidad());
			prts.executeUpdate();
			prts.close();
			Visor.mostrarCliente(cliente);
			Visor.mostrarSucces(cliente);
		} catch (Exception e) {
			System.err.println(e);
		}
	}
	
	public static void borraCliente(Scanner scan, String dni) {
		try {
			String sql = "DELETE FROM clientes WHERE clientes.dni = ?";
			Connection con = Conector.conectar();
			PreparedStatement prts = con.prepareStatement(sql);
			prts.setString(1, dni);
			prts.executeUpdate();
			prts.close();
			System.out.println("Usuario con DNI " + dni + " Eliminado!");
		} catch (Exception e) {
			System.out.println(e);
		}

	}
	
	public static Cliente getClienteByDni(String dni) {
		Cliente cliente = new Cliente();
		try {
			String sql = "SELECT * FROM clientes WHERE clientes.dni = '" + dni + "'" ;
			Connection conexion = Conector.conectar();
			Statement st = conexion.createStatement();
			ResultSet rst = st.executeQuery(sql);
			while(rst.next()) {
				cliente.setDni(rst.getString("dni"));
				cliente.setApellido(rst.getString("apellidos"));
				cliente.setDireccion(rst.getString("direccion"));
				cliente.setLocalidad(rst.getString("localidad"));
				cliente.setNombre(rst.getString("nombre"));
			}
			Conector.CERRAR();
			return cliente;
		} catch (SQLException e) {
			System.out.println(e);
		}
		return null;
	}
	
	public static void verClientesOrdenadosA() {
		ArrayList<Cliente> clientes = new ArrayList<Cliente>();
		try {
			String sql = "SELECT * FROM clientes";
			PreparedStatement prst = Conector.conectar().prepareStatement(sql);
			ResultSet rst = prst.executeQuery(sql);
			while(rst.next()) {
				Cliente cliente = new Cliente();
				cliente.setApellido(rst.getString("apellidos"));
				cliente.setDireccion(rst.getString("direccion"));
				cliente.setDni(rst.getString("dni"));
				cliente.setLocalidad(rst.getString("localidad"));
				cliente.setNombre(rst.getString("nombre"));
				clientes.add(cliente);
			}
			clientes.sort(new ComparadorApellidos());
			Visor.mostrarClientes(clientes);
		} catch (Exception e) {
			System.err.println(e);
		}

	}

	public static void verCleintesOrdenadosN() {
		ArrayList<Cliente> clientes = new ArrayList<Cliente>();
		try {
			String sql = "SELECT * FROM clientes";
			PreparedStatement prst = Conector.conectar().prepareStatement(sql);
			ResultSet rst = prst.executeQuery();
			while(rst.next()) {
				Cliente cliente = new Cliente();
				cliente.setApellido(rst.getString("apellidos"));
				cliente.setDireccion(rst.getString("direccion"));
				cliente.setDni(rst.getString("dni"));
				cliente.setLocalidad(rst.getString("localidad"));
				cliente.setNombre(rst.getString("nombre"));
				clientes.add(cliente);
			}
			clientes.sort(new ComparadorNombre());
		} catch (Exception e) {
			System.err.println(e);
		}
	}

	public static ArrayList<Reserva> verReservasCliente(String dni_Cliente) {
		ArrayList<Reserva> reservas = new ArrayList<Reserva>();
		ArrayList<Hotel> hoteles = new ArrayList<Hotel>();
		ArrayList<Habitacion> habitaciones = new ArrayList<Habitacion>();
		//select reserva.id=cliente.id
		String sql = "SELECT * FROM reservas WHERE reservas.dni = ?";
		try {
			PreparedStatement prst = Conector.conectar().prepareStatement(sql);
			prst.setString(1, dni_Cliente);
			//recorre crear reserva rrelanar
			ResultSet rst = prst.executeQuery();
			while(rst.next()) {
				Reserva reserva = new Reserva();
				reserva.setDesde(rst.getDate("desde"));
				reserva.setHasta(rst.getDate("hasta"));
				reserva.setId(rst.getInt("id"));
				//con reserva.id-habitacion conseguir abitacion
				Habitacion habitacion = HabitacionModelo.getHabitacionID(rst.getInt("id_habitacion"));
				//con habitacion.getIdHotel conseguir hotel
				Hotel hotel = HotelModelo.getHotelById(habitacion.getId_hotel());
				hoteles.add(hotel);
				//asignar hotel a la habitacion
				
				//asignar Habitacion a la reserva
				habitacion.setId_hotel(hotel.getId());
				//add la habitacion al array de reservas
				reserva.setHabitaciones(habitaciones);
				reservas.add(reserva);
			}
			return reservas;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	//return arrayList reservas
	}
	
	public static boolean getDniCliente(String dni) {
		String sql = "SELECT dni FROM reservas WHERE dni = ?";
		try {
			PreparedStatement prst  = Conector.conectar().prepareStatement(sql);
			prst.setString(1, dni);
			ResultSet rst = prst.executeQuery();
			if(rst.next()) {
				return true;
			}else {
				return false;
			}
		} catch (Exception e) {
			System.err.println(e);
		}
		return false;
	}
}


