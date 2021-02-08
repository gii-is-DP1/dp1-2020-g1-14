<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="proveedores">
    <h2>Proveedores</h2>
	<spring:url value="/restaurantes/${restauranteId}/proveedores/new" var="proveedorUrl">
                    </spring:url>
                    <a  class="btn btn-default" href="${fn:escapeXml(proveedorUrl)}">Agregar proveedor nuevo</a>
    <table id="proveedoresTable" class="table table-striped">
        <thead>
        <tr>
            <th style="width: 150px;">Name</th>
            <th style="width: 120px">Telephone</th>
            <th style="width: 120px">Actions</th>
            
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${proveedores}" var="proveedor">
            <tr>
                <td>
                    <c:out value="${proveedor.name}"/>
                </td>
                <td>
                    <c:out value="${proveedor.tlf}"/>
                </td>
				<td>
				<spring:url value="/restaurantes/${restauranteId}/proveedores/delete/{proveedorId}" var="proveedorUrl">
                        <spring:param name="proveedorId" value="${proveedor.id}"/>
                    </spring:url>
                    <a class="btn btn-default" href="${fn:escapeXml(proveedorUrl)}">Eliminar</a>
                    
                     <spring:url value="/restaurantes/${restauranteId}/proveedores/{proveedorId}/edit" var="proveedorUrl">
        				<spring:param name="proveedorId" value="${proveedor.id}"/>
    				</spring:url>
    				<a href="${fn:escapeXml(proveedorUrl)}" class="btn btn-default">Editar</a>
    				
    				<spring:url value="/restaurantes/${restauranteId}/proveedores/select/${proveedor.id}" var="proveedorUrl"/>
    				<a href="${fn:escapeXml(proveedorUrl)}" class="btn btn-default">A�adir Proveedor</a>
                </td>
                
                       
            </tr>
        </c:forEach>
       
        </tbody>
    </table>
</petclinic:layout>