package ar.com.codoacodo.database;

import java.sql.Connection;
import java.sql.DriverManager;

public class DatabaseConnection {
	
	public static Connection getConnection() {
		
		String hosts = "localhost";  // 127.0.0.1
		String port = "3306";
		String password = "";
		String username = "root";
		String nombredb = "bd_movies";
			
		// Driver de conexion a la base de datos
		String driveClassName = "com.mysql.cj.jdbc.Driver";
		
		// Aplicar manejo de excepciones
		Connection connection ;
		try {
			Class.forName(driveClassName);
			String url ="jdbc:mysql://" + hosts + ":" + port + "/" + nombredb + "?useSSL=false";
			connection = DriverManager.getConnection(url, username, password);
		} catch(Exception e){
			e.printStackTrace();
			connection = null;
		}
		
		return connection;
		
	}
	
	// Version de consola
	/*public static void main(String[] args) {
		
		Connection con = DatabaseConnection.getConnection();
			
		if(con != null) {
			System.out.println("Database connected!");
		} else {
			System.err.println("Cannot connect the database!");
		}
			
	}*/

}
