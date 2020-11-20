<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>


<petclinic:layout pageName="clientes">
    <h2>Socios</h2>
    <table id="clienteTable" class="table table-striped">
        <thead>
        <tr>
            <th style="width: 150px;">Nombre</th>
            <th style="width: 120px">Fecha</th>
            <th style="width: 120px">Número pedidos</th>
            <th style="width: 120px">Telefono</th>
            <th style="width: 120px">Acciones</th>
            
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${clientes}" var="cliente">
        <c:if test="${cliente.esSocio == true }">
            <tr>
                <td>
                    <c:out value="${cliente.name}"/>
                </td>
                <td>
  
    				<c:out value="${cliente.rDate}"/>
                    
                </td>
                <td>
                    <c:out value="${cliente.numPedidos}"/>
                </td>
                 <td>
                    <c:out value="${cliente.tlf}"/>
                </td>
				<td>
				<spring:url value="/clientes/delete/{clienteId}" var="clienteUrl">
                        <spring:param name="clienteId" value="${cliente.id}"/>
                    </spring:url>
                    <a href="${fn:escapeXml(clienteUrl)}">Delete</a>
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
            </c:if>
        </c:forEach>
       
        </tbody>
    </table>
</petclinic:layout>