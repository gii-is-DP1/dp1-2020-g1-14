<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="ofertas">
    
    <h2>Ofertas</h2>
	
	<spring:url value="/restaurantes/${restauranteId}/ofertas/new" var="ofertaUrl">
                    </spring:url>
                    <a class="btn btn-default" href="${fn:escapeXml(ofertaUrl)}">Agregar oferta</a>
    
    <table id="ofertasTable" class="table table-striped">
        
        <thead>
        <tr>
            <th style="width: 150px;">id</th>
            <th style="width: 120px">Descripcion</th>
            <th style="width: 120px">Precio mínimo</th>
            <th style="width: 120px">Descuento</th>
            <th style="width: 120px">Exclusivo</th>
            <th style="width: 120px">Actions</th>
            
        </tr>
        </thead>
        <tbody>
        
        <c:forEach items="${ofertas}" var="oferta">
            <tr>
                <td>
                    <c:out value="${oferta.id}"/>
                </td>
                
                <td>
                    <c:out value="${oferta.descripcion}"/>
                </td>
                
                <td>
                    <c:out value="${oferta.minPrice}"/>
                </td>
                
                <td>
                    <c:out value="${oferta.descuento}"/>
                </td>
                
                <td>
                    <c:out value="${oferta.exclusivo}"/>
                </td>
					
				<td>
				
				<spring:url value="/restaurantes/${restauranteId}/ofertas/delete/{ofertaId}" var="ofertaUrl">
                        <spring:param name="ofertaId" value="${oferta.id}"/>
                    </spring:url>
                    
                    <a class="btn btn-default" href="${fn:escapeXml(ofertaUrl)}">Eliminar</a>
                    
                     <spring:url value="/restaurantes/${restauranteId}/ofertas/edit/{ofertaId}" var="ofertaUrl">
        				<spring:param name="ofertaId" value="${oferta.id}"/>
    				</spring:url>
    				
    				<a href="${fn:escapeXml(ofertaUrl)}" class="btn btn-default">Editar</a>
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