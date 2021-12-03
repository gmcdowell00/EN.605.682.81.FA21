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
	<h3>My List</h3>
	<br>
	<br>
	<c:if test="${empty user.wishlist}">
		<i>No items saved to list.</i>
	</c:if>
	<c:if test="${not empty user.wishlist}">
		<table class="book-grid-table">
			<c:forEach items="${user.wishlist}" var="book">
				<tr>
					<td class="book-list-img"><img class="book-img-list" alt="" src=".${book.coverImageLink}.jpg"></td>
					<td class="book-list-info">${book.name} by ${book.author}</td>
					<td class="book-list-info">
						<form action="" method="post">
							<input type="hidden" name="action" value="removeFromList">
							<input type="hidden" name="removeBookFromList" value="${book.id}">
							<input type="image" class="delete-pic" src="images/delete.png" alt="Delete">
						</form>
					</td>
				</tr>
			</c:forEach>
		</table>	
	</c:if>
</section>


<c:import url="/footer.jsp" />