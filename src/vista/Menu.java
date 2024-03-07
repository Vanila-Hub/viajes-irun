package vista;

public class Menu {

	public final static int SALIR = 0;
	public static final int VER_CLIENTES = 1;
	public static final int DAR_DE_ALTA_CLIENTE = 2;
	public static final int VER_HOTEL_HABITACIONES = 3;
	public static final int DAR_DE_ALTA_RESERVA = 4;
	public static final int EDITAR_HABITACION = 5;
	//	public static final int MODIFICAR_CLIENTE = 100;
	public static final int VER_RESERVAS = 6;

	public static final int BORRAR_CLIENTE = 7;
	public static final int DAR_DE_BAJA_RESERVA = 8;
	public static final int DAR_DE_BAJA_HABITACION = 9;
	public static final int ALTA_HOTEL = 10;


	//MENU CLIENTES
	public static final int VER_CLIENTES_ORDENADOS_POR_APELLIDO = 1;
	public static final int VER_CLIENTES_ORDENADOS_POR_NOMBRE = 2;
	public static final int VER_CLIENTES_QUE_CONTENGAN_CADENA = 3;

	//MENU RESERVAS
	public static final int CONSULTA_RESERVAS_EN_DOS_FECHAS = 1;
	public static final int CONSULTAR_RESERVAS_DE_UN_CLIENTE = 2;
	public static final int CONSULTAR_RESERVA_POR_HOTEL = 3;
	public Menu() {

	}

	public static void menuPrincimpal() {
		System.out.println("MENU");
		System.out.println(VER_CLIENTES + ". Ver clientes");
		System.out.println(DAR_DE_ALTA_CLIENTE + ". Registrar cliente");
		System.out.println(VER_HOTEL_HABITACIONES + ". Ver hotel con sus habitaciones");
		System.out.println(DAR_DE_ALTA_RESERVA + ". Realizar reserva");
		System.out.println(EDITAR_HABITACION + ". Editar Habitacion");
		System.out.println(VER_RESERVAS + ". Ver Reservas");

		System.out.println(BORRAR_CLIENTE + ". Eliminar Cliente");
		System.out.println(DAR_DE_BAJA_RESERVA + ". Eliminar Reserva");
		System.out.println(DAR_DE_BAJA_HABITACION + ". Eliminar Habitacion");
		System.out.println(ALTA_HOTEL + ". Dar de alta un Hotel");

		System.out.println(SALIR + ". Salir");
		System.out.print("Seleccione una opción: ");

	}
	public void mostrarMenuClientes() {
		System.out.println(VER_CLIENTES_ORDENADOS_POR_APELLIDO + ". ver clientes ordenados por apellido");
		System.out.println(VER_CLIENTES_ORDENADOS_POR_NOMBRE + ". ver clientes ordenados por nombre");
		System.out.println(VER_CLIENTES_QUE_CONTENGAN_CADENA + ". ver clientes que en su nombre o apellido contenga una cadena");
		System.out.println(SALIR + ". Salir");
		System.out.print("Seleccione una opción: ");
	}

	public void mostrarMenuReservas() {
		System.out.println(CONSULTA_RESERVAS_EN_DOS_FECHAS + ". Consultar las reservas entre dos fechas introducida");
		System.out.println(CONSULTAR_RESERVAS_DE_UN_CLIENTE + ". Consultar las  reservas de un cliente.");
		System.out.println(CONSULTAR_RESERVA_POR_HOTEL + ". Ver Reservas del Hotel");
		System.out.println(SALIR + ". Salir"); 
	}
}
