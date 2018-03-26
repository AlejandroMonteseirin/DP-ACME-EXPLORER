<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<style type="text/css">

.links{font-size: 30px}
.current{color: blue;}

</style>


[A+ pagination]<br/>
<spring:message code="trip.showing1" />
<jstl:out value="${pageSize}" />
<spring:message code="trip.showing2" />
.
<spring:message code="trip.currentPage" />
:
<jstl:out value="${pageNumber}" />
.
<br/>
<jstl:if test="${isManager eq true}">
<jstl:forEach var="number" begin ="1" end="${totalPages}">
<jstl:if test="${number eq pageNumber}">
<a class="current links" href="trip/manager/list.do?pageNumber=<jstl:out value="${number}"/>"><jstl:out value="${number}"/></a>
</jstl:if>
<jstl:if test="${number ne pageNumber}">
<a class="links" href="trip/manager/list.do?pageNumber=<jstl:out value="${number}"/>"><jstl:out value="${number}"/></a>
</jstl:if>
&nbsp;&nbsp;&nbsp;&nbsp;
</jstl:forEach>
</jstl:if>

<jstl:if test="${empty isManager}">
<jstl:forEach var="number" begin ="1" end="${totalPages}">
<jstl:if test="${number eq pageNumber}">
<a class="current links" href="trip/list.do?pageNumber=<jstl:out value="${number}"/>"><jstl:out value="${number}"/></a>
</jstl:if>
<jstl:if test="${number ne pageNumber}">
<a class="links" href="trip/list.do?pageNumber=<jstl:out value="${number}"/>"><jstl:out value="${number}"/></a>
</jstl:if>
&nbsp;&nbsp;&nbsp;&nbsp;
</jstl:forEach>
</jstl:if>
<%-- [A+] El método que pagina el repositorio de Trip debe recibir dos parámetros, el 
	tamaño de las páginas y la página que se desea consultar. Para ello creamos el siguiente
	formulario --%>

<%-- <div id="pagForm">
	<form action="trip/list.do" method="GET">

		<label for="pageSize"> <spring:message code="trip.pageSize" />:
			<input name="pageSize" value="<jstl:out value="${pageSize}"/>" />
		</label><br /> <label for="pageNumber"> <spring:message
				code="trip.pageNumber" />: <input name="pageNumber"
			value="<jstl:out value="${pageNumber}"/>" /> <form:errors
				cssClass="error" path="pageNumber" />
		</label> <input id="boton" type="submit" name="save"
			value="<spring:message code="note.save" />" /><br />

	</form>
</div> --%>

<!-- Listing grid -->

<jstl:if test="${fromFinder eq true}">
<jstl:set var="patata" value="100"/>
</jstl:if>

<display:table class="displaytag" keepStatus="true" name="visibleTrips" pagesize="${patata}"
	requestURI="${requestURI}" id="row">

	<!-- Action links -->
	<!-- Attributes -->

	<%-- EL DELETE HABRIA QUE PONERLO PERO EN EL DELETE DENTRO DE UN IF
	<display:column>
	<a href="trip/delete.do?tripId=$row.id"><spring:message code="trip.delete" /></a>
	</display:column> --%>

	<!-- GUARDAR SI ES MANAGER PARA EL DISPLAY -->
	<security:authorize access="hasRole('MANAGER')" var="isManager" />

	<spring:message code="trip.title" var="titleHeader" />
	<display:column property="title" title="${titleHeader}" />

	<%-- <spring:message code="trip.description" var="descriptionHeader" />
	<display:column property="description" title="${descriptionHeader}" /> --%>


	<spring:message code="trip.ticker" var="tickerHeader" />
	<display:column property="ticker" title="${tickerHeader}" />

	<%--Guardamos el formato del precio--%>
	<spring:message code="master.page.price.format" var="priceFormat" />

	<spring:message code="trip.price" var="priceHeader" />
	<spring:message code="master.page.price.format" var="priceFormat" />
	<display:column property="price" title="${priceHeader}"
		format="${priceFormat}" />

	<%-- <spring:message code="trip.explorerRequirements" var="explorerRequirementsHeader" />
	<display:column property="explorerRequirements" title="${explorerRequirementsHeader}" />
	
	<spring:message code="trip.publicationDate" var="publicationDateHeader" />
	<display:column property="publicationDate" title="${publicationDateHeader}" format="{0,date,dd/MM/yyyy HH:mm}" /> --%>

	<spring:message code="trip.startDate" var="startDateHeader" />
	<spring:message code="master.page.date.format" var="dateFormat" />
	<display:column property="startDate" title="${startDateHeader}"
		format="{0,date,${dateFormat}}" />

	<spring:message code="trip.endDate" var="endDateHeader" />
	<spring:message code="master.page.date.format" var="dateFormat" />
	<display:column property="endDate" title="${endDateHeader}"
		format="{0,date,${dateFormat}}" />

	<%-- <spring:message code="trip.isCancelled" var="isCancelledHeader" />
	<display:column property="isCancelled" title="${isCancelledHeader}" />
	
	<spring:message code="trip.cancelReason" var="cancelReasonHeader" />
	<display:column property="cancelReason" title="${cancelReasonHeader}" /> --%>

	<display:column>
		<%-- TODO EL MUNDO PUEDE VER LOS DETALLES DE UN VIAJE PERO MANAGER TIENE OTRA VISTA --%>
		<jstl:choose>
			<jstl:when test="${isManager}">
				<a
					href="trip/manager/display.do?tripId=<jstl:out value="${row.getId()}"/>"><spring:message
						code="trip.display" /></a>
				<br />
			</jstl:when>
			<jstl:otherwise>
				<a href="trip/display.do?tripId=<jstl:out value="${row.getId()}"/>"><spring:message
						code="trip.display" /></a>
				<br />
			</jstl:otherwise>
		</jstl:choose>

		<%-- LOS EXPLORERS PUEDEN APLICAR A UN VIAJE --%>
		<%-- <security:authorize access="hasRole('EXPLORER')">
	<a href="application/explorer/create.do?tripId=<jstl:out value="${row.getId()}"/>"><spring:message code="trip.apply"/></a><br/>
	</security:authorize> --%>

		<%-- <%-- LOS AUDITOR PUEDEN CREAR NOTAS ASOCIADAS A LOS VIAJES --%>
		<%-- <security:authorize access="hasRole('AUDITOR')">
	<a href="note/auditor/create.do?tripId=<jstl:out value="${row.getId()}"/>"><spring:message code="trip.createNote"/></a><br/>
	</security:authorize> --%>

		<%-- UN AUDITOR LE PUEDE ASIGNAR UN AUDIT A UN VIAJE --%>
		<%-- <security:authorize access="hasRole('AUDITOR')">
	<a href="trip/auditor/createAudit.do?tripId=<jstl:out value="${row.getId()}"/>"><spring:message code="trip.createAudit"/></a><br/>
	</security:authorize> --%>
		<%-- 
	UN SPONSOR LE PUEDE ASIGNAR UN SPONSORSHIP A UN VIAJE
	<security:authorize access="hasRole('SPONSOR')">
	<a href="trip/sponsor/createSponsorship.do?tripId=<jstl:out value="${row.getId()}"/>"><spring:message code="trip.createSponsorship"/></a><br/>
	</security:authorize>
	 --%>
		<%-- LOS MANAGERS EDITAN TRIPS --%>
		<security:authorize access="hasRole('MANAGER')">
		</security:authorize>
	</display:column>

</display:table>


<security:authorize access="hasRole('MANAGER')">
	<a href="trip/manager/create.do"><spring:message code="trip.create" /></a>
</security:authorize>



