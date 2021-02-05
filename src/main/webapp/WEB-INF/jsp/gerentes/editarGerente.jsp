<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<petclinic:layout pageName="gerentes">
    <jsp:body>
        <h2>Gerentes</h2>
        <c:choose>
        	<c:when test="${gerente['new']}">
        		<c:set var="action" value= "/gerentes/save"/>
        	</c:when>
        	<c:otherwise>
        		<c:set var="action" value= "/gerentes/save/${gerente.id}"/>
        	</c:otherwise>
        </c:choose>
        
        <form:form modelAttribute="gerente" class="form-horizontal" action="${action}">
            <div class="form-group has-feedback">
                <petclinic:inputField label="Username" name="user.username"/>
            	<petclinic:inputField label="Password" name="user.password"/>
                <petclinic:inputField label="Nombre" name="name"/>
                <petclinic:inputField label="DNI" name="dni"/>
            </div>
	
            <div class="form-group">
                <div class="col-sm-offset-2 col-sm-10">
                    <input type="hidden" name="id" value="${gerente.id}"/>
                    <input type="hidden" name="version" value="${gerente.version}"/>
                    <c:choose>
                    	<c:when test="${gerente['new']}">
                    		<button class="btn btn-default" type="submit">Añadir gerente</button>
                    	</c:when>
                    	<c:otherwise>
                    		<button class="btn btn-default"type="submit">Update gerente</button>
                    	</c:otherwise>
                    </c:choose>
                    <spring:url value="/gerentes" var="gerenteUrl">
                    </spring:url>
                    <a class="btn btn-default" href="${fn:escapeXml(gerenteUrl)}">Volver atrás</a>
                </div>
            </div>
        </form:form>
    </jsp:body>


</petclinic:layout>