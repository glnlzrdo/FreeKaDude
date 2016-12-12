<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Sample Form</title>
</head>
<body>

<form action="App" method="post">
<table>
	<tr>
		<td>Firstname:</td><td><input type="text" name="firstname" placeholder="Firstname"/></td>
	</tr>
	<tr>
		<td>Lastname:</td><td><input type="text" name="lastname" placeholder="Lastname"/></td>
	</tr>
	<tr>
		<td>Username:</td><td><input type="text" name="username" placeholder="Username"/></td>
	</tr>
	<tr>
		<td>Password:</td><td><input type="password" name="password" /></td>
	</tr>
	<tr>
		<td>Email:</td><td><input type="text" name="email" placeholder="Email"/></td>
	</tr>
	<tr>
		<td><input type="submit" name="submit" value="Submit"/></td>
		<td><input type="submit" name="login" value="Log in"/></td>
	</tr>

</table>
</form>

</body>
</html>