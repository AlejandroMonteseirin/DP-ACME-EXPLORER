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
	name="stories" requestURI="${requestURI}" id="row">

	<spring:message code="master.page.trip" var="tripHeader" />
	<display:column property="trip.title" title="${tripHeader}" />
	
	<spring:message code="story.title" var="titleHeader" />
	<display:column property="title" title="${titleHeader}" />
	
	<display:column>
	<%-- TODO EL MUNDO PUEDE VER LOS DETALLES DE UN VIAJE PERO MANAGER TIENE OTRA VISTA --%>
			<a href="story/explorer/display.do?storyId=<jstl:out value="${row.getId()}"/>"><spring:message code="trip.display" /></a><br/>
	</display:column>


	<%-- <spring:message code="master.page.explorer" var="explorerModeHeader" />
	<display:column property="explorer.name" title="${explorerModeHeader}" /> --%>

</display:table>

<a href="story/explorer/create.do"><spring:message
		code="story.create" /></a>


