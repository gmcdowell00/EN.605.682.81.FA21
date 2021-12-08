<%@ page contentType="text/html" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:import url="./header.jsp" />
<c:import url="./menu.jsp" />

<!-- Ajax to Java File Upload Logic -->
  <script>
  async function uploadFile() {
    let formData = new FormData(); 
    formData.append("file", coverFile.files[0]);
    await fetch('FileUploadServlet', {
      method: "POST", 
      body: formData
    }); 
    alert('The file upload with Ajax and Java was a success!');
  }
  </script>

<section>
	<h1>Edit Book Information</h1>
</section>
<form action="AstonishingServlet" method="post" class="inline">
	<section>
		<table class="book-info-table">
			<tr>
				<td class="align-top">
					<c:if test="${not empty book.coverImageLink}"> 
						<img src=".${book.coverImageLink}.jpg" class="book-info-img"><br><br>
					</c:if>
					<c:if test="${empty book.coverImageLink}"> 
						<img src="./images/no-image-book.jpg" class="book-info-img"><br><br>
					</c:if>
					<input type="text" name="coverImageLink" value="${book.coverImageLink}" id="coverFile" >
					<input type="button" value="Upload" onclick="uploadFile()">
				</td>
				<td class="book-info">
					<table>
						<tr>
							<td class="label"><label>Title: </label></td>
							<td class="input"><input type="text" name="name" value="${book.name}" class="user-input" required></td> 
						</tr>
						<tr>
							<td class="label"><label>Author: </label></td>
							<td class="input"><input type="text" name="author" value="${book.author}" class="user-input" required></td> 
						</tr>
						<tr>
							<td class="label"><label>Published Date: </label></td>
							<td class="input">
							<!-- 	<input type="date" name="publishedDate" value="${book.getPublishedDateString()}" class="user-input" required>  -->
								<input type="text" pattern="\d{2}\/\d{2}\/\d{4}" class="user-input" value="${book.getPublishedDateString()}" name="publishedDate" title="Please enter the date as mm/dd/yyyy" required/>
							</td> 
						</tr>
						<tr>
							<td class="label"><label>Genre: </label></td>
							<td class="input"><input type="text" name="genre" value="${book.genre}" class="user-input" required></td> 
						</tr>
						<tr>
							<td class="label"><label>Price: </label></td>
							<td class="input"><input type="text" step="0.01" name="price" value="${book.price}" class="user-input" required></td> 
						</tr>
						<tr>
							<td class="label"><label>Description: </label></td>
							<td class="input"><textarea id="description" name="description"  class="user-input user-input-big" required>${book.description}</textarea></td> 
						</tr>
					</table>
				</td>
			</tr>
		</table>
	</section>
	<hr>
	<section class="center">
		<input type="hidden" name="bookId" value="${book.id}">
		<input type="hidden" name="book" value="${book}">
		<input type="hidden" name="prefix" value="admin">
		<input type="hidden" name="action" value="saveBook">
		<input type="submit" class="orange-button submit-button inline" value="Save Changes">
	</section>
	</form>

<c:import url="/footer.jsp" />