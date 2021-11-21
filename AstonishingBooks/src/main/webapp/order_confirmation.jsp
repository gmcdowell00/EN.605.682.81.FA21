<%@ page contentType="text/html" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:import url="/header.jsp" />

<section class="center">

	<h1>Order Successful!</h1>
	<br>
	<br> Thank you <b>name${user.firstname} ${user.lastname}</b> for ordering at Astonishing Books!
	<br> Your total was <b>$???</b>.
	<br>
	<br>
	<br> An email confirmation will be sent to <b>email${user.email}</b> shortly.
	
</section>

<c:import url="/footer.jsp" />