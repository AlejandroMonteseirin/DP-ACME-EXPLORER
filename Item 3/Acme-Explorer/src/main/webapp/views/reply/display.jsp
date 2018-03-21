<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<ul style="list-style-type: disc">

	<li><b><spring:message code="reply.moment"></spring:message>:</b> <jstl:out
			value="${reply.moment}" /></li>

	<li><b><spring:message code="reply.text"></spring:message>:</b> <jstl:out
			value="${reply.text}" /></li>

	
</ul>

<input type="button" name="back"
	value="<spring:message code="ms.back" />"
	onclick="javascript: relativeRedir('note/manager/list.do')" />