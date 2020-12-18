<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="pedidos">
    <h2>Pedidos</h2>
	<spring:url value="/pedidos/new" var="pedidoUrl">
                    </spring:url>
                    <a href="${fn:escapeXml(pedidoUrl)}">Nuevo pedido</a>
    <table id="pedidoTable" class="table table-striped">
        <thead>
        <tr>
        	<th style="width: 150px;">Cliente</th>
            <th style="width: 150px;">Adress</th>
            <th style="width: 120px">Estado</th>
            <th style="width: 120px">Fecha</th>
            <th style="width: 120px">Price</th>
            <th style="width: 120px">Productos</th>
            <th style="width: 120px">Cantidad</th>
            <th style="width: 120px">Actions</th>
            
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${pedidos}" var="pedido">
            <tr>
            	<td>
                    <c:out value="${pedido.cliente.name}"/>
                </td>
                <td>
                    <c:out value="${pedido.adress}"/>
                </td>
                <td>
                    <c:out value="${pedido.estado}"/>
                </td>
                <td>
                    <c:out value="${pedido.orderDate}"/>
                </td>
                <td>
                    <c:out value="${pedido.price}"/>
                </td>
                <td>
            		<c:forEach var="lineaPedido" items="${pedido.lineaPedido}">
                        <c:out value="${lineaPedido.producto.name} "/>
                    </c:forEach>
            	</td>
            	<td>
            		<c:forEach var="lineaPedido" items="${pedido.lineaPedido}">
                        <c:out value="${lineaPedido.cantidad} "/>
                    </c:forEach>
				<td>
				<spring:url value="/pedidos/cancel/{pedidoId}" var="pedidoUrl">
                        <spring:param name="pedidoId" value="${pedido.id}"/>
                    </spring:url>
                    <a href="${fn:escapeXml(pedidoUrl)}">Cancelar</a>
                </td>
              </tr>
        </c:forEach>  
          
      
<!--
                <td> 
                    <c:out value="${owner.user.username}"/> 
                </td>
                <td> 
                   <c:out value="${owner.user.password}"/> 
                </td> 
-->
                
            
       
        </tbody>
    </table>
</petclinic:layout>