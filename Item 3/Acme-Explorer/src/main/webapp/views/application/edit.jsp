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


<form:form action="application/edit.do"
	modelAttribute="application">

	<%-- Parámetros ocultos --%>

	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="creationMoment" />
	<form:hidden path="explorer" />
	
	<security:authorize access="hasRole('EXPLORER')">
	<form:hidden path="status" />
	<%-- <form:hidden path="cancelReason" /> --%>
	</security:authorize>
	
	<jstl:if test="${application.id != 0}">
	<form:hidden path="trip" />
	<form:hidden path="comment" />
	</jstl:if>
	
	<%-- ************** --%>

	<security:authorize access="hasRole('EXPLORER')">
	
	<jstl:if test="${application.id eq 0}">
	<form:label path="trip">
		<spring:message code="master.page.trips" />:
	</form:label>
	<form:select id="trips" path="trip">
		<form:option value="0" label="----" />
		
		<jstl:forEach items="${trips}" var="trip" >
		<jstl:choose>
			<jstl:when test="${trip.id eq tripId}">
				<form:option value="${trip.id}" label="${trip.title}" selected="true"/>
			</jstl:when>
		
			<jstl:otherwise>
			<form:option value="${trip.id}" label="${trip.title}" />
			</jstl:otherwise>
		</jstl:choose>
		</jstl:forEach>
		
	</form:select>
	<form:errors cssClass="error" path="trip" />
	<br/><br/>
	
	<form:label path="comment">
		<spring:message code="application.comments" />:
	</form:label><br/>
	<form:textarea path="comment" />
	<br/><br/>
	</jstl:if>
	
	<jstl:if test="${application.id != 0 and application.status eq 'DUE'}">
	
	<form:hidden path="creditCard.id" />
	<form:hidden path="creditCard.version" />
	
	<form:label path="creditCard.holderName">
		<spring:message code="creditCard.holderName" />:
	</form:label>
	<form:input path="creditCard.holderName" />
	<form:errors cssClass="error" path="creditCard.holderName" />
	<br/>
	
	<form:label path="creditCard.brandName">
		<spring:message code="creditCard.brandName" />:
	</form:label>
	<form:input path="creditCard.brandName" />
	<form:errors cssClass="error" path="creditCard.brandName" />
	<br/>

	<form:label path="creditCard.number">
		<spring:message code="creditCard.number"/>:
	</form:label>
	<form:input path="creditCard.number" />
	<form:errors cssClass="error" path="creditCard.number"/>
	<br/>

	<form:label path="creditCard.expirationMonth">
		<spring:message code="creditCard.expirationMonth" />:
	</form:label>
	<form:input path="creditCard.expirationMonth" />
	<form:errors cssClass="error" path="creditCard.expirationMonth" />
	<br/>

	<form:label path="creditCard.expirationYear">
		<spring:message code="creditCard.expirationYear" />:
	</form:label>
	<form:input path="creditCard.expirationYear" />
	<form:errors cssClass="error" path="creditCard.expirationYear" />
	<br/>

	<form:label path="creditCard.CVV">
		<spring:message code="creditCard.CVV" />:
	</form:label>
	<form:input path="creditCard.CVV" />
	<form:errors cssClass="error" path="creditCard.CVV" />
	<br/>
	
	</jstl:if>
	
	</security:authorize>
	
	<security:authorize access="hasRole('MANAGER')">
	
	<spring:message code="application.rejected" var="rejected"/>
	<spring:message code="application.due" var="due"/>
	
	<form:label path="status">
		<spring:message code="application.status" />:
	</form:label>
	<form:select id="status" path="status">
		<%-- <form:option value="0" label="----" /> --%>
		<form:option  value="REJECTED" label="${rejected}" />
		<form:option value="DUE" label="${due}" />
	</form:select>
	<form:errors cssClass="error" path="status" />
	<br/><br/>
	
	<div id="cancelReasonDiv">
	<form:label path="cancelReason">
		<spring:message code="application.cancelReason" />:
	</form:label><br/>
	<form:textarea id="cancelReasonInput" path="cancelReason" />
	<form:errors cssClass="error" path="cancelReason" />
	</div><br/>
	
	</security:authorize>
	
	<security:authorize access="hasRole('EXPLORER')">
	
	<jstl:choose>
	<jstl:when test="${empty tripId}">
	<jstl:set value="application/explorer" var="cancelLink" />
	</jstl:when>
	
	<jstl:otherwise>
	<jstl:set value="trip" var="cancelLink" />
	</jstl:otherwise>
	</jstl:choose>
	
	<input type="submit" name="save"
		value="<spring:message code="application.submit" />" />
	<input type="button" name="cancel"
		value="<spring:message code="application.cancel"/>" onclick="javascript:relativeRedir('${cancelLink}/list.do');"/>
	<%-- <jstl:choose>
	<jstl:when test="${application.id eq 0}">
	<input type="submit" name="save"
		value="<spring:message code="application.submit" />" />
	</jstl:when>
	<jstl:otherwise>
	<input type="submit" name="addCreditCard"
		value="<spring:message code="application.submit" />" />
	</jstl:otherwise>
	</jstl:choose> --%>
	</security:authorize>
	
	<security:authorize access="hasRole('MANAGER')">
	<input type="submit" name="changeStatus"
		value="<spring:message code="application.submit" />" />
		
	<input type="button" name="cancel"
		value="<spring:message code="application.cancel"/>" onclick="javascript:relativeRedir('application/manager/list.do');"/>
	</security:authorize>
	
	

</form:form>