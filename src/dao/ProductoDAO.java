package dao;

/*
 * Se crea el DAO para la tabla, el DAO(Data Acces Object) es un patron de diseño de Java que se encarga de la
 * persistencia de datos en una tabla. El DAO no es mas que una clase que enlista todas las operaciones CRUD
 * que puede tener una tabla
 */


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import conexion.Conexion;
import modelo_BD.Producto;

public class ProductoDAO {
	
	//1- Se crea un objeto de tipo Connection para obtener la conexion del Pool
	private Connection connection;
	
	//2-Se crea el Statement
	private PreparedStatement statement;
	
	private boolean estadoOperacion;
	
	                   //********** Se crean los metodos CRUD**********
	
	//Guardar Producto
	public boolean guardar(Producto producto) throws SQLException{
		
		String sql =null;
		
		estadoOperacion = false;
		
		connection = obtenerConexion(); //Se obtiene la conexion
		
		try {
			connection.setAutoCommit(false); //La transaccion esta por empezar a partir de este punto
			
			sql ="INSERT INTO PRODUCTOS (ID, NOMBRE, CANTIDAD, PRECIO, FECHA_CREAR, FECHA_ACTUALIZAR)"
					+ " VALUES(?,?,?,?,?,?)";
			
			statement = connection.prepareStatement(sql);
			
			//Se establecen los parametros de la consulta
			statement.setString(1, null);
			
			statement.setString(2, producto.getNombre());
			
			statement.setDouble(3, producto.getCantidad());
			
			statement.setDouble(4, producto.getPrecio());
			
			statement.setDate(5, producto.getFechaCrear());
			
			statement.setDate(6, producto.getFechaActualizar());
			
			estadoOperacion = statement.executeUpdate() > 0; //Mayor a 0 es True
			
			connection.commit(); //Se realiza el commit
			
			statement.close(); //Se cierra el Statement
			
			connection.close(); //Se devuelve la conexion al Pool
			
		} catch (SQLException e) {
			
			connection.rollback(); //Por si ocurre un error
			
			e.printStackTrace();
		}		
				
		return estadoOperacion;
		
	}
	
	//Actualizar Producto
	public boolean editar(Producto producto) throws SQLException{
		
		String sql =null;
		
		estadoOperacion = false;
		
		connection = obtenerConexion(); //Se obtiene la conexion
		
		try {
			
			connection.setAutoCommit(false);
			
			sql = "UPDATE PRODUCTOS SET NOMBRE=?, CANTIDAD=?, PRECIO=?,FECHA_ACTUALIZAR=?"
					+ "WHERE ID=? ";
			
			statement = connection.prepareStatement(sql);
			
			statement.setString(1, producto.getNombre());
			
			statement.setDouble(2, producto.getCantidad());
			
			statement.setDouble(3, producto.getPrecio());
			
			statement.setDate(4, producto.getFechaActualizar());
			
			statement.setInt(5, producto.getId());
			
			//Se ejecuta la sentencia
			estadoOperacion = statement.executeUpdate() > 0;
			
			connection.commit();
			
			statement.close();
			
			connection.close();
								
		} catch (SQLException e) {
			
			connection.rollback();
			
			e.printStackTrace();
		}
		
		return estadoOperacion;
		
	}
	
	//Eliminar Producto
	public boolean eliminar(int idProducto) throws SQLException{
		
		String sql =null;
		
		estadoOperacion = false;
		
		connection = obtenerConexion(); //Se obtiene la conexion
		
		try {
			
			connection.setAutoCommit(false);
			
			sql = "DELETE FROM PRODUCTOS WHERE ID=?";
			
			statement = connection.prepareStatement(sql);
			
			//Se establece el parametro de la consulta
			statement.setInt(1, idProducto);
			
			
			//Se ejecuta la sentencia
			estadoOperacion = statement.executeUpdate() > 0;
			
			connection.commit();
			
			statement.close();
			
			connection.close();
								
		} catch (SQLException e) {
			
			connection.rollback();
			
			e.printStackTrace();
		}
		
		return estadoOperacion;
		
	}
	
	//Listar todos los productos
	public List<Producto> obtenerProductos() throws SQLException{
		
		ResultSet resultset = null; //Obtiene todos los registro de la tabla a consultar
		
		List<Producto> listaProductos = new ArrayList<>();
		
		String sql =null;
		
		estadoOperacion = false;
		
		connection = obtenerConexion(); //Se obtiene la conexion
		
		try {
			
			sql = "SELECT * FROM PRODUCTOS";
			
			statement = connection.prepareStatement(sql);
			
			resultset = statement.executeQuery(sql);
			
			while(resultset.next()){
				
				Producto p = new Producto();
				
				p.setId(resultset.getInt(1));
				
				p.setNombre(resultset.getString(2));
				
				p.setCantidad(resultset.getDouble(3));
				
				p.setPrecio(resultset.getDouble(4));
				
				p.setFechaCrear(resultset.getDate(5));
				
				p.setFechaActualizar(resultset.getDate(6));
				
				listaProductos.add(p); //Se agrega el objeto a la lista por cada iteracion
								
			}
			
			
		} catch (SQLException e) {
			
			e.printStackTrace();
			
		}		
		
		return listaProductos;
		
	}
	
	//Obtener un solo producto
	public Producto obtenerProducto(int idProducto) throws SQLException{
		
		ResultSet resultset = null; //Obtiene todos los registro de la tabla a consultar
		
		Producto p = new Producto();
		
		String sql = null;
		
		estadoOperacion = false;
		
		connection = obtenerConexion(); //Se obtiene la conexion
		
		try {
			
			sql = "SELECT * FROM PRODUCTOS WHERE ID=?";
			
			statement = connection.prepareStatement(sql);
			
			//Se establece el parametro
			statement.setInt(1, idProducto);
			
			resultset = statement.executeQuery();
			
			if(resultset.next()){											
				
				p.setId(resultset.getInt(1));
				
				p.setNombre(resultset.getString(2));
				
				p.setCantidad(resultset.getDouble(3));
				
				p.setPrecio(resultset.getDouble(4));
				
				p.setFechaCrear(resultset.getDate(5));
				
				p.setFechaActualizar(resultset.getDate(6));				
								
			}
			
			
		} catch (SQLException e) {
			
			e.printStackTrace();
			
		}		
		
		return p;
			
	}
	//Obtener conexion Pool
	private Connection obtenerConexion() throws SQLException{
		
		return Conexion.getConnection(); //Se retorna una conexion a la BD
		
	}

}
