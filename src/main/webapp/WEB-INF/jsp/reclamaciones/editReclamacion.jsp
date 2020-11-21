<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>


<petclinic:layout pageName="Reclamaciones">
    <jsp:body>
        <h2>Reclamaciones</h2>
        
        <form:form modelAttribute="reclamacion" class="form-horizontal" action="/reclamaciones/save">
            <div class="form-group has-feedback">
                <petclinic:inputField label="Fecha" name="fecha"/>
                <petclinic:inputField label="Descripcion" name="Descripcion"/>
            </div>
	
            <div class="form-group">
                <div class="col-sm-offset-2 col-sm-10">
                    <input type="hidden" name="id" value="${reclamacion.id}"/>
                    <button class="btn btn-default" type="submit">Save Reclamacion</button>
                </div>
            </div>
        </form:form>
    </jsp:body>

</petclinic:layout>
