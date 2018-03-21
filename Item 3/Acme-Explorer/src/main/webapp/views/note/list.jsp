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
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<!-- Listing grid -->

<display:table pagesize="5" class="displaytag" keepStatus="true"
	name="notes" requestURI="${requestURI}" id="row">
	
	<!-- Action links -->
	
	<!-- Attributes -->
	
	<spring:message code="note.trip" var="tripHeader" />
	<display:column property="trip.title" title="${tripHeader}"   />
	
	<spring:message code="note.creationMoment" var="creationMomentHeader" />
	<spring:message code="master.page.date.format" var="dateFormat" />
	<display:column property="creationMoment" title="${creationMomentHeader}"
		 format="{0,date,${dateFormat}}"/>

	<spring:message code="note.remark" var="remarkHeader" />
	<display:column property="remark" title="${remarkHeader}"  />
	
	<spring:message code="note.reply" var="replyHeader" />
	<display:column title="${replyHeader}" property="reply.text" />
	
	<security:authorize access="hasRole('MANAGER')">
	<display:column>
	<jstl:choose>
	<jstl:when test="${empty row.reply}">
	<a href="reply/manager/create.do?noteId=<jstl:out value="${row.id}"/>"><spring:message code="note.manager.reply"/></a>
	</jstl:when>
	<jstl:otherwise>
	<a href="reply/display.do?replyId=<jstl:out value="${row.reply.id}"/>"><spring:message code="note.seeReply"/></a>
	</jstl:otherwise>
	</jstl:choose>
	</display:column>
	</security:authorize>
	
	
	
</display:table>

<security:authorize access="hasRole('AUDITOR')">
	<a href="note/auditor/create.do"><spring:message code="master.page.auditor.note"/></a>
</security:authorize>
