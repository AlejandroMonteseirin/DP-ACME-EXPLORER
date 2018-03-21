<%--
 * edit.jsp
 *
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

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
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<script type="text/javascript">

$(document).ready(function() {
	 $("#formID").submit(function(){
	var m = document.getElementById("phone").value;
	var expreg = /^(\+\d{1,3})?\s(\(\d{3}\))?\s?\d{4,100}$/;
	
	if(!expreg.test(m)){
		
		return confirm("Are you sure you want to save this phone?");
	}
});
});

</script>

<form:form action="actor/manager/edit.do" id="formID"
	modelAttribute="manager">

	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="folders" />
	<form:hidden path="socialIdentities" />
	<form:hidden path="userAccount" />


	<form:label path="name">
		<spring:message code="manager.name" />:
	</form:label>
	<br>
	<form:input path="name" />
	<form:errors cssClass="error" path="name" />
	<br>
	<br>

	<form:label path="surname">
		<spring:message code="manager.surname" />:
	</form:label>
	<br>
	<form:input path="surname" />
	<form:errors cssClass="error" path="surname" />
	<br>
	<br>

	<form:label path="email">
		<spring:message code="manager.email" />:
	</form:label>
	<br>
	<form:input path="email" />
	<form:errors cssClass="error" path="email" />
	<br>
	<br>

	<form:label path="phoneNumber">
		<spring:message code="manager.phoneNumber" />:
	</form:label>
	<br>
	<form:input id="phone" path="phoneNumber" />
	<form:errors cssClass="error" path="phoneNumber" />
	<br>
	<br>


	<form:label path="address">
		<spring:message code="manager.address" />:
	</form:label>
	<br>
	<form:input path="address" />
	<form:errors cssClass="error" path="address" />
	<br>
	<br>




	<input type="submit" name="save"
		value="<spring:message code="manager.edit" />" />

	<input type="button" name="cancel"
		value="<spring:message code="manager.cancel" />"
		onclick="javascript: relativeRedir('/');" />
	<br />

</form:form>
<h2>Social Identities:</h2>

<display:table pagesize="3" class="displaytag" keepStatus="true"
	name="socialIdentities" requestURI="actor/admin/manager/edit.do" id="row">

	<!-- Action links -->


	<!-- Attributes -->

	<display:column title="${photoUrlHeader}">
		<img src="${row.photoUrl}"
			alt="<spring:message code="image.notfound"/>" width="75" height="75" />
	</display:column>
	
	<spring:message code="manager.socialIdentity.nick"
		var="nickHeader" />
	<display:column property="nick" title="${nickHeader}" sortable="false" />

	<spring:message code="manager.socialidentity.SocialNetworkName"
		var="socialNetworkNameHeader" />
	<display:column property="socialNetworkName"
		title="${socialNetworkNameHeader}" sortable="false" />

	<spring:message code="manager.socialidentity.ProfileLink"
		var="profileLinkHeader" />
		
	<display:column title="${profileLinkHeader}"> 
	<a href="${row.profileLink}"><jstl:out value="${row.profileLink}" /></a>
	</display:column>
	
	<spring:message code="manager.socialidentity.PhotoUrl"
		var="photoUrlHeader" />
	<security:authorize access="isAuthenticated()">
		<display:column>
			<a href="socialIdentity/edit.do?socialIdentityId=${row.id}"> <spring:message
					code="manager.socialIdentity.editar" />
			</a>
		</display:column>
	</security:authorize>


</display:table>

<!-- Create SocialIdentity link -->

<security:authorize access="isAuthenticated()">
	<div>
		<a href="socialIdentity/create.do"> <spring:message
				code="manager.socialIdentity.create" />
		</a>
	</div>
</security:authorize>

<%-- name / surname / emailAddress / phoneNumber / postalAddress --%>









