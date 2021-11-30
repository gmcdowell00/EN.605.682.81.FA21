<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<body>
	<table class="menu-table">
		<tr>
			<td class="logo">Astonishing Books</td>
			<td>
				<form action="AstonishingServlet" method="post">
					<input type="hidden" name="action" value="search">
					<input type="text" class="search menu-search" name="searchQuery" placeholder="Search for book by title, author, or genre...">
				</form>
			</td>
			<td>
				<form action="AstonishingServlet" method="post">
					<input type="hidden" name="action" value="viewProfile">
					<input type="submit" value="${loggedIn ? 'Profile' : 'Login' }" class="login-menu menu-font">
				</form>
			</td>
			<td class="cart">
				<c:if test="${!user.isAdmin}">
					<form action="AstonishingServlet" method="post" id="menuCart"> 
						<input type="hidden" name="action" value="cart">
						<input type="image" class="cart-pic" src="images/cart-icon-brown.png" alt="Cart">
					</form>
				 </c:if>
				 <c:if test="${user.isAdmin}">
					<form action="AstonishingServlet" method="post" id="menuCart"> 
						<input type="hidden" name="action" value="inventory">
						<input type="image" class="cart-pic" src="images/inventory-icon.png" alt="Inventory">
					</form>
				 </c:if>
				 
			</td>
		</tr>
	</table>
	<table class="menu-table">
		<tr>
			<td class="menu-2 menu-font">
				<form action="AstonishingServlet" method="post">
					<input type="hidden" name="action" value="goToHome">
					<input type="submit" value="Home" class="menu-button menu-font">
				</form>
			</td>
			<td class="menu-2 menu-font">
				<form action="" method="post">
					<input type="hidden" name="action" value="goToFiction">
					<input type="submit" value="Fiction" class="menu-button menu-font">
				</form>
			</td>
			<td class="menu-2 menu-font">
				<form action="" method="post">
					<input type="hidden" name="action" value="goToNonFiction">
					<input type="submit" value="Non-Fiction" class="menu-button menu-font">
				</form>
			</td>
			<td class="menu-2 menu-font">
				<form action="" method="post">
					<input type="hidden" name="action" value="goToMagazine">
					<input type="submit" value="Magazine" class="menu-button menu-font">
				</form>
			</td>
			<td class="menu-2 menu-font">
				<form action="" method="post">
					<input type="hidden" name="action" value="goToReference">
					<input type="submit" value="Reference" class="menu-button menu-font">
				</form>
			</td>
		</tr>
	</table>