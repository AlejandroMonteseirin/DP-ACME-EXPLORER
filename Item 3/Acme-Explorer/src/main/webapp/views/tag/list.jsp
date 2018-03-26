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
<form action="tag/list.do">
<input type="hidden" name="tripId" value="${trip.id}"/>
<select name="tagId">
<jstl:forEach items="${tags}" var="tag">
<option label="${tag.name}" value="${tag.id}"/>
</jstl:forEach>
</select>
<input type="submit" code="tag.mostrar"/>
</form>

<display:table pagesize="5" class="displaytag" keepStatus="true"
	name="values"  requestURI="tag/list.do" id="row">
	
	<spring:message code="tag.value.value" var="nameHeader" />
	<display:column title="${nameHeader}" property="content"/>
	
	<security:authorize access="hasRole('MANAGER')">
	<display:column>
	
		<jstl:set var="contains" value="false" />
		<jstl:forEach var="item" items="${trip.values}">
			<jstl:if test="${item.id eq row.id}">
				<jstl:set var="contains" value="true" />
			</jstl:if>
		</jstl:forEach>

		<jstl:choose>
	 	<jstl:when test="${contains eq false}">
			<spring:url value="trip/addValue.do?valueId=${row.id}&tripId=${trip.id}&tagId=${tagId}"
				var="addValue" />
			<a href="${addValue}"><spring:message code="tag.addValue"/></a>
		</jstl:when>
	
		<jstl:otherwise>
			<spring:url value="trip/removeValue.do?valueId=${row.id}&tripId=${trip.id}&tagId=${tagId}"
				var="addValue" />
			<a href="${addValue}"><spring:message code="tag.removeValue"/></a>
		</jstl:otherwise>
	
	</jstl:choose>
	</display:column>
	</security:authorize>
	
	<security:authorize access="hasRole('ADMIN')">
	<display:column>
	<a href="value/admin/edit.do?valueId=${row.id}">Editar valor</a>
	</display:column>
	</security:authorize>
	

</display:table>

<security:authorize access="hasRole('ADMIN')">
<jstl:if test="${not empty tagId}">
<a href="tag/admin/edit.do?tagId=${tagId}"><spring:message code="tag.modify"/></a>
<a href="value/admin/create.do?tagId=${tagId}"><spring:message code="tag.addValue"/></a>
</jstl:if>
<a href="tag/admin/create.do"><spring:message code="tag.create"/></a>
</security:authorize>

<jstl:if test="${not empty trip}">
<input type="button" name="cancel"
		value="<spring:message code="folder.back" />"
		onclick="javascript: relativeRedir('trip/manager/display.do?tripId=${trip.id}');" />
	<br />
</jstl:if>





