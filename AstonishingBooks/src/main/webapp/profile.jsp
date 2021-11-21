<%@ page contentType="text/html" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:import url="/header.jsp" />
<c:import url="/menu.jsp" />

<section class="title">
	<h1>My Profile</h1>
</section>

<section class="left">
	<h3>My Information</h3>
	<table>
		<tr>
			<td><br>Name:</td>
			<td><br>${user.firstname} ${user.lastname }</td> 
		</tr>
		<tr>
			<td><br>Email:</td>
			<td><br>${user.email}</td> 
		</tr>
		<tr>
			<td><br>Address:</td>
			<td><br>${user.address}</td> 
		</tr>
	</table>
</section>

<section class="right">
	<h3>My List</h3>
	<br>
	<br>
	<table class="book-grid-table">
		<c:forEach items="1,2,3,4,5" var="book">
			<tr>
				<td class="book-list-img"><img class="book-img-list" alt="" src="images/test-book-cover.jpg"></td>
				<td class="book-list-info">book title by book author</td>
				<td class="book-list-info">
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