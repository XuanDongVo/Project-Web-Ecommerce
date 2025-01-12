<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>ProductDetail</title>

<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/css/admin/main.css">
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/css/admin/admin_product_detail.css">

<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/npm/boxicons@latest/css/boxicons.min.css">
<!-- or -->
<link rel="stylesheet"
	href="https://unpkg.com/boxicons@latest/css/boxicons.min.css">
<!-- Font-icon css-->
<link rel="stylesheet" type="text/css"
	href="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css">
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/sweetalert/2.1.2/sweetalert.min.js"></script>
<link rel="stylesheet"
	href="https://use.fontawesome.com/releases/v5.8.2/css/all.css">
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/jquery-confirm/3.3.2/jquery-confirm.min.css">

<!-- Bootstrap CSS -->
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/adding/bootstrap/boostrap.min.css">

<script
	src="${pageContext.request.contextPath}/adding/bootstrap/bootstrap.bundle.min.js"></script>



</head>

<body class="app sidebar-mini rtl">
	<!-- Navbar-->
	<header class="app-header">
		<!-- Sidebar toggle button-->
		<a class="app-sidebar__toggle" href="#" data-toggle="sidebar"
			aria-label="Hide Sidebar"></a>
		<!-- Navbar Right Menu-->
		<ul class="app-nav">


			<!-- User Menu-->
			<li><a class="app-nav__item" href="${pageContext.request.contextPath}/logout"><i
					class='bx bx-log-out bx-rotate-180'></i> </a></li>
		</ul>
	</header>
	<!-- Sidebar menu-->
	<div class="app-sidebar__overlay" data-toggle="sidebar"></div>
	<aside class="app-sidebar">
		<div class="app-sidebar__user">
			<div>
				<p class="app-sidebar__user-name">
					<b>Võ Trường</b>
				</p>
				<p class="app-sidebar__user-designation">Chào mừng bạn trở lại</p>
			</div>
		</div>
		<hr>
		<ul class="app-menu">
			<li><a class="app-menu__item "
				href="${pageContext.request.contextPath}/view/admin/admin.jsp"><i
					class='app-menu__icon bx bx-tachometer'></i><span
					class="app-menu__label">Bảng điều khiển</span></a></li>
			<li><a class="app-menu__item " href="${pageContext.request.contextPath}/admin_employee"><i
					class='app-menu__icon bx bx-id-card'></i> <span
					class="app-menu__label">Quản lý nhân viên</span></a></li>
			<li><a class="app-menu__item" href="${pageContext.request.contextPath}/admin_customer"><i
					class='app-menu__icon bx bx-user-voice'></i><span
					class="app-menu__label">Quản lý khách hàng</span></a></li>
			<li><a class="app-menu__item active"
				href="${pageContext.request.contextPath}/adminProduct"><i
					class='app-menu__icon bx bx-purchase-tag-alt'></i><span
					class="app-menu__label">Quản lý sản phẩm</span></a></li>
			<li><a class="app-menu__item "
				href="${pageContext.request.contextPath}/order"><i
					class='app-menu__icon bx bx-task'></i><span class="app-menu__label">Quản
						lý đơn hàng</span></a></li>
						<li><a class="app-menu__item "
				href="${pageContext.request.contextPath}/inventory"><i
					class='app-menu__icon bx bx-task'></i><span class="app-menu__label">Quản
						lý hàng tồn kho</span></a></li>
		</ul>
	</aside>

	<main class="app-content">

		<div class="row mb-4">
			<div class="col-12">
				<h3 class="text-center font-weight-bold mt-4 mb-3"
					style=" padding: 10px;">Chi
					tiết sản phẩm</h3>
			</div>

		</div>
		<div class="container view">
			<c:set value="${productResponse}" var="product" />
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
						<span class="productPrice"><fmt:formatNumber
								value="${product.price}" pattern="#,###" /> đ</span>
					</p>

					<!-- Color Options -->
					<h6 id="color-name">Màu sắc:
						${product.productSkus[0].color.toUpperCase()}</h6>


					<div class="color-options d-flex mt-3 mb-3">
						<c:forEach items="${product.productSkus}" var="sku">
							<button class="btn btn-outline-primary color-btn"
								style="background-color: ${sku.color};"
								onclick="selectImage(this,'${sku.img}', '${fn:escapeXml(sku.sizeAndStock)}' , '${sku.color}')" /></button>
						</c:forEach>
					</div>

					<!-- Label cho bảng -->
					<h5>Bảng Size và Số lượng</h5>
					<!-- Bảng hiển thị size và quantity -->
					<div class="table-responsive mt-3">
						<table class="table table-striped table-hover table-bordered"
							style="max-width: 600px;">
							<thead class="table-primary text-center">
								<tr>
									<th scope="col">Size</th>
									<th scope="col">Số lượng</th>
								</tr>
							</thead>
							<tbody id="sizeTableBody">
								<c:forEach items="${product.productSkus[0].sizeAndStock}"
									var="entry">
									<tr class="text-center">
										<td class="text-center">${fn:toUpperCase(entry.key)}</td>
										<td class="text-center">${entry.value}</td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
					</div>
				</div>
			</div>

		</div>
	</main>



	<script src="${pageContext.request.contextPath}/js/admin/main.js"></script>

	<script type="text/javascript"
		src="${pageContext.request.contextPath}/js/admin/admin_product.js"></script>



</body>
</html>