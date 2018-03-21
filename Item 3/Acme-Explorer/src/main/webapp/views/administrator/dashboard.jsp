
<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

	<!-- Min, max, stdev and stdev of trips per manager. -->
	<h5>
		<spring:message code="administrator.applicationsPerTrip" />
	</h5>

	<spring:message code="administrator.minimum" /> = <fmt:formatNumber value="${minApplications}" maxFractionDigits="2"
		minFractionDigits="2" />
	<spring:message code="administrator.average" /> = <fmt:formatNumber value="${avgApplications}" maxFractionDigits="2"
		minFractionDigits="2" />
	<spring:message code="administrator.maximum" /> = <fmt:formatNumber value="${maxApplications}" maxFractionDigits="2"
		minFractionDigits="2" />
	<spring:message code="administrator.stdev" /> =	<fmt:formatNumber value="${stdevApplications}" maxFractionDigits="2"
		minFractionDigits="2" />
		<br />
		
	<!-- Min, max, stdev and stdev of trips per manager. -->
	<h5>
		<spring:message code="administrator.tripsPerManager" />
	</h5>
	
	
	<spring:message code="administrator.minimum" /> = <fmt:formatNumber value="${minTrip1}" maxFractionDigits="2"
		minFractionDigits="2" />
	<spring:message code="administrator.average" /> = <fmt:formatNumber value="${avgTrip1}" maxFractionDigits="2"
		minFractionDigits="2" />
	<spring:message code="administrator.maximum" /> = <fmt:formatNumber value="${maxTrip1}" maxFractionDigits="2"
		minFractionDigits="2" />
	<spring:message code="administrator.stdev" /> =	<fmt:formatNumber value="${stdevTrip1}" maxFractionDigits="2"
		minFractionDigits="2" />
		<br />
	<br />
	
	<!-- Min, max, stdev and stdev of trip price. -->
	<h5>
		<spring:message code="administrator.tripsPriceStat" />
	</h5>
	
	
	<spring:message code="administrator.minimum" /> = <fmt:formatNumber value="${minTrip2}" maxFractionDigits="2"
		minFractionDigits="2" />
	<spring:message code="administrator.average" /> = <fmt:formatNumber value="${avgTrip2}" maxFractionDigits="2"
		minFractionDigits="2" />
	<spring:message code="administrator.maximum" /> = <fmt:formatNumber value="${maxTrip2}" maxFractionDigits="2"
		minFractionDigits="2" />
	<spring:message code="administrator.stdev" /> =	<fmt:formatNumber value="${stdevTrip2}" maxFractionDigits="2"
		minFractionDigits="2" />
		<br />
	<br />
	
	<!-- Min, max, stdev and stdev of trips per ranger. -->
	<h5>
		<spring:message code="administrator.tripsPerRanger" />
	</h5>
	
	
	<spring:message code="administrator.minimum" /> = <fmt:formatNumber value="${minTrip3}" maxFractionDigits="2"
		minFractionDigits="2" />
	<spring:message code="administrator.average" /> = <fmt:formatNumber value="${avgTrip3}" maxFractionDigits="2"
		minFractionDigits="2" />
	<spring:message code="administrator.maximum" /> = <fmt:formatNumber value="${maxTrip3}" maxFractionDigits="2"
		minFractionDigits="2" />
	<spring:message code="administrator.stdev" /> =	<fmt:formatNumber value="${stdevTrip3}" maxFractionDigits="2"
		minFractionDigits="2" />
		<br />
	<br />
	
	<h5>
		<spring:message code="administrator.applicationsStatusSummary" />
	</h5>
	<spring:message code="administrator.ratioAccepted" /> = <fmt:formatNumber value="${ratioApplicationsAccepted}" maxFractionDigits="2"
		minFractionDigits="2" />
		<br/>
	<spring:message code="administrator.ratioCancelled" /> = <fmt:formatNumber value="${ratioApplicationsCancelled}" maxFractionDigits="2"
		minFractionDigits="2" />
		<br/>
	<spring:message code="administrator.ratioDue" /> = <fmt:formatNumber value="${ratioApplicationsDue}" maxFractionDigits="2"
		minFractionDigits="2" />
		<br/>
	<spring:message code="administrator.ratioPending" /> =	<fmt:formatNumber value="${ratioApplicationsPending}" maxFractionDigits="2"
		minFractionDigits="2" />
		<br />
	<br />
	<h5>
		<spring:message code="administrator.tripsCancelledRatio" />
	</h5>
	<spring:message code="administrator.tripsCancelledRatio" /> = <fmt:formatNumber value="${ratioTripsCancelledVsOrganised}" maxFractionDigits="2"
		minFractionDigits="2" />
		<br/>
	<br/>
	<h5>
		<spring:message code="administrator.trips10PercentMore" />
	</h5>
	<display:table class="displaytag" keepStatus="true"
	name="tripsMoreApplications" requestURI="${requestURI}" id="row">
	
		<spring:message code="trip.title" var="titleHeader" />
		<display:column property="title" title="${titleHeader}" />
		
		<spring:message code="trip.ticker" var="tickerHeader" />
		<display:column property="ticker" title="${tickerHeader}" />
		
		<spring:message code="master.page.price.format" var="priceFormat" />
	
		<spring:message code="trip.price" var="priceHeader" />
		<spring:message code="master.page.price.format" var="priceFormat" />
		<display:column property="price" title="${priceHeader}" format="${priceFormat}"/>
		
		<spring:message code="trip.startDate" var="startDateHeader" />
		<spring:message code="master.page.date.format" var="dateFormat" />
		<display:column property="startDate" title="${startDateHeader}"
			format="{0,date,${dateFormat}}"/>
			
		<spring:message code="trip.endDate" var="endDateHeader" />
		<spring:message code="master.page.date.format" var="dateFormat" />
		<display:column property="endDate" title="${endDateHeader}"
			 format="{0,date,${dateFormat}}"/>
			 
		<display:column>
			<a href="trip/display.do?tripId=<jstl:out value="${row.getId()}"/>"><spring:message code="trip.display" /></a><br/>
		</display:column>
	</display:table>
	<br/><br/>
	<h5>
		<spring:message code="administrator.legalTextCount" />
	</h5>
	<table class="displaytag">
		<thead>
			<tr>
			<spring:message code="administrator.legalTextTitle" var="titulo_ltext" />
			<spring:message code="administrator.timesLegalText" var="cuenta_ltext" />
			   <th>${titulo_ltext}</th>
			   <th>${cuenta_ltext}</th>      
			</tr>
		</thead>
		<tbody>
			<c:forEach items="#{countLegalText}" var="item1">   
			          
			   <tr>
			   <td><jstl:out value="${item1.key.title}"/></td>
			   <td><jstl:out value="${item1.value}"/></td>      
			   </tr>
			</c:forEach>
		</tbody>
	</table>
	
	<!-- Min, max, stdev and stdev of trips per manager. -->
	<h5>
		<spring:message code="administrator.notesPerTripStats" />
	</h5>
	<spring:message code="administrator.minimum" /> = <fmt:formatNumber value="${minNote}" maxFractionDigits="2"
		minFractionDigits="2" />
	<spring:message code="administrator.average" /> = <fmt:formatNumber value="${avgNote}" maxFractionDigits="2"
		minFractionDigits="2" />
	<spring:message code="administrator.maximum" /> = <fmt:formatNumber value="${maxNote}" maxFractionDigits="2"
		minFractionDigits="2" />
	<spring:message code="administrator.stdev" /> =	<fmt:formatNumber value="${stdevNote}" maxFractionDigits="2"
		minFractionDigits="2" />
		<br />
	<br />
	<h5>
		<spring:message code="administrator.auditsPerTrip" />
	</h5>
	<spring:message code="administrator.minimum" /> = <fmt:formatNumber value="${minAudit}" maxFractionDigits="2"
		minFractionDigits="2" />
	<spring:message code="administrator.average" /> = <fmt:formatNumber value="${avgAudit}" maxFractionDigits="2"
		minFractionDigits="2" />
	<spring:message code="administrator.maximum" /> = <fmt:formatNumber value="${maxAudit}" maxFractionDigits="2"
		minFractionDigits="2" />
	<spring:message code="administrator.stdev" /> =	<fmt:formatNumber value="${stdevAudit}" maxFractionDigits="2"
		minFractionDigits="2" />
		<br />
	<br />
	<h5>
		<spring:message code="administrator.tripsWithAudit" />
	</h5>
	<spring:message code="administrator.tripsWithAudit" /> = <fmt:formatNumber value="${ratioTripsAudit}" maxFractionDigits="2"
		minFractionDigits="2" />
		<br/>
	<br/>
	
	<h5>
		<spring:message code="administrator.rangersCurricula" />
	</h5>
	<spring:message code="administrator.rangersWithRegCurricula" /> = <fmt:formatNumber value="${ratioManagerCurriculaReg}" maxFractionDigits="2"
		minFractionDigits="2" />
		<br/>
	<spring:message code="administrator.rangersWithEndCurricula" /> = <fmt:formatNumber value="${ratioManagerCurriculaEnd}" maxFractionDigits="2"
		minFractionDigits="2" />
		<br />
	<br />
	<h5>
		<spring:message code="administrator.suspiciousRatios" />
	</h5>
	<spring:message code="administrator.suspiciousManagerRatio" /> = <fmt:formatNumber value="${ratioSuspiciousManager}" maxFractionDigits="2"
		minFractionDigits="2" />
		<br/>
	<spring:message code="administrator.suspiciousRangerRatio" /> = <fmt:formatNumber value="${ratioSuspiciousRanger}" maxFractionDigits="2"
		minFractionDigits="2" />
		<br />
	<br />


