<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>


<petclinic:layout pageName="Ingredientes">
    <jsp:body>
        <h2>Ingredientes</h2>
        
        <c:choose>
        	<c:when test="${ingrediente['new']}">
        		<c:set var="action" value= "/restaurantes/${restauranteId}/ingredientes/save"/>
        	</c:when>
        	<c:otherwise>
        		<c:set var="action" value= "/restaurantes/${restauranteId}/ingredientes/save/${ingredienteId}"/>
        	</c:otherwise>
        </c:choose>
        
        <form:form modelAttribute="ingrediente" class="form-horizontal" action="${action}">
            <div class="form-group has-feedback">
                <petclinic:inputField label="Nombre" name="name"/>
                <petclinic:inputField label="Stock" name="stock"/>
                <petclinic:selectField label="Unidad de Medida" name="medida" names="${medidas}" size="${fn:length(medidas)}"/>
            </div>
	
            <div class="form-group">
                <div class="col-sm-offset-2 col-sm-10">
                    <input type="hidden" name="id" value="${ingrediente.id}"/>
                    <input type="hidden" name="restaurante" value="${restaurante}"/>
                    <input type="hidden" name= "version" value="${ingrediente.version}"/>
                    <c:choose>
                    	<c:when test="${oferta['new']}">
                    		<button class="btn btn-default" type="submit">Crear ingrediente</button>
                    	</c:when>
                    	<c:otherwise>
                    		<button class="btn btn-default" type="submit">Update ingrediente</button>
                    	</c:otherwise>
                    </c:choose>
                    
                    <spring:url value="/restaurantes/${restaurante.id}/ingredientes" var="ingredienteUrl">
                    </spring:url>
                    <a class="btn btn-default" href="${fn:escapeXml(ingredienteUrl)}">Volver atrás</a>
                    
                </div>
            </div>
        </form:form>
    </jsp:body>

</petclinic:layout>
