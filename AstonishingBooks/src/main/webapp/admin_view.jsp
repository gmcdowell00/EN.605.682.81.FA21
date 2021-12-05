<%@ page contentType="text/html" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:import url="/header.jsp" />
<c:import url="/menu.jsp" />

<section class="title">
	<h1>Admin Page</h1>
</section>

<section class="center">
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
			<td>
				<form action="AstonishingServlet" method="post">
					<input type="hidden" name="action" value="manageUsers">
					<input type="submit" class="orange-button submit-button" value="Manage Administrators">
				</form>
			</td>
		</tr>
	</table>	
</section>


<c:import url="/footer.jsp" />