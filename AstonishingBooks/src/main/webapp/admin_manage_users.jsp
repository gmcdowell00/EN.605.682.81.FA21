<%@ page contentType="text/html" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:import url="/header.jsp" />
<c:import url="/menu.jsp" />
<section>
	<h1>Manage Users</h1>
	<table class="book-grid-table center">
			<tr>
				<th>Name</th>
				<th>Email</th>
				<th>Admin Privileges</th>
			</tr>
			<c:forEach items="${users}" var="user">
				<tr>
					<td class="book-list-info">${user.firstname} ${user.lastname}</td>
					<td class="book-list-info">${user.email}</td>
					<td class="book-list-info">
						<form action="AstonishingServlet" method="post">
							<input type="hidden" name="action" value="giveAdminPrivileges" >
							<input type="hidden" name ="email" value="${user.email}" >
							<input type="hidden" name ="isAdmin" value ="${user.isAdmin}">
							<input type="checkbox" value="{user.isAdmin}" ${user.isAdmin ? 'checked' : ''} onchange="submit()">
						</form>						
					</td>
				</tr>
			</c:forEach>
			<tr>
				<td></td>
				<td><br><br><br><input type="submit" value="Save Changes" class="orange-button submit-button"></td>
				<td></td>
			</tr>
		</table>
	
	<hr>
</section>


<c:import url="/footer.jsp" />