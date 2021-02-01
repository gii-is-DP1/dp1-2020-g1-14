<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<petclinic:layout pageName="Reclamaciones">
    <jsp:body>
        <h2>Reclamaciones</h2>
        
        <form:form modelAttribute="reclamacion" class="form-horizontal" action="/restaurantes/${restauranteId}/reclamaciones/save">
            <div class="form-group has-feedback">
                <c:out value = "Fecha: ${reclamacion.fecha} "> </c:out>
                <petclinic:inputField label="Descripcion" name="descripcion"/>
            </div>
	
            <div class="form-group">
                <div class="col-sm-offset-2 col-sm-10">
                    <input type="hidden" name="id" value="${reclamacion.id}"/>
                    <button class="btn btn-default" type="submit">Guardar Reclamacion</button>
                </div>
            </div>
        </form:form>
    </jsp:body>

</petclinic:layout>
