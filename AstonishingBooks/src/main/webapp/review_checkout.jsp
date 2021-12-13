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
			<td><br>${checkoutUser.firstname} ${checkoutUser.lastname}</td> 
		</tr>
		<tr>
			<td><br>Email:</td>
			<td><br>${checkoutUser.email}</td> 
		</tr>
		<tr>
			<td><br>Address:</td>
			<td><br>${checkoutUser.address}</td> 
		</tr>
		<tr>
			<td><br>Credit Card Type:</td>
			<td><br>${checkoutUser.payment.cardType}</td> 
		</tr>
		<tr>
			<td><br>Name on Card:</td>
			<td><br>${checkoutUser.payment.cardname}</td> 
		</tr>
		<tr>
			<td><br>Card Number:</td>
			<td><br>${checkoutUser.payment.cardNumber}</td> 
		</tr>
		<tr>
			<td><br>Expiration Date:</td>
			<td><br>${checkoutUser.payment.experiationMonth} / ${checkoutUser.payment.experiationYear}</td> 
		</tr>
	</table>	
</section>

<section class="right">
	<h3>Items</h3>
	<br>
	<br>
	<table class="book-grid-table">
		<c:forEach items="${checkoutUser.cart.books}" var="book">
			<tr>
				<td class="book-list-img"><img class="book-img-list" alt="" src="${book.coverImageLink}"></td>
				<td class="book-list-info">${book.name} by ${book.author}</td>
				<td class="book-list-info">$${book.price}</td>
			</tr>
		</c:forEach>
		<tr>
			<td></td>
			<td><br><br><b>Total:</b></td>
			<td><br><br><b>$${checkoutUser.cart.getTotal()}</b></td>
		</tr>
		<tr>
			<td></td>
			<td>
				<br><br><br>
				<form action="AstonishingServlet" method="post">
					<input type="hidden" name="action" value="cart">
					<input type="submit" class="grey-button submit-button" value="Edit Cart">
				</form>
			</td>
			<td></td>
		</tr>
	</table>	
</section>
<hr>
<section class="center">
	<form action="AstonishingServlet" method="post" class="inline">
		<input type="hidden" name="action" value="checkout">
		<input type="submit" value="Edit Information" class="grey-button submit-button"> 
	</form>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	<form action="AstonishingServlet" method="post" class="inline">
		<input type="hidden" name="action" value="orderConfirmation">
		<input type="submit" value="Place Order" class="orange-button submit-button"> 
	</form>
</section>


<c:import url="/footer.jsp" />