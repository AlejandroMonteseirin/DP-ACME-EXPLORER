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
		$('#table_grid').dynatable({
			 
			inputs : {
				queryEvent : 'keyup'
			} 
		});
	});
	
</script>



<spring:message code="master.page.price.currencyCode" var="currencyCode" />
<%-- <spring:message code="master.page.price.format" var="priceFormat" /> --%>
<spring:message code="master.page.price.format.display"
	var="priceFormat" />
<fmt:setLocale value="${locale}" />
<spring:message code="master.page.date.format" var="dateFormat" />

<table id="table_grid">
	<thead>
		<th><spring:message code="trip.title" /></th>
		<th><spring:message code="trip.startDate" /></th>
		<th><spring:message code="trip.endDate" /></th>
		<th><spring:message code="trip.price" /></th>
		<th></th>
	</thead>
	<tbody>
		<jstl:forEach items="${visibleTrips}" var="trip">
			<tr>
				<td>${trip.title}</td>
				<td><fmt:formatDate value="${trip.getStartDate()}" pattern="${dateFormat}" /></td>
				<td><fmt:formatDate value="${trip.getEndDate()}" pattern="${dateFormat}" /></td>
				<td><fmt:formatNumber value="${trip.getPrice()}"
						pattern="${priceFormat}" currencySymbol="${currencyCode}" /> <jstl:out
						value="${currency}" /></td>
				<td><a
					href="trip/display.do?tripId=<jstl:out value="${trip.getId()}"/>"><spring:message
							code="trip.display" /></a></td>
			</tr>

		</jstl:forEach>
	</tbody>
</table><br/><br/>


<br/>