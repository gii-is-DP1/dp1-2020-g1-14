<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>

<petclinic:layout pageName="reservas">
    <h2>Reservas</h2>
    
    <sec:authorize access="hasAuthority('cliente')">
	<spring:url value="/restaurantes/${restauranteId}/reservas/${name}/new" var="reservaUrl">
                    </spring:url>
                    <a href="${fn:escapeXml(reservaUrl)}" class="btn btn-default">New</a>
	<spring:url value="/restaurantes/${restauranteId}/reservas/${name}"  var="reservaUrl1">
                    </spring:url>
                    <a href="${fn:escapeXml(reservaUrl1)}" class="btn btn-default">Refrescar página</a>
    </sec:authorize>                
                   
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
        <c:forEach items="${reservas}" var="reserva">
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
					<spring:url value="/restaurantes/${restauranteId}/reservas/${name}/delete/{reservaId}" var="reservaUrl">
	                	<spring:param name="reservaId" value="${reserva.id}"/>
	                </spring:url>
	                <a href="${fn:escapeXml(reservaUrl)}" class="btn btn-default">Delete</a>
	                <sec:authorize access="hasAuthority('gerente') || hasAuthority('admin')">
	                <spring:url value="/restaurantes/${restauranteId}/reservas/${name}/present/{reservaId}" var="reservaUrl">
	                	<spring:param name="reservaId" value="${reserva.id}"/>
	                </spring:url>
	                <a href="${fn:escapeXml(reservaUrl)}" class="btn btn-default">Presentado</a>
	                </sec:authorize> 
	                </td>           
	            </tr>
        </c:forEach>
       
        </tbody>
    </table>
</petclinic:layout>