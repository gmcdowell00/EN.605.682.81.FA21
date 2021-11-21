<%@ page contentType="text/html" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:import url="./header.jsp" />
<c:import url="../menu.jsp" />

<section>
	<h1>Edit Book Information</h1>
</section>
<form action="AstonishingServlet" method="post" class="inline">
	<section>
		<table class="book-info-table">
			<tr>
				<td class="align-top">
					<img src="../images/test-book-cover.jpg" class="book-info-img"><br><br>
				</td>
				<td class="book-info">
					<table>
						<tr>
							<td class="label"><label>Title: </label></td>
							<td class="input"><input type="text" name="title" value="${book.name}" class="user-input" required></td> 
						</tr>
						<tr>
							<td class="label"><label>Author: </label></td>
							<td class="input"><input type="text" name="author" value="${book.author}" class="user-input" required></td> 
						</tr>
						<tr>
							<td class="label"><label>Published Date: </label></td>
							<td class="input"><input type="date" name="publishedDate" value="${book.publishedDate}" class="user-input" required></td> 
						</tr>
						<tr>
							<td class="label"><label>Genre: </label></td>
							<td class="input"><input type="text" name="title" value="${book.genre}" class="user-input" required></td> 
						</tr>
						<tr>
							<td class="label"><label>Price: </label></td>
							<td class="input"><input type="number" step="0.01" name="price" value="${book.price}" class="user-input" required></td> 
						</tr>
						<tr>
							<td class="label"><label>Description: </label></td>
							<td class="input"><textarea id="description" name="description" placeholder="${book.description}" class="user-input user-input-big" required></textarea></td> 
						</tr>
					</table>
				</td>
			</tr>
		</table>
	</section>
	<hr>
	<section class="center">
		<input type="hidden" name="bookId" value="${book.id}">
		<input type="hidden" name="prefix" value="admin">
		<input type="submit" class="grey-button submit-button inline" name="action" value="Delete Book">
		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		<input type="submit" class="orange-button submit-button inline" name="action" value="Save Changes">
	</section>
	
</form>


<c:import url="/footer.jsp" />