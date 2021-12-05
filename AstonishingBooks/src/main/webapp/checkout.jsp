<%@ page contentType="text/html" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:import url="/header.jsp" />
<c:import url="/menu.jsp" />

<section>

	<h1>Checkout</h1>
	
	<form action="AstonishingServlet" method="post" class="register">
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
				<td class="input"><input type="email" name="email" value="${user.id != '61aa68b78a5d542ac9f844b9' ? user.email : ''}" class="user-input" required></td> 
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
			<tr>
				<td></td>
				<td><hr><br></td>
			</tr>
			<tr>
				<td class="label"><label>Credit Card Type:</label></td>
				<td class="input">
					<select name="creditCardType" class="user-input">
						<option value="MASTER" required>MasterCard</option>
						<option value="VISA">Visa</option>
						<option value="DISCOVERY">Discovery</option>
						<option value="AMEX">American Express</option>
						<option value="DINERS">Diners</option>
					</select>
				</td> 
			</tr>
			<tr>
				<td class="label"><label>Name on Card:</label></td>
				<td class="input"><input type="text" name="cardName" value="${user.payment.cardname}" class="user-input" required></td> 
			</tr>
			<tr>
				<td class="label"><label>Card Number:</label></td>
				<td class="input"><input type="text" name="cardNumber" value="${user.payment.cardNumber}" class="user-input" required></td> 
			</tr>
			<tr>
				<td class="label"><label>Expiration Date:</label></td>
				<td class="input">
					<input type="number" name="expireMonth" value="${user.payment.experiationMonth}" class="user-input-short" required>
					/
					<input type="number" name="expireYear" value="${user.payment.experiationYear}" class="user-input-short" required>
				</td> 
			</tr>
		</table>
		<br>
		<hr>
		<br>
		<c:if test="${not empty message}">
			<div class="error-text">Error: ${message}</div>
		</c:if>
		<section class="center"><input type="submit" value="Review Information" class="orange-button submit-button"></section>
		<input type="hidden" name="action" value="reviewInfo">
	</form>

</section>

<c:import url="/footer.jsp" />