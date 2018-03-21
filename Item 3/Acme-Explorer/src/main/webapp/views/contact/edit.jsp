

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

<script type="text/javascript">

$(document).ready(function() {
	 $("#formID").submit(function(){
	var m = document.getElementById("phone").value;
	if(m != ""){
	var expreg = /^(\+\d{1,3})?\s(\(\d{3}\))?\s?\d{4,100}$/;
	
	if(!expreg.test(m)){
		
		return confirm("Are you sure you want to save this phone?");
	}
	}
});
});

</script>

<form:form id="formID" action="contact/explorer/edit.do" modelAttribute="contact">

	<form:hidden path="id" />
	<form:hidden path="version" />

	<!-- Guardamos en una variable el formato de la fecha según el idioma -->
	<spring:message code="master.page.date.format" var="dateFormat" />

	<form:label path="name">
		<spring:message code="contact.name" />:
	</form:label>
	<form:input path="name" />
	<form:errors cssClass="error" path="name" />
	</br>
	<form:label path="phoneNumber">
		<spring:message code="contact.phoneNumber" />:
	</form:label>
	<form:input id="phone" path="phoneNumber" />
	<form:errors cssClass="error" path="phoneNumber" />

	</br>

	<form:label path="email">
		<spring:message code="contact.email" />:
	</form:label>
	<form:input path="email" />
	<form:errors cssClass="error" path="email" />

	</br>



	</br>
	<input type="submit" name="save"
		value="<spring:message code="contact.save" />" />&nbsp; 
	<input type="submit" name="delete"
		value="<spring:message code="contact.delete" />"
		onclick="return confirm('<spring:message code="contact.confirm.delete" />')" />
	<input type="button" name="cancel"
		value="<spring:message code="contact.cancel" />"
		onclick="javascript: relativeRedir('contact/explorer/list-my.do');" />



</form:form>