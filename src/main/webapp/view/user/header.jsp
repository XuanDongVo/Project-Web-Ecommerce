<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="ISO-8859-1">
    <title>ECOMMERCE</title>
    <link rel="stylesheet" href="../../css/Navbar.css">
    <link rel="stylesheet" href="../../css/SearchBar.css">

    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="../../adding/bootstrap/boostrap.min.css">

    <!-- jQuery -->
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    <script src="../../adding/bootstrap/bootstrap.bundle.min.js"></script>

    <script src="../../js/navbar.js"></script>
</head>
<body>
    <div class="hero" style="display: flex;">
        <div class="nav-header">
            <a href="home" style="text-decoration: none; color: inherit; z-index: 3; margin-top: 3.5rem; position: fixed; z-index: 9999;">
                <p style="color: white;">ECOMMERCE</p>
            </a>
        </div>
        <div class="shop-navbar">
            <ul class="nav-menu">
                <!-- Render categories here -->
                <li class="nav-item">
                    <a href="/product/gender/nam" style="text-decoration: none; color: inherit;">NAM</a>
                    <hr />

                    <div class="subcategories" style="margin: 0; width: 30rem;">
                        <!-- SUBCATEGORY 1 -->
                        <a href="javascript:void(0)" onclick="toggleSubcategory(1)" style="text-decoration: none;">
                            <p style="display: inline;"> SUBCATEGORY 1 +</p>
                        </a>
                        <!-- Container cho các mục con của subcategory 1 -->
                        <div id="subcategory1-extra" class="subcategory-extra" style="display: none; margin-left: 8%;">
                            <a href="#" style="text-decoration: none;">
                                <p>Extra Subcategory 1.1</p>
                            </a>
                            <a href="#" style="text-decoration: none;">
                                <p>Extra Subcategory 1.2</p>
                            </a>
                        </div>

                        <!-- SUBCATEGORY 2 -->
                        <a href="javascript:void(0)" onclick="toggleSubcategory(2)" style="text-decoration: none;">
                            <p>SUBCATEGORY 2 +</p>
                        </a>
                        <!-- Container cho các mục con của subcategory 2 -->
                        <div id="subcategory2-extra" class="subcategory-extra" style="display: none;margin-left: 8%;">
                            <a href="#" style="text-decoration: none;">
                                <p>Extra Subcategory 2.1</p>
                            </a>
                            <a href="#" style="text-decoration: none;">
                                <p>Extra Subcategory 2.2</p>
                            </a>
                        </div>

                        <!-- SUBCATEGORY 3 -->
                        <a href="javascript:void(0)" onclick="toggleSubcategory(3)" style="text-decoration: none;">
                            <p>SUBCATEGORY 3 +</p>
                        </a>
                        <!-- Container cho các mục con của subcategory 3 -->
                        <div id="subcategory3-extra" class="subcategory-extra" style="display: none;margin-left: 8%;">
                            <a href="#" style="text-decoration: none;">
                                <p>Extra Subcategory 3.1</p>
                            </a>
                            <a href="#" style="text-decoration: none;">
                                <p>Extra Subcategory 3.2</p>
                            </a>
                        </div>
                    </div>

                </li>
                <span style="font-size: 2rem; margin-bottom:2%;">|</span>
                <li class="nav-item">
                    <a href="/product/gender/nữ" style="text-decoration: none; color: inherit;">NỮ</a>
                    <hr />
                    <div class="subcategories" style="margin: 0; width: 30rem;">
                        <!-- SUBCATEGORY 4 -->
                        <a href="javascript:void(0)" onclick="toggleSubcategory(4)" style="text-decoration: none;">
                            <p style="display: inline;"> SUBCATEGORY 4 +</p>
                        </a>
                        <!-- Container cho các mục con của subcategory 4 -->
                        <div id="subcategory4-extra" class="subcategory-extra" style="display: none;margin-left: 8%;">
                            <a href="#" style="text-decoration: none;">
                                <p>Extra Subcategory 4.1</p>
                            </a>
                            <a href="#" style="text-decoration: none;">
                                <p>Extra Subcategory 4.2</p>
                            </a>
                        </div>

                        <!-- SUBCATEGORY 5 -->
                        <a href="javascript:void(0)" onclick="toggleSubcategory(5)" style="text-decoration: none;">
                            <p>SUBCATEGORY 5 +</p>
                        </a>
                        <!-- Container cho các mục con của subcategory 5 -->
                        <div id="subcategory5-extra" class="subcategory-extra" style="display: none;margin-left: 8%;">
                            <a href="#" style="text-decoration: none;">
                                <p>Extra Subcategory 5.1</p>
                            </a>
                            <a href="#" style="text-decoration: none;">
                                <p>Extra Subcategory 5.2</p>
                            </a>
                        </div>
                    </div>
                </li>
            </ul>

            <div class="nav-login-cart">
                <!-- Search bar -->
                <div class="search-bar" style="margin: 0.5px; border: none; border-bottom: 2px solid white; outline: none; background-color: transparent; display: flex; align-items: center;">
                    <span style="font-size: 1rem; color: white;">
                        <img src="../../img/search.png" alt="" style="height: 20px;">
                    </span>
                    <input type="text" id="searchInput" placeholder="Tìm kiếm" style="background: transparent; outline: none; border: none; color: white;">
                </div>

                <!-- User icon and login options -->
                <div class="user-menu">
                    <span class="user-icon" style="color: white; font-size: 2rem; cursor: pointer;">
                        <img src="../../img/avatar.png" alt="" style="height: 30px; margin-top: 5%;">
                    </span>
                    <div class="user-options" style="display: none;">
                        <!-- Login/Logout and Profile Links -->
                        <a href=""><button style="color: white; width: 100%; height: 40px; margin-bottom: 0.5rem; font-size: 15px; background-color: black;">Đăng nhập</button></a>
                        <!-- <a href=""><button style="color: white; width: 100%; height: 40px; margin-bottom: 0.5rem; font-size: 15px; background-color: black;">My Profile</button></a>
                        <a href=""><button style="color: white; width: 100%; height: 40px; margin-bottom: 0.5rem; font-size: 15px; background-color: black;">Logout</button></a> -->
                    </div>
                </div>

                <!-- Heart icon -->
                <div class="heart">
                    <span style="color: white; font-size: 2rem; cursor: pointer;">
                        <img src="../../img/heart.png" alt="" style="height: 25px; opacity: 1;">
                    </span>
                </div>

                <!-- Cart icon -->
                <div class="cart">
                    <a href="#" data-bs-toggle="offcanvas" data-bs-target="#cartOffcanvas">
                        <span style="color: white; font-size: 2rem; cursor: pointer;">
                            <img src="../../img/shopping-cart.png" alt="" style="height: 30px; padding-left: 15px;">
                        </span>
                    </a>
                </div>

                <!-- Offcanvas (cart) -->
                <div class="offcanvas offcanvas-end" tabindex="-1" id="cartOffcanvas" aria-labelledby="cartOffcanvasLabel">
                    <div class="offcanvas-header">
                        <h5 class="offcanvas-title" id="cartOffcanvasLabel">Giỏ hàng của tôi</h5>
                        <button type="button" class="btn-close" data-bs-dismiss="offcanvas" aria-label="Close"></button>
                    </div>
                    <div class="offcanvas-body">
                        <!-- Cart items here -->
                    </div>
                    <div class="offcanvas-footer">
                        <button class="view-cart-btn" onclick="redirectCartDetail()">Xem giỏ hàng</button>
                    </div>
                </div>
                <div class="nav-cart-count"></div>
            </div>
        </div>
    </div>

    <!-- Modal (optional) -->
    <!-- <div class="modal fade" id="exampleModalCenter" tabindex="-1" role="dialog" aria-labelledby="exampleModalCenterTitle" aria-hidden="true">
        <div class="modal-dialog modal-dialog-centered" role="document">
            <div class="modal-content">
                <div class="modal-header text-center">
                    <h5 class="modal-title" id="exampleModalLongTitle">Giỏ hàng trống</h5>
                    <button type="button" class="btn-close" data-dismiss="modal" aria-label="Close" onclick="closeModal()"></button>
                </div>
                <div class="modal-body">Bạn chưa có sản phẩm nào trong giỏ hàng. Tiếp tục mua hàng nào!</div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-dark" data-dismiss="modal" onclick="closeModal()">Mua Hàng</button>
                </div>
            </div>
        </div>
    </div> -->
</body>
</html>
