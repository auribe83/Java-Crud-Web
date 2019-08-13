package controlador;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import modelo_BD.Producto;
import dao.ProductoDAO;

/**
 * Servlet implementation class ProductoController
 */
@WebServlet(description = "Administra peticiones para la tabla producto", urlPatterns = { "/productos" })
public class ProductoController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ProductoController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//Se recibe la peticion
		String opcion = request.getParameter("opcion");
		
		if (opcion.equals("crear")) {
			
			System.out.println("Usted a presionado la opcion crear");
			
			RequestDispatcher requestDispatcher = request.getRequestDispatcher("/vistas/crear.jsp");
			
			requestDispatcher.forward(request, response);
			
		}else if(opcion.equals("listar")){
			
			System.out.println("Usted a presionado la opcion listar");
			
			ProductoDAO productoDAO = new ProductoDAO();
			
			//Se crea una lista
			List<Producto> lista = new ArrayList<>();
			
			
			try {
				
				lista = productoDAO.obtenerProductos(); //Se obtiene el listado de los productos
				
				for( Producto producto : lista){
					
					System.out.println(producto); //Se imprimen por consola los productos
				}
				
				//Se envia un parametro del tipo lista  a la vista
				request.setAttribute("lista", lista);
				
				
				RequestDispatcher requestDispatcher = request.getRequestDispatcher("/vistas/listar.jsp");
				
				requestDispatcher.forward(request, response);
				
			} catch (SQLException e) {
				
				e.printStackTrace();
			}
			
		}else if(opcion.equals("meditar")){
			
			int id = Integer.parseInt(request.getParameter("id"));
			
			System.out.println("Editar id: " + id);
			
			ProductoDAO productoDAO = new ProductoDAO();
			
			Producto p1 = new Producto();
			
			try {
				
				p1 = productoDAO.obtenerProducto(id);
				
				System.out.println(p1);
				
				//Se pasa el producto a una nueva vista
				request.setAttribute("producto", p1);
				
				//Se redireeciona a una nueva Pagina 
				RequestDispatcher requestDispatcher = request.getRequestDispatcher("/vistas/actualizar.jsp");
				
				requestDispatcher.forward(request, response);
				
			} catch (SQLException e) {
				
				e.printStackTrace();
			}
			
		}else if(opcion.equals("eliminar")){
			
			ProductoDAO productoDAO = new ProductoDAO();
			
			int id = Integer.parseInt(request.getParameter("id"));
			
			try {
				
				productoDAO.eliminar(id);
				
				System.out.println("Registro Eliminado Satisfactoriamente....");
				
				//Se redirecciona a la pagina
				RequestDispatcher requestDispatcher = request.getRequestDispatcher("/index.jsp");
				
				//Forward
				requestDispatcher.forward(request, response);
								
				
			} catch (SQLException e) {
				
				e.printStackTrace();
			}			
		}
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String opcion = request.getParameter("opcion");
		
		Date fechaActual = new Date();
		
		if(opcion.equals("guardar")){
			
			ProductoDAO productoDAO = new ProductoDAO();
			
			Producto producto = new Producto();
			
			//Se pasan los valores
			producto.setNombre(request.getParameter("nombre"));
			
			producto.setCantidad(Double.parseDouble(request.getParameter("cantidad")));
			
			producto.setPrecio(Double.parseDouble(request.getParameter("precio")));
			
			//Se llenan los campos fechas
			producto.setFechaCrear(new java.sql.Date(fechaActual.getTime()));
			
			//Se persisten los datos
			try {
				
				productoDAO.guardar(producto);
				
				System.out.println("Registro guardado Satisfactoriamente....");
				
				//Se redirecciona a la pagina
				RequestDispatcher requestDispatcher = request.getRequestDispatcher("/index.jsp");
				
				requestDispatcher.forward(request, response);
				
			} catch (SQLException e) {
				
				e.printStackTrace();
			}
			
			
		}else if(opcion.equals("editar")){
			
			Producto producto = new Producto();
			
			ProductoDAO productoDAO = new ProductoDAO();
			
			producto.setId(Integer.parseInt(request.getParameter("id")));
			
			producto.setNombre(request.getParameter("nombre"));
			
			producto.setCantidad(Double.parseDouble(request.getParameter("cantidad")));
			
			producto.setPrecio(Double.parseDouble(request.getParameter("precio")));
			
			//Se llenan los campos fechas
			producto.setFechaActualizar(new java.sql.Date(fechaActual.getTime()));
			
			
			try {
				
				productoDAO.editar(producto); //Se llama al metodo editar
				
				System.out.println("Registro Actualizado Satisfactoriamente....");
				
				//Se redirecciona a la pagina
				RequestDispatcher requestDispatcher = request.getRequestDispatcher("/index.jsp");
				
				//Forward
				requestDispatcher.forward(request, response);
				
			} catch (SQLException e) {
				
				e.printStackTrace();
			}
		}
		
		
	}

}
