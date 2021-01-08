<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<petclinic:layout pageName="lineaPedido">
    <jsp:body>
        <h2>LineaPedidos</h2>
        
        <form:form modelAttribute="lineaPedido" class="form-horizontal" action="/pedidos/${pedido.id}/lineaPedidos/save">
            <div class="form-group has-feedback">
                <petclinic:selectField name="producto" label="nombres " names="${nombres}" size="${fn:length(nombres)}"/>     
             	<petclinic:inputField label="cantidad" name="cantidad"/>
            </div>
	

            <div class="form-group">
                <div class="col-sm-offset-2 col-sm-10">
                    <input type="hidden" name="id" value="${lineaPedido.id}"/>
                    <input type="hidden" name="pedido" value="${pedido.id}"/>
                    <button class="btn btn-default" type="submit">Save lineaPedido</button>
                </div>
            </div>
            
            
        </form:form>
    </jsp:body>

</petclinic:layout>
