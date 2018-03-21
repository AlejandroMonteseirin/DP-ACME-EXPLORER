<link rel="stylesheet" href="styles/trip.css" type="text/css">

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

<script type="text/javascript">

$(document).ready(function() {
	 $("#formID").submit(function(){
	var partsPD = document.getElementById("publicationDateID").value.split('/');
	var publicationDate = new Date(partsPD[2],partsPD[1]-1,partsPD[0]); 
	var partsSD = document.getElementById("startDateID").value.split('/');
	var startDate = new Date(partsSD[2],partsSD[1]-1,partsSD[0]); 
	var partsED = document.getElementById("endDateID").value.split('/');
	var endDate = new Date(partsED[2],partsED[1]-1,partsED[0]); 
	
	if(publicationDate > startDate){
		alert("Publication date must be before the start date");
		return false;
	}else if(publicationDate > endDate){
		alert("Publication date must be before the end date");
		return false;
	}else if(startDate > endDate){
		alert("Start date must be before the end date");
		return false;
	}
	
});
});

</script>


<%-- SACAMOS LA FECHA ACTUAL --%>
<jsp:useBean id="date" class="java.util.Date" />

<form:form id="formID" action="trip/manager/edit.do" modelAttribute="trip">

	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="ticker" />
	<form:hidden path="stages" />
	<form:hidden path="applications" />
	<form:hidden path="audits" />
	<form:hidden path="notes" />
	<form:hidden path="sponsorships" />

	<jstl:choose>

		<jstl:when test="${trip.getPublicationDate() lt date and trip.id!=0}">

			<form:hidden path="title" />
			<form:hidden path="description" />
			<form:hidden path="explorerRequirements" />
			<form:hidden path="startDate" />
			<form:hidden path="publicationDate" />
			<form:hidden path="endDate" />
			<jstl:if test="${empty legalText}">
				<form:hidden path="legalText" value="0"/>
			</jstl:if>
			<jstl:if test="${not empty legalText}">
				<form:hidden path="legalText"/>
			</jstl:if>
			<form:hidden path="ranger" />
			<form:hidden path="category" />

			<form:label path="status">
				<spring:message code="application.status" />:
	</form:label>

			<spring:message code="trip.active" var="active" />
			<spring:message code="trip.cancelled" var="cancelled" />

			<form:select id="statusTrip" path="status">
				<form:option value="CANCELLED" label="${cancelled}" selected="true"/>
				<%-- <form:option value="ACTIVE" label="${active}" /> --%>
			</form:select>
			<form:errors cssClass="error" path="status" />
			<br />
			<br />

			<div id="cancelReasonTripDiv">
				<form:label path="cancelReason">
					<spring:message code="application.cancelReason" />:
	</form:label>
				<br />
				<form:textarea id="cancelReasonInputTrip" path="cancelReason" />
				<form:errors cssClass="error" path="cancelReason" />
			</div>
			<br />

		</jstl:when>
		<jstl:otherwise>

			<div id="tripForm">

				<form:hidden path="status" />

				<!-- Guardamos en una variable el formato de la fecha según el idioma -->
				<spring:message code="master.page.date.format" var="dateFormat" />

				<form:label path="title">
					<spring:message code="trip.title" />:
	</form:label>
				<form:input path="title" />
				<form:errors cssClass="error" path="title" />
				<br /> <br />

				<form:label path="description">
					<spring:message code="trip.description" />:
	</form:label>
				<form:textarea path="description" />
				<form:errors cssClass="error" path="description" />
				<br /> <br />


				<form:label path="explorerRequirements">
					<spring:message code="trip.explorerRequirements" />:
	</form:label>
				<form:textarea path="explorerRequirements" />
				<form:errors cssClass="error" path="explorerRequirements" />
				<br /> <br />


				<form:label path="publicationDate">
					<spring:message code="trip.publicationDate" />:
	</form:label>
				<form:input id="publicationDateID" path="publicationDate" class="tripDate" placeholder="${dateFormat}" />
				<form:errors cssClass="error" path="publicationDate" />
				<br /> <br />


				<form:label path="startDate">
					<spring:message code="trip.startDate" />:
	</form:label>
				<form:input id="startDateID" path="startDate" class="tripDate" placeholder="${dateFormat}" />
				<form:errors cssClass="error" path="startDate" />
				<br /> <br />


				<form:label path="endDate">
					<spring:message code="trip.endDate" />:
	</form:label>
				<form:input id="endDateID" path="endDate" class="tripDate" placeholder="${dateFormat}" />
				<form:errors cssClass="error" path="endDate" />
				<br /> <br />


				<%-- <!-- Inicialmente un trip no está cancelado -->
		<jstl:if test="${trip.id!=0}">

			<jstl:choose>
				<jstl:when test="${isCancelled == true}">
					<spring:message code="trip.cancelled" var="status" />
				</jstl:when>
				<jstl:otherwise>
					<spring:message code="trip.active" var="status" />
				</jstl:otherwise>
			</jstl:choose>
			
			<spring:message code="trip.cancelled" var="cancelled"/>
			<spring:message code="trip.active" var="active"/>
			
			<form:label path="isCancelled">
				<spring:message code="trip.isCancelled" />:
			</form:label>

			<form:select id="tripStatus" path="isCancelled" >
			<form:option value="TRUE" label="${cancelled}" />"/>
			<form:option value="FALSE" label="${active}"/>
			</form:select>
			<form:errors cssClass="error" path="isCancelled" />
			<br/>
			<br/>
	
		<jstl:if test="${trip.isCancelled eq true}">
		<div id="cancelReasonTrip">
			<form:label path="cancelReason">
				<spring:message code="trip.cancelReason" />:
		</form:label>
			<form:textarea id="cancelReasonInput" path="cancelReason" />
			<form:errors cssClass="error" path="cancelReason" />
			<br />
			<br />
			</div>
		</jstl:if>
		</jstl:if> --%>

				<!-- RELACIONES -->



				<form:label path="ranger">
					<spring:message code="master.page.ranger" />:
	</form:label>
				<form:select id="ranger" path="ranger">
					<form:option value="0" label="----" />
					<form:options items="${rangers}" itemValue="id"
						itemLabel="userAccount.username" />
				</form:select>
				<form:errors cssClass="error" path="ranger" />
				<br /> <br />


				<form:label path="category">
					<spring:message code="master.page.category" />:
	</form:label>
				<form:select path="category">
					<form:option value="0" label="----" />
					<%-- <form:options items="${categories}" itemValue="id" itemLabel="name" /> --%>
					<jstl:forEach items="${categories}" var="cat">
						<form:option value="${cat.id}"
							label="${cat.name} [Parent: ${cat.parentCategory.name}]" />
					</jstl:forEach>
				</form:select>
				<form:errors cssClass="error" path="category" />
				<br /> <br />

				<form:label path="legalText">
					<spring:message code="master.page.legalText" />:
		</form:label>
				<form:select id="legalText" path="legalText">
					<form:option value="0" label="----" />
					<jstl:forEach items="${legalTexts}" var="cat">
						<form:option value="${cat.id}" label="${cat.title}" />
					</jstl:forEach>
				</form:select>
				<form:errors cssClass="error" path="legalText" />
				<br /> <br />

				<jstl:if test="${trip.id != 0}">
					<input type="button"
						value="<spring:message code="trip.addStage" />"
						onclick="javascript: relativeRedir('stage/manager/create.do?tripId=${trip.id}');" />
					<input type="button" value="<spring:message code="trip.addTag" />"
						onclick="javascript: relativeRedir('tag/list.do?tripId=<jstl:out value="${trip.getId()}"/>');" />
				</jstl:if>
				<%-- SOLO LOS MANAGERS PUEDEN EDITAR Y ELIMINAR LOS VIAJES  Y SI LA PUBLICATION DATE NO HA PASADO 	--%>

				<%-- CONTROLAMOS QUE LA PUBLICATION DATE NO HAYA PASADO PARA ELIMINAR Y CANCELAR --%>
				<jstl:if test="${trip.getPublicationDate() gt date}">


					<%-- SOLO LOS MANAGERS PUEDEN ELIMINAR Y CANCELAR LOS VIAJES --%>
					<security:authorize access="hasRole('MANAGER')">
						<jstl:if test="${trip.id != 0}">
							<input type="submit" name="delete"
								value="<spring:message code="trip.delete" />"
								onclick="return confirm('<spring:message code="trip.confirm.delete" />')" />

							<%-- SOLO SE MUESTRA EL BOTON DE CANCELAR SI EL VIAJE NO ESTA YA CANCELADO --%>
							<%-- <jstl:if test="${trip.isCancelled eq false}">
						<input type="button" name="cancel"
							value="<spring:message code="trip.cancel" />"
							onclick="javascript: relativeRedir('trip/manager/cancel.do?tripId=${trip.getId()}')" />&nbsp; 
					</jstl:if> --%>
						</jstl:if>
					</security:authorize>
				</jstl:if>

			</div>
			<br />
		</jstl:otherwise>

	</jstl:choose>

	<br />

	<input type="submit" name="save"
		value="<spring:message code="trip.save" />" />&nbsp; 
	<jstl:if test="${trip.id != 0 and status eq 'ACTIVE'}">
		<input type="submit" name="delete"
			value="<spring:message code="category.delete" />"
			onclick="return confirm('<spring:message code="category.confirm.delete" />')" />
	</jstl:if>
	<input type="button" name="cancel"
		value="<spring:message code="application.cancel" />"
		onclick="javascript: relativeRedir('trip/manager/list.do');" />
	<br />

</form:form>