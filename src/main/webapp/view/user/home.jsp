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
<link rel="stylesheet" href="../../css/Navbar.css">
<link rel="stylesheet" href="../../css/Hero.css">
<link rel="stylesheet" href="../../css/SearchBar.css">
<link rel="stylesheet" href="../../css/Popular.css">
<link rel="stylesheet" href="../../css/Item.css">
<link rel="stylesheet" href="../../css/NewCollections.css">
<link rel="stylesheet" href="../../css/NewLetter.css">
<link rel="stylesheet" href="../../css/Footer.css">


<!-- Bootstrap CSS -->
<link rel="stylesheet" href="../../adding/bootstrap/boostrap.min.css">
<!-- <script src="./adding/jquery/jquery-3.4.1.min.js"></script>
    <script src="./adding/poper/poper.min.js"></script>
    <script src="./adding/bootstrap/boostrap.min.js"></script>  -->

<script src="../../adding/bootstrap/bootstrap.bundle.min.js"></script>
<script type="text/javascript" src="../../js/product_collection.js"></script>


</head>

<body>
	<!-- BEGIN nav -->
	<jsp:include page="header.jsp"></jsp:include>
	<!-- END nav -->

	<!-- Collections -->
	<div class="container new-collections mt-5">
		<h1>NEW COLLECTIONS</h1>
		<hr />
		<div class="collections">
			<div class="collection-item">
				<a href="">
					<div class="image-container">
						<img id="image-main" class="image-main"
							src="../../img/product_1.png" alt="" /> <img id="image-hover"
							class="image-hover" src="../../img/product_1.png" alt="" /> <img
							src="../../img/add.png" alt="" class="plus" />
					</div>
				</a>
				<div class="size-container">
					<div class="size-options" id="size-options">
						<button class="size-btn">S</button>
						<button class="size-btn">M</button>
						<button class="size-btn">L</button>
						<button class="size-btn">XL</button>
					</div>
				</div>

				<div class="image-cate">
					<img src="../../img/product_25.png" alt=""
						onclick="selectImage(this)"> <img
						src="../../img/product_26.png" alt="" onclick="selectImage(this)">
					<img src="../../img/product_27.png" alt=""
						onclick="selectImage(this)">
				</div>
				<p>Product 1 Name</p>
				<div class="item-price-new">$100</div>
			</div>
			
			<div class="collection-item">
				<a href="">
					<div class="image-container">
						<img id="image-main" class="image-main"
							src="../../img/product_1.png" alt="" /> <img id="image-hover"
							class="image-hover" src="../../img/product_1.png" alt="" /> <img
							src="../../img/add.png" alt="" class="plus" />
					</div>
				</a>
				<div class="size-container">
					<div class="size-options" id="size-options">
						<button class="size-btn">S</button>
						<button class="size-btn">M</button>
						<button class="size-btn">L</button>
						<button class="size-btn">XL</button>
					</div>
				</div>

				<div class="image-cate">
					<img src="../../img/product_25.png" alt=""
						onclick="selectImage(this)"> <img
						src="../../img/product_26.png" alt="" onclick="selectImage(this)">
					<img src="../../img/product_27.png" alt=""
						onclick="selectImage(this)">
				</div>
				<p>Product 1 Name</p>
				<div class="item-price-new">$100</div>
			</div>
			
			<div class="collection-item">
				<a href="">
					<div class="image-container">
						<img id="image-main" class="image-main"
							src="../../img/product_1.png" alt="" /> <img id="image-hover"
							class="image-hover" src="../../img/product_1.png" alt="" /> <img
							src="../../img/add.png" alt="" class="plus" />
					</div>
				</a>
				<div class="size-container">
					<div class="size-options" id="size-options">
						<button class="size-btn">S</button>
						<button class="size-btn">M</button>
						<button class="size-btn">L</button>
						<button class="size-btn">XL</button>
					</div>
				</div>

				<div class="image-cate">
					<img src="../../img/product_25.png" alt=""
						onclick="selectImage(this)"> <img
						src="../../img/product_26.png" alt="" onclick="selectImage(this)">
					<img src="../../img/product_27.png" alt=""
						onclick="selectImage(this)">
				</div>
				<p>Product 1 Name</p>
				<div class="item-price-new">$100</div>
			</div>
			
			<div class="collection-item">
				<a href="">
					<div class="image-container">
						<img id="image-main" class="image-main"
							src="../../img/product_1.png" alt="" /> <img id="image-hover"
							class="image-hover" src="../../img/product_1.png" alt="" /> <img
							src="../../img/add.png" alt="" class="plus" />
					</div>
				</a>
				<div class="size-container">
					<div class="size-options" id="size-options">
						<button class="size-btn">S</button>
						<button class="size-btn">M</button>
						<button class="size-btn">L</button>
						<button class="size-btn">XL</button>
					</div>
				</div>

				<div class="image-cate">
					<img src="../../img/product_25.png" alt=""
						onclick="selectImage(this)"> <img
						src="../../img/product_26.png" alt="" onclick="selectImage(this)">
					<img src="../../img/product_27.png" alt=""
						onclick="selectImage(this)">
				</div>
				<p>Product 1 Name</p>
				<div class="item-price-new">$100</div>
			</div>
			
			<div class="collection-item">
				<a href="">
					<div class="image-container">
						<img id="image-main" class="image-main"
							src="../../img/product_1.png" alt="" /> <img id="image-hover"
							class="image-hover" src="../../img/product_1.png" alt="" /> <img
							src="../../img/add.png" alt="" class="plus" />
					</div>
				</a>
				<div class="size-container">
					<div class="size-options" id="size-options">
						<button class="size-btn">S</button>
						<button class="size-btn">M</button>
						<button class="size-btn">L</button>
						<button class="size-btn">XL</button>
					</div>
				</div>

				<div class="image-cate">
					<img src="../../img/product_25.png" alt=""
						onclick="selectImage(this)"> <img
						src="../../img/product_26.png" alt="" onclick="selectImage(this)">
					<img src="../../img/product_27.png" alt=""
						onclick="selectImage(this)">
				</div>
				<p>Product 1 Name</p>
				<div class="item-price-new">$100</div>
			</div>
			
			<div class="collection-item">
				<a href="">
					<div class="image-container">
						<img id="image-main" class="image-main"
							src="../../img/product_1.png" alt="" /> <img id="image-hover"
							class="image-hover" src="../../img/product_1.png" alt="" /> <img
							src="../../img/add.png" alt="" class="plus" />
					</div>
				</a>
				<div class="size-container">
					<div class="size-options" id="size-options">
						<button class="size-btn">S</button>
						<button class="size-btn">M</button>
						<button class="size-btn">L</button>
						<button class="size-btn">XL</button>
					</div>
				</div>

				<div class="image-cate">
					<img src="../../img/product_25.png" alt=""
						onclick="selectImage(this)"> <img
						src="../../img/product_26.png" alt="" onclick="selectImage(this)">
					<img src="../../img/product_27.png" alt=""
						onclick="selectImage(this)">
				</div>
				<p>Product 1 Name</p>
				<div class="item-price-new">$100</div>
			</div>
			
			<div class="collection-item">
				<a href="">
					<div class="image-container">
						<img id="image-main" class="image-main"
							src="../../img/product_1.png" alt="" /> <img id="image-hover"
							class="image-hover" src="../../img/product_1.png" alt="" /> <img
							src="../../img/add.png" alt="" class="plus" />
					</div>
				</a>
				<div class="size-container">
					<div class="size-options" id="size-options">
						<button class="size-btn">S</button>
						<button class="size-btn">M</button>
						<button class="size-btn">L</button>
						<button class="size-btn">XL</button>
					</div>
				</div>

				<div class="image-cate">
					<img src="../../img/product_25.png" alt=""
						onclick="selectImage(this)"> <img
						src="../../img/product_26.png" alt="" onclick="selectImage(this)">
					<img src="../../img/product_27.png" alt=""
						onclick="selectImage(this)">
				</div>
				<p>Product 1 Name</p>
				<div class="item-price-new">$100</div>
			</div>
			
			<div class="collection-item">
				<a href="">
					<div class="image-container">
						<img id="image-main" class="image-main"
							src="../../img/product_1.png" alt="" /> <img id="image-hover"
							class="image-hover" src="../../img/product_1.png" alt="" /> <img
							src="../../img/add.png" alt="" class="plus" />
					</div>
				</a>
				<div class="size-container">
					<div class="size-options" id="size-options">
						<button class="size-btn">S</button>
						<button class="size-btn">M</button>
						<button class="size-btn">L</button>
						<button class="size-btn">XL</button>
					</div>
				</div>

				<div class="image-cate">
					<img src="../../img/product_25.png" alt=""
						onclick="selectImage(this)"> <img
						src="../../img/product_26.png" alt="" onclick="selectImage(this)">
					<img src="../../img/product_27.png" alt=""
						onclick="selectImage(this)">
				</div>
				<p>Product 1 Name</p>
				<div class="item-price-new">$100</div>
			</div>
		</div>
	</div>




	<div class="container newsletter mt-5">
		<h1>Get Exclusive Ofers On You Email</h1>
		<p>Subscribe to our newletter and say updated</p>
		<div>
			<input type="email" placeholder='Your Email' />
			<button>Subscibe</button>
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
			<p>Designed by Pham Quoc Khanh & Vo Xuan Dong</p>
		</div>
	</div>

</body>
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>

<script
	src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>



</html>