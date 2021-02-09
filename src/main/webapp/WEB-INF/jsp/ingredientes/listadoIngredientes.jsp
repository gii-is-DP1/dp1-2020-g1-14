<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="ingredientes">
    <h2>Ingredientes</h2>
	<spring:url value="/restaurantes/${restauranteId}/ingredientes/new" var="ingredienteUrl">
                    </spring:url>
                    <a href="${fn:escapeXml(ingredienteUrl)}" class="btn btn-default">New</a>
   <spring:url value="/restaurantes/${restauranteId}/ingredientes" var="ingredienteUrl">
                    </spring:url>
                    <a href="${fn:escapeXml(ingredienteUrl)}" class="btn btn-default">Actualizar</a>

    <table id="ingredientesTable" class="table table-striped">
        <thead>
        <tr>
            <th style="width: 150px;">Nombre</th>
            <th style="width: 200px;">Stock</th>
            <th>Acciones</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${ingredientes}" var="ingrediente">
            <tr>
            	<td>
					<c:out value="${ingrediente.name}"/>
                </td>
                <td>
                    <c:out value="${ingrediente.stock} ${ingrediente.medida}"/>
                </td>
                <td>
                    <spring:url value="/restaurantes/${restauranteId}/ingredientes/delete/{ingredienteId}" var="ingredienteUrl">
                        <spring:param name="ingredienteId" value="${ingrediente.id}"/>
                    </spring:url>
                	<a class="btn btn-default" href="${fn:escapeXml(ingredienteUrl)}">Borrar</a>
                	
                	<spring:url value="/restaurantes/${restauranteId}/ingredientes/{ingredienteId}/edit" var="ingredienteUrl">
                        <spring:param name="ingredienteId" value="${ingrediente.id}"/>
                    </spring:url>
                	<a class="btn btn-default" href="${fn:escapeXml(ingredienteUrl)}">Editar</a>
                	
                	<spring:url value="/restaurantes/${restauranteId}/proveedores/${ingrediente.id}" var="ingredienteUrl"/>
                	<a class="btn btn-default" href="${fn:escapeXml(ingredienteUrl)}">Ver proveedores vinculados</a>
                	
                	<spring:url value="/restaurantes/${restauranteId}/proveedores/{ingredienteId}/vincula" var="ingredienteUrl">
                        <spring:param name="ingredienteId" value="${ingrediente.id}"/>
                    </spring:url>
                	<a class="btn btn-default" href="${fn:escapeXml(ingredienteUrl)}">Vincular proveedor</a>
                	
                	<spring:url value="/restaurantes/${restauranteId}/productos/${ingrediente.id}" var="ingredienteUrl"/>
                	<a class="btn btn-default" href="${fn:escapeXml(ingredienteUrl)}">Ver productos vinculados</a>
                	
                	<c:if test="${prod==true}">
	    				<spring:url value="/restaurantes/${restauranteId}/ingredientes/${productoId}/vincula/${ingrediente.id}" var="productoUrl"/>
	    				<a href="${fn:escapeXml(productoUrl)}" class="btn btn-default">Vincular producto al ingrediente</a>
    				</c:if>
    				
    				<c:if test="${prod==false}">
	    				<spring:url value="/restaurantes/${restauranteId}/ingredientes/${productoId}/separa/${ingrediente.id}" var="productoUrl"/>
                    	<a class="btn btn-default" href="${fn:escapeXml(productoUrl)}">desvincular</a>
    				</c:if>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
    <spring:url value="/restaurantes/${restauranteId}" var="restauranteUrl"/>
    <a class="btn btn-default" href="${fn:escapeXml(restauranteUrl)}">Volver atrás</a>
</petclinic:layout>
 