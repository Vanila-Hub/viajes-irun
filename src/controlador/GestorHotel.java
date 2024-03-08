package controlador;

import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

import modelo.Cliente;
import modelo.ClienteModelo;
import modelo.Habitacion;
import modelo.HabitacionModelo;
import modelo.Hotel;
import modelo.HotelModelo;
import modelo.Reserva;
import modelo.ReservaModelo;
import vista.Formulario;
import vista.Menu;
import vista.Visor;

public class GestorHotel {
	public static void main(String[] args) {
		int opcion = 0;
		Scanner scan = new Scanner(System.in);

		do {
			Menu.menuPrincimpal();
			opcion = Integer.parseInt(scan.nextLine());
			switch (opcion) {
			case Menu.VER_CLIENTES:
				verClientes(scan);
				break;
			case Menu.DAR_DE_ALTA_CLIENTE:
				darAltaCliente(scan);
				break;
			case Menu.VER_HOTEL_HABITACIONES:
				verHotelHabitaciones(scan);
				break;
			case Menu.DAR_DE_ALTA_RESERVA:
				darAltaReserva(scan);
				break;
			case Menu.EDITAR_HABITACION:
				editarHabitacion(scan);
				break;
			case Menu.VER_RESERVAS:
				verReservas(scan);
				break;
			case Menu.BORRAR_CLIENTE:
				borrarCliente(scan);
				break;
			case Menu.DAR_DE_BAJA_RESERVA:
				anularReserva(scan);
				break;
			case Menu.DAR_DE_BAJA_HABITACION:
				anularHabitacion(scan);
				break;
			case Menu.ALTA_HOTEL:
				darAltaHotel(scan);
				break;
			default:
				break;
			}
		} while (opcion!=Menu.SALIR);
	}

	private static void darAltaHotel(Scanner scan) {
		Hotel hotel = Formulario.pedirNuevosDatorHotel(scan);
		HotelModelo.darAltaHotel(scan, hotel);

	}

	private static void anularHabitacion(Scanner scan) {
		int id_habitacion = Formulario.pedirIdHabitacion(scan);
		HabitacionModelo.borrarHabitacion(scan,id_habitacion);

	}

	private static void anularReserva(Scanner scan) {
		int id_reserva = Formulario.pedirIdReserva(scan); 
		ReservaModelo.anularReserva(scan,id_reserva);

	}

	private static void editarHabitacion(Scanner scan) {
		int id_habitacion = Formulario.pedirIdHabitacion(scan);
		Habitacion habitacion = new Habitacion();
		habitacion = HabitacionModelo.getHabitacionID(id_habitacion);
		habitacion = Formulario.modificarHabitacion(scan,habitacion);
		HabitacionModelo.editarHabitacion(habitacion);

	}

	private static void verHotelHabitaciones(Scanner scan) {
		String hotel_cif = Formulario.pedirCif(scan);
		Hotel hotel = HotelModelo.verHotelHabitaciones(scan,hotel_cif);
		Visor.mostrarHoteles(hotel);
		

	}

	private static void darAltaReserva(Scanner scan) {
		String dni = Formulario.pedriDNI(scan);
		String nombre_hotel = Formulario.pedirNombreHotel(scan);

		Date fechaDesde =  Formulario.pedirFechaDesde(scan);
		Date fechaAsta =  Formulario.pedirFechaHasta(scan);
		ReservaModelo.darAltaReserva(scan,dni,nombre_hotel, fechaAsta,fechaDesde);
	}

	private static void verReservas(Scanner scan) {
		Menu menu = new Menu();
		int opcion = 0;
		do {
			menu.mostrarMenuReservas();
			opcion = Formulario.pedirOpciones(scan);
			switch (opcion) {
			case Menu.CONSULTA_RESERVAS_EN_DOS_FECHAS:
				Date fechaEntrada = Formulario.pedirFechaDesde(scan);
				Date fechaSalida = Formulario.pedirFechaHasta(scan);
				Reserva reserva = ReservaModelo.verReservasEndosFechas(fechaEntrada,fechaSalida);
				Visor.mostrarReserva(reserva);
				break;
			case Menu.CONSULTAR_RESERVAS_DE_UN_CLIENTE:
				String dni_Cliente = Formulario.pedirDniCliente();
				ArrayList<Reserva> reservas = ClienteModelo.verReservasCliente(dni_Cliente);
				Visor.mostrarResrvas(reservas);
				break;
			case Menu.CONSULTAR_RESERVA_POR_HOTEL:
				int id_hotel = Formulario.pedirIdHotel(scan);
				ArrayList<Habitacion> haitaciones = HabitacionModelo.getHabitacionesHotel(scan, id_hotel);
				ArrayList<Reserva> reservas2 = new ArrayList<Reserva>();
				
				for (Habitacion habitacion : haitaciones) {
					reservas2 = ReservaModelo.verReservas(habitacion.getId());
				}
				Visor.mostrarResrvas(reservas2);
				
				break;
			default:
				break;
			}
		} while (opcion!=Menu.SALIR);
	}

	private static void borrarCliente(Scanner scan) {
		Cliente cliente = new Cliente();
		String dni = Formulario.pedriDNI(scan);
		if (ClienteModelo.getDniCliente(dni)!=true) {
			ClienteModelo.borraCliente(scan,dni);	
		}else {
			Visor.mostrarErrorCliente(dni);
		}
	}

	private static void darAltaCliente(Scanner scan) {
		Cliente cliente = new Cliente();
		cliente = Formulario.modificarCliente(cliente, scan);
		ClienteModelo.darAtltaCliente(scan,cliente);
	}

	//	private static void modificarClientes(Scanner scan) {
	//		GestorBBDD.modificarCliente(scan);
	//	}

	private static void verClientes(Scanner scan) {
		Menu menu = new Menu();
		int opcion = 0;
		do {
			menu.mostrarMenuClientes();
			opcion = Formulario.pedirOpciones(scan);
			switch (opcion) {
			case Menu.VER_CLIENTES_ORDENADOS_POR_APELLIDO:
				ClienteModelo.verClientesOrdenadosA();
				break;
			case Menu.VER_CLIENTES_ORDENADOS_POR_NOMBRE:
				ClienteModelo.verCleintesOrdenadosN();
				break;
			case Menu.VER_CLIENTES_QUE_CONTENGAN_CADENA:
				String cadenaIntroducida = Formulario.pedirStringTeclado();
				ClienteModelo.verClientes(cadenaIntroducida);
				break;
			default:
				break;
			}		} while (opcion!=Menu.SALIR);
		//GestorBBDD.verClientes();
	}
}
