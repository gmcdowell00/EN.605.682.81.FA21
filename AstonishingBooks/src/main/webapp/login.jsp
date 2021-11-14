<%@ page contentType="text/html" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:import url="/header.jsp" />

<section class="center">

	<h1>Log in to Account</h1>
	
	<form action="" method="post">
		<table class="login">
			<tr>
				<td class="label"><label>Email:</label></td>
				<td class="input"><input type="email" name="email" value="${user.email}" class="user-input" required></td> 
			</tr>
		<tr>
			<td class="label"><label>Password:</label></td>
			<td class="input"><input type="password" name="password" value="${user.password}" class="user-input" required></td> 
		</tr>
		</table>
		<br>
		<br>
		<input type="submit" value="Login" class="orange-button submit-button">
	</form>
	<br>
	<hr>
	<br>
	<a href="">Click here to create an account</a>

</section>

<c:import url="/footer.jsp" />