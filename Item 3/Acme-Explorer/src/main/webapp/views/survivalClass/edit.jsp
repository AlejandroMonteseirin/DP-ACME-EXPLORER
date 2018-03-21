

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

<form:form action="survivalClass/manager/edit.do" modelAttribute="survivalClass">



	<form:hidden path="id" />
	<form:hidden path="version" />
	<%-- <form:hidden path="location" />  --%>
	<form:hidden path="explorers" />
	 

	<!-- Guardamos en una variable el formato de la fecha según el idioma -->
	<spring:message code="master.page.date.format" var="dateFormat" />

	<form:label path="title">
		<spring:message code="survivalClass.title" />:
	</form:label>
	<form:input path="title" />
	<form:errors cssClass="error" path="title" />
<br/>
	<form:label path="description">
		<spring:message code="survivalClass.description" />:
	</form:label>
	<form:input path="description" />
	<form:errors cssClass="error" path="description" />

<br/>
	<form:label path="organizationDate">
		<spring:message code="survivalClass.organizationDate" />:
	</form:label>
	<form:input path="organizationDate"  placeholder="${dateFormat} " />
	<form:errors cssClass="error" path="organizationDate" />
	
	<br/>
	<form:label path="location.name">
		<spring:message code="survivalClass.location.name" />:
	</form:label>
	<form:input path="location.name"   />
	<form:errors cssClass="error" path="location.name" />
	
	<br/>
	<form:label path="location.coordinates">
		<spring:message code="survivalClass.location.coordinates" />:
	</form:label>
	<form:input path="location.coordinates" placeholder="13.55,3.1" />
	<form:errors cssClass="error" path="location.coordinates" />
	
	<form:label path="trip">
		<spring:message code="master.page.trips" />:
	</form:label>
	<form:select id="trips" path="trip">
		<form:option value="0" label="----" />
		
		<jstl:forEach items="${trips}" var="trip" >
		<jstl:choose>
			<jstl:when test="${trip.id eq tripId}">
				<form:option value="${trip.id}" label="${trip.title}" selected="true"/>
			</jstl:when>
		
			<jstl:otherwise>
			<form:option value="${trip.id}" label="${trip.title}" />
			</jstl:otherwise>
		</jstl:choose>
		</jstl:forEach>
		
	</form:select>
	

<br/>
	<input type="submit" name="save"
		value="<spring:message code="survivalClass.save" />" />&nbsp; 
		
		<jstl:if test="${survivalClass.id != 0}">
	<input type="submit" name="delete"
		value="<spring:message code="survivalClass.delete" />"
		onclick="return confirm('<spring:message code="survivalClass.confirm.delete" />')" />
	</jstl:if>
	<input type="button" name="cancel"
		value="<spring:message code="survivalClass.cancel" />"
		onclick="javascript: relativeRedir('survivalClass/manager/list-all.do');" />
		
	
		
		</form:form>