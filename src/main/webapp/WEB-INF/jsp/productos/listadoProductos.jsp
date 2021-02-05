<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="productos">
    <h2>Productos</h2>
	<spring:url value="/restaurantes/${restauranteId}/productos/new" var="productoUrl">
                    </spring:url>
                    <a href="${fn:escapeXml(productoUrl)}" class="btn btn-default">Agregar producto</a>
    <table id="productoTable" class="table table-striped">
        <thead>
        <tr>
            <th style="width: 150px;">Nombre</th>
            <th style="width: 120px">Precio</th>
            <th style="width: 120px">Alergenos</th>
            <th style="width: 120px">Actions</th>
            
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${productos}" var="producto">
            <tr>
                <td>
                    <c:out value="${producto.name}"/>
                </td>
                <td>
                    <c:out value="${producto.precio}"/>
                </td>
                <td>
                    <c:out value="${producto.alergenos}"/>
                </td>
				<td>
				<spring:url value="/restaurantes/${restauranteId}/productos/delete/{productoId}" var="productoUrl">
                        <spring:param name="productoId" value="${producto.id}"/>
                    </spring:url>
                    <a href="${fn:escapeXml(productoUrl)}" class="btn btn-default">Eliminar producto</a>
                
                 <spring:url value="/restaurantes/${restauranteId}/productos/{productoId}/edit" var="productoUrl">
        				<spring:param name="productoId" value="${producto.id}"/>
    				</spring:url>
    				<a href="${fn:escapeXml(productoUrl)}" class="btn btn-default">Editar producto</a>
                	
                </td>      
            </tr>
        </c:forEach>
       
        </tbody>
    </table>
</petclinic:layout>