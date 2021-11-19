<%@ page contentType="text/html" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:import url="/header.jsp" />

<section class="left">
	<h3>My Information</h3>
	<br>
	<br>
	Name: ${user.firstname} ${user.lastname}
	<br>
	<br>
	Email: ${user.email}
	<br>
	<br>
	Address: 
</section>

<section class="right">
	<h3>My List</h3>
	<br>
	<br>
	<table class="book-grid-table">
		<c:forEach items="1,2,3,4,5" var="book">
			<tr>
				<td>book cover</td>
				<td>book title by book author</td>
				<td>
					<form action="" method="post">
						<input type="hidden" name="action" value="removeFromList">
						<input type="hidden" name="removeBookFromList" value="bookid">
						<input type="image" class="delete-pic" src="images/delete.png" alt="Delete">
					</form>
				</td>
			</tr>
		</c:forEach>
	</table>
	
</section>


<c:import url="/footer.jsp" />