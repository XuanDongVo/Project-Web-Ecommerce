<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html lang="en">

<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Document</title>
<link rel="stylesheet" href="css/Navbar_procate.css">
<link rel="stylesheet" href="css/Hero_procate.css">
<link rel="stylesheet" href="css/SearchBar.css">
<link rel="stylesheet" href="css/Popular.css">
<link rel="stylesheet" href="css/Item.css">
<link rel="stylesheet" href="css/NewCollections.css">
<link rel="stylesheet" href="css/NewLetter.css">
<link rel="stylesheet" href="css/Footer.css">
<link rel="stylesheet" href="css/Cate.css">
<link rel="stylesheet" href="css/ProductDisplay.css">


<!-- Bootstrap CSS -->
<link rel="stylesheet" href="adding/bootstrap/boostrap.min.css">
<!-- <script src="adding/jquery/jquery-3.4.1.min.js"></script>
    <script src="adding/poper/poper.min.js"></script>
    <script src="adding/bootstrap/boostrap.min.js"></script>  -->

<script src="adding/bootstrap/bootstrap.bundle.min.js"></script>
<script src="js/navbar.js"></script>
</head>

<body>
	<jsp:include page="header.jsp"></jsp:include>

	<c:set value="${productResponse }" var="product" />

	<div class="cate-container container">
		<div class="cate-left">
			<ul class="cate-left-breadcrumb" style="margin-left: 20px;">
				<li><span class="ant-breadcrumb-link"><a href="home"
						draggable="false" aria-label="Trang chủ"
						class="ellipsis-1 text-sm  inline breadcrumb-link">TRANG CHỦ</a></span></li>
				<li class="ant-breadcrumb-separator" aria-hidden="true"><span
					class="items-center inline h-full">></span></li>
				<li><span class="ant-breadcrumb-link"><a
						aria-label="Thời trang"
						class="ellipsis-1 text-sm  inline breadcrumb-link">${product.name.toUpperCase()}</a></span></li>

			</ul>
		</div>
	</div>

	<div class="container mt-4">

		<div class="row">
			<!-- Left Section: Image Gallery -->
			<div class="col-md-4">
				<div class="main-image mb-3">
					<img id="mainImage" src="${product.productSkus[0].img}"
						alt="${product.name}" class="img-fluid" style="width: 100%;">
				</div>
			</div>

			<!-- Right Section: Product Info -->
			<div class="col-md-8">
				<h2 id="productTitle">${product.name}</h2>
				<p>
					SKU: <span id="productSKU">10S24TSSA003_001</span>
				</p>
				<p>
					<span class="productPrice"><fmt:formatNumber
							value="${product.price}" pattern="#,###" /> đ</span>
				</p>
				<p>
					<span class="text-muted">★ 99 sản phẩm đã bán</span>
				</p>

				<!-- Color Options -->
				<h5 id="color-name">${product.productSkus[0].color.toUpperCase()}</h5>

				<div class="items-center gap-1 text-xs text-muted mb-3">
					<!-- <span>Chỉ còn 8 sản phẩm </span> -->
				</div>

				<div class="color-options d-flex mt-3 mb-3">
					<c:forEach items="${product.productSkus}" var="sku">
						<button class="btn btn-outline-primary color-btn"
							style="background-color: ${sku.color};"
							onclick="selectImage('${sku.productColorImgId}', '${sku.img}', '${fn:escapeXml(sku.sizeAndStock)}', '${product.typeProduct}' , '${sku.color}')" /></button>
					</c:forEach>
				</div>
				
				<div class="size-message">
				</div>

				<!-- Size Selector -->
				<div class="size-options mt-3 mb-3" id="size-options"
					style="display: block; padding: 0">
					<c:choose>
						<c:when test="${product.typeProduct == 'áo'}">
							<c:set var="sizeString" value="s,m,l,xl,xxl" />
						</c:when>
						<c:when test="${product.typeProduct == 'quần'}">
							<c:set var="sizeString" value="28,29,30,31,32" />
						</c:when>
					</c:choose>

					<c:set var="sizeList" value="${fn:split(sizeString, ',')}" />
					<div class="size-buttons d-flex">
						<c:forEach items="${sizeList}" var="size">
							<c:set var="stock"
								value="${product.productSkus[0].sizeAndStock[size]}" />
							<c:choose>
								<c:when test="${stock != null && stock > 0}">
									<button class="btn btn-outline-primary size-btn"
										onclick="chooseSize(event, '${size}', '${stock}')">
										${size.toUpperCase()}</button>
								</c:when>
								<c:otherwise>

									<button
										class="btn btn-outline-primary size-btn size-unavailable">
										${size.toUpperCase()}</button>
								</c:otherwise>
							</c:choose>
						</c:forEach>
					</div>
				</div>

				<!-- Quantity and Add to Cart -->
				<div class="row align-items-center mt-3 mb-3">
					<div class="col-2">
						<div class="input-group" style="width: 90%">
							<button class="btn btn-outline-secondary" id="decreaseQuantity">-</button>
							<input type="number" id="quantity"
								class="form-control text-center quantity-input" value="1"
								min="1" readonly="readonly">
							<button class="btn btn-outline-secondary" id="increaseQuantity">+</button>
						</div>
					</div>
					<div class="col-10">
						<button class="btn btn-primary w-100 btn-cart"
							data-color-img-id="${product.productSkus[0].productColorImgId}"
							data-stock="" data-selected-size="" id="addToCart"
							onclick="addToCart(event)">Thêm vào giỏ hàng</button>
					</div>
				</div>

			</div>
		</div>
	</div>



	<!-- Out of Stock Modal -->
	<div class="modal fade" id="outOfStockModal" tabindex="-1"
		aria-labelledby="outOfStockModalLabel" aria-hidden="true">
		<div class="modal-dialog modal-dialog-centered">
			<div class="modal-content">
				<div class="modal-header">
					<h5 class="modal-title" id="outOfStockModalLabel">Sản phẩm
						không đủ hàng</h5>
					<button type="button" class="btn-close" data-bs-dismiss="modal"
						aria-label="Close"></button>
				</div>
				<div class="modal-body">
					<p></p>
					<p>Vui lòng điều chỉnh số lượng</p>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-secondary"
						data-bs-dismiss="modal">Đóng</button>
				</div>
			</div>
		</div>
	</div>



	<!-- Footer -->
	<div class="footer">
		<div class="footer-logo">
			<img src="footer_logo.png" alt="" />
			<p>SHOPPEE</p>
		</div>
		<ul class="footer-links">
			<li>Company</li>
			<li>Products</li>
			<li>Offices</li>
			<li>About</li>
			<li>Contact</li>
		</ul>
		<div class="footer-social-icon">
			<div class="footer-icons-container">
				<img src="instagram.png" alt="" />
			</div>
			<div class="footer-icons-container">
				<img src="pinterest.png" alt="" />
			</div>
			<div class="footer-icons-container">
				<img src="whatsapp.png" alt="" />
			</div>
		</div>
		<div class="footer-copyright">
			<hr />
			<p>© 2023 by Shoppee. Proudly created with Wix.com</p>
		</div>
	</div>
</body>

<script type="text/javascript" src="js/product_detail.js"></script>

</html>
