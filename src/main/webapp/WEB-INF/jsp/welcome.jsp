<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>  

<petclinic:layout pageName="home">
    <h2><fmt:message key="welcome"/></h2>
    
    <div class="row">
    
    <h2> Project ${Title}</h2>
    <h2> Group ${Group}</h2>
    <ul>
    <c:forEach items="${Persons}" var="Person">
    	<li>${Person.firstName} ${Person.lastName}</li>
    </c:forEach>
    </ul>
    
    <sec:authorize access="hasAuthority('admin') || hasAuthority('cliente')">
    <spring:url value="/restaurantes" var="restaurantesUrl"/>
    <a href="${fn:escapeXml(restaurantesUrl)}" class="btn btn-default">Ver Restaurantes</a>
    </sec:authorize>
    
    <sec:authorize access="hasAuthority('gerente')">
	<spring:url value="/restaurantes/${restaurante.id}" var="restauranteUrl"/>
    <a href="${fn:escapeXml(restauranteUrl)}" class="btn btn-default">Ir al restaurante</a>
    </sec:authorize>
    
    </div>
    <div class="row">
        <div class="col-md-12">
        	<spring:url value="/resources/images/logoPNG_3.png" htmlEscape="true" var="logo"/>
        	<img class="img-responsive" src="${logo}"/>
			<spring:url value="/resources/images/pets.png" htmlEscape="true" var="petsImage"/>
            <img class="img-responsive" src="${petsImage}"/>
        </div>
    </div>
</petclinic:layout>
