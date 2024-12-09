<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Document</title>
<link rel="stylesheet" href="css/Navbar_procate.css">
<link rel="stylesheet" href="css/Hero_procate.css">
<!-- <link rel="stylesheet" href="css/SearchBar.css"> -->
<!-- <link rel="stylesheet" href="css/Popular.css"> -->
<!-- <link rel="stylesheet" href="css/Item.css"> -->
<link rel="stylesheet" href="css/NewCollections.css">
<!-- <link rel="stylesheet" href="css/NewLetter.css"> -->
<link rel="stylesheet" href="css/Footer.css">
<link rel="stylesheet" href="css/Cate.css">


<!-- Bootstrap CSS -->
<link rel="stylesheet" href="adding/bootstrap/boostrap.min.css">

<script src="adding/bootstrap/bootstrap.bundle.min.js"></script>

<script src="js/cate_filter.js"></script>
<script src="js/product_collection.js"></script>
<script src="js/navbar.js"></script>
</head>

<body>
	<jsp:include page="header.jsp"></jsp:include>

	<div class="cate-container container" style="height: 60px">
		<div class="cate-left">
			<ul class="cate-left-breadcrumb" style="margin-left: 20px;">
				<li><span class="ant-breadcrumb-link"><a href="home"
						draggable="false" aria-label="Trang chủ"
						class="ellipsis-1 text-sm  inline breadcrumb-link">TRANG CHỦ</a></span></li>
				<li class="ant-breadcrumb-separator" aria-hidden="true"><span
					class="items-center inline h-full">></span></li>
				<li><span class="ant-breadcrumb-link"><a
						aria-label="Thời trang"
						class="ellipsis-1 text-sm  inline breadcrumb-link">THỜI TRANG</a></span></li>

				<!-- Sử dụng JSTL c:forEach để tạo các mục breadcrumb từ danh sách `beadcrumb` -->
				<c:forEach items="${beadcrumb}" var="breadcrumbItem">
					<li class="ant-breadcrumb-separator" aria-hidden="true"><span
						class="items-center inline h-full">></span></li>
					<li><span class="ant-breadcrumb-link"><a
							draggable="false"
							class="ellipsis-1 text-sm  inline breadcrumb-link">${breadcrumbItem.toUpperCase()}</a></span></li>
				</c:forEach>
			</ul>

		</div>
		<div class="cate-right">
			<i class="filter-icon" onclick="toggleFilter()"><img
				src="img/filter.png" alt="" style="height: 20px; width: 20px;">
				<!-- Filter Panel -->
				<div class="filter-panel min-w-[400px] " id="filterPanel"
					onclick="event.stopPropagation()">
					<h4 class="font-style-normal"
						style="font-style: normal; color: black;">Bộ lọc</h4>
					<div class="filter-tabs">
						<button class="tab-btn active" onclick="showTab('colorTab')">Màu</button>
						<button class="tab-btn" onclick="showTab('sizeTab')">Kích
							Cỡ</button>
						<button class="tab-btn" onclick="showTab('priceTab')">Giá</button>
					</div>

					<!-- Tab Content: Color -->
					<div class="tab-content" id="colorTab">
						<div class="color-options">
							<!-- 	render color -->
							<div class="ant-col ant-col-6 item-color"
								style="padding-left: 5px; padding-right: 5px; padding-bottom: 10px; width: 100px;">
								<div
									class="border-border p-2.5 py-3 border-[1px] cursor-pointer flex flex-col items-center justify-center">
									<div class="mb-2 w-7 h-7 "
										style="background-color: rgb(127, 33, 45);"></div>
									<div class="ellipsis-t w-full text-xs font-medium text-center">RED</div>
								</div>
							</div>
						</div>
					</div>

					<!-- Tab Content: Size -->
					<div class="tab-content" id="sizeTab" style="display: none;">
						<div class="size-options">
							<label><input type="checkbox"> S</label> <label><input
								type="checkbox"> M</label> <label><input type="checkbox">
								L</label> <label><input type="checkbox"> XL</label> <label><input
								type="checkbox"> XXL</label>
						</div>
					</div>

					<!-- Tab Content: Price -->
					<div class="tab-content" id="priceTab" style="display: none;">
						<div class="price-range">
							<label for="price">Giá:</label> <input type="range" id="price"
								min="0" max="10000000"> <span>0đ</span> - <span>10,000,000đ</span>
						</div>
					</div>



					<button class="reset-button">Xoá hết</button>
					<!-- 	<button class="result-button">Xem kết quả (29)</button> -->
				</div> </i> <i class="sort-icon">⇅</i> <i class="view-icon">◻</i> <i
				class="grid-icon">☷</i>
		</div>
	</div>




	<!-- Collections -->
	<div class="container new-collections mt-5">
		<h1>NEW COLLECTIONS</h1>
		<hr />
		<div class="collections">
			<c:forEach items="${listResponses}" var="product">
				<div class="collection-item">
					<a href="productDetail?id=${product.productId}">
						<div class="image-container">
							<img id="image-main" class="image-main"
								src="${product.productSkus[0].img}" alt="${product.name} Image" />
							<img id="image-hover" class="image-hover"
								src="${product.productSkus[0].img}"
								alt="${product.name} Hover Image" /> <img src="img/add.png"
								alt="" class="plus" />
						</div>
					</a>
					<div class="size-container">
						<div class="size-options" id="size-options">
							<c:choose>
								<c:when test="${product.typeProduct == 'áo'}">
									<c:set var="sizeString" value="s,m,l,xl,xxl" />
								</c:when>
								<c:when test="${product.typeProduct == 'quần'}">
									<c:set var="sizeString" value="28,29,30,31,32" />
								</c:when>
							</c:choose>
							<c:set var="sizeList" value="${fn:split(sizeString, ',')}" />

							<c:forEach items="${sizeList}" var="size">
								<c:set var="stock"
									value="${product.productSkus[0].sizeAndStock[size]}" />
								<c:choose>
									<c:when test="${stock != null && stock > 0}">
										<button class="size-btn"
											onclick="addProductToCart('${product.productSkus[0].productColorImgId}', '${size}', '1')">
											${size.toUpperCase()}</button>
									</c:when>
									<c:otherwise>
										<button class="size-btn size-unavailable">
											${size.toUpperCase()}</button>
									</c:otherwise>
								</c:choose>
							</c:forEach>
						</div>
					</div>

					<div class="image-cate">
						<c:forEach items="${product.productSkus}" var="sku"
							varStatus="status">
							<img src="${sku.img}" alt="${sku.color} Image"
								onclick="selectImage('${sku.productColorImgId}', '${sku.img}', '${fn:escapeXml(sku.sizeAndStock)}', '${product.typeProduct}')" />
						</c:forEach>
					</div>

					<div class="item-price-new">
						<fmt:formatNumber value="${product.price}" pattern="#,###" />
						đ
					</div>
					<p class="product-name">
						<strong><a href="productDetail?id=${product.id}">
								${product.name}</a></strong>
					</p>
				</div>
			</c:forEach>
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


	<script type="text/javascript">
		$.ajax({
			url : "filter",
			method : "GET",
			success : function(response) {
				renderFilter(response.colors, response.sizes);
			},
			error : function(xhr, status, error) {
				console.error("Lỗi: ", error);
			}
		});
	</script>

</body>

</html>
</html>