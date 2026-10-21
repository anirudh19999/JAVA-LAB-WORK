<%@ tag body-content="empty" %>
<%@ attribute name="value" required="true" type="java.lang.Integer" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<span class="product-brand" aria-label="Rating ${value} out of 5"><c:forEach begin="1" end="${value}">★</c:forEach></span>
