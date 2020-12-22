<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="lineaPedidos">
    <h2>LineaPedidos</h2>
	<spring:url  value="/lineaPedidos/new" var="lineaPedidosUrl">
                    </spring:url>
                    <a href="${fn:escapeXml(lineaPedidosUrl)}" class="btn btn-default">New</a>
    <table id="LineaPedidoTable" class="table table-striped">
        <thead>
        <tr>
        	<th style="width: 120px">Productos</th>
           	<th style="width: 120px">Cantidad</th>
            
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${lineaPedidos}" var="LineaPedido">
            <tr>
                <td>
                    <c:out value="${LineaPedido.producto.name}"/>
                </td>
                <td>
                    <c:out value="${LineaPedido.cantidad}"/>
                

                
                
                
      
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