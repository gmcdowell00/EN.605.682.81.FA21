<%@ page contentType="text/html" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:import url="/header.jsp" />
<c:import url="/menu.jsp" />

<form id="homepage" action="AstonishingServlet" method="post">
	<input type="hidden" name="action" value="home">
</form>

<!-- <script type="text/javascript">
    document.getElementById("homepage").submit();
</script>   -->

<c:import url="/footer.jsp" />