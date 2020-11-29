<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<petclinic:layout pageName="gerentes">
    <jsp:body>
        <h2>Gerentes</h2>
        
        <form:form modelAttribute="gerente" class="form-horizontal" action="/gerentes/save">
            <div class="form-group has-feedback">
                <petclinic:inputField label="Nombre" name="name"/>
                <petclinic:inputField label="DNI" name="dni"/>
                <petclinic:inputField label="Contraseña" name="password"/>
                <c:choose>
                    	<c:when test="${gerente['new']}">
                    		<petclinic:selectField label="Restaurante" name="restaurante" names="${restaurantes}" size="${fn:length(restaurantes)}"/>
                    	</c:when>
                    </c:choose>
            </div>
	
            <div class="form-group">
                <div class="col-sm-offset-2 col-sm-10">
                    <input type="hidden" name="id" value="${gerente.id}"/>
                    <button class="btn btn-default" type="submit">Guardar</button>
                </div>
            </div>
        </form:form>
    </jsp:body>

</petclinic:layout>
