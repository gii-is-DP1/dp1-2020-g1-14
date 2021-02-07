<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>

<petclinic:layout pageName="restaurantes">

    <h2>${restaurante.name}</h2>


    <table class="table table-striped">
        <tr>
            <th>Tipo</th>
            <td><b><c:out value="${restaurante.tipo}"/></b></td>
        </tr>
        <tr>
            <th>Localización</th>
            <td><c:out value="${restaurante.localizacion}"/></td>
        </tr>
    </table>
	
	<sec:authorize access="hasAuthority('cliente')">
    <spring:url value="/restaurantes/{restaurantesId}/reservas/{userName}/new" var="reservaUrl">
        <spring:param name="restaurantesId" value="${restaurante.id}"/>
        <spring:param name="userName" value="${username}"/>
    </spring:url>
    <a href="${fn:escapeXml(reservaUrl)}" class="btn btn-default">Realizar Reserva</a>

    <spring:url value="/restaurantes/{restaurantesId}/pedidos/{userName}/new" var="pedidoUrl">
    	<spring:param name="userName" value="${username}"/>
        <spring:param name="restaurantesId" value="${restaurante.id}"/>
    </spring:url>
    <a href="${fn:escapeXml(pedidoUrl)}" class="btn btn-default">Realizar Pedido</a>

	<spring:url value="/restaurantes/${restaurante.id}/reservas/{userName}" var="reservaClienteUrl">
        <spring:param name="userName" value="${username}"/>
    </spring:url>
    <a href="${fn:escapeXml(reservaClienteUrl)}" class="btn btn-default">Mis reservas</a>
    
    <spring:url value="/restaurantes/${restaurante.id}/pedidos/{userName}" var="pedidoClienteUrl">
        <spring:param name="userName" value="${username}"/>
    </spring:url>
    <a href="${fn:escapeXml(pedidoClienteUrl)}" class="btn btn-default">Mis pedidos</a>
    
    <spring:url value="/restaurantes/${restaurante.id}/reclamaciones/new" var="pedidosUrl"/>
    <a href="${fn:escapeXml(pedidosUrl)}" class="btn btn-default">Redactar Reclamación</a>
    </sec:authorize>
    
    
    <sec:authorize access="hasAuthority('admin') || hasAuthority('gerente')">
	<spring:url value="/restaurantes/${restaurante.id}/reservas/{userName}" var="reservasUrl">
	<spring:param name="userName" value="${username}"/>
	</spring:url>
    <a href="${fn:escapeXml(reservasUrl)}" class="btn btn-default">Listar reservas</a>
    
    <spring:url value="/restaurantes/${restaurante.id}/reservas/{userName}" var="pedidosUrl">
    	<spring:param name="userName" value="${username}"/>
    </spring:url>
    <a href="${fn:escapeXml(pedidosUrl)}" class="btn btn-default">Listar pedidos</a>
    
    <spring:url value="/restaurantes/${restaurante.id}/ingredientes" var="ingredientesUrl"/>
    <a href="${fn:escapeXml(ingredientesUrl)}" class="btn btn-default">Listar Ingredientes</a>
    
    <spring:url value="/restaurantes/${restaurante.id}/reclamaciones" var="reclamacionesUrl"/>
    <a href="${fn:escapeXml(reclamacionesUrl)}" class="btn btn-default">Listar Reclamaciones</a>
    
    <spring:url value="/restaurantes/${restaurante.id}/ofertas" var="ofertasUrl"/>
    <a href="${fn:escapeXml(ofertasUrl)}" class="btn btn-default">Listar Ofertas</a>
    
    <spring:url value="/restaurantes/${restaurante.id}/productos" var="productosUrl"/>
    <a href="${fn:escapeXml(productosUrl)}" class="btn btn-default">Listar Productos</a>
    
    <spring:url value="/restaurantes/${restaurante.id}/proveedores" var="proveedoresUrl"/>
    <a href="${fn:escapeXml(proveedoresUrl)}" class="btn btn-default">Listar Proveedores</a>
    </sec:authorize>
    
    <br/>
    <br/>
    <br/>
    <h2>Productos Disponibles</h2>

    <table class="table table-striped">
        <c:forEach var="producto" items="${productos}">
            <tr>
                <td valign="top">
                    <dl class="dl-horizontal">
                        <dt>Nombre</dt>
                        <dd><c:out value="${producto.name}"/></dd>
                        <dt>Precio</dt>
                        <dd><c:out value="${producto.precio}"/></dd>
                        <dt>Alérgenos</dt>
                        <dd><c:out value="${producto.alergenos}"/></dd>
                    </dl>
                </td>
            </tr>
        </c:forEach>
    </table>
</petclinic:layout>