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
    <!-- <link rel="stylesheet" href="./css/SearchBar.css"> -->
    <!-- <link rel="stylesheet" href="./css/Popular.css"> -->
    <!-- <link rel="stylesheet" href="./css/Item.css"> -->
    <link rel="stylesheet" href="../../css/NewCollections.css">
    <!-- <link rel="stylesheet" href="./css/NewLetter.css"> -->
    <link rel="stylesheet" href="../../css/Footer.css">
    <link rel="stylesheet" href="../../css/Cate.css">


    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="../../adding/bootstrap/boostrap.min.css">
    <!-- <script src="./adding/jquery/jquery-3.4.1.min.js"></script>
    <script src="./adding/poper/poper.min.js"></script>
    <script src="./adding/bootstrap/boostrap.min.js"></script>  -->

    <script src="../../adding/bootstrap/bootstrap.bundle.min.js"></script>

    <script src="../../js/cate_filter.js"></script>
    <script src = "../../js/product_collection.js"></script>
    <script src="../../js/navbar.js"></script>
</head>

<body>
    <div class="hero" style="display: flex;">
        <div class="nav-header">
            <a href="/" style="text-decoration: none; color: inherit; z-index: 3; margin-top: 3.5rem; position: fixed;  z-index: 9999;">
                <p style="color: white;">XUN_DON</p>
            </a>
        </div>
        <div class="shop-navbar">
            <ul class="nav-menu">
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
                <div class="search-bar"
                    style="margin: 0.5px; border: none; border-bottom: 2px solid white; outline: none; background-color: transparent; display: flex; align-items: center;">
                    <span style="font-size: 1rem; color: white;"><img src="../../img/search.png" alt=""
                            style="height: 20px;"></span> <!-- Search icon placeholder -->
                    <input type="text" placeholder="Tìm kiếm"
                        style="background: transparent; outline: none; border: none; color: white;">
                </div>

                <!-- User icon and hidden login/register options -->
                <div class="user-menu">
                    <span class="user-icon" style="color: white; font-size: 2rem; cursor: pointer;">
                        <img src="../../img/avatar.png" alt="" style="height: 30px; margin-top: 5%;">
                    </span>
                    <div class="user-options" style="display: none;">
                        <button
                            style="color: white; width: 100%; height: 40px; margin-bottom: 0.5rem; font-size: 15px; background-color: black;">
                            Đăng nhập
                        </button>
                        <button
                            style="color: black; width: 100%; height: 40px; font-size: 15px; background-color: white; border: 1px solid black">
                            Đăng ký
                        </button>
                    </div>
                </div>


                <!-- Heart icon -->
                <div class="heart">
                    <span style="color: white; font-size: 2rem; cursor: pointer;"><img src="../../img/heart.png" alt=""
                            style="height: 25px; opacity: 1;"></span>
                </div>

                <!-- Cart icon -->
                <div class="cart">
                    <a href="#" data-bs-toggle="offcanvas" data-bs-target="#cartOffcanvas">
                        <span style="color: white; font-size: 2rem; cursor: pointer;">
                            <img src="../../img/shopping-cart.png" alt="" style="height: 30px; padding-left: 15px;">
                        </span>
                    </a>
                </div>

                <!-- Offcanvas (cart frame) nằm bên phải -->
                <div class="offcanvas offcanvas-end" tabindex="-1" id="cartOffcanvas"
                    aria-labelledby="cartOffcanvasLabel">
                    <div class="offcanvas-header">
                        <h5 class="offcanvas-title" id="cartOffcanvasLabel">Giỏ hàng của tôi</h5>
                        <button type="button" class="btn-close" data-bs-dismiss="offcanvas" aria-label="Close"></button>
                    </div>
                    <div class="offcanvas-body">
                        <!-- Cart Item -->
                        <div class="cart-item">
                            <div class="cart-item-image">
                                <img src="https://via.placeholder.com/100x100" alt="Product Image" />
                            </div>
                            <div class="cart-item-details">
                                <p class="cart-item-name">Áo Polo nam phối màu. Fitted</p>
                                <p class="cart-item-description">BRIGHT WHITE, L</p>
                                <div class="cart-item-actions">
                                    <span class="cart-item-quantity">SL: 1</span>
                                    <button class="remove-item-btn">
                                        <img src="https://via.placeholder.com/20x20" alt="Remove Icon">
                                    </button>
                                </div>
                            </div>
                            <div class="cart-item-price">569.000đ</div>
                        </div>

                        <div class="cart-item">
                            <div class="cart-item-image">
                                <img src="https://via.placeholder.com/100x100" alt="Product Image" />
                            </div>
                            <div class="cart-item-details">
                                <p class="cart-item-name">Áo Polo nam phối màu. Fitted</p>
                                <p class="cart-item-description">BRIGHT WHITE, L</p>
                                <div class="cart-item-actions">
                                    <span class="cart-item-quantity">SL: 1</span>
                                    <button class="remove-item-btn">
                                        <img src="https://via.placeholder.com/20x20" alt="Remove Icon">
                                    </button>
                                </div>
                            </div>
                            <div class="cart-item-price">569.000đ</div>
                        </div>

                        <div class="cart-item">
                            <div class="cart-item-image">
                                <img src="https://via.placeholder.com/100x100" alt="Product Image" />
                            </div>
                            <div class="cart-item-details">
                                <p class="cart-item-name">Áo Polo nam phối màu. Fitted</p>
                                <p class="cart-item-description">BRIGHT WHITE, L</p>
                                <div class="cart-item-actions">
                                    <span class="cart-item-quantity">SL: 1</span>
                                    <button class="remove-item-btn">
                                        <img src="https://via.placeholder.com/20x20" alt="Remove Icon">
                                    </button>
                                </div>
                            </div>
                            <div class="cart-item-price">569.000đ</div>
                        </div>


                      


                       
                    </div>
                    <div class="offcanvas-footer">
                        <button class="view-cart-btn">Xem giỏ hàng (1 sản phẩm)</button>
                    </div>
                </div>



                <!-- Cart count -->
                <div class="nav-cart-count">0</div>


            </div>
        </div>
        <!-- Navbar placeholder -->
    </div>

    <div class="cate-container container">
        <div class="cate-left">
            <strong>29 Sản phẩm</strong>
            <div class="cate-left-breadcrumb" style="margin-left: 20px; margin-top: 16px; ">
                <a href="#">
                    <span class="breadcrumb" style="text-decoration: none; color: inherit;">
                        Trang chủ > <span class="current-category">Coffee Lovers</span>
                    </span>
                </a>
            </div>
        </div>
        <div class="cate-right">
            <i class="filter-icon" onclick="toggleFilter()"><img src="./img/filter.png" alt="" style="height: 20px; width: 20px;"></i>
            <i class="sort-icon">⇅</i>
            <i class="view-icon">◻</i>
            <i class="grid-icon">☷</i>
        </div>
    </div>

    <!-- Filter Panel -->
    <div class="filter-panel" id="filterPanel">
        <h4>Bộ lọc</h4>
        <div class="filter-tabs">
            <button class="tab-btn active" onclick="showTab('colorTab')">Màu</button>
            <button class="tab-btn" onclick="showTab('sizeTab')">Kích Cỡ</button>
            <button class="tab-btn" onclick="showTab('priceTab')">Giá</button>
        </div>

        <!-- Tab Content: Color -->
        <div class="tab-content" id="colorTab">
            <div class="color-options" >
                <div class="color-box red">RED</div>
                <div class="color-box brown">BROWN</div>
                <div class="color-box white">WHITE</div>
                <div class="color-box yellow">YELLOW</div>
                <div class="color-box orange">ORANGE</div>
                <div class="color-box green">GREEN</div>
                <div class="color-box blue">BLUE</div>
                <div class="color-box pink">PINK</div>
                <div class="color-box purple">PURPLE</div>
                <div class="color-box beige">BEIGE</div>
                <div class="color-box grey">GREY</div>
                <div class="color-box olive">OLIVE</div>
            </div>
        </div>

        <!-- Tab Content: Size -->
        <div class="tab-content" id="sizeTab" style="display:none;">
            <div class="size-options">
                <label><input type="checkbox"> S</label>
                <label><input type="checkbox"> M</label>
                <label><input type="checkbox"> L</label>
                <label><input type="checkbox"> XL</label>
                <label><input type="checkbox"> XXL</label>
            </div>
        </div>

        <!-- Tab Content: Price -->
        <div class="tab-content" id="priceTab" style="display:none;">
            <div class="price-range">
                <label for="price">Giá:</label>
                <input type="range" id="price" min="0" max="10000000">
                <span>0đ</span> - <span>10,000,000đ</span>
            </div>
        </div>

        <button class="reset-button">Xoá hết</button>
        <button class="result-button">Xem kết quả (29)</button>
    </div>


      <!-- Collections -->
      <div class="container new-collections mt-5">
        <h1>NEW COLLECTIONS</h1>

        <hr />

        <div class="collections">
            <!-- Sản phẩm 1 -->
            <div class="collection-item">
                <a href="/product/1">
                    <div class="image-container">
                        <img class="image-main" src="../../img/product_1.png" alt="Product 1 Image" />
                        <img class="image-hover" src="../../img/product_1_hover.png" alt="Product 1 Hover Image" />
                        <img src="../../img/add.png" alt="" class="plus">
                    </div>
                    <div class="size-container">
                        <div class="size-optionss">
                            <button class="size-btn">S</button>
                            <button class="size-btn">M</button>
                            <button class="size-btn">L</button>
                            <button class="size-btn">XL</button>
                        </div>
                    </div>
                </a>
                <div class="image-cate">
                    <img src="../../img/product_25.png" alt="" onclick="selectImage(this)">
                    <img src="../../img/product_26.png" alt="" onclick="selectImage(this)">
                    <img src="../../img/product_27.png" alt="" onclick="selectImage(this)">
                </div>
                <p>Product 1 Name</p>
                <div class="item-price-new">$100</div>
            </div>

            <!-- Sản phẩm 2 -->
            <div class="collection-item">
                <a href="/product/1">
                    <div class="image-container">
                        <img class="image-main" src="../../img/product_1.png" alt="Product 1 Image" />
                        <img class="image-hover" src="../../img/product_1_hover.png" alt="Product 1 Hover Image" />
                        <img src="../../img/add.png" alt="" class="plus">
                    </div>
                    <div class="size-container">
                        <div class="size-optionss">
                            <button class="size-btn">S</button>
                            <button class="size-btn">M</button>
                            <button class="size-btn">L</button>
                            <button class="size-btn">XL</button>
                        </div>
                    </div>
                </a>
                <div class="image-cate">
                    <img src="../../img/product_25.png" alt="" onclick="selectImage(this)">
                    <img src="../../img/product_26.png" alt="" onclick="selectImage(this)">
                    <img src="../../img/product_27.png" alt="" onclick="selectImage(this)">
                </div>
                <p>Product 1 Name</p>
                <div class="item-price-new">$100</div>
            </div>


            <!-- Sản phẩm 3 -->
           <div class="collection-item">
                <a href="/product/1">
                    <div class="image-container">
                        <img class="image-main" src="../../img/product_1.png" alt="Product 1 Image" />
                        <img class="image-hover" src="../../img/product_1_hover.png" alt="Product 1 Hover Image" />
                        <img src="../../img/add.png" alt="" class="plus">
                    </div>
                    <div class="size-container">
                        <div class="size-optionss">
                            <button class="size-btn">S</button>
                            <button class="size-btn">M</button>
                            <button class="size-btn">L</button>
                            <button class="size-btn">XL</button>
                        </div>
                    </div>
                </a>
                <div class="image-cate">
                    <img src="../../img/product_25.png" alt="" onclick="selectImage(this)">
                    <img src="../../img/product_26.png" alt="" onclick="selectImage(this)">
                    <img src="../../img/product_27.png" alt="" onclick="selectImage(this)">
                </div>
                <p>Product 1 Name</p>
                <div class="item-price-new">$100</div>
            </div>

            <!-- Sản phẩm 4 -->
            <div class="collection-item">
                <a href="/product/1">
                    <div class="image-container">
                        <img class="image-main" src="../../img/product_1.png" alt="Product 1 Image" />
                        <img class="image-hover" src="../../img/product_1_hover.png" alt="Product 1 Hover Image" />
                        <img src="../../img/add.png" alt="" class="plus">
                    </div>
                    <div class="size-container">
                        <div class="size-optionss">
                            <button class="size-btn">S</button>
                            <button class="size-btn">M</button>
                            <button class="size-btn">L</button>
                            <button class="size-btn">XL</button>
                        </div>
                    </div>
                </a>
                <div class="image-cate">
                    <img src="../../img/product_25.png" alt="" onclick="selectImage(this)">
                    <img src="../../img/product_26.png" alt="" onclick="selectImage(this)">
                    <img src="../../img/product_27.png" alt="" onclick="selectImage(this)">
                </div>
                <p>Product 1 Name</p>
                <div class="item-price-new">$100</div>
            </div>

            <!-- Sản phẩm 5 -->
           <div class="collection-item">
                <a href="/product/1">
                    <div class="image-container">
                        <img class="image-main" src="../../img/product_1.png" alt="Product 1 Image" />
                        <img class="image-hover" src="../../img/product_1_hover.png" alt="Product 1 Hover Image" />
                        <img src="../../img/add.png" alt="" class="plus">
                    </div>
                    <div class="size-container">
                        <div class="size-optionss">
                            <button class="size-btn">S</button>
                            <button class="size-btn">M</button>
                            <button class="size-btn">L</button>
                            <button class="size-btn">XL</button>
                        </div>
                    </div>
                </a>
                <div class="image-cate">
                    <img src="../../img/product_25.png" alt="" onclick="selectImage(this)">
                    <img src="../../img/product_26.png" alt="" onclick="selectImage(this)">
                    <img src="../../img/product_27.png" alt="" onclick="selectImage(this)">
                </div>
                <p>Product 1 Name</p>
                <div class="item-price-new">$100</div>
            </div>

            <!-- Sản phẩm 6 -->
           <div class="collection-item">
                <a href="/product/1">
                    <div class="image-container">
                        <img class="image-main" src="../../img/product_1.png" alt="Product 1 Image" />
                        <img class="image-hover" src="../../img/product_1_hover.png" alt="Product 1 Hover Image" />
                        <img src="../../img/add.png" alt="" class="plus">
                    </div>
                    <div class="size-container">
                        <div class="size-optionss">
                            <button class="size-btn">S</button>
                            <button class="size-btn">M</button>
                            <button class="size-btn">L</button>
                            <button class="size-btn">XL</button>
                        </div>
                    </div>
                </a>
                <div class="image-cate">
                    <img src="../../img/product_25.png" alt="" onclick="selectImage(this)">
                    <img src="../../img/product_26.png" alt="" onclick="selectImage(this)">
                    <img src="../../img/product_27.png" alt="" onclick="selectImage(this)">
                </div>
                <p>Product 1 Name</p>
                <div class="item-price-new">$100</div>
            </div>

            <!-- Sản phẩm 7 -->
            <div class="collection-item">
                <a href="/product/1">
                    <div class="image-container">
                        <img class="image-main" src="../../img/product_1.png" alt="Product 1 Image" />
                        <img class="image-hover" src="../../img/product_1_hover.png" alt="Product 1 Hover Image" />
                        <img src="../../img/add.png" alt="" class="plus">
                    </div>
                    <div class="size-container">
                        <div class="size-optionss">
                            <button class="size-btn">S</button>
                            <button class="size-btn">M</button>
                            <button class="size-btn">L</button>
                            <button class="size-btn">XL</button>
                        </div>
                    </div>
                </a>
                <div class="image-cate">
                    <img src="../../img/product_25.png" alt="" onclick="selectImage(this)">
                    <img src="../../img/product_26.png" alt="" onclick="selectImage(this)">
                    <img src="../../img/product_27.png" alt="" onclick="selectImage(this)">
                </div>
                <p>Product 1 Name</p>
                <div class="item-price-new">$100</div>
            </div>

            <!-- Sản phẩm 8 -->
           <div class="collection-item">
                <a href="/product/1">
                    <div class="image-container">
                        <img class="image-main" src="../../img/product_1.png" alt="Product 1 Image" />
                        <img class="image-hover" src="../../img/product_1_hover.png" alt="Product 1 Hover Image" />
                        <img src="../../img/add.png" alt="" class="plus">
                    </div>
                    <div class="size-container">
                        <div class="size-optionss">
                            <button class="size-btn">S</button>
                            <button class="size-btn">M</button>
                            <button class="size-btn">L</button>
                            <button class="size-btn">XL</button>
                        </div>
                    </div>
                </a>
                <div class="image-cate">
                    <img src="../../img/product_25.png" alt="" onclick="selectImage(this)">
                    <img src="../../img/product_26.png" alt="" onclick="selectImage(this)">
                    <img src="../../img/product_27.png" alt="" onclick="selectImage(this)">
                </div>
                <p>Product 1 Name</p>
                <div class="item-price-new">$100</div>
            </div>
            <!-- Sản phẩm thêm -->
            <!-- Thêm các sản phẩm tương tự -->
        </div>
    </div>

    <!-- <!-- Pagination 
    <div class="pagination">
        <a href="#">&laquo;</a> Nút trước
        <a href="#" class="active">1</a> Trang hiện tại
        <a href="#">2</a>
        <a href="#">3</a>
        <a href="#">4</a>
        <a href="#">5</a>
        <a href="#">&raquo;</a> Nút tiếp theo
    </div> -->

    <!-- Footer -->
    <div class="footer">
        <div class="footer-logo">
            <img src="../../footer_logo.png" alt="" />
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
                <img src="../../instagram.png" alt="" />
            </div>
            <div class="footer-icons-container">
                <img src="../../pinterest.png" alt="" />
            </div>
            <div class="footer-icons-container">
                <img src="../../whatsapp.png" alt="" />
            </div>
        </div>
        <div class="footer-copyright">
            <hr />
            <p>© 2023 by Shoppee. Proudly created with Wix.com</p>
        </div>
    </div>
</body>

</html>