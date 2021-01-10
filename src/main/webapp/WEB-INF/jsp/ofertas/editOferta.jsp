<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>


<petclinic:layout pageName="Ofertas">
    <jsp:body>
        <h2>Ofertas</h2>
        
        <form:form modelAttribute="oferta" class="form-horizontal" action="/ofertas/save">
            <div class="form-group has-feedback">
          
                <petclinic:inputField label="Descripcion" name="descripcion"/>
            </div>
	
            <div class="form-group">
                <div class="col-sm-offset-2 col-sm-10">
                    <input type="hidden" name="id" value="${oferta.id}"/>
                    <c:choose>
                    	<c:when test="${oferta['new']}">
                    		<button class="btn btn-default" type="submit">Add oferta</button>
                    	</c:when>
                    	<c:otherwise>
                    		<button class="btn btn-default" type="submit">Update oferta</button>
                    	</c:otherwise>
                    </c:choose>
                </div>
            </div>
        </form:form>
    </jsp:body>

</petclinic:layout>
