<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<petclinic:layout pageName="Productos">
    <jsp:body>
        <h2>Productos</h2>
        
        <c:choose>
        	<c:when test="${producto['new']}">
            	<c:set var="action" value="/restaurantes/${restauranteId}/productos/save"/>
            </c:when>
            <c:otherwise>
                <c:set var="action" value="/restaurantes/${restauranteId}/productos/save/${producto.id}"/>
            </c:otherwise>
       </c:choose>
        <form:form modelAttribute="producto" class="form-horizontal" action="${action}">
            <div class="form-group has-feedback">
                <petclinic:inputField label="Name" name="name"/>
                
                <spring:bind path="precio">
               		<c:set var="cssGroup" value="form-group ${status.error ? 'has-error' : '' }"/>
    				<c:set var="valid" value="${not status.error and not empty status.actualValue}"/>
    				<div class="${cssGroup}">
        				<label class="col-sm-2 control-label">Precio</label>

        				<div class="col-sm-10">
        					<input type="number" name="precio" value="${producto.precio}" step=".01">
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
                
                <petclinic:inputField label="Alergenos" name="alergenos"/>
            </div>
	

            <div class="form-group">
                <div class="col-sm-offset-2 col-sm-10">
                    <input type="hidden" name="id" value="${producto.id}"/>
                    <input type="hidden" name="restaurante" value="${restaurante}"/>
                    <input type="hidden" name="version" value="${producto.version}"/>
                    <c:choose>
                    	<c:when test="${producto['new']}">
                    		<button class="btn btn-default" type="submit">Añadir producto</button>
                    	</c:when>
                    	<c:otherwise>
                    		<button class="btn btn-default" type="submit">Actualizar producto</button>
                    	</c:otherwise>
                    </c:choose>
                    
                    <spring:url value="/restaurantes/${restauranteId}/productos" var="productoUrl">
                    </spring:url>
                    <a href="${fn:escapeXml(productoUrl)}" class="btn btn-default">Volver atrás</a>
                </div>
            </div>
            
            
        </form:form>
    </jsp:body>

</petclinic:layout>
