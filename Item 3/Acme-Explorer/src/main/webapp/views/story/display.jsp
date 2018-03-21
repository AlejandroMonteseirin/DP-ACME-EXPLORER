<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<spring:message code="master.page.date.format" var="dateFormat" />

<div id="audit" >
	<h1><jstl:out value="${story.getTitle()}" /></h1>
	<br/>

	<ul style="list-style-type:disc">
	
	<li><b><spring:message code="story.text"></spring:message>:</b>
				<jstl:out value="${story.text}"/></li>

	<li>
	<b><spring:message code="story.attachmentURLs"></spring:message>:</b>
	<jstl:out value="${story.attachmentURLs}"/>
	</li>
	
	<li>
	<b><spring:message code="master.page.explorer"></spring:message>:</b>
	<jstl:out value="${story.getExplorer().getName()}"/>
	</li>
	
	<li>
	<b><spring:message code="audit.tripTitle"></spring:message>:</b>
	<jstl:out value="${story.getTrip().getTitle()}"/>
	</li>

	</ul>
	
</div>

	<input type="button" name="edit"
		value="<spring:message code="story.edit" />"
		onclick="javascript: relativeRedir('story/explorer/edit.do?storyId=${story.getId()}')" />
	
	<input type="button" name="back"
		value="<spring:message code="audit.back" />"
		onclick="javascript: relativeRedir('story/explorer/list.do')" />
	