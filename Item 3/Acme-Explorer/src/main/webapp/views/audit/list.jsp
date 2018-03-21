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

<display:table pagesize="5" class="displaytag" keepStatus="true"
	name="audits" requestURI="${requestURI}" id="row">

	<!-- Action links -->
	<!-- Attributes -->

	<spring:message code="audit.auditionMoment" var="auditionMomentHeader" />
	<spring:message code="master.page.date.format" var="dateFormat" />
	<display:column property="auditionMoment"
		title="${auditionMomentHeader}" 
		format="{0,date,${dateFormat}}" />

	<spring:message code="audit.title" var="titleHeader" />
	<display:column property="title" title="${titleHeader}" />

	<spring:message code="legalText.savedMode" var="finalModeHeader" />
	<display:column title="${finalModeHeader}">
	<jstl:choose>
		<jstl:when test="${row.savedMode eq 'FINAL MODE'}">
		<spring:message code="legalText.finalMode"/>
		</jstl:when>
		
		<jstl:otherwise>
		<spring:message code="legalText.draftMode"/>[<a href="audit/auditor/edit.do?auditId=${row.id}"><spring:message code="category.edit"/></a>]
		</jstl:otherwise>
	</jstl:choose>
	</display:column>

	<display:column>
		<a
			href="audit/auditor/display.do?auditId=<jstl:out value="${row.getId()}"/>"><spring:message
				code="audit.display" /></a>
	</display:column>

</display:table>

<a href="audit/auditor/create.do"><spring:message
		code="audit.create" /></a>


