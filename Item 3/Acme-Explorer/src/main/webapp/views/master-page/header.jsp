<%--
 * header.jsp
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

<!-- <script type="text/javascript">
$(document).ready(function() {
	 /* $("#banner").load("configuration/loadBanner.do"); */
	$.ajax({
        url: 'configuration/loadBanner.do',
        type: 'get',

        success: function (data) {
            $("#banner").attr("src",data);

        }
    });

});

</script> -->

<img id="bannerIMG" height='300px'
	src="${bannerURL}" alt='ACME, Inc.  Your trips Company' />

<div>
	<ul id="jMenu">

		<!-- Opciones comunes a usuarios anónimos y registrados sin importal el rol -->

		<li><a class="fNiv"><spring:message code="master.page.trips" /></a>
			<ul>
				<li class="arrow"></li>
				<li><a href="trip/list.do"><spring:message
							code="master.page.tripsList" /></a></li>
				<li><a href="trip/searchByKeywordForm.do"><spring:message
							code="master.page.tripsSearch" /></a></li>
				<li><a href="category/list.do"><spring:message
							code="master.page.categories" /></a></li>
				<li><a href="trip/searchForm.do"><spring:message
							code="master.page.ajaxSearch" /></a></li>

			</ul></li>


		<li><a class="fNiv"><spring:message code="master.page.ranger" /></a>
			<ul>
				<li><a href="ranger/list.do"><spring:message
							code="master.page.rangers" /></a></li>

			</ul></li>

		<!-- Opciones según roles -->

		<security:authorize access="hasRole('ADMIN')">
			<%-- <li><a class="fNiv"><spring:message
						code="master.page.administrator" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="actor/administrator/list.do"><spring:message
								code="master.page.administrator.actors" /></a></li>
					<li><a href="actor/administrator/edit.do"><spring:message
								code="master.page.administrator.actors" /></a></li>
				</ul></li> --%>




			<li><a class="fNiv"><spring:message
						code="master.page.administrator" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="configuration/admin/display.do"><spring:message
								code="master.page.displayConfig" /></a></li>
					<li><a href="actor/admin/list.do"><spring:message
								code="master.page.listSuspicious" /></a></li>
					<li><a href="legalText/admin/list.do"><spring:message
								code="legalText.see" /></a></li>
					<li><a href="tag/list.do"><spring:message
								code="master.page.editTags" /></a></li>
					<li><a href="administrator/dashboard.do"><spring:message
								code="administrator.dashboard.name" /></a></li>
				</ul></li>


			<li><a class="fNiv"><spring:message
						code="master.page.registerAdmin" /></a>
				<ul>
					<li><a href="manager/registration/registration.do"><spring:message
								code="master.page.manager.registration" /></a></li>
					<li><a href="ranger/registration/registration.do"><spring:message
								code="master.page.rangerAdmin.registration" /></a></li>

				</ul></li>

		</security:authorize>

		<security:authorize access="hasRole('MANAGER')">
			<li><a class="fNiv"><spring:message
						code="master.page.manager" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="note/manager/list.do"><spring:message
								code="master.page.notes" /></a></li>
					<li><a href="trip/manager/create.do"><spring:message
								code="master.page.createTrip" /></a></li>
					<li><a href="survivalClass/manager/list-all.do"><spring:message
								code="master.page.administrator.survivalClass" /></a></li>
					<li><a href="survivalClass/manager/create.do"><spring:message
								code="master.page.administrator.create.survivalClass" /></a></li>
					<li><a href="application/manager/list.do"><spring:message
								code="master.page.applications" /></a></li>
					<li><a href="trip/manager/list.do"><spring:message
								code="master.page.organisedTrips" /></a></li>

				</ul></li>
		</security:authorize>

		<security:authorize access="hasRole('SPONSOR')">
			<li><a class="fNiv"><spring:message
						code="master.page.sponsor" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="sponsorship/sponsor/create.do"><spring:message
								code="master.page.sponsor.sponsorship" /></a></li>
					<li><a href="sponsorship/sponsor/list.do"><spring:message
								code="master.page.sponsorships" /></a></li>
				</ul></li>
		</security:authorize>

		<security:authorize access="hasRole('RANGER')">
			<li><a class="fNiv"><spring:message
						code="master.page.curriculum" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="curriculum/ranger/displayMyCurriculum.do"><spring:message
								code="master.page.ranger.curriculum" /></a></li>
				</ul></li>
		</security:authorize>

		<security:authorize access="hasRole('AUDITOR')">
			<li><a class="fNiv"><spring:message
						code="master.page.auditor" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="note/auditor/list.do"><spring:message
								code="master.page.notes" /></a></li>
					<li><a href="audit/auditor/list.do"><spring:message
								code="master.page.audits" /></a></li>
				</ul></li>
		</security:authorize>

		<security:authorize access="hasRole('EXPLORER')">

			<li><a class="fNiv"><spring:message
						code="master.page.explorer" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="application/explorer/list.do"><spring:message
								code="application.page.list" /></a></li>
					<li><a href="story/explorer/list.do"><spring:message
								code="master.page.stories" /></a></li>
					<li><a href="contact/explorer/list-my.do"><spring:message
								code="master.page.contact" /></a></li>
					<li><a href="contact/explorer/create.do"><spring:message
								code="master.page.contact.create" /></a></li>
					<li><a href="application/explorer/create.do"><spring:message
								code="aplication.header" /></a></li>
					<li><a href="survivalClass/explorer/list-registered.do"><spring:message
								code="master.page.survivalClass.registered" /></a></li>
					<li><a href="survivalClass/explorer/list-not-registered.do"><spring:message
								code="master.page.survivalClass.NotRegistered" /></a></li>
					<li><a href="finder/explorer/create.do"><spring:message
								code="master.page.finder" /></a></li>
				</ul></li>

		</security:authorize>

		<security:authorize access="isAnonymous()">

			<li><a class="fNiv" href="security/login.do"><spring:message
						code="master.page.login" /></a></li>

			<li><a class="fNiv"><spring:message
						code="master.page.register" /></a>
				<ul>
					<li><a href="explorer/registration/registration.do"><spring:message
								code="master.page.explorer.registration" /></a></li>
					<li><a href="ranger/registration/registration.do"><spring:message
								code="master.page.ranger.registration" /></a></li>
					<li><a href="sponsor/registration/registration.do"><spring:message
								code="master.page.sponsor.registration" /></a></li>
					<li><a href="auditor/registration/registration.do"><spring:message
								code="master.page.auditor.registration" /></a></li>

				</ul></li>
		</security:authorize>

		<security:authorize access="isAuthenticated()">
			<li><a class="fNiv"> <spring:message
						code="master.page.profile" /> (<security:authentication
						property="principal.username" />)
			</a>
				<ul>
					<li class="arrow"></li>
						<security:authorize access="hasRole('ADMIN')">
							<li><a href="actor/admin/edit.do"><spring:message
								code="master.page.profile.edit" /> </a></li>
						</security:authorize>
					
						<security:authorize access="hasRole('EXPLORER')">
							<li><a href="actor/explorer/edit.do"><spring:message
								code="master.page.profile.edit" /> </a></li>
						</security:authorize>
								
						<security:authorize access="hasRole('MANAGER')">
							<li><a href="actor/manager/edit.do"><spring:message
								code="master.page.profile.edit" /> </a></li>
						</security:authorize>
								
						<security:authorize access="hasRole('RANGER')">
							<li><a href="actor/ranger/edit.do"><spring:message
								code="master.page.profile.edit" /> </a></li>
						</security:authorize>
								
						<security:authorize access="hasRole('SPONSOR')">
							<li><a href="actor/sponsor/edit.do"><spring:message
								code="master.page.profile.edit" /> </a></li>
						</security:authorize>
						
						<security:authorize access="hasRole('AUDITOR')">
							<li><a href="actor/auditor/edit.do"><spring:message
								code="master.page.profile.edit" /> </a></li>
						</security:authorize>
								
								
					<li><a href="j_spring_security_logout"><spring:message
								code="master.page.profile.logout" /> </a></li>
				</ul></li>
		</security:authorize>

		<security:authorize access="isAuthenticated()">
			<li><a class="fNiv"> <spring:message
						code="master.page.messages" />
			</a>
				<ul>
					<li class="arrow"></li>
					<li><a href="message/create.do"><spring:message
								code="master.page.newmessage" /> </a></li>
					<security:authorize access="hasRole('ADMIN')">
						<li><a href="notification/admin/create.do"><spring:message
									code="master.page.newnotification" /></a></li>
					</security:authorize>
					<li><a href="folder/list.do"><spring:message
								code="master.page.myfolders" /></a></li>

				</ul></li>
		</security:authorize>

	</ul>
</div>

<div>
	<a href="?language=en">en</a> | <a href="?language=es">es</a>
</div>

