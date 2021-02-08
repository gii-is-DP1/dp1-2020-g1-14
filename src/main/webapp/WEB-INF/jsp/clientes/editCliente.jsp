<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>


<petclinic:layout pageName="Clientes">
    <jsp:body>
        <h2>Clientes</h2>
        
        <c:choose>
        	<c:when test="${cliente['new']}">
            	<c:set var="action" value="/clientes/save"/>
            </c:when>
            <c:otherwise>
                <c:set var="action" value="/clientes/save/${oferta.id}"/>
            </c:otherwise>
       </c:choose>
        <form:form modelAttribute="cliente" class="form-horizontal" action="/clientes/save">

            <div class="form-group has-feedback">
            	<petclinic:inputField label="Username" name="user.username"/>
            	<petclinic:inputField label="Password" name="user.password"/>
          		<petclinic:inputField label="tlf"  name="tlf"/>
               	
              </div>
              
              
            <div class="form-group">
                <div class="col-sm-offset-2 col-sm-10">
                    <input type="hidden" name="id" value="${cliente.id}"/>
                    <button class="btn btn-default" type="submit">Añadir cliente</button>
                    
                    <spring:url value="/clientes" var="clienteUrl">
                    </spring:url>
                    <a class="btn btn-default" href="${fn:escapeXml(clienteUrl)}">Volver atrás</a>
                </div>
                
                
             </div>
        </form:form>
    </jsp:body>
 
</petclinic:layout>
