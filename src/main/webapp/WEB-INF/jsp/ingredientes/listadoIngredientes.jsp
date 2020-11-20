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
                    <spring:url value="/ingredientes/delete/{ingredienteId}" var="ingredienteUrl">
                        <spring:param name="ingredienteId" value="${ingrediente.id}"/>
                    </spring:url>
                	<a href="${fn:escapeXml(ingredienteUrl)}">Borrar</a>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</petclinic:layout>
 