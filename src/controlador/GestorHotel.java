package controlador;

import java.util.Date;
import java.util.Scanner;

import modelo.Cliente;
import modelo.GestorBBDD;
import modelo.Habitacion;
import modelo.Hotel;
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
		GestorBBDD.darAltaHotel(scan, hotel);

	}

	private static void anularHabitacion(Scanner scan) {
		int id_habitacion = Formulario.pedirIdHabitacion(scan);
		GestorBBDD.borrarHabitacion(scan,id_habitacion);

	}

	private static void anularReserva(Scanner scan) {
		int id_reserva = Formulario.pedirIdReserva(scan); 
		GestorBBDD.anularReserva(scan,id_reserva);

	}

	private static void editarHabitacion(Scanner scan) {
		int id_habitacion = Formulario.pedirIdHabitacion(scan);
		Habitacion habitacion = new Habitacion();
		habitacion = GestorBBDD.getHabitacionID(id_habitacion);
		habitacion = Formulario.modificarHabitacion(scan,habitacion);
		GestorBBDD.editarHabitacion(habitacion);

	}

	private static void verHotelHabitaciones(Scanner scan) {
		String hotel_cif = Formulario.pedirCif(scan);
		GestorBBDD.verHotelHabitaciones(scan,hotel_cif);

	}

	private static void darAltaReserva(Scanner scan) {
		String dni = Formulario.pedriDNI(scan);
		String nombre_hotel = Formulario.pedirNombreHotel(scan);

		Date fechaDesde =  Formulario.pedirFechaDesde(scan);
		Date fechaAsta =  Formulario.pedirFechaHasta(scan);
		GestorBBDD.darAltaReserva(scan,dni,nombre_hotel, fechaAsta,fechaDesde);
	}

	private static void verReservas(Scanner scan) {
		Menu menu = new Menu();
		int opcion = 0;
		do {
			menu.mostrarMenuReservas();
			opcion = Formulario.pedirOpciones(scan);
			switch (opcion) {
			case Menu.CONSULTA_RESERVAS_EN_DOS_FECHAS:
				GestorBBDD.verReservasEndosFechas();
				break;
			case Menu.CONSULTAR_RESERVAS_DE_UN_CLIENTE:
				String dni_Cliente = Formulario.pedirDniCliente();
				GestorBBDD.verReservasCliente(dni_Cliente);
				break;
			case Menu.CONSULTAR_RESERVA_POR_HOTEL:
				int id_hotel = Formulario.pedirIdHotel(scan);
				GestorBBDD.verReservas(id_hotel);
				break;
			default:
				break;
			}
		} while (opcion!=Menu.SALIR);
	}

	private static void borrarCliente(Scanner scan) {
		Cliente cliente = new Cliente();
		String dni = Formulario.pedriDNI(scan);
		if (GestorBBDD.getDniCliente(dni)!=true) {
			GestorBBDD.borraCliente(scan,dni);	
		}else {
			Visor.mostrarErrorCliente(dni);
		}
	}

	private static void darAltaCliente(Scanner scan) {
		Cliente cliente = new Cliente();
		cliente = Formulario.modificarCliente(cliente, scan);
		GestorBBDD.darAtltaCliente(scan,cliente);
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
				GestorBBDD.verClientesOrdenadosA();
				break;
			case Menu.VER_CLIENTES_ORDENADOS_POR_NOMBRE:
				GestorBBDD.verCleintesOrdenadosN();
				break;
			case Menu.VER_CLIENTES_QUE_CONTENGAN_CADENA:
				String cadenaIntroducida = Formulario.pedirStringTeclado();
				GestorBBDD.verClientes(cadenaIntroducida);
				break;
			default:
				break;
			}		} while (opcion!=Menu.SALIR);
		//GestorBBDD.verClientes();
	}
}
