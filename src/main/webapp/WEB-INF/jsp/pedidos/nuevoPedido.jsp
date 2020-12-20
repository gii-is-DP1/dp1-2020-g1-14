<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>


<petclinic:layout pageName="pedidos">
    <jsp:body>
        <h2>Pedidos</h2>
        
        <form:form modelAttribute="pedido" class="form-horizontal" action="/pedidos/order">

            <div class="form-group has-feedback">
          
                <petclinic:inputField label="adress" name="adress"/>
                
                <petclinic:inputField label="estado" name="estado"/>
                
                <c:out value = "orderDate: ${pedido.orderDate} "> </c:out>
               
                
                <select class="sproduct" multiple name="productos" id="multipleSelect">
                	<option selected> Selecionar productos</option>
                	<c:forEach var="producto" items="${productos}">
                		<option value="${producto.precio}">${producto.name}</option>
                		
                	</c:forEach>
               </select>
                
            </div>
			
			<div class="form-group">
    			<label for="price"> Price</label>
    			<input type="text" class="form-control price-input" readonly>
			</div>
            
            
            <div class="form-group">
                <div class="col-sm-offset-2 col-sm-10">
                    <input type="hidden" name="id" value="${pedido.id}"/>
                    <button class="btn btn-default" type="submit">Encargar pedido</button>
                </div>
               
              <div id="result"></div>
            </div>
        </form:form>
    </jsp:body>
 
</petclinic:layout>
<script type="text/javascript">
/*$('.has-feedback').on('change', function() {
	  var sum=0;
	  $('.price-input')
	  .val($(this).find(':selected').data('price')
	  );

	}); */
	let sum = 0;
	$("#multipleSelect").on("change", function(){
	  sum = 0;
	  ($(this).val() || []).map(v=>sum += +v)
	  $(".price-input").val(sum);
	})
</script>