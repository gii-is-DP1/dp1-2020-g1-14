<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>

<petclinic:layout pageName="pedidos">
    <h2>Pedidos</h2>
    
    <sec:authorize access="hasAuthority('cliente')">
    	<spring:url value="/restaurantes/{restauranteId}/pedidos/${userName}/new" var="pedidoUrl">
    	<spring:param name="restauranteId" value="${restauranteId}"/>
                    	</spring:url>
                    	<a class="btn btn-default" href="${fn:escapeXml(pedidoUrl)}">Nuevo pedido</a>
	</sec:authorize>
	
    <table id="pedidoTable" class="table table-striped">
        <thead>
        <tr>
        	<sec:authorize access="hasAuthority('admin') || hasAuthority('gerente')">
        	<th style="width: 150px">Cliente</th>
        	</sec:authorize>
            <th style="width: 150px">Adress</th>
            <th style="width: 120px">Estado</th>
            <th style="width: 120px">Fecha</th>
            <th style="width: 120px">Price</th>
            <th style="width: 120px">Productos</th>
            <th style="width: 120px">Cantidad</th>
            <th style="width: 120px">Oferta</th>
            <sec:authorize access="hasAuthority('cliente')">
            <th style="width: 120px">Añadir producto</th>
            <th style="width: 120px">Añadir oferta</th>
            <th style="width: 120px">Cancelar</th>
            <th style="width: 120px">Actualizar</th>
            <th style="width: 120px">Hacer pedido</th>
            </sec:authorize>
            <sec:authorize access="hasAuthority('admin') || hasAuthority('gerente')">
            <th style="width: 120px">Preparando</th>
            <th style="width: 120px">En reparto</th>
            <th style="width: 120px">Recibido</th>
            </sec:authorize>
            <th style="width: 120px">¿Actualizado?</th>
            
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${pedidos}" var="pedido">
        	<sec:authorize access="hasAuthority('cliente')">
	            <tr>
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
	            		<c:forEach var="lineaPedido" items="${lineaPedidos}">
	            			<c:if test="${lineaPedido.pedido.id==pedido.id}">
	            				<c:out value="${lineaPedido.producto.name} "/>
	            			</c:if>
	                    </c:forEach>
	            	</td>
	            	
	            	<td>
	            		<c:forEach var="lineaPedido" items="${lineaPedidos}">
	            			<c:if test="${lineaPedido.pedido.id==pedido.id}">
	                        	<c:out value="${lineaPedido.cantidad} "/>
	                        </c:if>
	                    </c:forEach>
					</td>
					
					<td>
						<c:out value="${pedido.oferta.descripcion}"/>
					</td>
					
					<td>
	                  <spring:url  value="/restaurantes/${restauranteId}/pedidos/${userName}/${pedido.id}/lineaPedidos/new" var="lineaPedidosUrl">
	                    </spring:url>
	                    <a href="${fn:escapeXml(lineaPedidosUrl)}" class="btn btn-default">Añade producto</a> 
	                </td>
	                
	                <td>
	                  <spring:url  value="/restaurantes/${restauranteId}/pedidos/${userName}/${pedido.id}/oferta" var="pedidoUrl">
	                    </spring:url>
	                    <a href="${fn:escapeXml(pedidoUrl)}" class="btn btn-default">Añade oferta</a> 
	                </td>
	                   
	                <td>
	                <spring:url value="/restaurantes/{restauranteId}/pedidos/${userName}/cancel/{pedidoId}" var="pedidoUrl">
	                	<spring:param name="restauranteId" value="${restauranteId}"/>
	                    <spring:param name="pedidoId" value="${pedido.id}"/>
	                </spring:url>
	                <a class="btn btn-default" href="${fn:escapeXml(pedidoUrl)}">Cancel</a>
	                </td>
	                
	                <td>
	                <spring:url value="/restaurantes/{restauranteId}/pedidos/${userName}/refresh/{pedidoId}" var="pedidoUrl">
	                        <spring:param name="pedidoId" value="${pedido.id}"/>
	                        <spring:param name="restauranteId" value="${restauranteId}"/>
	                    </spring:url>
	                    <a class="btn btn-default" href="${fn:escapeXml(pedidoUrl)}">Refresca</a>
	                </td>
	                
	                 <td>
	                <spring:url value="/restaurantes/{restauranteId}/pedidos/${userName}/verify/{pedidoId}" var="pedidoUrl">
	                <spring:param name="restauranteId" value="${restauranteId}"/>
	                        <spring:param name="pedidoId" value="${pedido.id}"/>
	                    </spring:url>
	                    <a class="btn btn-default" href="${fn:escapeXml(pedidoUrl)}">Confirmar pedido</a>
	                </td> 
	                   
	                   <td>
	                    <c:out value="${pedido.checkea}"/>
	                </td>
	              </tr>
              </sec:authorize>
              

	        	<sec:authorize access="hasAuthority('admin') || hasAuthority('gerente')">
		            <tr>
		            	<td>
		                    <c:out value="${pedido.cliente.user.username}"/>
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
		            		<c:forEach var="lineaPedido" items="${lineaPedidos}">
		            			<c:if test="${lineaPedido.pedido.id==pedido.id}">
	                        		<c:out value="${lineaPedido.producto.name} "/>
	                        	</c:if>
		                    </c:forEach>
		            	</td>
		            	
		            	<td>
		            		<c:forEach var="lineaPedido" items="${lineaPedidos}">
		            			<c:if test="${lineaPedido.pedido.id==pedido.id}">
		                        	<c:out value="${lineaPedido.cantidad} "/>
	                        	</c:if>

		                    </c:forEach>
						</td>
						
						<td>
							<c:out value="${pedido.oferta.descripcion}"/>
						</td>		                
		                
		                <td>
		                <spring:url value="/restaurantes/{restauranteId}/pedidos/${userName}/preparando/{pedidoId}" var="pedidoUrl">
		                <spring:param name="restauranteId" value="${restauranteId}"/>
		                        <spring:param name="pedidoId" value="${pedido.id}"/>
		                    </spring:url>
		                    <a class="btn btn-default" href="${fn:escapeXml(pedidoUrl)}">Preparando</a>
		                </td>
		                
		                <td>
		                <spring:url value="/restaurantes/{restauranteId}/pedidos/${userName}/reparto/{pedidoId}" var="pedidoUrl">
		                <spring:param name="restauranteId" value="${restauranteId}"/>
		                        <spring:param name="pedidoId" value="${pedido.id}"/>
		                    </spring:url>
		                    <a class="btn btn-default" href="${fn:escapeXml(pedidoUrl)}">En Reparto</a>
		                </td>
		                
		                <td>
		                <spring:url value="/restaurantes/{restauranteId}/pedidos/${userName}/recibido/{pedidoId}" var="pedidoUrl">
		                		<spring:param name="restauranteId" value="${restauranteId}"/>
		                        <spring:param name="pedidoId" value="${pedido.id}"/>
		                    </spring:url>
		                    <a class="btn btn-default" href="${fn:escapeXml(pedidoUrl)}">Recibido</a>
		                </td> 
		                   
		                   <td>
		                    <c:out value="${pedido.checkea}"/>
		                </td>
		              </tr>
              </sec:authorize>
              
        </c:forEach>        
        </tbody>
    </table>
</petclinic:layout>