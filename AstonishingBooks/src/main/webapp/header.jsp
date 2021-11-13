<!DOCTYPE html>
<html>
<head>
	<meta charset="ISO-8859-1">
	<title>Astonishing Books</title>
	<link rel="stylesheet" href="styles/main.css" type="text/css"/>
</head>
<body>
	<table class="menu-table-1">
		<tr>
			<td class="logo">Astonishing Books</td>
			<td class="menu-1">
				<form action="" method="post">
					<input type="hidden" name="action" value="search">
					<input type="text" class="search menu-search" name="searchQuery" placeholder="Search for book by title, author, or genre...">
				</form>
			</td>
			<td class="menu-1">
				<form action="" method="post">
					<input type="hidden" name="action" value="viewProfile">
					<input type="submit" value="${loggedIn ? 'Profile' : 'Login' }" class="login menu-font">
				</form>
			</td>
			<td class="cart menu-1">
				<form action="" method="post" id="menuCart"> 
					<a href="#" onclick="alert('hello')"> <!-- document.getElementbyId('menuCart').submit() -->
						<input type="hidden" name="action" value="showCart">
						<img src="./images/cart-icon-brown.png" alt="Shopping Cart" class="cart-pic">
					</a>
				</form>
			</td>
		</tr>
	</table>
	<table class="menu-table-2">
		<tr>
			<td class="menu-2 menu-font">
				<form action="" method="post">
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