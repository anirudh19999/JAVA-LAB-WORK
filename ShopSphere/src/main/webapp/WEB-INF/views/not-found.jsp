<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:set var="pageTitle" value="Page not found" scope="request"/>
<%@ include file="partials/header.jspf" %>
<section class="content-page empty-state"><h1>That page wandered off.</h1><p>Try the collection instead.</p><a class="button button-dark" href="${pageContext.request.contextPath}/">Back to shop</a></section>
<%@ include file="partials/footer.jspf" %>
