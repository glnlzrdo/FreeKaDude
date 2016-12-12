<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Free Ka Ba Home</title>
<spring:url value="/resources/jquery.js" var="jqueryJS" />
<spring:url value="/resources/app.js" var="appJS" />
<spring:url value="/resources/app.css" var="appCSS" />
<script type="text/javascript" src="${jqueryJS}"></script>
<script type="text/javascript" src="${appJS}"></script>
<link rel="stylesheet" href="${appCSS}">
</head>
<body>
	<h1>Welcome ${user.firstname}</h1>
	<h1>username: ${user.username}</h1>
	<h4>ID = ${user.user_id}</h4>
	
	<p>CreateEvent</p>
	<form action="App" method="post">
	
	<input type="hidden" name="allday" value="">
	
	<table>
	<tr>
	<td>From: <input type="text" name="from" size="3" placeholder="From"/></td><td>To: <input type="text" name="to" size="3" placeholder="To" /></td>
	<td><input type="text" name="descr"/></td>
	</tr>
	
	<tr>
	<td><input id="hour1" class="hour" type="checkbox" name="hour" value="1" />1</td><td><input id="event1" disabled type="text" name="event" placeholder="Event for 1am" /></td>
	</tr>
	<tr>
	<td><input id="hour2" class="hour" type="checkbox" name="hour" value="2" />2</td><td><input id="event2" disabled type="text" name="event" placeholder="Event for 2am" /></td>
	</tr>
	<tr>
	<td><input id="hour3" class="hour" type="checkbox" name="hour" value="3" />3</td><td><input id="event3" disabled type="text" name="event"  placeholder="Event for 3am" /></td>
	</tr>
	<tr>
	<td><input id="hour4" class="hour" type="checkbox" name="hour" value="4" />4</td><td><input id="event4" disabled type="text" name="event"  placeholder="Event for 4am" /></td>
	</tr>
	<tr>
	<td><input id="hour5" class="hour" type="checkbox" name="hour" value="5" />5</td><td><input id="event5" disabled type="text" name="event"  placeholder="Event for 5am" /></td>
	</tr>
	<tr>
	<td><input id="hour6" class="hour" type="checkbox" name="hour" value="6" />6</td><td><input id="event6" disabled type="text"  name="event"  placeholder="Event for 6am" /></td>
	</tr>
	<tr>
	<td><input id="hour7" class="hour" type="checkbox" name="hour" value="7" />7</td><td><input id="event7" disabled type="text" name="event"  placeholder="Event for 7am" /></td>
	</tr>
	<tr>
	<td><input id="hour8" class="hour" type="checkbox" name="hour" value="8" />8</td><td><input id="event8" disabled type="text" name="event"  placeholder="Event for 8am" /></td>
	</tr>
	<tr>
	<td><input type="submit" value="Submit" name="addEvent"/></td>
	</tr>
	</table>
	</form>
	
</body>
</html>