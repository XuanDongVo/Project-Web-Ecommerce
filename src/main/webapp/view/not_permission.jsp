<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>403 - Không có quyền truy cập</title>
<!-- Bootstrap CSS -->
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css"
	rel="stylesheet">
<style>
body {
	background-color: #f8f9fa;
	height: 100vh;
	display: flex;
	justify-content: center;
	align-items: center;
}

.error-container {
	text-align: center;
	background: #fff;
	border-radius: 15px;
	padding: 30px;
	box-shadow: 0 4px 10px rgba(0, 0, 0, 0.15);
}

.error-image {
	max-width: 100%;
	height: auto;
}

.btn-custom {
	background-color: #007bff;
	color: #fff;
	padding: 10px 20px;
	border: none;
	border-radius: 5px;
	transition: 0.3s;
}

.btn-custom:hover {
	background-color: #0056b3;
}
</style>
</head>
<body>
	<div class="container">
		<div class="row justify-content-center align-items-center">
			<div class="col-md-6">
				<div class="error-container">
					<img src="../img/loi-403-forbidden-error.jpg"
						alt="403 Forbidden" class="error-image">
					<h1 class="mt-4 text-danger">Bạn không có quyền truy cập</h1>
					<p class="text-muted">Rất tiếc, bạn không được phép truy cập
						trang này. Vui lòng quay lại trang chủ.</p>
					<a href="<%=request.getContextPath()%>/home"> <button class="btn btn-dark mt-3"
						>Quay về trang chủ</button>
						</a>
				</div>
			</div>
		</div>
	</div>

	<!-- Bootstrap JS -->
	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>