<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Document</title>
    <link rel="stylesheet" href="../../css/Navbar_procate.css">
    <link rel="stylesheet" href="../../css/Hero_procate.css">
    <link rel="stylesheet" href="../../css/SearchBar.css">
    <link rel="stylesheet" href="../../css/Popular.css">
    <link rel="stylesheet" href="../../css/Item.css">
    <link rel="stylesheet" href="../..css/NewCollections.css">
    <link rel="stylesheet" href="../../css/NewLetter.css">
    <link rel="stylesheet" href="../../css/Footer.css">
    <link rel="stylesheet" href="../../css/Cate.css">
    <link rel="stylesheet" href="../../css/Cart.css">


    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="../../adding/bootstrap/boostrap.min.css">
    <!-- <script src="./adding/jquery/jquery-3.4.1.min.js"></script>
    <script src="./adding/poper/poper.min.js"></script>
    <script src="./adding/bootstrap/boostrap.min.js"></script>  -->

    <script src="../../adding/bootstrap/bootstrap.bundle.min.js"></script>
</head>

<body>
    <jsp:include page="header.jsp"></jsp:include>


    <div class="cate-container container">
        <div class="cate-left">
            <div class="cate-left-breadcrumb" style="margin-left: 20px; margin-top: 12px; text-decoration: none;">
                <a href="#" style="text-decoration: none;">
                    <span class="breadcrumb">
                        Trang chủ > <span class="current-category" style="cursor: default; margin-left: 10px;">Giỏ hàng
                            của tôi</span>
                    </span>
                </a>
            </div>
        </div>
    </div>


    <div class="container mt-5">
        <div class="row">
            <!-- Cart Items Section -->
            <div class="left-cart col-md-8">
                <div class="cart-items">
                    <div class="cart-header d-flex justify-content-between align-items-center p-2">
                        <span class="font-weight-bold">Giao Hàng</span>
                    </div>
                    <hr class="divider" />
                    <!-- Individual Cart Item -->
                    <input type="checkbox" id="selectAll" checked>
                    <label for="selectAll" class="ml-2">Chọn tất cả</label><br>
                    <div class="cart-item d-flex justify-content-between align-items-center p-3 border-bottom">
                        <input type="checkbox" id="selectItem" checked>
                        <div class="cart-item-image ml-5">
                            <img src="https://via.placeholder.com/80x80" alt="Product Image" class="img-fluid" />
                        </div>
                        <div class="cart-item-details ml-3">
                            <p class="cart-item-name mb-0">Áo thun nam ngắn tay sọc ngang. Loose</p>
                            <p class="cart-item-description text-muted mb-2">TOFU, M</p>
                            <div class="cart-item-actions">
                                <button class="btn-quantity" onclick="changeQuantity(-1, 'quantity1')">-</button>
                                <input type="text" class="item-quantity text-center" id="quantity1" value="1">
                                <button class="btn-quantity" onclick="changeQuantity(1, 'quantity1')">+</button>
                                <button class="remove-item-btn ml-2">
                                    <i class="bi bi-trash"></i>
                                </button>
                            </div>
                        </div>
                        <div class="cart-item-price font-weight-bold">441.000đ</div>
                    </div>
                    <!-- Add more cart items as needed -->
                    <!-- Another cart item with unique quantity ID -->
                    <div class="cart-item d-flex justify-content-between align-items-center p-3 border-bottom">
                        <input type="checkbox" id="selectItem2" checked>
                        <div class="cart-item-image ml-5">
                            <img src="https://via.placeholder.com/80x80" alt="Product Image" class="img-fluid" />
                        </div>
                        <div class="cart-item-details ml-3">
                            <p class="cart-item-name mb-0">Áo thun nam ngắn tay sọc ngang. Loose</p>
                            <p class="cart-item-description text-muted mb-2">TOFU, M</p>
                            <div class="cart-item-actions">
                                <button class="btn-quantity" onclick="changeQuantity(-1, 'quantity2')">-</button>
                                <input type="text" class="item-quantity text-center" id="quantity2" value="1">
                                <button class="btn-quantity" onclick="changeQuantity(1, 'quantity2')">+</button>
                                <button class="remove-item-btn ml-2">
                                    <i class="bi bi-trash"></i>
                                </button>
                            </div>
                        </div>
                        <div class="cart-item-price font-weight-bold">441.000đ</div>
                    </div>

                    <!-- Another cart item with unique quantity ID -->
                    <div class="cart-item d-flex justify-content-between align-items-center p-3 border-bottom">
                        <input type="checkbox" id="selectItem2" checked>
                        <div class="cart-item-image ml-5">
                            <img src="https://via.placeholder.com/80x80" alt="Product Image" class="img-fluid" />
                        </div>
                        <div class="cart-item-details ml-3">
                            <p class="cart-item-name mb-0">Áo thun nam ngắn tay sọc ngang. Loose</p>
                            <p class="cart-item-description text-muted mb-2">TOFU, M</p>
                            <div class="cart-item-actions">
                                <button class="btn-quantity" onclick="changeQuantity(-1, 'quantity3')">-</button>
                                <input type="text" class="item-quantity text-center" id="quantity3" value="1">
                                <button class="btn-quantity" onclick="changeQuantity(1, 'quantity3')">+</button>
                                <button class="remove-item-btn ml-2">
                                    <i class="bi bi-trash"></i>
                                </button>
                            </div>
                        </div>
                        <div class="cart-item-price font-weight-bold">441.000đ</div>
                    </div>
                </div>
            </div>


            <!-- Total Summary Section -->
            <div class="col-md-4">
                <div class="total-summary">
                    <p class="mb-2 font-weight-bold">Tổng đơn hàng:</p>
                    <h4 class="total-price">441.000đ</h4>
                    <button class="btn btn-dark btn-block mt-1">ĐẶT HÀNG</button>
                    <button class="btn btn-outline-dark btn-block mt-1">TIẾP TỤC MUA SẮM</button>
                </div>
            </div>
        </div>
    </div>

    <script>
        function changeQuantity(change, inputId) {
            var quantityInput = document.getElementById(inputId);
            var currentQuantity = parseInt(quantityInput.value);

            if (!isNaN(currentQuantity)) {
                var newQuantity = currentQuantity + change;

                // Ensure that the quantity is not less than 1
                if (newQuantity > 0) {
                    quantityInput.value = newQuantity;
                }
            }
        }

    </script>
    
    
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
</body>

</html>
</body>
</html>