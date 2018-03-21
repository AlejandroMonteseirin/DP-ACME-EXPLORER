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
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<form:form action="legalText/admin/edit.do" modelAttribute="legalText">

	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="moment" />

	<form:label path="title">
		<spring:message code="stage.title" />:
	</form:label><br/>
	<form:input path="title" />
	<form:errors cssClass="error" path="title" />
	<br/>
	<br/>
	
	<form:label path="body">
		<spring:message code="legalText.body" />:
	</form:label><br/>
	<form:textarea path="body" />
	<form:errors cssClass="error" path="body" />
	<br/>
	<br/>
	
	<form:label path="applicableLaws">
		<spring:message code="legalText.applicableLaws" />:
	</form:label><br/>
	<form:textarea path="applicableLaws" />
	<form:errors cssClass="error" path="applicableLaws" />
	<br/>
	<br/>
	
	<!-- Guardamos en una variable el formato de la fecha según el idioma -->
	<spring:message code="master.page.date.format" var="dateFormat" />
	
	<%-- <form:label path="moment">
		<spring:message code="legalText.moment" />:
	</form:label><br/>
	<form:input path="moment" placeholder="${dateFormat}" />
	<form:errors cssClass="error" path="moment" /><br /><br />
	<br/>
	<br/> --%>
	
	<form:label path="savedMode">
		<spring:message code="legalText.savedMode" />:
	</form:label><br/>
	<form:select path="savedMode" >
	<form:option value="DRAFT MODE" label="DRAFT MODE"/>
	<form:option value="FINAL MODE" label="FINAL MODE"/>
	</form:select>
	<form:errors cssClass="error" path="savedMode" />
	<br/>
	<br/>
	

	<input type="submit" name="save"
		value="<spring:message code="legalText.save" />" />&nbsp; 
	
	<jstl:if test="${legalText.id != 0}">
	<input type="submit" name="delete"
		value="<spring:message code="legalText.delete" />"
		onclick="return confirm('<spring:message code="category.confirm.delete" />')" />
	</jstl:if>
		
	<input type="button" name="cancel"
		value="<spring:message code="legalText.cancel" />"
		onclick="javascript: relativeRedir('legalText/admin/list.do');" />
	<br />

</form:form>
