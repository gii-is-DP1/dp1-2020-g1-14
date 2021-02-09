<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>

<petclinic:layout pageName="proveedores">
    <h2>Proveedores</h2>
    <c:if test="${condicion==true}">
		<spring:url value="/restaurantes/${restauranteId}/proveedores/new" var="proveedorUrl"/>
        <a  class="btn btn-default" href="${fn:escapeXml(proveedorUrl)}">Añadir nuevo proveedor</a>
    </c:if>
    <table id="proveedoresTable" class="table table-striped">
        <thead>
        <tr>
            <th style="width: 150px">Name</th>
            <th style="width: 120px">Telephone</th>
            <th style="width: 120px">Actions</th>
            
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${proveedores}" var="proveedor">
            <tr>
                <td>
                	<spring:url value="/restaurantes/${restauranteId}/ingredientes/${proveedor.id}" var="proveedorUrl"/>
                    <a href="${fn:escapeXml(proveedorUrl)}"><c:out value="${proveedor.name}"/></a>
                </td>
                <td>
                    <c:out value="${proveedor.tlf}"/>
                </td>
				<td>
					<sec:authorize access="hasAuthority('admin')">
						<spring:url value="/restaurantes/${restauranteId}/proveedores/delete/{proveedorId}" var="proveedorUrl">
                        	<spring:param name="proveedorId" value="${proveedor.id}"/>
                    	</spring:url>
                    	<a class="btn btn-default" href="${fn:escapeXml(proveedorUrl)}">Eliminar</a>
                    </sec:authorize>
                    <c:if test="${condicion==false}">
                    	<spring:url value="/restaurantes/${restauranteId}/proveedores/desvincula/{proveedorId}" var="proveedorUrl">
                        	<spring:param name="proveedorId" value="${proveedor.id}"/>
                    	</spring:url>
                    	<a class="btn btn-default" href="${fn:escapeXml(proveedorUrl)}">desvincular</a>
                    </c:if>
                     <spring:url value="/restaurantes/${restauranteId}/proveedores/{proveedorId}/edit" var="proveedorUrl">
        				<spring:param name="proveedorId" value="${proveedor.id}"/>
    				</spring:url>
    				<a href="${fn:escapeXml(proveedorUrl)}" class="btn btn-default">Editar</a>
    				
    				<c:if test="${condicion==true}">
	    				<spring:url value="/restaurantes/${restauranteId}/proveedores/select/${proveedor.id}" var="proveedorUrl"/>
	    				<a href="${fn:escapeXml(proveedorUrl)}" class="btn btn-default">Vincular proveedor al restaurante</a>
    				</c:if>
    				
    				<c:if test="${ing==true}">
	    				<spring:url value="/restaurantes/${restauranteId}/proveedores/${ingredienteId}/vincula/${proveedor.id}" var="proveedorUrl"/>
	    				<a href="${fn:escapeXml(proveedorUrl)}" class="btn btn-default">Vincular proveedor al ingrediente</a>
    				</c:if>
    				
    				<c:if test="${ing==false}">
	    				<spring:url value="/restaurantes/${restauranteId}/proveedores/${ingredienteId}/separa/${proveedor.id}" var="proveedorUrl"/>
                    	<a class="btn btn-default" href="${fn:escapeXml(proveedorUrl)}">desvincular</a>
    				</c:if>
    				
                </td>    
            </tr>
        </c:forEach>
        </tbody>
    </table>
</petclinic:layout>