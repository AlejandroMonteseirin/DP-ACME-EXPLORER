<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<form:form action="story/explorer/edit.do" modelAttribute="story">

	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="explorer" />

	<form:label path="title">
		<spring:message code="audit.title" />:
	</form:label>
	<form:input path="title" />
	<form:errors cssClass="error" path="title" />
	<br/>
	<br/>
	
	<form:label path="text">
		<spring:message code="story.text" />:
	</form:label>
	<form:textarea path="text" />
	<form:errors cssClass="error" path="text" />
	<br/>
	<br/>
	
	<form:label path="attachmentURLs">
		<spring:message code="story.attachmentURLs" />:
	</form:label>
	<form:textarea path="attachmentURLs" />
	<form:errors cssClass="error" path="attachmentURLs" />
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
		value="<spring:message code="story.save" />" />&nbsp;
		
	<jstl:if test="${story.id != 0}">
		<input type="submit" name="delete"
		value="<spring:message code="tag.delete" />"
		onclick="return confirm('<spring:message code="audit.confirm.delete" />')" />
	</jstl:if>
	
	<input type="button" name="cancel"
		value="<spring:message code="contact.cancel" />"
		onclick="javascript: relativeRedir('story/explorer/list.do');" />
	<br />

</form:form>
