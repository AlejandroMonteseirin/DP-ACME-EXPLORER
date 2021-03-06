<%--
 * edit.jsp
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
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<form:form action="stage/manager/edit.do" modelAttribute="stage">

	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="trip" />

	<form:label path="title">
		<spring:message code="stage.title" />:
	</form:label>
	<br />
	<form:input path="title" />
	<form:errors cssClass="error" path="title" />
	<br />
	<br />

	<form:label path="description">
		<spring:message code="stage.description" />:
	</form:label>
	<br />
	<form:textarea path="description" />
	<form:errors cssClass="error" path="description" />
	<br />
	<br />

	<form:label path="price">
		<spring:message code="stage.price" />:
	</form:label>
	<br />
	<form:input path="price" />
	<form:errors cssClass="error" path="price" />
	<br />
	<br />


	<input type="submit" name="save"
		value="<spring:message code="stage.save" />" />&nbsp; 
	<jstl:if test="${stage.id != 0}">
		<input type="submit" name="delete"
			value="<spring:message code="tag.delete" />"
			onclick="return confirm('<spring:message code="tag.confirm.delete" />');" />
	</jstl:if>
	<input type="button" name="cancel"
		value="<spring:message code="stage.cancel" />"
		onclick="javascript: relativeRedir('trip/manager/create.do');" />
	<br />

</form:form>
