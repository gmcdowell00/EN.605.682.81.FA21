<%@ page contentType="text/html" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:import url="/header.jsp" />
<c:import url="/menu.jsp" />

<section class="title">
	<h1>Review Information</h1>
</section>
<section class="left">
	<h3>Shipping/Billing Information</h3>
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
			<td><br>${user.address}</td> 
		</tr>
		<tr>
			<td><br>Credit Card Type:</td>
			<td><br>${user.payment.cardType}</td> 
		</tr>
		<tr>
			<td><br>Name on Card:</td>
			<td><br>${user.payment.cardname}</td> 
		</tr>
		<tr>
			<td><br>Card Number:</td>
			<td><br>${user.payment.cardNumber}</td> 
		</tr>
		<tr>
			<td><br>Expiration Date:</td>
			<td><br>${user.payment.experiationMonth} / ${user.payment.experiationYear}</td> 
		</tr>
	</table>	
</section>

<section class="right">
	<h3>Items</h3>
	<br>
	<br>
	<table class="book-grid-table">
		<c:forEach items="${user.cart.books}" var="book">
			<tr>
				<td class="book-list-img"><img class="book-img-list" alt="" src=".${book.coverImageLink}.jpg"></td>
				<td class="book-list-info">${book.name} by ${book.author}</td>
				<td class="book-list-info">$${book.price}</td>
				<td class="book-list-info">
					<form action="AstonishingServlet" method="post">
						<input type="hidden" name="action" value="removeFromCart">
						<input type="hidden" name="removeBookFromCart" value="bookid">
						<input type="image" class="delete-pic" src="images/delete.png" alt="Delete">
					</form>
				</td>
			</tr>
		</c:forEach>
		<tr>
			<td></td>
			<td><br><br><b>Total:</b></td>
			<td><br><br><b>$${user.cart.getTotal()}</b></td>
		</tr>
	</table>	
</section>
<hr>
<section class="center">
	<form action="AstonishingServlet" method="post" class="inline">
		<input type="hidden" name="action" value="reviewInfo">
		<input type="submit" value="Edit Information" class="grey-button submit-button"> 
	</form>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	<form action="AstonishingServlet" method="post" class="inline">
		<input type="hidden" name="action" value="orderConfirmation">
		<input type="submit" value="Place Order" class="orange-button submit-button"> 
	</form>
</section>


<c:import url="/footer.jsp" />