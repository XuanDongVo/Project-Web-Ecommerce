<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
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
			<li><a class="app-nav__item" href="/index.html"><i
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
			<li><a class="app-menu__item active"
				href="${pageContext.request.contextPath}/adminController"><i
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
			<li><a class="app-menu__item "
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
		<div class="row">
			<div class="col-md-12">
				<div class="app-title">
					<ul class="app-breadcrumb breadcrumb">
						<li class="breadcrumb-item"><a href="#"><b>Bảng điều
									khiển</b></a></li>
					</ul>
					<div id="clock"></div>
				</div>
			</div>
		</div>
		<div class="row">
			<!--Left-->
			<div class="col-md-12 col-lg-6">
				<div class="row">
					<!-- col-6 -->
					<div class="col-md-6">
						<div class="widget-small primary coloured-icon">
							<i class='icon bx bxs-user-account fa-3x'></i>
							<div class="info">
								<h4>Tổng khách hàng</h4>
								<p>
									<b>${list_customer.size()}</b>
								</p>
								<p class="info-tong">Tổng số khách hàng được quản lý.</p>
							</div>
						</div>
					</div>
					<!-- col-6 -->
					<div class="col-md-6">
						<div class="widget-small info coloured-icon">
							<i class='icon bx bxs-data fa-3x'></i>
							<div class="info">
								<h4>Tổng sản phẩm</h4>
								<p>
									<b>${products.size()}</b>
								</p>
								<p class="info-tong">Tổng số sản phẩm được quản lý.</p>
							</div>
						</div>
					</div>
					<!-- col-6 -->
					<div class="col-md-6">
						<div class="widget-small warning coloured-icon">
							<i class='icon bx bxs-shopping-bags fa-3x'></i>
							<div class="info">
								<h4>Tổng đơn hàng</h4>
								<p>
									<b>${list_order.size()}</b>
								</p>
								<p class="info-tong">Tổng số hóa đơn bán hàng trong tháng.</p>
							</div>
						</div>
					</div>
					<!-- col-6 -->
					<div class="col-md-6">
						<div class="widget-small danger coloured-icon">
							<i class='icon bx bxs-error-alt fa-3x'></i>
							<div class="info">
								<h4>Sắp hết hàng</h4>
								<p>
									<b>4 sản phẩm</b>
								</p>
								<p class="info-tong">Số sản phẩm cảnh báo hết cần nhập thêm.</p>
							</div>
						</div>
					</div>
					<!-- col-12 -->
					<div class="col-md-12">
						<div class="tile">
							<h3 class="tile-title">Tình trạng đơn hàng</h3>
							<div>
								<table class="table table-bordered">
									<thead>
										<tr>
											<th>ID đơn hàng</th>
											<th>Tên khách hàng</th>
											<th>Tổng tiền</th>
											<th>Trạng thái</th>
										</tr>
									</thead>
									<c:forEach var="order" items="${list_order}">
										<tbody>
											<tr>
												<td>${order.id_order_admin}</td>
												<td>${order.name_customer}</td>
												<td>${order.price}đ</td>
												<td><span class="badge bg-success">${order.status}</span></td>
											</tr>
										</tbody>
									</c:forEach>
								</table>

							</div>
							<!-- / div trống-->
						</div>
					</div>
					<!-- / col-12 -->
					<!-- col-12 -->
					<div class="col-md-12">
						<div class="tile">
							<h3 class="tile-title">Khách hàng mới</h3>
							<div>
								<table class="table table-hover">
									<thead>
										<tr>
											<th>ID</th>
											<th>Tên khách hàng</th>
											<th>Email</th>
											<th>Số điện thoại</th>
										</tr>
									</thead>
									<c:forEach items="${list_customer}" var="customer">
										<tbody>
											<tr>
												<td>${customer.user_id}</td>
												<td>${customer.user_name}</td>
												<td>${customer.email}</td>
												<td>${customer.phone}</td>
											</tr>
										</tbody>
									</c:forEach>
								</table>
							</div>

						</div>
					</div>
					<!-- / col-12 -->
				</div>
			</div>
			<!--END left-->
			<!--Right-->
			<div class="col-md-12 col-lg-6">
				<div class="row">
					<div class="col-md-12">
						<div class="tile">
							<h3 class="tile-title">Dữ liệu 6 tháng đầu vào</h3>
							<div class="embed-responsive embed-responsive-16by9">
								<canvas class="embed-responsive-item" id="lineChartDemo"></canvas>
							</div>
						</div>
					</div>
					<div class="col-md-12">
						<div class="tile">
							<h3 class="tile-title">Thống kê 6 tháng doanh thu</h3>
							<div class="embed-responsive embed-responsive-16by9">
								<canvas class="embed-responsive-item" id="barChartDemo"></canvas>
							</div>
						</div>
					</div>
				</div>

			</div>
			<!--END right-->
		</div>


	</main>

	<!--===============================================================================================-->
	<script src="${pageContext.request.contextPath}/js/admin/main.js"></script>
	<!--===============================================================================================-->
	<script type="text/javascript">
		//Thời Gian
		function time() {
			var today = new Date();
			var weekday = new Array(7);
			weekday[0] = "Chủ Nhật";
			weekday[1] = "Thứ Hai";
			weekday[2] = "Thứ Ba";
			weekday[3] = "Thứ Tư";
			weekday[4] = "Thứ Năm";
			weekday[5] = "Thứ Sáu";
			weekday[6] = "Thứ Bảy";
			var day = weekday[today.getDay()];
			var dd = today.getDate();
			var mm = today.getMonth() + 1;
			var yyyy = today.getFullYear();
			var h = today.getHours();
			var m = today.getMinutes();
			var s = today.getSeconds();
			m = checkTime(m);
			s = checkTime(s);
			nowTime = h + " giờ " + m + " phút " + s + " giây";
			if (dd < 10) {
				dd = '0' + dd
			}
			if (mm < 10) {
				mm = '0' + mm
			}
			today = day + ', ' + dd + '/' + mm + '/' + yyyy;
			tmp = '<span class="date"> ' + today + ' - ' + nowTime + '</span>';
			document.getElementById("clock").innerHTML = tmp;
			clocktime = setTimeout("time()", "1000", "Javascript");

			function checkTime(i) {
				if (i < 10) {
					i = "0" + i;
				}
				return i;
			}
		}
	</script>
</body>

</html>