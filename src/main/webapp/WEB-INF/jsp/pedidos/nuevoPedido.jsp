<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
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
                    <input type="hidden" name="id" value="${restaurante.id}"/>
                    <button class="btn btn-default" type="submit">Encargar pedido</button>
                </div>
        </form:form>
    </jsp:body>
 
</petclinic:layout>
