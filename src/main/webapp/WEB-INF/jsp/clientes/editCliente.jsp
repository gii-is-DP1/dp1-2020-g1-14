<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>


<petclinic:layout pageName="Clientes">
    <jsp:body>
        <h2>Clientes</h2>
        
        <form:form modelAttribute="cliente" class="form-horizontal" action="/clientes/save">

            <div class="form-group has-feedback">
            	<petclinic:inputField label="name" name="name"/>
            	<petclinic:inputField label="rDate" name="rDate"/>
            	<petclinic:inputField label="password" name="password"/>
            	<petclinic:inputField label="numPedidos" name="numPedidos"/>
            	<petclinic:inputField label="esSocio" name="esSocio"/>
          		<petclinic:inputField label="tlf"  name="tlf"/>
               	
              </div>
              
              
            <div class="form-group">
                <div class="col-sm-offset-2 col-sm-10">
                    <input type="hidden" name="id" value="${cliente.id}"/>
                    <button class="btn btn-default" type="submit">Añadir cliente</button>
                </div>
             </div>
        </form:form>
    </jsp:body>
 
</petclinic:layout>
