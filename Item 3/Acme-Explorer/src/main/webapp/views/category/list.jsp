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
	name="categories"  requestURI="category/list.do" id="row">
	
	<spring:message code="category.name" var="nameHeader" />
	<display:column title="${nameHeader}">
	
	<jstl:choose>
		<jstl:when test="${not empty row.childCategories}">
			<spring:url value="category/list.do?parentCategoryId=${row.id}"
				var="tripsByCategoryURL" />
			<a href="${tripsByCategoryURL}"><jstl:out value="${row.name}"/></a>
		</jstl:when>
	
		<jstl:otherwise>
			<jstl:out value="${row.name}"/>
		</jstl:otherwise>
	
	</jstl:choose>
	
	</display:column>
	
	<%-- <spring:message code="category.childCategories" var="columnHeader" />
	<display:column title="${columnHeader}">
	<jstl:if test="${not empty row.childCategories}">
		<spring:url value="category/list.do?parentCategoryId=${row.id}"
			var="tripsByCategoryURL" />
		<a href="${tripsByCategoryURL}"><spring:message code="category.childCategories"/></a>
	</jstl:if>
	</display:column> --%>
	
	<spring:message code="category.trips" var="columnHeader" />
	<display:column title="${columnHeader}">
		<spring:url value="trip/list.do?categoryId=${row.id}"
			var="childCategoriesURL" />
		<a href="${childCategoriesURL}"><spring:message code="category.trips"/></a>
	</display:column>	
	
	<security:authorize access="hasRole('ADMIN')">
	<spring:message code="category.edit" var="columnHeader" />
	<display:column title="${columnHeader}">
		<a href="category/edit.do?categoryId=${row.id}"><spring:message code="category.edit"/></a>
	</display:column>	
	</security:authorize>

</display:table>

<security:authorize access="hasRole('ADMIN')">
<a href="category/administrator/create.do?categoryId=${row.id}"><spring:message code="category.create"/></a>
</security:authorize>

<%-- <jstl:forEach var="x" items="${categories}">
		<spring:url value="category/list.do?parentCategoryId=${x.id}"
			var="childCategoriesURL" />
		<spring:url value="trip/list.do?categoryId=${x.id}"
			var="tripsByCategoryURL" />
		<li><a href="${childCategoriesURL}">${x.name}</a><br/><br/>
		<a href="${tripsByCategoryURL}"><b><spring:message code="category.trips"/></b></a></li><br/>
</jstl:forEach> --%>






