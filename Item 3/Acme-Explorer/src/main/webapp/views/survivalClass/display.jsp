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

	<!-- Guardamos en una variable el formato de la fecha  -->
	
	<spring:message code="master.page.locale" var="locale" />
	<spring:message code="master.page.date.format" var="dateFormat" />
	
	
 	<fmt:setLocale value="${locale}"/>
	
	
<div id="survivalClass">
<!-- 	<h1>
		<jstl:out value="${survivalClass.getTitle()}" />
	</h1> -->
	<br />

	<ul style="list-style-type: disc">

		<li><b><spring:message code="survivalClass.title"></spring:message>:</b>
			<jstl:out value="${survivalClass.getTitle()}" /></li>

		<li><b><spring:message code="survivalClass.description"></spring:message>:</b>
			<jstl:out value="${survivalClass.getDescription()}" /></li>

		<li><b><spring:message code="survivalClass.organizationDate"></spring:message>:</b> 
	<fmt:formatDate value="${survivalClass.getOrganizationDate()}" pattern="${dateFormat}" /></li>
	
		<li><b><spring:message code="survivalClass.location"></spring:message>:</b>
			<jstl:out value="${survivalClass.getLocation()}" /></li>

		<li><b><spring:message code="survivalClass.trip"></spring:message>:</b>
				<jstl:out value="${survivalClass.getTrip().getTitle()}"/></li>

	</ul>

</div>


	<security:authorize access="hasRole('MANAGER')">

		<input type="button" name="edit"
			value="<spring:message code="survivalClass.edit" />"
			onclick="javascript: relativeRedir('survivalClass/manager/edit.do?survivalClassId=${survivalClass.getId()}')" />
		<input type="button" name="cancel"
			value="<spring:message code="survivalClass.cancel" />"
			onclick="javascript: relativeRedir('survivalClass/manager/list-all.do')" />
	</security:authorize>

<security:authorize access="hasRole('EXPLORER')">
<input type="button" name="cancel"
	value="<spring:message code="survivalClass.cancel" />"
	onclick="javascript: relativeRedir('')" />
	
	</security:authorize>