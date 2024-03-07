package modelo;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Scanner;

import vista.Formulario;
import vista.Menu;
import vista.Visor;

public class GestorBBDD {



	public static void modificarCliente(Scanner scan) {
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
			Formulario.pedirModificarCliente(scan,clientes);
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

	private static Cliente getClienteByDni(String dni) {
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

	public static void verReservas(int id_hotel) {
		ArrayList<Reserva> reservas = new ArrayList<Reserva>();
		
		try {
			String sql = "SELECT * FROM reservas WHERE id_cliente = ?";
			Connection conexion = Conector.conectar();
			PreparedStatement st = conexion.prepareStatement(sql);
			ResultSet rst = st.executeQuery();
			while(rst.next()) {
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

	public static Habitacion getHabitacionID(int id_habitacion) {
		Habitacion habitacion = new Habitacion();
		try {
			String sql = "SELECT * FROM habitaciones WHERE habitaciones.id = " + id_habitacion;
			Connection conexion = Conector.conectar();
			Statement st = conexion.createStatement();
			ResultSet rst = st.executeQuery(sql);
			while(rst.next()) {
				habitacion.setId(rst.getInt("id"));
				habitacion.setNumero(rst.getInt("numero"));
				habitacion.setDescripcion(rst.getString("descripcion"));
				habitacion.setPrecio(rst.getInt("precio"));
				habitacion.setId_hotel(rst.getInt("id_hotel"));
			}
			Conector.CERRAR();
			return habitacion;
		} catch (SQLException e) {
			System.out.println(e);
		}
		return null;
	}

	private static Hotel getHotelById(int id) {
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

	public static void darAltaReserva(Scanner scan, String dni, String nombre_hotel, java.util.Date fechaAsta, java.util.Date fechaDesde) {
		try {
			Reserva reserva = new Reserva();
			if (dni.equalsIgnoreCase(getClienteByDni(dni).getDni())) {
			
				Hotel hotel = getHotelByNombre(nombre_hotel);
				Cliente cliente = getClienteByDni(dni);
				reserva.setHabitaciones(getHabitacionesHotel(scan,hotel.getId())); 
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

	private static ArrayList<Habitacion> getHabitacionesHotel(Scanner scan, int id_hotel) {
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
			Visor.mostrarHabitaciones(habitaciones);
			
			return habitaciones;
		} catch (Exception e) {
		System.err.println(e);
		}
		return null;
	}

	private static Hotel getHotelByNombre(String nombreHotel) {
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

	public static ArrayList<Habitacion> verHotelHabitaciones(Scanner scan, String hotel_cif) {
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
		getHabitaciones(hotel);
		return null;
	}

	public static void getHabitaciones(Hotel hotel) {
		ArrayList<Habitacion> habitaciones = new ArrayList<Habitacion>();
		try {
			String sql = "SELECT * FROM habitaciones WHERE id_hotel = ?";
			PreparedStatement prst = Conector.conectar().prepareStatement(sql);
			prst.setString(1, hotel.getCif());
			ResultSet rs = prst.executeQuery();
			while(rs.next()) {
				Habitacion habitacion = new Habitacion();
				habitacion.setDescripcion(rs.getString("descripcion"));
				habitacion.setId(rs.getInt("id"));
				habitacion.setNumero(rs.getInt("numero"));
				habitacion.setPrecio(rs.getInt("precio"));
				habitaciones.add(habitacion);
			}
			Visor.mostrHabitaciones(habitaciones);
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	public static void editarHabitacion(Habitacion habitacion) {
		try {
			String sql = "UPDATE habitaciones SET numero=?,descripcion=?,precio=?";
			PreparedStatement prst = Conector.conectar().prepareStatement(sql);
			prst.setInt(1, habitacion.getNumero());
			prst.setString(2, habitacion.getDescripcion());
			prst.setInt(3, habitacion.getPrecio());
			prst.executeUpdate();
			prst.close();
			Visor.mostrHabitacion(habitacion);
		} catch (Exception e) {
			System.out.println(e);
		}
	}

//	public static Hotel verHabitacionesDeunHotel(Scanner scan, Hotel hotel) {
//		try {
//			String sql = "SELECT * FROM habitaciones WHERE habitaciones.id_hotel = ?";
//			PreparedStatement prst = Conector.conectar().prepareStatement(sql);
//			prst.setInt(1, hotel.getId());
//			ResultSet rst = prst.executeQuery();
//			while(rst.next()) {
//				hotel.setHabitacion(getHabitacionID(rst.getInt("id")));
//			}
//			Visor.mostrHabitacion(hotel.getHabitacion());
//			return hotel;
//		} catch (Exception e) {
//			System.err.println(e);
//		}
//		return hotel;
//aqui
//	}

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
				darAltaHabitacion(hotel,scan);
				break;
			default:
				break;
			}
		} while (opciones!=Menu.SALIR);
	}

	private static void darAltaHabitacion(Hotel hotel, Scanner scan) {
		Habitacion habitacion = new Habitacion();
		habitacion = Formulario.pedirNuevosDatosHabitacion(scan,habitacion);
		try {
			String sql = "INSERT INTO habitaciones(id_hotel,numero,descripcion,precio) VALUES (?,?,?,?)";
			PreparedStatement prst = Conector.conectar().prepareStatement(sql);
			prst.setInt(1, hotel.getId());
			prst.setInt(2, habitacion.getNumero());
			prst.setString(3,  habitacion.getDescripcion());
			prst.setInt(4,  habitacion.getPrecio());
		} catch (Exception e) {
			System.err.println(e);
		}
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
				clientes.add(getClienteByDni(rst.getString("dni")));
				reserva.setClientes(clientes);
				reserva.setDesde(rst.getDate("desde"));
				habitaciones.add(getHabitacionID(rst.getInt("id_habitacion")));
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
				Habitacion habitacion = getHabitacionID(rst.getInt("id_habitacion"));
				//con habitacion.getIdHotel conseguir hotel
				Hotel hotel = getHotelById(habitacion.getId_hotel());
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
