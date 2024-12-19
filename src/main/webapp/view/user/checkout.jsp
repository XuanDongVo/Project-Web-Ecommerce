<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html lang="vi">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Checkout Page</title>
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css"
	rel="stylesheet">
<link rel="stylesheet" href="css/Navbar_procate.css">
<link rel="stylesheet" href="css/Hero_procate.css">
<!-- <link rel="stylesheet" href="css/SearchBar.css"> -->
<!-- <link rel="stylesheet" href="css/Popular.css"> -->
<!-- <link rel="stylesheet" href="css/Item.css"> -->
<link rel="stylesheet" href="css/NewCollections.css">
<!-- <link rel="stylesheet" href="css/NewLetter.css"> -->

<link rel="stylesheet" href="css/Cate.css">
<link rel="stylesheet" href="css/Cart.css">
<link rel="stylesheet" href="css/Checkout.css">
<link rel="stylesheet" href="css/Footer.css">

<!-- Bootstrap CSS -->
<link rel="stylesheet" href="adding/bootstrap/boostrap.min.css">
<!-- <script src="adding/jquery/jquery-3.4.1.min.js"></script>
    <script src="adding/poper/poper.min.js"></script>	
    <script src="adding/bootstrap/boostrap.min.js"></script>  -->

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
						class="ellipsis-1 text-sm  inline breadcrumb-link">THANH TOÁN</a></span></li>

			</ul>
		</div>
	</div>

	<div class="container mt-4">
		<form action="processingOrder" method="post">
			<div class="row">
				<c:if test="${sessionScope.user != null}">
					<c:set var="user" value="${sessionScope.user}" />
				</c:if>

				<!-- Phần bên trái: Thông tin thanh toán, Phương thức thanh toán, v.v. -->
				<div class="col-md-7">
					<!-- Thông tin giao hàng -->
					<div class="card mb-3">
						<div class="card-body">
							<h5 class="card-title">Thông tin giao hàng</h5>
							<label for="fullName" class="form-label">Họ và tên *</label> <input
								type="text" id="fullName" name="customerName"
								class="form-control" placeholder="Nhập đầy đủ họ và tên của bạn"
								required value=${user.name }> <label for="address"
								class="form-label mt-3">Địa chỉ *</label> <input type="text"
								id="address" name="address" class="form-control"
								placeholder="Ví dụ: Số xx Ngõ xx Phú Kiều, Bắc Từ Liêm, Hà Nội"
								required value=${user.name }> <label for="phoneNumber"
								class="form-label mt-3">Số điện thoại *</label> <input
								type="text" id="phoneNumber" name="customerPhone"
								class="form-control " required value=${user.phone }> <label
								for="email" class="form-label mt-3">Email (tuỳ chọn)</label> <input
								type="email" id="email" name="customerEmail"
								class="form-control" placeholder="Nhập Email của bạn"
								value=${user.email }>
						</div>
					</div>

					<!-- Thông tin bổ sung -->
					<div class="card mb-3">
						<div class="card-body">
							<h5 class="card-title">THÔNG TIN BỔ SUNG</h5>
							<label for="orderNotes" class="form-label">Ghi chú đơn
								hàng (tuỳ chọn)</label>
							<textarea id="orderNotes" name="orderNotes" class="form-control"
								placeholder="Ghi chú về đơn hàng, ví dụ: thời gian hay chỉ dẫn địa điểm giao hàng chi tiết hơn."></textarea>
						</div>
					</div>
				</div>

				<!-- Phần bên phải: Tóm tắt đơn hàng -->
				<div class="col-md-5">
					<div class="card">
						<div class="card-body">
							<h5>Đơn hàng</h5>
							<div class="cart-items px-0 is-limit">
								<c:forEach items="${selectedProducts}" var="product">
									<div
										class="cart-item d-flex justify-content-between align-items-center border-bottom">
										<div class="cart-item-image ml-5">
											<img src="${product.image }" alt="Product Image"
												class="img-fluid" />
										</div>
										<div class="cart-item-details ml-3">
											<p class="cart-item-name mb-0" style="font-size: 14px">${product.name }</p>
											<p class="cart-item-description text-muted mb-2"
												style="font-size: 12px">${product.color },
												${product.size}</p>
											<div class="cart-item-actions-checkout">
												<div class="quantity-container">
													<span class="quantity-display text-muted"
														style="font-size: 13px">SL: ${product.quantity}</span>
												</div>
												<div class="cart-item-price font-weight-bold">
													<fmt:formatNumber
														value="${product.quantity * product.price}"
														pattern="#,###" />
													đ
												</div>
											</div>
										</div>
									</div>
									<!-- Cộng dồn tổng tiền -->
									<c:set var="totalPrice"
										value="${totalPrice + (product.quantity * product.price)}" />
								</c:forEach>
							</div>
							<hr>
							<div class="d-flex justify-content-between">
								<p class="mb-0">Tổng giá trị đơn hàng</p>
								<p class="mb-0" style="margin-right: 1%;">
									<fmt:formatNumber value="${totalPrice}" pattern="#,###" />
									đ
								</p>
							</div>
							<div class="d-flex justify-content-between">
								<p class="mb-0">Phí vận chuyển</p>
								<p class="mb-0" style="margin-right: 1%;">0 đ</p>
							</div>
							<div class="d-flex justify-content-between">
								<p class="mb-0">Giảm giá</p>
								<p class="mb-0" style="margin-right: 1%;">0 đ</p>
							</div>
							<hr>
							<div class="d-flex justify-content-between">
								<p class="fw-bold mb-0">Thành tiền</p>
								<p class="fw-bold mb-0" style="margin-right: 1%;">
									<fmt:formatNumber value="${totalPrice}" pattern="#,###" />
									đ
								</p>
							</div>
							<!-- Truyền giá trị tổng tiền qua form -->
							<input type="hidden" name="totalPrice" value="${totalPrice}" />
							<button class="btn btn-dark w-100 mt-3">Đặt hàng</button>
							<p class="text-muted text-center mt-3">Khi tiếp tục, bạn đồng
								ý với các Điều khoản & Điều kiện và Chính sách của chúng tôi.</p>
						</div>
					</div>
				</div>
			</div>
		</form>

	</div>


	<!-- Out of Stock Modal -->
	<div class="modal fade" id="outOfStockModal" tabindex="-1"
		aria-labelledby="outOfStockModalLabel" aria-hidden="true">
		<div class="modal-dialog modal-dialog-centered">
			<div class="modal-content">
				<div class="modal-header">
					<h5 class="modal-title" id="outOfStockModalLabel">Sản phẩm hết
						hàng</h5>
					<button type="button" class="btn-close" data-bs-dismiss="modal"
						aria-label="Close"></button>
				</div>
				<div class="modal-body">
					<p>Các sản phẩm sau không đủ số lượng trong kho:</p>
					<ul>
						<c:forEach items="${outOfStockProducts}" var="entry">
							<li><strong>${entry.key}</strong>: Số lượng hàng tồn kho còn
								<strong>${entry.value}</strong> sản phẩm</li>
						</c:forEach>
					</ul>
					<p>Vui lòng điều chỉnh số lượng hoặc loại bỏ các sản phẩm này
						trước khi tiếp tục.</p>
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

	<c:if test="${not empty outOfStockProducts }">
epty</c:if>

	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>


	<script>
		window.onload = function() {
	<%if (request.getAttribute("outOfStockProducts") != null) {%>
		var outOfStockModal = new bootstrap.Modal(document
					.getElementById('outOfStockModal'));
			outOfStockModal.show();
	<%}%>
		};
	</script>
</body>
</html>
