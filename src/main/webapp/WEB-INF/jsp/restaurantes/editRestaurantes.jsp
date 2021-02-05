<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<petclinic:layout pageName="Restaurantes">
    <jsp:body>
          <h2>Restaurantes</h2>
          
          <c:choose>
          	<c:when test="${restaurante['new']}">
          		<c:set var="action" value="/restaurantes/save" />
          	</c:when>
          	<c:otherwise>
          		<c:set var ="action" value= "/restaurantes/save/${restaurante.id}"/>
          	</c:otherwise>
          </c:choose>
          
		<form:form modelAttribute="restaurante" class="form-horizontal" action= "${action}">
            <div class="form-group has-feedback">
            	<petclinic:inputField label="Nombre" name="name"/>
                <petclinic:inputField label="Tipo" name="tipo"/>
                <petclinic:inputField label="Localizacion" name="localizacion"/>
                <petclinic:inputField label="Aforo Maximo" name="aforomax"/>
                <petclinic:inputField label="Señal" name="senial"/>            
            </div>

            <div class="form-group">
                <div class="col-sm-offset-2 col-sm-10">
                    <input type="hidden" name="id" value="${restaurante.id}"/>
                    <input type="hidden" name="version" value="${restaurante.version}"/>
                    <input type="hidden" name="gerente" value="${restaurante.gerente.id}"/>
                    <c:choose>
                    	<c:when test="${restaurante['new']}">
                    		<button class="btn btn-default" type="submit">Add Restaurante</button>
                    	</c:when>
                    	<c:otherwise>
                    		<button class="btn btn-default" type="submit">Update Restaurante</button>
                    	</c:otherwise>
                    </c:choose>
                    
                    <spring:url value="/restaurantes" var="restauranteUrl">
                    </spring:url>
                    <a class="btn btn-default" href="${fn:escapeXml(restauranteUrl)}">Volver atrás</a>
                </div>
            </div>
        </form:form>       
    </jsp:body>

</petclinic:layout>
