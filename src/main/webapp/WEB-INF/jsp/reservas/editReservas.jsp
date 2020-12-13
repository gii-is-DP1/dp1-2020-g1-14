<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>


<petclinic:layout pageName="Reservas">
    <jsp:body>
          <h2>Reservas</h2>
		<form:form modelAttribute="reserva" class="form-horizontal" action= "/reservas/save" onsubmit="return validar()">
            <div class="form-group has-feedback">
            	<petclinic:inputField label="Fecha" name="fecha"/>
                <petclinic:inputField label="Hora Inicio" name="horaInicio"/>
                <petclinic:inputField label="Hora Fin" name="horaFin"/>
                <petclinic:inputField label="Evento" name="evento"/>
                <petclinic:inputField label="Nº Personas" name="nPersonas"/>                
            </div>

            <div class="form-group">
                <div class="col-sm-offset-2 col-sm-10">
                    <input type="hidden" name="id" value="${reservas.id}"/>
                    <c:choose>
                    	<c:when test="${reservas['new']}">
                    		<button class="btn btn-default" type="submit">Add reserva</button>
                    	</c:when>
                    	<c:otherwise>
                    		<button class="btn btn-default" type="submit">Update reserva</button>
                    	</c:otherwise>
                    </c:choose>
                </div>
            </div>
        </form:form>       
    </jsp:body>

</petclinic:layout>