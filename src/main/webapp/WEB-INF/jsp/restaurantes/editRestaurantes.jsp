<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>


<petclinic:layout pageName="Restaurantes">
    <jsp:body>
          <h2>Restaurantes</h2>
		<form:form modelAttribute="restaurantes" class="form-horizontal" action= "/restaurantes/save" onsubmit="return validar()">
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
                		if(!esMenor) alert("El aforo restante no puede ser mayor al máximo");
                		if(!esCorrecto) alert("El aforo debe ser un número");
                		return esMenor || esCorrecto;
                	}
                </script>
                
            </div>

            <div class="form-group">
                <div class="col-sm-offset-2 col-sm-10">
                    <input type="hidden" name="id" value="${restaurantes.id}"/>
                    <c:choose>
                    	<c:when test="${owner['new']}">
                    		<button class="btn btn-default" type="submit">Add Owner</button>
                    	</c:when>
                    	<c:otherwise>
                    		<button class="btn btn-default" type="submit">Update Owner</button>
                    	</c:otherwise>
                    </c:choose>
                </div>
            </div>
        </form:form>
       <!-- esto esta por aquí para apollo
        <h2>
            <c:if test="${pet['new']}">New </c:if> Pet
        </h2>
        <form:form modelAttribute="pet"
                   class="form-horizontal">
            <input type="hidden" name="id" value="${pet.id}"/>
            <div class="form-group has-feedback">
                <div class="form-group">
                    <label class="col-sm-2 control-label">Owner</label>
                    <div class="col-sm-10">
                        <c:out value="${pet.owner.firstName} ${pet.owner.lastName}"/>
                    </div>
                </div>
                <petclinic:inputField label="Name" name="name"/>
                <petclinic:inputField label="Birth Date" name="birthDate"/>
                <div class="control-group">
                    <petclinic:selectField name="type" label="Type " names="${types}" size="5"/>
                </div>
            </div>
            <div class="form-group">
                <div class="col-sm-offset-2 col-sm-10">
                    <c:choose>
                        <c:when test="${pet['new']}">
                            <button class="btn btn-default" type="submit">Add Pet</button>
                        </c:when>
                        <c:otherwise>
                            <button class="btn btn-default" type="submit">Update Pet</button>
                        </c:otherwise>
                    </c:choose>
                </div>
            </div>
        </form:form>
        <c:if test="${!pet['new']}">
        </c:if>-->

       
    </jsp:body>

</petclinic:layout>
