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

<spring:message code="master.page.date.format" var="dateFormat" />

<div id="audit">
	<h1>
		<jstl:out value="${audit.getTitle()}" />
	</h1>
	<br />

	<ul style="list-style-type: disc">

		<li><b><spring:message code="audit.auditionMoment"></spring:message>:</b>
			<fmt:formatDate value="${audit.getAuditionMoment()}"
				pattern="${dateFormat}" /></li>



		<li><b><spring:message code="audit.description"></spring:message>:</b>
			<jstl:out value="${audit.getDescription()}" /></li>

		<li><b><spring:message code="audit.attachmentURLs"></spring:message>:</b>
			<jstl:out value="${audit.getAttachmentURLs()}" /></li>

		<li><b><spring:message code="audit.finalMode"></spring:message>:</b>
			<jstl:out value="${audit.getSavedMode()}" /></li>

		<li><b><spring:message code="audit.auditor"></spring:message>:</b>
			<jstl:out value="${audit.getAuditor().getName()}" /></li>

		<li><b><spring:message code="audit.tripTitle"></spring:message>:</b>
			<jstl:out value="${audit.getTrip().getTitle()}" /></li>

	</ul>

</div>

<jstl:if test="${audit.savedMode eq 'DRAFT MODE'}">
	<input type="button" name="edit"
		value="<spring:message code="audit.edit" />"
		onclick="javascript: relativeRedir('audit/auditor/edit.do?auditId=${audit.getId()}')" />
</jstl:if>
<input type="button" name="back"
	value="<spring:message code="audit.back" />"
	onclick="javascript: relativeRedir('audit/auditor/list.do')" />
