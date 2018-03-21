<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>


<div id="ranger" >

	<ul style="list-style-type:disc">
	
	<li>
	<b><spring:message code="ranger.name"></spring:message>:</b>
	<jstl:out value="${ranger.getName()}"/>
	</li>
	
	<li>
	<b><spring:message code="ranger.surname"></spring:message>:</b>
	<jstl:out value="${ranger.getSurname()}"/>
	</li>
	
	<li>
	<b><spring:message code="ranger.email"></spring:message>:</b>
	<jstl:out value="${ranger.getEmail()}"/>
	</li>
	
	<li>
	<b><spring:message code="ranger.phoneNumber"></spring:message>:</b>
	<jstl:out value="${ranger.getPhoneNumber()}"/>
	</li>
	
	<li>
	<b><spring:message code="ranger.address"></spring:message>:</b>
	<jstl:out value="${ranger.getAddress()}"/>
	</li>
	
	<%-- SOLO EL ADMIN DEBE PODER VER LA INFORMACION RELACIONADO CON SOSPECHOSO Y BANEO --%>
	<security:authorize access="hasRole('ADMINISTRATOR')">
	<li>
	<b><spring:message code="ranger.suspicious"></spring:message>:</b>
	<jstl:out value="${ranger.getSuspicious()}"/>
	</li>
	
	<li>
	<b><spring:message code="ranger.isBanned"></spring:message>:</b>
	<jstl:out value="${ranger.getIsBanned()}"/>
	</li>
	</security:authorize>

	</ul>
	
</div>
<%-- SOLO PODREMOS ACCEDER AL CURRICULUM SI EL RANGER TIENE UNO --%>
<jstl:if test="${ranger.getCurriculum() != null}">
	<input type="button" name="curriculum"
		value="<spring:message code="ranger.curriculum" />"
		onclick="javascript: relativeRedir('curriculum/display.do?curriculumId=${ranger.getCurriculum().getId()}')" />
</jstl:if>

	<input type="button" name="back"
		value="<spring:message code="ranger.back" />"
		onclick="javascript: relativeRedir('ranger/list.do')" />
	