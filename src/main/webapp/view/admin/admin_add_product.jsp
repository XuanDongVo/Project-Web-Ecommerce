<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page import="java.net.URLDecoder"%>
<%@ page import="java.nio.charset.StandardCharsets"%>
<!DOCTYPE html>
<html lang="en">

<head>
<title>Danh sách nhân viên | Quản trị Admin</title>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<!-- Main CSS-->
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/css/admin/main.css">
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/css/admin/admin_add_product.css">
	

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


<body onload="time()" class="app sidebar-mini rtl">
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
			<li><a class="app-menu__item "
				href="${pageContext.request.contextPath}/admin_employee"><i
					class='app-menu__icon bx bx-id-card'></i> <span
					class="app-menu__label">Quản lý nhân viên</span></a></li>
			<li><a class="app-menu__item"
				href="${pageContext.request.contextPath}/admin_customer"><i
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
		<div class="container mt-5">
			<!-- Title -->
			<h2 class="text-center mb-4">Thêm Sản Phẩm</h2>

			<!-- Tab navigation -->
			<ul class="nav nav-tabs" id="productTab" role="tablist">
				<li class="nav-item">
					<button class="nav-link active" id="spu-tab" data-bs-toggle="tab"
						data-bs-target="#spu" type="button" role="tab">Thông Tin
						Sản Phẩm (SPU)</button>
				</li>
				<li class="nav-item">
					<button class="nav-link disabled" id="sku-tab" data-bs-toggle="tab"
						data-bs-target="#sku" type="button" role="tab">Thông Tin
						Biến Thể (SKU)</button>
				</li>
				<li class="nav-item">
					<button class="nav-link disabled" id="detail-sku-tab"
						data-bs-toggle="tab" data-bs-target="#detailsku" type="button"
						role="tab">Chi tiết Biến Thể (SKU)</button>
				</li>
			</ul>

			<!-- Tab content -->
			<div class="tab-content mt-4" id="productTabContent">
				<!-- SPU Form -->
				<div class="tab-pane fade show active" id="spu" role="tabpanel"
					aria-labelledby="spu-tab">
					<form id="spuForm">
						<div class="mb-3">
							<label for="productName" class="form-label">Tên Sản Phẩm</label>
							<input type="text" class="form-control" id="productName"
								name="name" required>
						</div>
						<div class="mb-3">
							<label for="productDescription" class="form-label">Mô Tả
								Sản Phẩm</label>
							<textarea class="form-control" id="productDescription"
								name="description" rows="3"></textarea>
						</div>

						<div class="mb-3">
							<label for="productType" class="form-label">Loại Sản Phẩm</label>
							<select class="form-select" id="productType" name="product_type"
								required onchange="updateSubCategory(this)">
								<option value="">Chọn loại sản phẩm</option>
								<c:forEach var="entry" items="${genderToSubCategoryMap}">
									<option value="${entry.key}"
										data-type-subCatgory="${fn:escapeXml(entry.value)}">${entry.key}</option>
								</c:forEach>
							</select>
						</div>

						<div class="mb-3">
							<label for="subCategory" class="form-label">Danh Mục</label> <select
								class="form-select" id="subCategory" name="subCatgory" required
								disabled>
								<option value="">Chọn danh mục</option>
							</select>
						</div>

						<div class="mb-3">
							<label for="price" class="form-label">Giá tiền</label> <input
								type="number" class="form-control" id="productPrice"
								name="price" required min="0" step="any">

						</div>


						<button type="button" class="btn btn-primary" id="saveSpu">Lưu
							Thông Tin SPU</button>
					</form>
				</div>

				<!-- SKU Form -->
				<div class="tab-pane fade" id="sku" role="tabpanel"
					aria-labelledby="sku-tab">
					<form id="skuForm">
						<div class="mb-3">
							<label for="productColor" class="form-label">Màu sắc</label> <select
								class="form-select" id="productColor" name="productColor"
								required>
								<option value="">Chọn màu sắc</option>
								<c:forEach items="${colors}" var="color">
									<option value="${color.name}">${color.name}</option>
								</c:forEach>
							</select>
						</div>


						<div class="mb-3">
							<label for="productImage" class="form-label">URL hình ảnh
								sản phẩm</label> <input type="file" class="form-control"
								id="productImage" name="product_color_img" accept="image/*"
								required>
						</div>

						<div class="mb-3">
							<label for="productSizeType" class="form-label">Loại sản
								phẩm</label> <select class="form-select" id="productSizeType"
								name="size" required onchange="updateSize(this)">
								<option value="">Chọn loại quần áo</option>
								<c:forEach var="entry" items="${sizeMap}">
									<option value="${entry.key}"
										data-type-size="${fn:escapeXml(entry.value)}">${entry.key}</option>
								</c:forEach>

							</select>
						</div>

						<div class="mb-3">
							<label for="productSize" class="form-label">Kích Thước</label> <select
								class="form-select" id="productSize" name="size" required
								disabled>
								<option value="">Chọn kích thước</option>

							</select>
						</div>
						<div class="mb-3">
							<label for="productStock" class="form-label">Số Lượng Tồn
								Kho</label> <input type="number" class="form-control" id="productStock"
								name="stock" required>
						</div>
						<button type="button" class="btn btn-success" id="addSku">Thêm
							SKU</button>
						<button type="button" class="btn btn-success fade" id="detailSku">Chi
							tiết SKU</button>
					</form>

					<!-- Temporary SKU Table -->
					<h5 class="mt-4">Danh Sách SKU</h5>
					<table class="table table-bordered">
						<thead>
							<tr>
								<th>Màu</th>
								<th>Hình Ảnh</th>
								<th>Kích Thước</th>
								<th>Số Lượng</th>
								<th>Hành Động</th>
							</tr>
						</thead>
						<tbody id="skuTableBody">
							<!-- Dữ liệu SKU sẽ được thêm vào đây -->
						</tbody>
					</table>
					<button type="button" class="btn btn-success fade" id="saveSku"
						onclick="saveProductSku()">Lưu sản phẩm</button>
				</div>
			</div>
		</div>
	</main>

	<%-- <div class="tab-pane fade" id="detailsku" role="tabpanel"
		aria-labelledby="detail-sku-tab">
		<div class="container ">
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
								onclick="selectImage('${sku.img}', '${fn:escapeXml(sku.sizeAndStock)}' , '${sku.color}')" /></button>
						</c:forEach>
					</div>
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
									<td>${fn:toUpperCase(entry.key)}</td>
									<td>${entry.value}</td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</div>
			</div>
		</div>
	</div> --%>

	<c:if test="${not empty message}">
		<div class="modal fade" id="message" tabindex="-1"
			aria-labelledby="outOfStockModalLabel" aria-hidden="true">
			<div class="modal-dialog modal-dialog-centered">
				<div class="modal-content">
					<div class="modal-header">
						<h5 class="modal-title" id="outOfStockModalLabel">Thông báo</h5>
						<button type="button" class="btn-close" data-bs-dismiss="modal"
							aria-label="Close"></button>
					</div>
					<div class="modal-body">
						<p>${fn:escapeXml(URLDecoder.decode(message, StandardCharsets.UTF_8.toString()))}</p>
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-secondary"
							data-bs-dismiss="modal">Đóng</button>
					</div>
				</div>
			</div>
		</div>
	</c:if>


	<script src="${pageContext.request.contextPath}/js/admin/main.js"></script>
	<script
		src="${pageContext.request.contextPath}/js/admin/admin_add_product.js"></script>

	<script type="text/javascript">
	// Lưu thông tin sản phẩm (SPU và SKU) dưới dạng JSON
	function saveProductSku() {
	    const product = JSON.stringify(productSpu);
	    // cập nhật lại 
	    productSpu = null;

	    // Tạo form ẩn và gửi dữ liệu
	    const form = document.createElement('form');
	    form.method = 'POST';
	    form.action = '<%=request.getContextPath()%>
		/adminAddProduct';

			const inputJson = document.createElement('input');
			inputJson.type = 'hidden';
			inputJson.name = 'product';
			inputJson.value = product;

			form.appendChild(inputJson);
			document.body.appendChild(form);
			form.submit();

		}
	</script>

</body>
</html>


