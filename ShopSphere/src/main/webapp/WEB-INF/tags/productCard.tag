<%@ tag body-content="empty" %>
<%@ attribute name="product" required="true" type="com.shopsphere.model.Product" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<article class="product-card"><div class="product-brand"><c:out value="${product.brand}"/></div><h3><c:out value="${product.name}"/></h3><p><c:out value="${product.description}"/></p><strong>₹${product.salePrice}</strong></article>
