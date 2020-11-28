<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="reclamaciones">
    <h2>Reclamaciones</h2>

<spring:url value="/reclamaciones/new" var="reclamacionUrl">
                    </spring:url>
                    <a href="${fn:escapeXml(reclamacionUrl)}">New</a>
    <table id="reclamacionesTable" class="table table-striped">
        <thead>
        <tr>
            <th style="width: 150px;">Fecha</th>
            <th style="width: 200px;">Descripci�n</th>


        </tr>
        </thead>
        <tbody>
        <c:forEach items="${reclamaciones}" var="reclamacion">
            <tr>
                <td>
                    <c:out value="${reclamacion.fecha}"/>
                </td>
                <td>
                    <c:out value="${reclamacion.descripcion}"/>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</petclinic:layout>
