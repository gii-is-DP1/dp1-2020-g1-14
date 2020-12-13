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
                    <a href="${fn:escapeXml(restauranteUrl)}" class="btn btn-default">New</a>
    <table id="restauranteTable" class="table table-striped">
        <thead>
        <tr>
            <th style="width: 150px">Nombre</th>
            <th style="width: 120px">Tipo</th>
            <th style="width: 120px">Localizacion</th>
            <th style="width: 120px">Aforo maximo</th>
            <th style="width: 120px">Aforo restante</th>
            <th style="width: 120px">Actions</th>
            
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${restaurantes}" var="restaurantes">
            <tr>
                <td>
                    <c:out value="${restaurantes.name}"/>
                </td>
                <td>
                    <c:out value="${restaurantes.tipo}"/>
                </td>
                <td>
                    <c:out value="${restaurantes.localizacion}"/>
                </td>
                <td>
                    <c:out value="${restaurantes.aforomax}"/>
                </td>
                <td>
                    <c:out value="${restaurantes.aforores}"/>
                </td>
				<td>
				<spring:url value="/restaurantes/delete/{restaurantesId}" var="restauranteUrl">
                        <spring:param name="restaurantesId" value="${restaurantes.id}"/>
                    </spring:url>
                    <a href="${fn:escapeXml(restauranteUrl)}" class="btn btn-default">Delete</a>
                    <spring:url value="/restaurantes/{restaurantesId}/edit" var="restauranteUrl">
        				<spring:param name="restaurantesId" value="${restaurantes.id}"/>
    				</spring:url>
    				<a href="${fn:escapeXml(restauranteUrl)}" class="btn btn-default">Edit</a>
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