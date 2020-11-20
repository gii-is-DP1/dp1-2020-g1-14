<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>


<petclinic:layout pageName="Productos">
    <jsp:body>
        <h2>Productos</h2>
        
        <form:form modelAttribute="producto" class="form-horizontal" action="/productos/save">
            <div class="form-group has-feedback">
                <petclinic:inputField label="Name" name="name"/>
                <petclinic:inputField label="Precio" name="precio"/>
                <petclinic:inputField label="Alergenos" name="alergenos"/>
            </div>
	
            <div class="form-group">
                <div class="col-sm-offset-2 col-sm-10">
                    <input type="hidden" name="id" value="${producto.id}"/>
                    <button class="btn btn-default" type="submit">Save Producto</button>
                </div>
            </div>
        </form:form>
    </jsp:body>

</petclinic:layout>
