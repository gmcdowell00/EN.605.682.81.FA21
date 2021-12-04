<%@ page contentType="text/html" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:import url="/header.jsp" />
<c:import url="/menu.jsp" />

<section>
	<table class="book-info-table">
		<tr>
			<td>
				<img src=".${book.coverImageLink}.jpg" class="book-info-img"><br><br>
				<c:if test="${(not empty user) && (user.isAdmin)}">
					<form action="AstonishingServlet" method="post">
						<input type="hidden" name="action" value="deleteBook">
						<input type="submit" class="grey-button book-info-button" value="Delete Book">
					</form>
					<br>
					<form action="AstonishingServlet" method="post">
						<input type="hidden" name="action" value="editBook">
						<input type="submit" class="book-info-button orange-button" value="Edit Book Information">
					</form>
				</c:if>
				<c:if test="${(empty user) || (user.id != '61aa68b78a5d542ac9f844b9') || (not user.isAdmin)}">
					<form action="AstonishingServlet" method="post">
						<input type="hidden" name="action" value="addCart">
						<input type="submit" class="book-info-button orange-button" value="Add to Cart"><br><br>
					</form>
					<c:if test="${user.id != '61aa68b78a5d542ac9f844b9'}">
						<form action="AstonishingServlet" method="post">
							<input type="hidden" name="action" value="saveList">
							<input type="submit" class="book-info-button grey-button" value="Save to List"><br><br>
						</form>
					</c:if>
				</c:if>
			</td>
			<td class="book-info">
				<h1>${book.name}</h1>
				<i>By: ${book.author}</i>
				<hr>
				<br>
				<b>Price: $${book.price}</b>	
				<br>
				<br>
				Summary: 
				<br>
				<br>
				${book.description}		
			</td>
		</tr>
	</table>
</section>

<c:import url="/footer.jsp" />