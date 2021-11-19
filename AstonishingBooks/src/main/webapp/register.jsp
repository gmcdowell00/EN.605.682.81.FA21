<%@ page contentType="text/html" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:import url="/header.jsp" />

<section>

	<h1>Create a New Account</h1>
	
	<form action="AstonishingServlet" method="post">
		<table>
			<tr>
				<td class="label"><label>First Name:</label></td>
				<td class="input"><input type="text" name="firstname" value="${user.firstname}" class="user-input" required></td> 
			</tr>
			<tr>
				<td class="label"><label>Last Name:</label></td>
				<td class="input"><input type="text" name="lastname" value="${user.lastname}" class="user-input" required></td> 
			</tr>
			<tr>
				<td class="label"><label>Email:</label></td>
				<td class="input"><input type="email" name="email" value="${user.email}" class="user-input" required></td> 
			</tr>
			<tr>
				<td class="label"><label>Password:</label></td>
				<td class="input"><input type="password" name="password" value="${user.password}" class="user-input" required></td> 
			</tr>
			<tr>
				<td class="label"><label>Address:</label></td>
				<td class="input"><input type="text" name="address" value="${user.address}" class="user-input" required></td> 
			</tr>
			<tr>
				<td class="label"><label>City:</label></td>
				<td class="input"><input type="text" name="city" value="${user.city}" class="user-input" required></td> 
			</tr>
			<tr>
				<td class="label"><label>State:</label></td>
				<td class="input"><input type="text" name="state" value="${user.state}" class="user-input" required></td> 
			</tr>
			<tr>
				<td class="label"><label>Country:</label></td>
				<td class="input"><input type="text" name="country" value="${user.country}" class="user-input" required></td> 
			</tr>
			<tr>
				<td class="label"><label>Zip Code:</label></td>
				<td class="input"><input type="number" name="zip" value="${user.zip}" class="user-input" required></td> 
			</tr>
		</table>
		<br>
		<br>
		<section class="center"><input type="submit" value="Create Account" class="orange-button submit-button"></section>
		<input type="hidden" name="action" value="newAccount">
	</form>

</section>

<c:import url="/footer.jsp" />