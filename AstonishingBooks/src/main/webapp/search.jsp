<%@ page contentType="text/html" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:import url="/header.jsp" />

<section>

	<form action="AstonishingServlet" method="post" class="search-bar">
		<input type="hidden" name="action" value="search">
		<input type="text" id="" class="search search-bar" name="searchQuery" value="${searchQuery}" placeholder="Search for book by title, author, or genre...">
	</form>
	<br>
	<table class="book-search-table">
		<c:forEach items="1,2,3,4,5,6,7,8,9,10" var="book">
			<tr class=".book-grid-row">
				<td class="book-search-img">
					<form action="AstonishingServlet" method="post" id="${book}"> 
						<a href="#" onclick="alert('hello')" class=""> <!-- document.getElementbyId('${book}').submit() -->
							<input type="hidden" name="action" value="showBookInfo">
							<input type="hidden" name="bookId" value=""> <!-- book.id -->
							<img class="book-img-grid" alt="" src="images/test-book-cover.jpg">
						</a>
					</form>							
				</td>
				<td class="book-search-info">
					<form action="AstonishingServlet" method="post" class="book-grid-item"id="${book}"> 
						<a href="#" onclick="alert('hello')" class="book-grid-item"> <!-- document.getElementbyId('${book}').submit() -->
							<input type="hidden" name="action" value="showBookInfo">
							<input type="hidden" name="bookId" value=""> <!-- book.id -->
							${book}<br>
							author<br>
						</a>
					</form>
				</td>
			</tr>
		</c:forEach>
	</table>
	
	
</section>

<c:import url="/footer.jsp" />