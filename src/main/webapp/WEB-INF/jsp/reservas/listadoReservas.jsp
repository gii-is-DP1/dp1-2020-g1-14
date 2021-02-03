<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="reservas">
    <h2>Reservas</h2>
	<spring:url value="/restaurantes/${restaurante.id}/reservas/${name}/new" var="reservaUrl">
                    </spring:url>
                    <a href="${fn:escapeXml(reservaUrl)}" class="btn btn-default">New</a>
	<spring:url value="/restaurantes/${restaurante.id}/reservas/${name}"  var="reservaUrl1">
                    </spring:url>
                    <a href="${fn:escapeXml(reservaUrl1)}" class="btn btn-default">Refrescar página</a>
    <table id="reservaTable" class="table table-striped">
        <thead>
        <tr>
            <th style="width: 150px">Fecha</th>
            <th style="width: 120px">Hora Inicio</th>
            <th style="width: 120px">Hora Fin</th>
            <th style="width: 120px">Evento</th>
            <th style="width: 120px">Nº Personas</th>
            <th style="width: 120px">Acciones</th>
            
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${restaurante.reservas}" var="reserva">
            <c:if test="${reserva.cliente.user.username==name}">
	            <tr>
	                <td>
	                    <c:out value="${reserva.fecha}"/>
	                </td>
	                <td>
	                    <c:out value="${reserva.horaInicio}"/>
	                </td>
	                <td>
	                    <c:out value="${reserva.horaFin}"/>
	                </td>
	                <td>
	                    <c:out value="${reserva.evento}"/>
	                </td>
	                <td>
	                    <c:out value="${reserva.nPersonas}"/>
	                </td>
					<td>
					<spring:url value="/restaurantes/${restaurante.id}/reservas/${name}/delete/{reservaId}" var="reservaUrl">
	                        <spring:param name="reservaId" value="${reserva.id}"/>
	                    </spring:url>
	                    <a href="${fn:escapeXml(reservaUrl)}" class="btn btn-default">Delete</a>
	                    <spring:url value="/restaurantes/${restaurante.id}/reservas/{reservaId}/${name}/edit" var="reservaUrl">
	        				<spring:param name="reservaId" value="${reserva.id}"/>
	    				</spring:url>
	    				<a href="${fn:escapeXml(reservaUrl)}" class="btn btn-default">Editar</a>
	                </td>           
	            </tr>
	        </c:if>
        </c:forEach>
       
        </tbody>
    </table>
</petclinic:layout>