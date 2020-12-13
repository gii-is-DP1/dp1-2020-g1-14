<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="gerentes">
    <h2>Gerentes</h2>
    <spring:url value="/gerentes/new" var="gerenteUrl">
                    </spring:url>
                    <a href="${fn:escapeXml(gerenteUrl)}" class="btn btn-default">New</a>

    <table id="gerentesTable" class="table table-striped">
        <thead>
        <tr>
            <th style="width: 150px;">Nombre</th>
            <th style="width: 200px;">DNI</th>
            <th>Contraseña</th>
            <th>Fecha de Registro</th>
            <th>Restaurante</th>
            <th>Acciones</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${gerentes}" var="gerente">
            <tr>
                <td>
                    <c:out value="${gerente.name}"/>
                </td>
                <td>
                    <c:out value="${gerente.dni}"/>
                </td>
                <td>
                    <c:out value="${gerente.password}"/>
                </td>
                <td>
                    <c:out value="${gerente.rDate}"/>
                </td>
                <td>
                    <c:out value="${gerente.restaurante.name}"/>
                </td>
                <td>
                    <spring:url value="/gerentes/delete/{gerenteId}" var="gerenteUrl">
                        <spring:param name="gerenteId" value="${gerente.id}"/>
                    </spring:url>
                	<a href="${fn:escapeXml(gerenteUrl)}" >Borrar</a>
                	<spring:url value="/gerentes/{gerenteId}/edit" var="gerenteUrl">
        				<spring:param name="gerenteId" value="${gerente.id}"/>
    				</spring:url>
    				<a href="${fn:escapeXml(gerenteUrl)}" >Editar</a>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</petclinic:layout>
 