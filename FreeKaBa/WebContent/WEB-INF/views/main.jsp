<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!DOCTYPE html>
<html>

<head>
    <meta charset="utf-8"/>
    <title>free ka dude?</title>
        
    <spring:url value ="/resources/" var="resourceURL" />
    
    <link rel='stylesheet' href='${resourceURL}jquery-ui/jquery-ui.min.css' />
    <link rel='stylesheet' href='${resourceURL}fullcalendar/fullcalendar.css' />
    <link rel='stylesheet' href='${resourceURL}css/bootstrap-combined.min.css' />
    <script src='${resourceURL}fullcalendar/lib/jquery.min.js'></script>
    <script src='${resourceURL}css/bootstrap.min.js'></script>
    <script src='${resourceURL}fullcalendar/lib/moment.min.js'></script>
    <script src='${resourceURL}fullcalendar/fullcalendar.js'></script>
    <script src='${resourceURL}js/gcal.js'></script>

    <script src='${resourceURL}js/calendar.js'></script>
    <link href="${resourceURL}lato/latofonts.css" rel="stylesheet">
    <link rel="stylesheet" href="${resourceURL}css/style.css" />
</head>

<body>

    <div class="header">
        <div class="button-holder right">
            <span>Welcome back, <c:out value="${user.firstname}"/>!</span>
            <form method="post" action="/FreeKaBa/App/logout">
            	<input class="ui-button ui-widget ui-corner-all" type="submit" value="Logout">
            </form>
        </div>
        <img src="${resourceURL}images/FkD Logo.png">
    </div>

    <div class="main-container">
        <div class="calendar-container">
            <div id='calendar'></div>
        </div>

        <div class="dude-board-header">
            <h3>Free Dude Board</h3>
            <div class="dude-board-search">
	            <h4>Search Common Free Time</h4>
	            <form action="Search" method="post">
	            	<div id="search-members">
	            		<table>
		            		<tr>
		            		<td><span id="checkalltd"><input id="checkAll" type="checkbox"/></span></td>
		            		<td><span id="friends-text">Friends</span></td>
		            		</tr>
		            	</table>
		            	<div id="search-members-list">
		            		<table>
		            			<c:forEach items="${friends}" var="friends">
		            			<tr>
		            				<td><input type="checkbox" value="${friends.user_id}" name="checkedfriends"/></td>
		            				<td class="friend-list-names">${friends.firstname}</td>
		            			</tr>
		            			</c:forEach>
		            		</table>
		            	</div>
		            </div>
	            	<div id="search-parameters">
	            		<table>
		            	<tr>
		            		<td>From:</td><td><input name="searchFrom" type="date" required="required"></td>
		            	</tr>
		            	<tr>
		            		<td>Until:</td><td><input name="searchTo" type="date" required="required"></td>
		            	</tr>
		            	<tr>
		            		<td></td>
		            		<td>
		            			<input id="searchbutton" class="ui-button ui-widget ui-corner-all" type="submit" name="search" value="Search" disabled="disabled">
		            		</td>
	            		</tr>
	            		</table>
	            	</div>
	            </form>
	        </div>
        </div>
        <div class="dude-board-content">
        
        <c:choose>
        <c:when test="${not empty dateListList}">
      		<div class="dude-match-headers">
        		<label class="dude-matches">
        		<c:forEach var="friendsbottom" items="${friendsbottom}">
        			${friendsbottom.firstname},
        		</c:forEach>
        			and You are free on:
        		</label>
        	</div>
        	<c:forEach var="dateList" items="${dateListList}" >
        	<div class="libre-matches">
	                <label class="date-time-matches">${dateList[0]} until ${dateList[1]}</label>
	                <label class="date-time-matches">Length of Free Time: ${dateList[2]}</label>
	        </div>
            </c:forEach>
      	</c:when>
      	<c:when test="${not empty login}">
      		<div class="libre-matches">
                <label class="dude-matches">Start searching your common free time.</label>
                <label class="date-time-matches">No search results to view yet.</label>
            </div>
      	</c:when>
      	<c:otherwise>
      		<div class="libre-matches">
                <label class="dude-matches">Unfortunately, you guys have no common free time.</label>
                <label class="date-time-matches">That was unfortunate. :(</label>
            </div>
      	</c:otherwise>
      	</c:choose>
      	
        </div>
    </div>

<div id="createEventModal" class="modal hide fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel1" aria-hidden="true">
    <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">x</button>
        <h3 id="myModalLabel1">Create Activity</h3>
    </div>
    <div class="modal-body">
    <form id="createActivityForm" class="form-horizontal">
        <div class="control-group">
            <label class="control-label" for="activityName">Activity:</label>
            <div class="controls">

                <input type="text" name="activityName" id="activityName" style="margin: 0 auto;" />
                  <!--<input type="text" id="activityName" />-->
                  <input type="hidden" id="apptStartTime"/>
                  <input type="hidden" id="apptEndTime"/>
                  <input type="hidden" id="apptAllDay" />
            </div>
        </div>

            <div class="control-group">
                <label class="control-label" for="when">When:</label>
                <div class="controls controls-row" id="when" style="margin-top:5px;">
                </div>
            </div>

    </form>
    </div>
    <div class="modal-footer">
        <button id="prompt-cancel" class="btn" data-dismiss="modal" aria-hidden="true">Cancel</button>
        <button id="prompt-save" type="submit" class="btn btn-primary">Save</button>
    </div>
</div>

<div id="editEventModal" class="modal hide fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel1" aria-hidden="true">
    <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">x</button>
        <h3 id="myModalLabel1">Edit Activity</h3>
    </div>
    <div class="modal-body">
    <form id="editActivityForm" class="form-horizontal">
        <div class="control-group">
            <label class="control-label edit-label" for="inputActivity">Activity:</label>
            <div class="controls edit-control">

                <input type="text" name="activityName" id="inputActivity" style="margin: 0 auto;" />
                  <!--<input type="text" id="activityName" />-->
                  <input type="hidden" id="editStartTime"/>
                  <input type="hidden" id="editEndTime"/>
                  <input type="hidden" id="editAllDay" />
            </div>
        </div>

            <div class="control-group edit-control">
                <label class="control-label edit-label" for="when">When:</label>
                <div class="controls controls-row" id="editWhen" style="margin-top:5px;">
                </div>
            </div>

    </form>
    </div>
    <div class="modal-footer">
        <button id="prompt-cancel-edit" class="btn" data-dismiss="modal" aria-hidden="true">Cancel</button>
        <button id="prompt-delete" class="btn btn-danger">Delete</button>
        <button id="prompt-update" class="btn btn-primary">Update</button>
    </div>
</div>


</body>

</html>
