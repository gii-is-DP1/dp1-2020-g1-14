<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<petclinic:layout pageName="Ofertas">
    <jsp:body>
        <h2>Ofertas</h2>
        <c:choose>
        	<c:when test="${oferta['new']}">
            	<c:set var="action" value="/restaurantes/${restaurante.id}/ofertas/save"/>
            </c:when>
            <c:otherwise>
                <c:set var="action" value="/restaurantes/${restaurante.id}/ofertas/save/${oferta.id}"/>
            </c:otherwise>
       </c:choose>
              
        <form:form modelAttribute="oferta" class="form-horizontal" action="${action}">
            <div class="form-group has-feedback">
          
                <petclinic:inputField label="Descripcion" name="descripcion"/>
                
               	<spring:bind path="descuento">
               		<c:set var="cssGroup" value="form-group ${status.error ? 'has-error' : '' }"/>
    				<c:set var="valid" value="${not status.error and not empty status.actualValue}"/>
    				<div class="${cssGroup}">
        				<label class="col-sm-2 control-label">Descuento</label>

        				<div class="col-sm-10">
        					<input type="number" name="descuento" value="${oferta.descuento}">
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
                
                <spring:bind path="minPrice">
               		<c:set var="cssGroup" value="form-group ${status.error ? 'has-error' : '' }"/>
    				<c:set var="valid" value="${not status.error and not empty status.actualValue}"/>
    				<div class="${cssGroup}">
        				<label class="col-sm-2 control-label">Precio mínimo</label>

        				<div class="col-sm-10">
        					<input type="number" name="minPrice" value="${oferta.minPrice}">
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
                
               <spring:bind path="exclusivo">
    				<c:set var="cssGroup" value="form-group ${status.error ? 'has-error' : '' }"/>
    				<c:set var="valid" value="${not status.error and not empty status.actualValue}"/>
    				<div class="${cssGroup}">
        				<label class="col-sm-2 control-label">Exclusividad</label>
        				<div class="col-sm-10">
        				
        					<fieldset>
        						<form:radiobutton path="exclusivo" value="true"/> Para socios
								<form:radiobutton path="exclusivo" value="false"/> Para todos
    						</fieldset>
    						
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
	
            <div class="form-group">
                <div class="col-sm-offset-2 col-sm-10">
                    <input type="hidden" name="id" value="${oferta.id}"/>
                    <input type="hidden" name="restaurante" value="${restaurante}"/>
                    <input type="hidden" name="version" value="${oferta.version}"/>
                    <c:choose>
                    	<c:when test="${oferta['new']}">
                    		<button class="btn btn-default" type="submit">Añadir oferta</button>
                    	</c:when>
                    	<c:otherwise>
                    		<button class="btn btn-default" type="submit">Update oferta</button>
                    	</c:otherwise>
                    </c:choose>
                    
                    <spring:url value="/restaurantes/${restaurante.id}/ofertas" var="ofertaUrl">
                    </spring:url>
                    <a class="btn btn-default" href="${fn:escapeXml(ofertaUrl)}">Volver atrás</a>
                </div>
				
           </div>
           </div>
        </form:form>
    </jsp:body>

</petclinic:layout>
