<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
<link rel="stylesheet" href="css/Navbar.css">
<link rel="stylesheet" href="css/SearchBar.css">

<!-- Bootstrap CSS -->
<link rel="stylesheet" href="adding/bootstrap/boostrap.min.css">

<!-- Thêm link jQuery (phải tải trước Bootstrap JS) -->
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script src="adding/bootstrap/bootstrap.bundle.min.js"></script>
<!-- Thêm link jQuery -->

<script src="js/navbar.js"></script>
<!-- js  -->

</head>
<body>
	<div class="hero" style="display: flex;">
		<div class="nav-header">
			<a href="home"
				style="text-decoration: none; color: inherit; z-index: 3; margin-top: 3.5rem; position: fixed; z-index: 9999;">
				<p style="color: white;">ECOMMERCE</p>
			</a>
		</div>
		<div class="shop-navbar">
			<ul class="nav-menu">
				<!-- render data  -->
			</ul>

			<div class="nav-login-cart">
				<!-- Search bar -->
				<div class="search-bar"
					style="margin: 0.5px; border: none; border-bottom: 2px solid white; outline: none; background-color: transparent; display: flex; align-items: center;">
					<span style="font-size: 1rem; color: white;"> <img
						src="./img/search.png" alt="" style="height: 20px;">
					</span> <input type="text" id="searchInput" placeholder="Tìm kiếm"
						style="background: transparent; outline: none; border: none; color: white;">
				</div>

				<!-- User icon and hidden login/register options -->
				<div class="user-menu">
					<span class="user-icon"
						style="color: white; font-size: 2rem; cursor: pointer;"> <img
						src="./img/avatar.png" alt=""
						style="height: 30px; margin-top: 5%;">
					</span>
					<div class="user-options" style="display: none;">
						<button
							style="color: white; width: 100%; height: 40px; margin-bottom: 0.5rem; font-size: 15px; background-color: black;">
							Đăng nhập</button>
						<button
							style="color: black; width: 100%; height: 40px; font-size: 15px; background-color: white; border: 1px solid black">
							Đăng kí</button>
					</div>
				</div>


				<!-- Heart icon -->
				<div class="heart">
					<span style="color: white; font-size: 2rem; cursor: pointer;"><img
						src="./img/heart.png" alt="" style="height: 25px; opacity: 1;"></span>
				</div>

				<!-- Cart icon -->
				<div class="cart">
					<a href="#" data-bs-toggle="offcanvas"
						data-bs-target="#cartOffcanvas"> <span
						style="color: white; font-size: 2rem; cursor: pointer;"> <img
							src="./img/shopping-cart.png" alt=""
							style="height: 30px; padding-left: 15px;">
					</span>
					</a>
				</div>

				<!-- Offcanvas (cart frame) náº±m bÃªn pháº£i -->
				<div class="offcanvas offcanvas-end" tabindex="-1"
					id="cartOffcanvas" aria-labelledby="cartOffcanvasLabel">
					<div class="offcanvas-header">
						<h5 class="offcanvas-title " id="cartOffcanvasLabel">Giỏ hàng
							của tôi</h5>
						<button type="button" class="btn-close"
							data-bs-dismiss="offcanvas" aria-label="Close"></button>
					</div>

					<div class="offcanvas-body">
						<!-- Cart Item -->
						<!-- Rednder dữ liệu -->
					</div>

					<div class="offcanvas-footer">
						<button class="view-cart-btn" onclick="redirectCartDetail()">Xem
							giỏ hàng</button>
					</div>
				</div>
				<div class="nav-cart-count"></div>
			</div>
		</div>
		<!-- Navbar placeholder -->
	</div>

	<!-- Modal -->
	<!-- 	<div class="modal fade" id="exampleModalCenter" tabindex="-1"
		role="dialog" aria-labelledby="exampleModalCenterTitle"
		aria-hidden="true">
		<div class="modal-dialog modal-dialog-centered" role="document">
			<div class="modal-content">
				<div class="modal-header text-center">
					<h5 class="modal-title" id="exampleModalLongTitle">Giỏ hàng
						trống</h5>
					<button type="button" class="btn-close" data-dismiss="modal"
						aria-label="Close" onclick="closeModal()"></button>
				</div>
				<div class="modal-body">Bạn chưa có sản phẩm nào trong giỏ
					hàng. Tiếp tục mua hàng nào!</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-dark" 
						data-dismiss="modal" onclick="closeModal()">Mua Hàng</button>
				</div>
			</div>
		</div>
	</div> -->


	<script type="text/javascript">
		// Biến flag để kiểm tra trạng thái đã load hay chưa
		let isDataLoaded = false;

		/*sử dụng ajax lấy dữ liệu cho header*/
		$.ajax({
			url : "header",
			method : "GET",
			success : function(response) {
				// render dữ liệu
				renderDataDropListCategoryHeader(response.dropListCategory)
				renderDataQuantityProductHeader(response.quantityProduct)
				renderDataListCartDetailHeader(response.listCartDetail)
				// Đánh dấu dữ liệu đã được load
				isDataLoaded = true;
			},
			error : function(xhr, status, error) {
				console.error("Lỗi: ", error);
			}
		});
	</script>


	<script>
		// Hàm này sẽ được gọi khi người dùng nhấn Enter
		function handleSearch() {
			const searchInput = document.getElementById('searchInput');
			const searchTerm = searchInput.value; // Lấy giá trị từ input
			performSearch(searchTerm);
		}

		// gọi controller tìm kiếm
		function performSearch(term) {
			window.location.href = 'search?search=' + term;
		}
	</script>


</body>
</html>