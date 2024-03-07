package vista;

import java.util.ArrayList;
import java.util.Scanner;

import modelo.Cliente;
import modelo.Habitacion;
import modelo.Hotel;
import modelo.Reserva;

public class Visor {

	public static void mostrarClientes(ArrayList<Cliente> clientes) {
		for (Cliente cliente : clientes) {
			System.out.println(cliente);
		}
	}

	public static void mostrarCliente(Cliente cliente) {
		System.out.println(cliente);
	}

	public static void mostrarReserva(Reserva reserva) {
		System.out.println(reserva);
	}

	public static void mostrarHoteles(Hotel hotel) {
		System.out.println(hotel);
	}

	public static void mostrarError() {
		System.out.println("No hay criterios que coincidan con esa busqueda");;

	}

	public static void mostrarResrvaAceptada(Reserva reserva) {
		System.out.println(reserva + " :: realizada");
	}

//	public static Hotel mostarHabitacionHotel(Scanner scan, Hotel hotel) {
//		return GestorBBDD.verHabitacionesDeunHotel(scan,hotel);
//	}

	public static void mostrHabitacion(Habitacion habitacion) {
		System.out.println(habitacion);

	}

	public static void mostrarStatementExitoso() {
		System.out.println("Reserva Eliminada Correctamente");

	}

	public static void mostrarOopcionesHotel() {
		System.out.println("0. Salir");
		System.out.println("1. Crear Habitacion");

	}

	public static void mostrarClientesqueContengan(ArrayList<Cliente> clientes, String cadenaIntroducida) {
		for (Cliente cliente : clientes) {
			if(cliente.getNombre().toLowerCase().contains(cadenaIntroducida) || cliente.getApellido().toLowerCase().contains(cadenaIntroducida)) {
				System.out.println(cliente);
			}
		}
	}

	public static void mostrarResrvas(ArrayList<Reserva> reservas) {
		for (Reserva reserva : reservas) {
			System.out.println(reserva);
		}
	}

	public static void mostrarSucces(Cliente cliente) {
		System.out.println("Cliente " + cliente.getNombre() + " Registrado!");
		
	}

	public static void mostrHabitaciones(ArrayList<Habitacion> habitaciones) {
		for (Habitacion habitacion : habitaciones) {
			System.err.println(habitacion);
		}
		
	}

	public static void mostrarHabitaciones(ArrayList<Habitacion> habitaciones) {
		for (Habitacion habitacion : habitaciones) {
			System.out.println(habitacion);
		}
		
	}

	public static void mostrarErrorCliente(String dni) {
		System.out.println("El cliente con DNI " + dni + " Tiene una reserva hecha y no se borrar");
		
	}
}
