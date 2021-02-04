<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>


<petclinic:layout pageName="clientes">
    <h2>Clientes</h2>
    <spring:url value="/clientes/new" var="clienteUrl">
                    </spring:url>
                    <a class="btn btn-default" href="${fn:escapeXml(clienteUrl)}">Agregar cliente</a>
    <spring:url value="/clientes/socios" var="clienteUrl">
                    </spring:url>
                    <a class="btn btn-default" href="${fn:escapeXml(clienteUrl)}">Socios</a>
    <table id="clienteTable" class="table table-striped">
        <thead>
        <tr>
            <th style="width: 150px;">Usuario</th>
            <th style="width: 120px">Fecha de Registro</th>
            <th style="width: 120px">Número pedidos</th>
            <th style="width: 120px">Telefono</th>
            <th style="width: 120px">Acciones</th>     
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${clientes}" var="cliente">
            <tr>
                <td>
                    <c:out value="${cliente.user.username}"/>
                </td>
                <td>
    				<c:out value="${cliente.user.rDate}"/>
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
                    <a class="btn btn-default"  href="${fn:escapeXml(clienteUrl)}">Eliminar</a>

                    
                    <spring:url value="/clientes/upgrade/{clienteId}" var="clienteUrl">
                        <spring:param name="clienteId" value="${cliente.id}"/>
                    </spring:url>
                    <a class="btn btn-default"  href="${fn:escapeXml(clienteUrl)}">Hacerlo socio</a>
                </td>
             
            
                
                
      
<!--
                <td> 
                    <c:out value="${owner.user.username}"/> 
                </td>
                <td> 
                   <c:out value="${owner.user.password}"/> 
                </td> 
-->
                

                </td>               

            </tr>
        </c:forEach>
        </tbody>
    </table>
</petclinic:layout>