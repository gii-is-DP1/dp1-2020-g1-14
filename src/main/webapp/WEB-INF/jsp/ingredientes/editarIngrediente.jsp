<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>


<petclinic:layout pageName="Ingredientes">
    <jsp:body>
        <h2>Ingredientes</h2>
        
        <form:form modelAttribute="ingrediente" class="form-horizontal" action="/restaurantes/${restauranteId}/ingredientes/save">
            <div class="form-group has-feedback">
                <petclinic:inputField label="Nombre" name="name"/>
                <petclinic:inputField label="Stock" name="stock"/>
                <petclinic:selectField label="Unidad de Medida" name="medida" names="${medidas}" size="${fn:length(medidas)}"/>
            </div>
	
            <div class="form-group">
                <div class="col-sm-offset-2 col-sm-10">
                    <input type="hidden" name="id" value="${ingrediente.id}"/>
                    <input type="hidden" name="restaurante" value="${restaurante}"/>
                    <button class="btn btn-default" type="submit">Guardar Ingrediente</button>
                    
                </div>
            </div>
        </form:form>
    </jsp:body>

</petclinic:layout>
