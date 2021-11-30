<%@ page contentType="text/html" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:import url="/header.jsp" />
<c:import url="/menu.jsp" />

<section>
	<h1>Non-Fiction Books</h1>
	
	<table class="book-grid-table">
		<!--  loop over books here -->
		<tr>
			<c:forEach items="${Books}" var="book" end="3"> <!-- {get list of books that query applies to} -->
				<td>
					<div class="book-grid">
						<form action="AstonishingServlet" method="post" class="book-grid-item" id="${book}"> 
							<a href="" onclick="document.getElementbyId('${book}').submit()" class="book-grid-item"> <!--  -->
								<input type="hidden" name="action" value="showBookInfo">
								<input type="hidden" name="bookId" value="${book.id}">
								<img class="book-img-grid" alt="${book.name} cover" src=".${book.coverImageLink}.jpg"><br>
								${book.name}<br>
								${book.author}<br>
							</a>
						</form>
					</div>
				</td>
			</c:forEach>
		</tr>
		<tr>
			<c:forEach items="${Books}" var="book" begin="4" end="7">
				<td>
					<div class="book-grid">
						<form action="AstonishingServlet" method="post" class="book-grid-item" id="${book}"> 
							<a href="#" onclick="alert('hello')" class="book-grid-item"> <!-- document.getElementbyId('${book}').submit() -->
								<input type="hidden" name="action" value="showBookInfo">
								<input type="hidden" name="bookId" value="${book.id}">
								<img class="book-img-grid" alt="" src=".${book.coverImageLink}.jpg"><br>
								${book.name}<br>
								${book.author}<br>
							</a>
						</form>
					</div>
				</td>
			</c:forEach>
		</tr>
		<tr>
			<c:forEach items="${Books}" var="book" begin="8" end="11">
				<td>
					<div class="book-grid">
						<form action="AstonishingServlet" method="post" class="book-grid-item" id="${book}"> 
							<a href="#" onclick="alert('hello')" class="book-grid-item"> <!-- document.getElementbyId('${book}').submit() -->
								<input type="hidden" name="action" value="showBookInfo">
								<input type="hidden" name="bookId" value="${book.id}">
								<img class="book-img-grid" alt="" src=".${book.coverImageLink}.jpg"><br>
								${book.name}<br>
								${book.author}<br>
							</a>
						</form>
					</div>
				</td>
			</c:forEach>
		</tr>
	</table>
</section>

<c:import url="/footer.jsp" />