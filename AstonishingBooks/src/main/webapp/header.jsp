<!DOCTYPE html>
<html>
<head>
	<meta charset="ISO-8859-1">
	<title>Astonishing Books</title>
	<link rel="stylesheet" href="styles/main.css" type="text/css"/>
</head>
<body>
	<table class="menu-table">
		<tr>
			<td class="logo">Astonishing Books</td>
			<td>
				<form action="" method="post">
					<input type="hidden" name="action" value="search">
					<input type="text" class="search menu-search" name="searchQuery" placeholder="Search for book by title, author, or genre...">
				</form>
			</td>
			<td>
				<form action="" method="post">
					<input type="hidden" name="action" value="viewProfile">
					<input type="submit" value="${loggedIn ? 'Profile' : 'Login' }" class="login-menu menu-font">
				</form>
			</td>
			<td class="cart">
				<form action="" method="post" id="menuCart"> 
					<a href="#" onclick="alert('hello')"> <!-- document.getElementbyId('menuCart').submit() -->
						<input type="hidden" name="action" value="showCart">
						<img src="./images/cart-icon-brown.png" alt="Shopping Cart" class="cart-pic">
					</a>
				</form>
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
			<!--  td class="menu-2 menu-font">
				<form action="Test" method="GET">
					<input type="submit" name="reference" value="Test" class="menu-button menu-font">
				</form>
			</td-->
		</tr>
	</table>