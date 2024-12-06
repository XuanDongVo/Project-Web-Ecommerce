<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Document</title>
    <link rel="stylesheet" href="../../css/Navbar_procate.css">
    <link rel="stylesheet" href="../../css/Hero_procate.css">
    <link rel="stylesheet" href="../../css/SearchBar.css">
    <link rel="stylesheet" href="../../css/Footer.css">
    <link rel="stylesheet" href="../../css/Cate.css">



    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="../../adding/bootstrap/boostrap.min.css">
    <!-- <script src="./adding/jquery/jquery-3.4.1.min.js"></script>
    <script src="./adding/poper/poper.min.js"></script>
    <script src="./adding/bootstrap/boostrap.min.js"></script>  -->

    <script src="../../adding/bootstrap/bootstrap.bundle.min.js"></script>
    <script src="../../js/navbar.js"></script>
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

    <!--
    
-->
    <div class="container mt-4">
        <div class="row">
            <!-- Left Section: Image Gallery -->
            <div class="col-md-4">
                <div class="main-image mb-3">
                    <img id="mainImage" src="../../img/product_1.png" class="img-fluid" alt="Product Image"
                        style="width: 100%;">
                </div>
                <!-- <div class="thumbnail-images d-flex justify-content-start">
                <img src="./img/product_25.png" class="img-thumbnail me-2 thumbnail" data-large="./img/product_25.png" alt="Thumb 1">
                <img src="./img/product_26.png" class="img-thumbnail me-2 thumbnail" data-large="./img/product_26.png" alt="Thumb 2">
                <img src="./img/product_27.png" class="img-thumbnail me-2 thumbnail" data-large="./img/product_27.png" alt="Thumb 3">
            </div> -->
            </div>

            <!-- Right Section: Product Info -->
            <div class="col-md-8">
                <h2 id="productTitle">Áo thun nam tay ngắn thể thao. Fitted</h2>
                <p>SKU: <span id="productSKU">10S24TSSA003_001</span></p>
                <p><span id="productPrice">324,000 đ</span></p>
                <p><span class="text-muted">★ 99 sản phẩm đã bán</span></p>

                <!-- Color Options -->
                <h5>Dark Sapphire</h5>
                <div class="color-options d-flex">
                    <button class="btn btn-outline-primary color-btn" style="background-color: #d0d2d3;"
                        data-image="./img/product_25.png"></button>
                    <button class="btn btn-outline-primary color-btn" style="background-color: #2e3b55;"
                        data-image="./img/product_26.png"></button>
                    <button class="btn btn-outline-primary color-btn" style="background-color: #4b4439;"
                        data-image="./img/product_27.png"></button>
                </div>

                <!-- Size Selector -->
                <div class="size-options mt-3 mb-3">
                    <div class="size-buttons d-flex">
                        <button class="btn btn-outline-primary size-btn" data-size="S">S</button>
                        <button class="btn btn-outline-primary size-btn" data-size="M">M</button>
                        <button class="btn btn-outline-primary size-btn" data-size="L">L</button>
                        <button class="btn btn-outline-primary size-btn" data-size="XL">XL</button>
                    </div>
                </div>

                <!-- Quantity and Add to Cart -->
                <div class="row align-items-center mb-3">
                    <div class="col-2">
                        <div class="input-group">
                            <button class="btn btn-outline-secondary" id="decreaseQuantity">-</button>
                            <input type="number" id="quantity" class="form-control text-center quantity-input" value="1"
                                min="1">
                            <button class="btn btn-outline-secondary" id="increaseQuantity">+</button>
                        </div>
                    </div>
                    <div class="col-10">
                        <button class="btn btn-primary w-100 btn-cart">Thêm vào giỏ hàng</button>
                    </div>
                </div>

                <!-- <button class="btn btn-success w-100">Đặt Hàng Ngay</button> -->
            </div>
        </div>
    </div>

    <style>
        .main-image {
            border: 1px solid #ddd;
            padding: 5px;
        }

        .thumbnail {
            cursor: pointer;
            width: 80px;
            height: auto;
        }

        .color-options button {
            width: 40px;
            height: 40px;
            margin-right: 10px;
            border: 2px solid #999999;
        }
        .color-options button:hover{
            border-color: black;
        }

        .color-options button:active{
            border-color: black;
        }
        .size-btn.active {
            background-color: white;            /* color: white; */
            color: black;
            border-color: black;
        }
        .size-btn:hover{
            border-color: black;
            background-color: white;   
            color: black;
        }
        .size-btn:active{
            border-color: black;
            background-color: white;   
            color: black;
        }
        .size-options .size-btn {
            width: 50px;
            margin-right: 10px;
        }

        .size-buttons button {
            border-color: #999999;
            color: black;
        }

        .btn-cart {
            color: black;
            background-color: white;
            border-color: #999999;
        }

        .btn-cart:hover {
            border-color: black;
            background-color: white;
            color: black;
        }

        .quantity-input {
            border: 1px solid #999999;
            width: 2px;
        }

        /* Hide spinner arrows in Chrome, Safari, Edge, and Opera */
        .quantity-input::-webkit-outer-spin-button,
        .quantity-input::-webkit-inner-spin-button {
            -webkit-appearance: none;
            margin: 0;
        }

        /* Hide spinner arrows in Firefox */
        .quantity-input[type=number] {
            -moz-appearance: textfield;
        }
    </style>

    <script>
        // Handle thumbnail click to change main image
        document.querySelectorAll('.thumbnail').forEach((thumbnail) => {
            thumbnail.addEventListener('click', function () {
                const largeImage = this.src;
                document.getElementById('mainImage').src = largeImage;
            });
        });

        // Handle color button click to change main image
        document.querySelectorAll('.color-btn').forEach((colorBtn) => {
            colorBtn.addEventListener('click', function () {
                const colorImage = this.getAttribute('data-image');
                document.getElementById('mainImage').src = colorImage;
            });
        });

        // Handle quantity increment and decrement
        document.getElementById('increaseQuantity').addEventListener('click', function () {
            const quantityInput = document.getElementById('quantity');
            quantityInput.value = parseInt(quantityInput.value) + 1;
        });

        document.getElementById('decreaseQuantity').addEventListener('click', function () {
            const quantityInput = document.getElementById('quantity');
            if (quantityInput.value > 1) {
                quantityInput.value = parseInt(quantityInput.value) - 1;
            }
        });

        // Handle size button click to highlight selected size
        document.querySelectorAll('.size-btn').forEach((sizeBtn) => {
            sizeBtn.addEventListener('click', function () {
                document.querySelectorAll('.size-btn').forEach((btn) => {
                    btn.classList.remove('active');
                });
                this.classList.add('active');
            });
        });


// Handle size button click to highlight selected size
document.querySelectorAll('.size-btn').forEach((sizeBtn) => {
    sizeBtn.addEventListener('click', function () {
        document.querySelectorAll('.size-btn').forEach((btn) => {
            btn.classList.remove('active');
        });
        this.classList.add('active');
    });
});

        
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