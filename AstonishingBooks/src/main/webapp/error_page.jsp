<%@ page contentType="text/html" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:import url="/header.jsp" />
<c:import url="/menu.jsp" />

<section class="center">

	<h1>Uh-Oh!</h1>
	<br>
	Sorry, something went wrong. Use the navigation bar to navigate to another page.
	<br>
	<br>
	<br>
	<br>
	<h3>Details</h3>
	<p>Type: ${pageContext.exception["class"]}</p>
	<p>Message: ${pageContext.exception.message}</p>
	
</section>

<c:import url="/footer.jsp" />