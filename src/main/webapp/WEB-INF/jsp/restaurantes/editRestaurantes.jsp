<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>


<petclinic:layout pageName="Restaurantes">
    <jsp:body>
          <h2>Restaurantes</h2>
		<form:form modelAttribute="restaurante" class="form-horizontal" action= "/restaurantes/save" onsubmit="return validar()">
            <div class="form-group has-feedback">
            	<petclinic:inputField label="Nombre" name="name"/>
                <petclinic:inputField label="Tipo" name="tipo"/>
                <petclinic:inputField label="Localizacion" name="localizacion"/>
                <petclinic:inputField label="Aforo Maximo" name="aforomax"/>
                <petclinic:inputField label="Aforo Restante " name="aforores"/>
                <script type="text/javascript">
                	function validar(){
                		var aforores = document.getElementById("aforores").value;
                		var aforomax = document.getElementById("aforomax").value;
                		var esMenor = true;
                		var esCorrecto = true;
                		if(aforores > aforomax){
                			esMenor = false;
                		}if(isNaN(aforores) || isNaN(aforomax)){
                			esCorrecto = false
                		}
                		if(!esMenor) alert("El aforo restante no puede ser mayor al m�ximo");
                		if(!esCorrecto) alert("El aforo debe ser un n�mero");
                		return esMenor || esCorrecto;
                	}
                </script>
                
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
