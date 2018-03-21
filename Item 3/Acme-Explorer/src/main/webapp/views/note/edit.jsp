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

<form:form action="note/auditor/edit.do" modelAttribute="note">

	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="creationMoment" />
	<form:hidden path="auditor"/>

	<form:label path="remark">
		<spring:message code="note.remark" />:
	</form:label>
	<br/>
	<form:textarea path="remark" />
	<form:errors cssClass="error" path="remark" />
	<br/>
	<br/>

	<form:label path="trip">
		<spring:message code="master.page.trips" />:
	</form:label>
	<form:select id="trips" path="trip">
		<form:option value="0" label="----" />
		<form:options items="${trips}" itemValue="id" itemLabel="title" />
	</form:select>
	<form:errors cssClass="error" path="trip" />
	<br/>
	<br/>


	<input type="submit" name="save"
		value="<spring:message code="note.save" />" />&nbsp; 
	<input type="button" name="cancel"
		value="<spring:message code="note.cancel" />"
		onclick="javascript: relativeRedir('note/auditor/list.do');" />
	<br />

</form:form>
