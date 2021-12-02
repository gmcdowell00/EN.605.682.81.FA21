<%@ page contentType="text/html" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:import url="/header.jsp" />
<c:import url="/menu.jsp" />

<section>
	<h1>My Cart</h1>
	<br>
	<br>
	<table class="book-grid-table center">
		<c:forEach items="${user.cart.books}" var="book">
			<tr>
				<td class="book-list-img"><img class="book-img-list" alt="" src=".${book.coverImageLink}.jpg"></td>
				<td class="book-list-info">${book.name} by ${book.author}</td>
				<td class="book-list-info">$${book.price}</td>
				<td class="book-list-info">
					<form action="" method="post">
						<input type="hidden" name="action" value="removeFromList">
						<input type="hidden" name="removeBookFromList" value="bookid">
						<input type="image" class="delete-pic" src="images/delete.png" alt="Delete">
					</form>
				</td>
			</tr>
		</c:forEach>
		<tr>
			<td></td>
			<td></td>
			<td><br><br><b>Total: $${totalCost}</b></td>
			<td></td>
		</tr>
	</table>
	<hr>
	<form action="AstonishingServlet" method="post" class="center">
		<input type="hidden" name="action" value="checkout">
		<input type="submit" value="Checkout" class="submit-button orange-button">
	</form>	
</section>


<c:import url="/footer.jsp" />