<%@ page contentType="text/html" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:import url="/header.jsp" />
<c:import url="/menu.jsp" />

<section>
	<h1>Manage Inventory</h1>
	<table class="book-grid-table center-no-padding">
		<tr>
			<td class="add-book">
				<form action="AstonishingServlet" method="post">
					<input type="hidden" name="action" value="addBook">
					<input type="submit" value="Add Book" class="grey-button submit-button ">
				</form>		
			</td>
		</tr>
	</table>
	<table class="book-grid-table center">
		<c:forEach items="${Books}" var="book">
			<tr>
				<td class="book-list-img"><img class="book-img-list" alt="cover" src="${book.coverImageLink}"></td>
				<td class="book-list-info">${book.name} by ${book.author}</td>
				<td class="book-list-info">$${book.price}</td>
				<td class="book-list-info">
					<form action="AstonishingServlet" method="post">
						<input type="hidden" name="action" value="editBook">
						<input type="hidden" name="bookId" value="${book.id}">
						<input type="image" class="edit-pic" src="images/edit-icon.png" alt="Edit">
					</form>
				</td>
				<td class="book-list-info">
					<form action="AstonishingServlet" method="post">
						<input type="hidden" name="action" value="deleteBook">
						<input type="hidden" name="bookId" value="${book.id}">
						<input type="image" class="delete-pic" src="images/delete.png" alt="Delete">
					</form>
				</td>
			</tr>
		</c:forEach>
	</table>
	<hr>
</section>


<c:import url="/footer.jsp" />