
<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>

<form:form action="socialIdentity/edit.do" modelAttribute="socialIdentity">

	<form:hidden path="id" />
	<form:hidden path="version" />


	<form:label path="nick">
		<spring:message code="socialIdentity.nick" />:
	</form:label>
	<form:input path="nick" />
	<form:errors cssClass="error" path="nick" />
	<br />

	<form:label path="socialNetworkName">
		<spring:message code="socialIdentity.socialNetworkName" />:
	</form:label>
	<form:input path="socialNetworkName" />
	<form:errors cssClass="error" path="socialNetworkName" />
	<br />

	<form:label path="profileLink">
		<spring:message code="socialIdentity.profileLink" />:
	</form:label>
	<form:input path="profileLink" />
	<form:errors cssClass="error" path="profileLink" />
	<br />

	<form:label path="photoUrl">
		<spring:message code="socialIdentity.photoUrl" />:
	</form:label>
	<form:input path="photoUrl" />
	<form:errors cssClass="error" path="photoUrl" />
	<br />


	<input type="submit" name="save"
		value="<spring:message code="socialIdentity.save"/>" />
		<!-- onclick="javascript: window.location.replace('welcome/index.do'); -->
		
	<input type="button" name="cancel"
		value="<spring:message code="educationRecord.cancel" />"
		onclick="javascript: relativeRedir('/curriculum/ranger/displayMyCurriculum.do');" />
		
	<jstl:if test="${socialIdentity.id != 0}">
		<input type="submit" name="delete"
			value="<spring:message code="educationRecord.delete" />"
			onclick="return confirm('<spring:message code="socialIdentity.confirm.delete" />')" />&nbsp;
	</jstl:if>
	
	<acme:cancel url="/" code="socialIdentity.cancel"/>


</form:form>

<%-- name / surname / emailAddress / phoneNumber / postalAddress --%>