<%--
 * list.jsp
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

<!-- Listing grid -->

<display:table pagesize="4" class="displaytag" keepStatus="true"
	name="contacts" requestURI="${requestURI}" id="row">

	<!-- Action links -->
	<!-- Attributes -->

	<spring:message code="contact.name" var="nameHeader" />
	<display:column property="name" title="${nameHeader}" />

	<spring:message code="contact.email" var="emailHeader" />
	<display:column property="email" title="${emailHeader}" />



	<spring:message code="contact.phoneNumber" var="phoneNumberHeader" />
	<display:column property="phoneNumber" title="${phoneNumberHeader}" />







	<display:column>
		<%-- <input type="button" name="edit"
				value="<spring:message code="contact.edit" />"
				onclick="javascript: relativeRedir('contact/edit.do?contactId=${row.id}')" />
		 --%>



		<br />
		<a
			href="contact/explorer/edit.do?contactId=<jstl:out value="${row.getId()}"/>"><spring:message
				code="contact.edit" /></a>
		<br />
	</display:column>

</display:table>


