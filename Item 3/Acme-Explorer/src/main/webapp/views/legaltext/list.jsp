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
	name="legalTexts"  requestURI="legalText/admin/list.do" id="row">
	
	<spring:message code="legalText.title" var="titleHeader" />
	<display:column property="title" title="${titleHeader}"/>
	
	<spring:message code="legalText.body" var="bodyHeader" />
	<display:column property="body" title="${bodyHeader}"/>
	
	<spring:message code="legalText.applicableLaws" var="applicableLawHeader" />
	<display:column property="applicableLaws" title="${applicableLawHeader}"/>
	
	<spring:message code="legalText.moment" var="momentHeader" />
	<spring:message code="master.page.date.format" var="dateFormat" />
	<display:column property="moment" format="{0,date,${dateFormat}}" title="${momentHeader}"/>
	
	<spring:message code="legalText.savedMode" var="finalModeHeader" />
	<display:column title="${finalModeHeader}">
	<jstl:choose>
		<jstl:when test="${row.savedMode eq 'FINAL MODE'}">
		<spring:message code="legalText.finalMode"/>
		</jstl:when>
		
		<jstl:otherwise>
		<spring:message code="legalText.draftMode"/>[<a href="legalText/admin/edit.do?legalTextId=${row.id}"><spring:message code="category.edit"/></a>]
		</jstl:otherwise>
	</jstl:choose>
	</display:column>
	
</display:table>

<a href="legalText/admin/create.do"><spring:message code="legalText.create"/></a>