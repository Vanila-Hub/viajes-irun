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


	



}


	

	