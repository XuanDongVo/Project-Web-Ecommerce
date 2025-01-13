<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html><%@taglib prefix="c"
	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Quản lý sản phẩm</title>



<link rel="stylesheet" type="text/css"
	href="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css">
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/sweetalert/2.1.2/sweetalert.min.js"></script>
<link rel="stylesheet"
	href="https://use.fontawesome.com/releases/v5.8.2/css/all.css">
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/jquery-confirm/3.3.2/jquery-confirm.min.css">


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
<!-- <link rel="stylesheet"
	href="https://use.fontawesome.com/releases/v5.8.2/css/all.css">
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/jquery-confirm/3.3.2/jquery-confirm.min.css"> -->

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
			<h2 class="text-center mb-4">Quản lý sản phẩm</h2>
			<ul class="nav nav-tabs" id="productTab" role="tablist">
				<li class="nav-item" role="presentation">
					<button class="nav-link active" id="basic-info-tab"
						data-bs-toggle="tab" data-bs-target="#basic-info" type="button"
						role="tab">Thông tin cơ bản</button>
				</li>
				<li class="nav-item" role="presentation">
					<button class="nav-link" id="color-image-tab" data-bs-toggle="tab"
						data-bs-target="#color-image" type="button" role="tab">Màu
						sắc và hình ảnh</button>
				</li>
			</ul>

			<div class="tab-content mt-3" id="productTabContent">
				<c:set value="${productResponse}" var="product" />
				<!-- Tab 1: Thông tin cơ bản -->
				<div class="tab-pane fade show active" id="basic-info"
					role="tabpanel">
					<form action="adminUpdateProduct" method="post">
						<input name="productId" value="${productResponse.productId }"
							hidden>
						<div class="mb-3">
							<label for="productName" class="form-label">Tên sản phẩm</label>
							<input type="text" class="form-control" id="productName"
								name="name" value="${productResponse.name}" required>
						</div>
						<div class="mb-3">
							<label for="description" class="form-label">Mô tả</label>
							<textarea class="form-control" id="description"
								name="description" rows="4">${productResponse.description}</textarea>
						</div>
						<div class="mb-3">
							<label for="productType" class="form-label">Loại Sản Phẩm</label>
							<select class="form-select" id="productType" name="product_type"
								required>
								<option value="">Chọn loại sản phẩm</option>
								<c:forEach var="subCategory" items="${subCategory}">
									<option value="${subCategory.name}"
										<c:if test="${subCategory.name == productResponse.subCategory}">
                   						 selected
               							 </c:if>>
										${subCategory.name}</option>
								</c:forEach>
							</select>
						</div>

						<div class="mb-3">
							<label for="price" class="form-label">Giá tiền</label> <input
								type="number" class="form-control" id="productPrice"
								name="price" required min="0" value="${productResponse.price}">

						</div>
						<button type="submit" class="btn btn-primary">Cập nhật</button>
					</form>
				</div>

				<!-- Tab 2: Màu sắc và hình ảnh -->
				<div class="tab-pane fade" id="color-image" role="tabpanel">
					<form action="adminUpdateProductColorImg" method="POST">
						<div class="container edit ">
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
									<!-- Tabs Navigation -->
									<ul class="nav nav-tabs" id="colorTab" role="tablist">
										<c:forEach items="${product.productSkus}" var="sku"
											varStatus="status">
											<li class="nav-item" role="presentation">
												<button
													class="nav-link ${status.index == 0 ? 'active' : ''}"
													id="tab-${sku.color}" data-bs-toggle="tab"
													data-bs-target="#content-${sku.color}" type="button"
													role="tab" aria-controls="content-${sku.color}"
													aria-selected="${status.index == 0}">
													${sku.color.toUpperCase()}</button>
											</li>
										</c:forEach>
									</ul>

									<!-- Tab Content -->
									<div class="tab-content mt-3" id="colorTabContent">
										<c:forEach items="${product.productSkus}" var="sku"
											varStatus="status">
											<input name="colorImgId" value="${sku.productColorImgId }" hidden>
											<div
												class="tab-pane fade ${status.index == 0 ? 'show active' : ''}"
												id="content-${sku.color}" role="tabpanel"
												aria-labelledby="tab-${sku.color}">
												<h6>Màu sắc: ${sku.color.toUpperCase()}</h6>

												<!-- Bảng hiển thị size và quantity -->
												<div class="table-responsive mt-3">
													<table
														class="table table-bordered table-hover text-center align-middle">
														<thead>
															<tr>
																<th>Màu</th>
																<th>Hình Ảnh</th>
																<th>Kích Thước</th>
															</tr>
														</thead>
														<tbody>
															<c:forEach items="${sku.sizeAndStock}" var="sizeStock"
																varStatus="sizeStatus">
																<tr class="text-center">
																	<!-- Hiển thị màu và hình ảnh chỉ một lần -->
																	<c:if test="${sizeStatus.index == 0}">
																		<td class="text-center"
																			rowspan="${fn:length(sku.sizeAndStock)}">
																			<div class="mt-2">
																				<select name="color"
																					class="form-select form-select-sm"  onchange="updateHiddenInput(this, '${sku.color}')">
																					<c:forEach var="color" items="${colors}">
																						<option value="${color.name}"
																							<c:if test="${color.name == sku.color}">
                     																		   selected
                 				   														</c:if>>
																							${color.name}</option>
																					</c:forEach>
																				</select>
																			</div>

																		</td>
																		<td class="text-center"
																			rowspan="${fn:length(sku.sizeAndStock)}"><img
																			id="skuImage-${sku.color}" src="${sku.img}"
																			alt="${sku.color}" class="img-fluid"
																			style="height: 100px; width: 100px;"> <!-- Input file (ẩn) -->
																			<input type="file" id="fileInput-${sku.color}"
																			style="display: none;" accept="image/*"
																			onchange="handleFileChange('${sku.color}')" /> <!-- Input ẩn chứa Base64 -->
																			<input type="hidden" id="hiddenBase64-${sku.color}"
																			name="image-${sku.color}" /> <!-- Nút sửa -->
																			<button class="btn btn-primary btn-sm edit mt-2"
																				type="button" title="Sửa"
																				onclick="openFileChooser('${sku.color}')">
																				<i class="fas fa-edit"></i>
																			</button></td>
																	</c:if>
																	<td class="text-center">${sizeStock.key}</td>
																</tr>
															</c:forEach>
														</tbody>
													</table>
													<button type="submit" class="btn btn-primary">Cập
														nhật</button>
												</div>
											</div>
										</c:forEach>
									</div>
								</div>
							</div>
						</div>
					</form>
				</div>

			</div>
		</div>
	</main>

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
						<p>${message}</p>
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
		src="${pageContext.request.contextPath}/js/admin/admin_update_product.js"></script>
</body>
</html>
