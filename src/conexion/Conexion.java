package conexion;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.apache.tomcat.dbcp.dbcp2.BasicDataSource;


/*
 * Se establecen las propiedades del pool de conexion
 */
public class Conexion {
	
	private static BasicDataSource dataSource = null;
	
	//Se crea metodo para acceder a las propiedades del Pool de Conexion
	private static DataSource getDataSource(){
		
		if (dataSource==null) {
			
			dataSource = new BasicDataSource();
			
			dataSource.setDriverClassName("com.mysql.jdbc.Driver");
			
			dataSource.setUsername("root");
			
			dataSource.setPassword("");
			
			dataSource.setUrl("jdbc:mysql://localhost:3306/crud?autoReconnect=true&useSSL=false");
			
			dataSource.setInitialSize(20); //Inicia el pool con 20 conexiones
			
			dataSource.setMaxIdle(15);    //Se establecen 15 conexiones activas
			
			 //pueden existir 20 conexiones en total activas, pero en el peor de los casos 10
			dataSource.setMaxTotal(20);
			
			//Tiempo que espera el servidor el pool de conexiones para verificar si hay conexiones activas o no
			dataSource.setMaxWaitMillis(5000); 
		}
		
		return dataSource;
	}
	
	public static Connection getConnection() throws SQLException{
		
		return getDataSource().getConnection();
		
	}

}
