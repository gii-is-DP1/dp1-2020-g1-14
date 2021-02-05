<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>


<petclinic:layout pageName="pedidos">
    <jsp:body>
        <h2>Pedidos</h2>
        
        <form:form modelAttribute="pedido" class="form-horizontal" action="order">

            <div class="form-group has-feedback">
            	<petclinic:inputField label="adress" name="adress"/>
     
               
            </div>
            <div class="form-group">
                <div class="col-sm-offset-2 col-sm-10">
                    <input type="hidden" name="id" value="${pedido.id}"/>
                    <input type="hidden" name="id" value="${restauranteId}"/>
                    <button class="btn btn-default" type="submit">Encargar pedido</button>
                    
                    <spring:url value="/restaurantes/{restauranteId}/pedidos/${name}/order" var="pedidoUrl">
    					<spring:param name="restauranteId" value="${restauranteId}"/>
                    </spring:url>
                    <a class="btn btn-default" href="${fn:escapeXml(pedidoUrl)}">Volver atrás</a>
                </div>
            </div>
        </form:form>
    </jsp:body>
 
</petclinic:layout>
