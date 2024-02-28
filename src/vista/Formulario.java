package vista;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Scanner;

import modelo.Cliente;
import modelo.GestorBBDD;

public class Formulario {

	public static void pedirModificarCliente(Scanner scan, ArrayList<Cliente> clientes) {
		String dni = pedriDNI(scan);
		for (Cliente cliente : clientes) {
			if (cliente.getDni().equals(dni)) {
				Visor.mostrarCliente(cliente);
				cliente =  modificarCliente(cliente,scan);
				GestorBBDD.updateCliente(cliente);
			}
		}
	}

	public static Cliente modificarCliente(Cliente cliente, Scanner scan) {
	        System.out.println("Introduce el nombre:");
	        String nombre = scan.nextLine();
	        cliente.setNombre(nombre);
	        System.out.println("Introduce el apellido:");
	        String apellido = scan.nextLine();
	        cliente.setApellido(apellido);
	        System.out.println("Introduce la dirección:");
	        String direccion = scan.nextLine();
	        cliente.setDireccion(direccion);
	        System.out.println("Introduce la localidad:");
	        String localidad = scan.nextLine();
	        cliente.setLocalidad(localidad);		
	        return cliente;
	}

	public static String pedriDNI(Scanner scan) {
		System.out.println("Introduzca el DNI del cliente");
		String dni =  scan.nextLine();
		return dni;
	}

	public static int pedirIdHabitacion(Scanner scan) {
		System.out.println("Introduzca el Id de la Hbaitacion");
		int id =  Integer.parseInt(scan.nextLine());
		return id;
	}

	public static Date pedirFechaDesde(Scanner scan) {
		System.out.println("Introduzca la fecha de entrada de la Reserva");
		Date fecha = Date.valueOf(scan.nextLine());
		return fecha;
	}

	public static Date pedirFechaHasta(Scanner scan) {
		System.out.println("Introduzca la fecha de salida de la Reserva");
		Date fecha = Date.valueOf(scan.nextLine());
		return fecha;
	}


}
