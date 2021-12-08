<%@ page contentType="text/html" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:import url="/header.jsp" />
<c:import url="/menu.jsp" />

<section>
	<form action="AstonishingServlet" method="post" class="search-bar">
		<input type="hidden" name="action" value="search">
		<input type="text" id="" class="search search-bar" name="searchQuery" value="${searchQuery}" placeholder="Search for book by title, author, or genre...">
	</form>
	<br>
	<c:if test="${empty Books}">
		<i>No results were found.</i>
	</c:if>
	<c:if test="${not empty Books}">
		<table class="book-search-table">
			<c:forEach items="${Books}" var="book">
				<tr class="book-grid-row">
					<td class="book-search-img">
						<form action="AstonishingServlet" method="post" id="${book}"> 
							<input type="hidden" name="action" value="showBookInfo">
							<input type="hidden" name="bookId" value="${book.id}">
							<input type="image" class="book-img-grid" alt="" src="${book.coverImageLink}">
						</form>							
					</td>
					<td class="book-search-info">
						<form action="AstonishingServlet" method="post" class="book-grid-item" id="${book.id}"> 
								<input type="hidden" name="action" value="showBookInfo">
								<input type="hidden" name="bookId" value="${book.id}">
								${book.name}<br>
								${book.author}<br>
						</form>
					</td>
				</tr>
			</c:forEach>
		</table>
	</c:if>	
</section>

<c:import url="/footer.jsp" />