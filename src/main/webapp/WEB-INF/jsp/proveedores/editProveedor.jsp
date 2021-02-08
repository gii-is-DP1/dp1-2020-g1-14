<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<petclinic:layout pageName="Proveedores">
    <jsp:body>
        <h2>Proveedores</h2>
       <c:choose>
        	<c:when test="${proveedor['new']}">
            	<c:set var="action" value="/restaurantes/${restauranteId}/proveedores/save"/>
            </c:when>
            <c:otherwise>
                <c:set var="action" value="/restaurantes/${restauranteId}/proveedores/save/${proveedor.id}"/>
            </c:otherwise>
       </c:choose>        
        
        
        <form:form modelAttribute="proveedor" class="form-horizontal" action="${action}">
            <div class="form-group has-feedback">
                <petclinic:inputField label="Name" name="name"/>
                <petclinic:inputField label="Telephone" name="tlf"/>
            </div>
	
            <div class="form-group">
                <div class="col-sm-offset-2 col-sm-10">
                    <input type="hidden" name="id" value="${proveedor.id}"/>
                    <input type="hidden" name="version" value="${proveedor.version}"/>
                    <c:choose>
                    	<c:when test="${proveedor['new']}">
                    		<button class="btn btn-default" type="submit">Añadir proveedor</button>
                    	</c:when>
                    	<c:otherwise>
                    		<button class="btn btn-default" type="submit">Actualizar proveedor</button>
                    	</c:otherwise>
                    </c:choose>
                    
                    <spring:url value="/proveedores" var="proveedorUrl">
                    </spring:url>
                    <a  class="btn btn-default" href="${fn:escapeXml(proveedorUrl)}">Volver atrás</a>
   
                </div>
            </div>
        </form:form>
    </jsp:body>

</petclinic:layout>
