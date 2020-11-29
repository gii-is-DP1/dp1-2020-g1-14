<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>


<petclinic:layout pageName="Restaurantes">
    <jsp:body>
          <h2>Restaurantes</h2>
		<form:form modelAttribute="restaurante" class="form-horizontal" action= "/restaurantes/save">
            <div class="form-group has-feedback">
            	<petclinic:inputField label="Nombre" name="name"/>
                <petclinic:inputField label="Tipo" name="tipo"/>
                <petclinic:inputField label="Localizacion" name="localizacion"/>
                <petclinic:inputField label="Aforo Maximo" name="aforomax"/>
                <petclinic:inputField label="Aforo Restante " name="aforores"/>                
            </div>

            <div class="form-group">
                <div class="col-sm-offset-2 col-sm-10">
                    <input type="hidden" name="id" value="${restaurante.id}"/>
                    <c:choose>
                    	<c:when test="${restaurante['new']}">
                    		<button class="btn btn-default" type="submit">Add Restaurante</button>
                    	</c:when>
                    	<c:otherwise>
                    		<button class="btn btn-default" type="submit">Update Restaurante</button>
                    	</c:otherwise>
                    </c:choose>
                </div>
            </div>
        </form:form>       
    </jsp:body>

</petclinic:layout>
