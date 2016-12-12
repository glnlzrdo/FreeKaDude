<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<!DOCTYPE html>
<html>

<head>
    <meta charset="utf-8"/>
    <title>free ka dude?</title>
    
    <spring:url value="/resources/jquery-ui/jquery-ui.min.css" var="jQui" />
    <spring:url value="/resources/fullcalendar/fullcalendar.css" var="fCal" />
    <spring:url value="/resources/css/bootstrap-combined.min.css" var="bsCom" />
    <spring:url value="/resources/fullcalendar/lib/jquery.min.js" var="jQmin" />
    <spring:url value="/resources/css/bootstrap.min.js" var="bsMin" />
    <spring:url value="/resources/fullcalendar/lib/moment.min.js" var="moment" />
    <spring:url value="/resources/fullcalendar/fullcalendar.js" var="fulCal" />
    <spring:url value="/resources/js/gcal.js" var="gCal" />
    <spring:url value="/resources/js/calendar.js" var="calFunc" />
    <spring:url value="/resources/lato/latofonts.css" var="latoFont" />
    <spring:url value="/resources/css/style.css" var="customStyle" />
    
    <link rel='stylesheet' href='${jQui}' />
    <link rel='stylesheet' href='${fCal}' />
    <link rel='stylesheet' href='${bsCom}' />
    <script src='${jQmin}'></script>
    <script src='${bsMin}'></script>
    <script src='${moment}'></script>
    <script src='${fulCal}'></script>
    <script src='${gCal}'></script>

    <script src='${calFunc}'></script>
    <link href="${latoFont}" rel="stylesheet">
    <link rel="stylesheet" href="${customStyle}" />
</head>

<body>
  <div class="header">
      <img src="resources/images/FkD Logo.png">
  </div>
    <div id="login-container">
        <div id="login-label">
                <h1>Login</h1>
        </div>
        <form method="post" action="/FreeKaBa/App/home">
	        <div id="login-left-box">
	            <div class="login-box">
	                <p>Username</p>
	                <input class="login-fields" type="text" name="username" placeholder="Username"/>
	                <p>Password</p>
	                <input class="login-fields" type="password" name="password" placeholder="Password"/><br/><br/>
	                <button class="btn btn-info" type="submit">Login</button>
	            </div>
	        </div>
	    </form>
	    <div id="login-right-box">
	        <p>Not yet a member?<br> Click on the button below to register!</p><br/>
	        <button class="btn btn-primary" type="button" data-toggle="modal" data-target="#myModal">Register</button>
    	</div>
    </div>

    <!--MODAL-->
    <!-- Modal -->
    <form method="post" action="/FreeKaBa/App/registeruser">
	    <div id="myModal" class="modal fade" role="dialog">
	        <div class="modal-dialog">
	        <!-- Modal content-->
	            <div class="modal-content">
	                <div class="modal-header">
	                    <button type="button" class="close" data-dismiss="modal">&times;</button>
	                    <h4 id="modal-title-head" class="modal-title">Register New Account</h4>
	                </div>
	                <div class="modal-body">
	                    <div id="register-firstname">
	                        <p>First Name</p>
	                        <input class="login-fields" type="text" name="firstname" placeholder="First Name" required="required"/>
	                    </div>
	                    <div id="register-lastname">
	                        <p>Last Name</p>
	                        <input class="login-fields" type="text" name="lastname" placeholder="Last Name" required="required"/>
	                    </div>
	                    <div id="register-username">
	                        <p>Username</p>
	                        <input class="login-fields" type="text" name="username" placeholder="Username" required="required"/>
	                    </div>
	                    <div id="register-password">
	                        <p>Password</p>
	                        <input class="login-fields" type="password" name="password" placeholder="Password" required="required"/>
	                    </div>
	                    <div id="register-email">
	                        <p>Email Address</p>
	                        <input class="login-fields" type="email" name="email" placeholder="email@email.com" required="required"/><br/><br/>
	                        <button class="btn btn-primary" type="submit">Register</button>
	                    </div>
	                </div>
	                <div class="modal-footer">
	                    <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
	                </div>
	            </div>
	        </div>
	    </div>
	</form>

</body>
