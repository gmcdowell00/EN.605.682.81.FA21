<%@ page contentType="text/html" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:import url="/header.jsp" />
<c:import url="/menu.jsp" />

<section class="center">

	<h1>Order Successful!</h1>
	<br>
	<br> Thank you <b>${checkoutUser.firstname} ${checkoutUser.lastname}</b> for ordering at Astonishing Books!
	<br> Your total was <b>$${checkoutUser.cart.getTotal()}</b>.
	<br>
	<br>
	<br> An email confirmation has been sent to <b>${checkoutUser.email}</b>.
	
</section>

<c:import url="/footer.jsp" />