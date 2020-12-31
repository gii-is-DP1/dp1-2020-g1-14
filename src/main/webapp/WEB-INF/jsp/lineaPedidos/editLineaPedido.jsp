<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>


<petclinic:layout pageName="lineaPedido">
    <jsp:body>
        <h2>LineaPedidos</h2>
        
        <form:form modelAttribute="lineaPedido" class="form-horizontal" action="/lineaPedidos/save">
            <div class="form-group has-feedback">
                <petclinic:selectField name="producto" label="Producto " names="${productos}" size="5"/>     
             	<petclinic:inputField label="cantidad" name="cantidad"/>
            </div>
	

            <div class="form-group">
                <div class="col-sm-offset-2 col-sm-10">
                    <input type="hidden" name="id" value="${lineaPedido.id}"/>
                    <input type="hidden" name="id" value="${pedidos.id}"/>
                    <button class="btn btn-default" type="submit">Save lineaPedido</button>
                </div>
            </div>
            
            
        </form:form>
    </jsp:body>

</petclinic:layout>
