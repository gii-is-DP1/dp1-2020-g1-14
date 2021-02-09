<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<petclinic:layout pageName="Reservas">
	<jsp:attribute name="customScript">
        <script>
            $(function () {
                $("#fecha").datepicker({dateFormat: 'yy/mm/dd'});
            });
        </script>
    </jsp:attribute>
    <jsp:body>
          <h2>Reservas</h2>
		<form:form modelAttribute="reserva" class="form-horizontal" action= "/restaurantes/${restauranteId}/reservas/${username}/save" >
            <div class="form-group has-feedback">
            
            	<petclinic:inputField label="Fecha" name="fecha"/>
            	
            	<spring:bind path="horaInicio">
    				<c:set var="cssGroup" value="form-group ${status.error ? 'has-error' : '' }"/>
    				<c:set var="valid" value="${not status.error and not empty status.actualValue}"/>
    				<div class="${cssGroup}">
        				<label class="col-sm-2 control-label">Hora Inicio</label>

        				<div class="col-sm-10">
        					<input type="time" name="horaInicio" value="00:00">
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
				
                <spring:bind path="horaFin">
    				<c:set var="cssGroup" value="form-group ${status.error ? 'has-error' : '' }"/>
    				<c:set var="valid" value="${not status.error and not empty status.actualValue}"/>
    				<div class="${cssGroup}">
        				<label class="col-sm-2 control-label">Hora Fin</label>

        				<div class="col-sm-10">
        					<input type="time" name="horaFin" value="00:00">
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
				
                <spring:bind path="evento">
    				<c:set var="cssGroup" value="form-group ${status.error ? 'has-error' : '' }"/>
    				<c:set var="valid" value="${not status.error and not empty status.actualValue}"/>
    				<div class="${cssGroup}">
        				<label class="col-sm-2 control-label">Evento</label>
        				<div class="col-sm-10">
        				
        					<fieldset>
        						<form:radiobutton path="evento" value="true"/> Si
								<form:radiobutton path="evento" value="false"/> No
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
				
                <petclinic:inputField label="Nº Personas" name="nPersonas"/>
                      
                        
            </div>

            <div class="form-group">
                <div class="col-sm-offset-2 col-sm-10">
                    <input type="hidden" name="id" value="${reserva.id}"/>
                    <input type="hidden" name="restaurante" value="${restaurante}"/> 
                    <c:choose>
                    	<c:when test="${reserva['new']}">
                    		<button class="btn btn-default" type="submit">Add reserva</button>
                    	</c:when>
                    	<c:otherwise>
                    		<button class="btn btn-default" type="submit">Update reserva</button>
                    	</c:otherwise>
                    </c:choose>
                    <spring:url value="/restaurantes/${restauranteId}" var="restauranteUrl"/>
    				<a class="btn btn-default" href="${fn:escapeXml(restauranteUrl)}">Volver atrás</a>
                </div>
            </div>
        </form:form>       
    </jsp:body>

</petclinic:layout>