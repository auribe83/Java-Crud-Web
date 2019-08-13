<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
 <%@  taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core" %>  
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Listar Producto</title>
</head>
<body>

	<h1>Listar Producto</h1>
	<table border="1">
		<tr>
			<td>Id</td>
			<td>Nombre</td>
			<td>Cantidad</td>
			<td>Precio</td>
			<td>Fecha Creacion</td>
			<td>Fecha Actualizacion</td>
			<td>Accion</td>
		</tr>
		
		                  <!-- Se utilizan JSTL para agregar codigo Java -->
		                  
		<!-- el for-each tiene varias propiedades la primera es var la cual es la variable u objeto que 
		se va a recibir por cada iteracion de la lista. El items hace referecia a la lista que se recibe
		desde el Servlet -->
		
		<c:forEach var="producto" items="${lista}"> 
			<!--El elmento c:out permite presentar en una pagina jsp el valor de una variable, en este 
			caso se imprime cada atributo que se recibe por producto de la lista-->
			<tr>
				<td><a href="productos?opcion=meditar&id=<c:out value="${producto.id }"></c:out>"><c:out value="${producto.id }"></c:out></a></td>
				<td><c:out value="${producto.nombre }"></c:out></td>
				<td><c:out value="${producto.cantidad }"></c:out></td>
				<td><c:out value="${producto.precio}"></c:out></td>
				<td><c:out value="${producto.fechaCrear}"></c:out></td>
				<td><c:out value="${producto.fechaActualizar}"></c:out></td>
				<td><a href="productos?opcion=eliminar&id=<c:out value="${producto.id}"></c:out>">Eliminar</a></td>
			</tr>
		</c:forEach>
		
	</table>

</body>
</html>