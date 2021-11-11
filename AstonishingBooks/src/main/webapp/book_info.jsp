<%@ page contentType="text/html" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:import url="/header.jsp" />

<section>
	<table class="book-info-table">
		<tr>
			<td>
				<img src="images/test-book-cover.jpg" class="book-info-img"><br><br>
				<form>
					<input type="hidden" name="action" value="addCart">
					<input type="submit" class="book-info-button add-cart-button" value="Add to Cart"><br><br>
				</form>
				<form>
					<input type="hidden" name="action" value="saveList">
					<input type="submit" class="book-info-button save-list-button" value="Save to List"><br><br>
				</form>
			</td>
			<td class="book-info">
				<h1>${book.title}Title here</h1>
				<i>By: ${book.author}Author here</i>
				<hr>
				<br>
				<b>Price: $${book.price}</b>	
				<br>
				<br>
				Summary: 
				<br>
				${book.summary}	fjdkslfdalk		
			</td>
		</tr>
	</table>
</section>

<c:import url="/footer.jsp" />