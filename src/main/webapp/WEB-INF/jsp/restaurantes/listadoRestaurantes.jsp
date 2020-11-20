<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="restaurantes">
    <h2>Restaurantes</h2>
	<spring:url value="/restaurantes/new" var="restauranteUrl">
                    </spring:url>
                    <a href="${fn:escapeXml(restauranteUrl)}">New</a>
    <table id="restaurantesTable" class="table table-striped">
        <thead>
        <tr>
            <th style="width: 150px;">Name</th>
            <th style="width: 120px">tipo</th>
            <th style="width: 120px">localizacion</th>
            <th style="width: 120px">aforo maximo</th>
            <th style="width: 120px">aforo restante</th>
            <th style="width: 120px">actions</th>
            
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${restaurantes}" var="restaurante">
            <tr>
                <td>
                    <c:out value="${restaurante.name}"/>
                </td>
                <td>
                    <c:out value="${restaurante.tipo}"/>
                </td>
                <td>
                    <c:out value="${restaurante.localizacion}"/>
                </td>
                <td>
                    <c:out value="${restaurante.aforomax}"/>
                </td>
                <td>
                    <c:out value="${restaurante.aforores}"/>
                </td>
				<td>
				<spring:url value="/restaurantes/delete/{restaurantesId}" var="RestauranteUrl">
                        <spring:param name="restauranteId" value="${restaurante.id}"/>
                    </spring:url>
                    <a href="${fn:escapeXml(restauranteUrl)}">Delete</a>
                </td>
                
                
                
      
<!--
                <td> 
                    <c:out value="${owner.user.username}"/> 
                </td>
                <td> 
                   <c:out value="${owner.user.password}"/> 
                </td> 
-->
                
            </tr>
        </c:forEach>
       
        </tbody>
    </table>
</petclinic:layout>