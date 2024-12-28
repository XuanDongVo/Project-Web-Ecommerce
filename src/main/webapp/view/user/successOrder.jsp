<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<title>Order Success</title>
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap/5.1.3/css/bootstrap.min.css">

<link rel="stylesheet" href="css/Navbar_procate.css">
<link rel="stylesheet" href="css/Hero_procate.css">
<link rel="stylesheet" href="css/Footer.css">
<link rel="stylesheet" href="css/Cate.css">


<script src="js/cate_filter.js"></script>
<script src="js/navbar.js"></script>
<style>
.success-container {
	display: flex;
	justify-content: center;
	align-items: center;
	min-height: 100vh;
	text-align: center;
}

.success-icon {
	font-size: 3rem;
	color: green;
}

.btn-container {
	margin-top: 20px;
}
</style>
</head>
<body>

	<jsp:include page="header.jsp"></jsp:include>
	<div class="container success-container">
		<div>
			<div class="success-icon">
				<i class="bi bi-check-circle-fill"></i>
			</div>
			<h2>Đặt hàng thành công!</h2>
			<p>Chúng tôi sẽ liên hệ với Quý Khách để xác nhận đơn hàng trong
				thời gian sớm nhất!</p>
			<div class="btn-container">
				<a href="home" class="btn btn-warning">Tiếp tục mua hàng</a>
			</div>
		</div>
	</div>

	<script
		src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap/5.1.3/js/bootstrap.bundle.min.js"></script>
</body>
</html>
