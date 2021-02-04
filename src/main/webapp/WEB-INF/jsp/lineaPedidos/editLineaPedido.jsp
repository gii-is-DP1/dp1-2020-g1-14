<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<petclinic:layout pageName="lineaPedido">
    <jsp:body>
        <h2>LineaPedidos</h2>
        
        <form:form modelAttribute="lineaPedido" class="form-horizontal" action="save">
            <div class="form-group has-feedback">
                <petclinic:selectField name="producto" label="nombres " names="${nombres}" size="${fn:length(nombres)}"/>     
             	<spring:bind path="cantidad">
               		<c:set var="cssGroup" value="form-group ${status.error ? 'has-error' : '' }"/>
    				<c:set var="valid" value="${not status.error and not empty status.actualValue}"/>
    				<div class="${cssGroup}">
        				<label class="col-sm-2 control-label">Cantidad</label>

        				<div class="col-sm-10">
        					<input type="number" name="cantidad" value="0">
           					<c:if test="${valid}">
               					<span class="glyphicon glyphicon-ok form-control-feedback" aria-hidden="true"></span>
            				</c:if>
            				<c:if test="${status.error}">
                				<span class="glyphicon glyphicon-remove form-control-feedback" aria-hidden="true"></span>
                				<span class="help-inline">${status.errorMessage}</span>
            				</c:if>
        				</div>
    				</div>
               	</spring:bind>
            </div>
	

            <div class="form-group">
                <div class="col-sm-offset-2 col-sm-10">
                    <input type="hidden" name="id" value="${lineaPedido.id}"/>
                    <input type="hidden" name="pedido" value="${pedido.id}"/> 
                    <input type="hidden" name="pedido" value="${restaurante.id}"/> 
                    <button class="btn btn-default" type="submit">Agregar producto</button>
                    
                    <spring:url value="/restaurantes/{restauranteId}/pedidos" var="pedidoUrl">
    					<spring:param name="restauranteId" value="${restauranteId}"/>
                    </spring:url>
                    <a class="btn btn-default" href="${fn:escapeXml(pedidoUrl)}">Volver atrás</a>
                </div>
                
            </div>
            
            
        </form:form>
    </jsp:body>

</petclinic:layout>
