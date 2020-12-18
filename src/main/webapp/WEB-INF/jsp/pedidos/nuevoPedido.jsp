<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>


<petclinic:layout pageName="pedidos">
    <jsp:body>
        <h2>Pedidos</h2>
        
        <form:form modelAttribute="pedido" class="form-horizontal" action="/pedidos/order">

            <div class="form-group has-feedback">
          
                <petclinic:inputField label="adress" name="adress"/>
                <petclinic:inputField label="estado" name="estado"/>
                <c:out value = "orderDate: ${pedido.orderDate} "> </c:out>
                <petclinic:inputField label="price" name="price"/>
                <select multiple name="productos" id="productos">
                	<option selected> Selecionar productos</option>
                	<c:forEach var="producto" items="${productos}">
                		<option value="${producto.name}">${producto.name}</option>
                	</c:forEach>
               </select>
                
            </div>
	
            <div class="form-group">
                <div class="col-sm-offset-2 col-sm-10">
                    <input type="hidden" name="id" value="${pedido.id}"/>
                    <button class="btn btn-default" type="submit">Encargar pedido</button>
                </div>
            </div>
        </form:form>
    </jsp:body>
 
</petclinic:layout>
