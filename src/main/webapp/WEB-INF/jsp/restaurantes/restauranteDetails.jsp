<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

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

    <spring:url value="{restaurantesId}/reservas/new" var="reservaUrl">
        <spring:param name="restaurantesId" value="${restaurante.id}"/>
    </spring:url>
    <a href="${fn:escapeXml(reservaUrl)}" class="btn btn-default">Realizar Reserva</a>

    <spring:url value="{restaurantesId}/pedido/new" var="pedidoUrl">
        <spring:param name="restaurantesId" value="${restaurante.id}"/>
    </spring:url>
    <a href="${fn:escapeXml(pedidoUrl)}" class="btn btn-default">Realizar Pedido</a>

    <br/>
    <br/>
    <br/>
    <h2>Productos Disponibles</h2>

    <table class="table table-striped">
        <c:forEach var="producto" items="${restaurante.productos}">
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