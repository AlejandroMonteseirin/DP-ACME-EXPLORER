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

<form:form action="value/admin/edit.do" modelAttribute="value">

	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="tag" />

	<form:label path="content">
		<spring:message code="tag.value.value" />:
	</form:label>
	<br/>
	<form:textarea path="content" />
	<form:errors cssClass="error" path="content" />
	<br/>
	<br/>

	<input type="submit" name="save"
		value="<spring:message code="note.save" />" />&nbsp;
	<jstl:if test="${value.id != 0}">
		<input type="submit" name="delete"
		value="<spring:message code="category.delete" />"
		onclick="return confirm('<spring:message code="category.confirm.delete" />')" />
	</jstl:if> 
	<input type="button" name="cancel"
		value="<spring:message code="note.cancel" />"
		onclick="javascript: relativeRedir('tag/list.do');" />
	<br />

</form:form>
