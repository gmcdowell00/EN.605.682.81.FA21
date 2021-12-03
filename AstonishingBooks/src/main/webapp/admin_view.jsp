<%@ page contentType="text/html" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:import url="/header.jsp" />
<c:import url="/menu.jsp" />

<section class="title">
	<h1>Admin Page</h1>
</section>

<section class="left">
	<h3>My Information</h3>
	<table>
		<tr>
			<td><br>Name:</td>
			<td><br>${user.firstname} ${user.lastname}</td> 
		</tr>
		<tr>
			<td><br>Email:</td>
			<td><br>${user.email}</td> 
		</tr>
		<tr>
			<td><br>Address:</td>
			<td>
				<br>${user.address}
				<br>${user.city}, ${user.state} ${user.country} ${user.zip}
			</td> 
		</tr>
	</table>
</section>

<section class="right">
	<h3>Actions</h3>
	<br>
	<br>
	<table class="book-grid-table">
		<tr>
			<td>
				<form action="AstonishingServlet" method="post">
					<input type="hidden" name="action" value="inventory">
					<input type="submit" class="orange-button submit-button" value="Manage Inventory">
				</form>
			</td>
		</tr>
		<tr>
			<td>
				<form action="AstonishingServlet" method="post">
					<input type="hidden" name="action" value="manageAdmin">
					<input type="submit" class="orange-button submit-button" value="Manage Administrators">
				</form>
			</td>
		</tr>
	</table>	
</section>


<c:import url="/footer.jsp" />