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

	<!-- Guardamos en una variable el formato de la fecha y el precio según el idioma -->
	
	<spring:message code="master.page.locale" var="locale" />
	<spring:message code="master.page.date.format" var="dateFormat" />
	<spring:message code="master.page.price.format.display" var="priceFormat" />
	<spring:message code="master.page.price.currencyCode" var="currencyCode" />
	<fmt:setLocale value="${locale}"/>
	
	
<div id="tripDiv">
	<h2>
		<jstl:out value="${trip.getTitle()}" />
	</h2>
	<br />
	
	<h5><spring:message code="application.status"/>:
		<jstl:choose>
		<jstl:when test="${trip.status eq 'ACTIVE'}">
		<spring:message code="trip.active"/>
		</jstl:when>
		
		<jstl:otherwise>
		<spring:message code="trip.cancelled"/><br/>
		<spring:message code="application.cancelReason"/>: <jstl:out value="${trip.cancelReason}"/>
		</jstl:otherwise>
		</jstl:choose></h5>

	<ul style="list-style-type: disc">

		<div id="descriptionDiv">
			<jstl:out value="${trip.getDescription()}" />
		</div>
		
		<%-- <li><b><spring:message code="trip.ticker"></spring:message>:</b>
			<jstl:out value="${trip.getTicker()}" /></li> --%>

		<%-- <li><b><spring:message code="trip.description"></spring:message>:</b> --%>
		<%-- <li><jstl:out value="${trip.getDescription()}" /></li> --%>
	<br/>
		<li><b><spring:message code="trip.price"></spring:message>:</b>
		<fmt:setLocale value="es_ES"/> 
		<fmt:formatNumber value="${trip.getPrice()}" pattern="${priceFormat}" currencySymbol="${currencyCode}"/>
		<jstl:out value="${currency}"/>
		</li>

		<li><b><spring:message code="trip.explorerRequirements"></spring:message>:</b>
			<jstl:out value="${trip.getExplorerRequirements()}" /></li>

		<li><b><spring:message code="trip.publicationDate"></spring:message>:</b>
				<fmt:formatDate value="${trip.getPublicationDate()}" pattern="${dateFormat}" /></li>

		<li><b><spring:message code="trip.startDate"></spring:message>:</b>
				<fmt:formatDate value="${trip.getStartDate()}" pattern="${dateFormat}" /></li>

		<li><b><spring:message code="trip.endDate"></spring:message>:</b>
				<fmt:formatDate value="${trip.getEndDate()}" pattern="${dateFormat}" /></li>

		<li><b><spring:message code="application.status"></spring:message>:</b>
			<jstl:out value="${trip.getStatus()}" /></li>

		<jstl:if test="${not empty trip.getCancelReason()}">
			<li><b><spring:message code="trip.cancelReason"></spring:message></b>
				<jstl:out value="${trip.getCancelReason()}" /></li>
		</jstl:if>
		
		
		<h5><spring:message code="trip.stages"/></h5>
		<jstl:choose>
		<jstl:when test="${not empty trip.stages}">
		<jstl:forEach items="${trip.stages}" var="stage">
		
		<div id="descriptionStage">
		<h5><jstl:out value="${stage.title}"/><br/></h5><br/>
		<jstl:out value="${stage.description}"/>
		</div><br/><br/>
		
		<security:authorize access="hasRole('MANAGER')">
		<jstl:if test="${ownTrip eq true and isPublished eq false and trip.status eq 'ACTIVE'}">
		<input type="button" name="edit"
			value="<spring:message code="trip.edit" />"
			onclick="javascript: relativeRedir('stage/manager/edit.do?stageId=${stage.getId()}')" />
		</jstl:if>
		</security:authorize>
		</jstl:forEach>
		</jstl:when>
		
		<jstl:otherwise>
		<spring:message code="trip.noStages"/>
		</jstl:otherwise>
		
		</jstl:choose>
		
		<h5><spring:message code="trip.legalText"/></h5>
		
		<jstl:choose>
		<jstl:when test="${not empty trip.legalText}">
		<div id="legalTextDiv">
		<h5><jstl:out value="${trip.legalText.title}"/></h5><br/>
		<jstl:out value="${trip.legalText.body}"/><br/>
		</div>
		<br/>
		</jstl:when>
		
		<jstl:otherwise>
		<spring:message code="trip.noLegalText"/>
		</jstl:otherwise>
		</jstl:choose><br/><br/>
		
		<div id="categoryDiv">
		<spring:message code="master.page.category"/>:<br/>
		<jstl:out value="${trip.category.name}"/>
		</div>
		
		<div id="tagsDiv">
		<spring:message code="trip.tags"/>:
		
		<jstl:choose>
		<jstl:when test="${not empty trip.values}">
		<jstl:forEach items="${trip.values}" var="value">
		<jstl:out value="${value.content}"/> |
		</jstl:forEach>
		</jstl:when>
		
		<jstl:otherwise>
		<spring:message code="trip.noTags"/>
		</jstl:otherwise>
		</jstl:choose>
		</div>
		
	</ul>

</div>

<jstl:if test="${not empty sponsorship }">
<img src="${sponsorship.bannerURL}"/>
</jstl:if>

	<%-- SOLO LOS MANAGERS PUEDEN EDITAR SUS PROPIOS VIAJES --%>
	<security:authorize access="hasRole('MANAGER')">
	<jstl:if test="${ownTrip eq true and trip.status eq 'ACTIVE'}">
		<input type="button" name="edit"
			value="<spring:message code="trip.edit" />"
			onclick="javascript: relativeRedir('trip/manager/edit.do?tripId=${trip.getId()}')" />
	</jstl:if>
	</security:authorize>


<input type="button" name="back"
	value="<spring:message code="trip.back" />"
	onclick="javascript: relativeRedir('trip/list.do')" />