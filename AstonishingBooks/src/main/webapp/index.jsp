<%@ page contentType="text/html" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:import url="/header.jsp" />

<section>
	<h1>New Books</h1>
	
	<table class="book-grid-table">
		<!--  loop over books here -->
		<tr>
			<c:forEach items="1,2,3,4,5,6,7,8,9,10" var="book" end="3">
				<td>
					<div class="book-grid">
						<a href="" class="book-grid-item">
						<img class="book-img-grid" alt="" src="images/test-book-cover.jpg"><br>
						${book}<br>
						author<br>
						</a>
					</div>
				</td>
			</c:forEach>
		</tr>
		<tr>
			<c:forEach items="1,2,3,4,5,6,7,8,9,10" var="book" begin="4" end="7">
				<td>
					<div class="book-grid">
						<a href="" class="book-grid-item">
						<img class="book-img-grid" alt="" src="images/test-book-cover.jpg"><br>
						${book}<br>
						author<br>
						</a>
					</div>
				</td>
			</c:forEach>
		</tr>
		<tr>
			<c:forEach items="1,2,3,4,5,6,7,8,9,10,11,12" var="book" begin="8" end="11">
				<td>
					<div class="book-grid">
						<a href="" class="book-grid-item">
						<img class="book-img-grid" alt="" src="images/test-book-cover.jpg"><br>
						${book}<br>
						author<br>
						</a>
					</div>
				</td>
			</c:forEach>
		</tr>
	</table>
</section>


<c:import url="/footer.jsp" />