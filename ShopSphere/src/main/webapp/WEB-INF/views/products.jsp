<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<%@ taglib prefix="ss" uri="https://shopsphere.example/tags" %>
<c:set var="pageTitle" value="Everyday goods" scope="request"/>
<%@ include file="partials/header.jspf" %>
<section class="hero">
  <div class="hero-copy"><span class="eyebrow">THE SHOPSPHERE EDIT</span><h1>Good things for<br>everyday living.</h1><p>Useful design, considered details, and a little joy in the things you use daily.</p><a class="button button-light" href="#collection">Explore the collection <span>↘</span></a></div>
  <div class="hero-art"><div class="hero-orbit orbit-one"></div><div class="hero-orbit orbit-two"></div><div class="hero-sun"></div><div class="hero-caption">Objects with<br>a point of view.</div><div class="hero-number">01 <span>— 06</span></div></div>
</section>
<section class="collection" id="collection">
  <div class="section-heading"><div><span class="eyebrow">CURATED FOR YOU</span><h2>Find your next favorite.</h2></div><span class="result-count"><c:out value="${products.size()}"/> pieces</span></div>
  <c:if test="${not empty query}"><p class="search-note">Showing results for “<c:out value="${query}"/>” <a href="${pageContext.request.contextPath}/products">Clear</a></p></c:if>
  <div class="product-grid">
    <c:forEach var="product" items="${products}">
      <article class="product-card">
        <a class="product-image" href="#product-${product.id}"><img src="<c:out value='${product.imageUrl}'/>" alt="<c:out value='${product.name}'/>" loading="lazy"><c:if test="${product.discountPercent gt 0}"><span class="badge">SAVE <fmt:formatNumber value="${product.discountPercent}" maxFractionDigits="0"/>%</span></c:if></a>
        <div class="product-meta"><div><span class="product-brand"><c:out value="${product.brand}"/> · <ss:stockStatus stock="${product.stock}"/></span><h3><c:out value="${product.name}"/></h3></div><strong>₹<fmt:formatNumber value="${product.salePrice}" minFractionDigits="0" maxFractionDigits="2"/></strong></div>
        <p class="product-description"><c:out value="${product.description}"/></p>
        <form action="${pageContext.request.contextPath}/cart" method="post"><input type="hidden" name="action" value="add"><input type="hidden" name="productId" value="${product.id}"><input type="hidden" name="quantity" value="1"><button class="add-button" type="submit" <c:if test="${product.stock lt 1}">disabled</c:if>><c:choose><c:when test="${product.stock lt 1}">Sold out</c:when><c:otherwise>Add to bag <span>+</span></c:otherwise></c:choose></button></form>
      </article>
    </c:forEach>
  </div>
  <c:if test="${empty products}"><div class="empty-state"><h3>No pieces found</h3><p>Try another search, or browse the full collection.</p><a href="${pageContext.request.contextPath}/products">View all products</a></div></c:if>
</section>
<%@ include file="partials/footer.jspf" %>
