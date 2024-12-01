// Hàm để toggle (hiện/ẩn) mục con của subcategory 1
function toggleSubcategory(subcategoryId) {
	const subcategory = document.getElementById(`subcategory${subcategoryId}-extra`);
	if (subcategory) {
		subcategory.style.display = (subcategory.style.display === "none" ? "block" : "none");
	}
}

document.addEventListener("DOMContentLoaded", function() {
	const searchInput = document.getElementById('searchInput');

	if (searchInput) {
		searchInput.addEventListener('keydown', (event) => {
			if (event.key === 'Enter') {
				handleSearch();
			}
		});
	}
});


document.addEventListener("DOMContentLoaded", function() {
	const userIcon = document.querySelector('.user-icon');
	const userOptions = document.querySelector('.user-options');

	userIcon.addEventListener('click', function() {
		// Toggle hiển thị menu
		if (userOptions.style.display === 'flex') {
			userOptions.style.display = 'none';
			userOptions.style.opacity = '0';
		} else {
			userOptions.style.display = 'flex';
			userOptions.style.opacity = '1';
		}
	});

	// Đóng menu nếu click ra bên ngoài
	/*document.addEventListener('click', function(event) {
		if (!userMenu.contains(event.target)) {
			userOptions.style.display = 'none';
			userOptions.style.opacity = '0';
		}
	});*/
});



window.addEventListener('scroll', () => {
	const navbar = document.querySelector('.shop-navbar');
	if (window.scrollY > 50) {
		navbar.classList.add('scrolled');
	} else {
		navbar.classList.remove('scrolled');
	}
});



/*render dropListCategory*/
function renderDataDropListCategoryHeader(dropListCategory) {
	// Lấy phần tử HTML chứa menu để chèn dữ liệu mới vào
	const navMenu = document.querySelector('.nav-menu');

	// Xóa nội dung cũ nếu có
	navMenu.innerHTML = '';

	// Duyệt qua các phần tử trong dropListCategory và tạo cấu trúc HTML
	for (const categoryKey in dropListCategory) {
		if (dropListCategory.hasOwnProperty(categoryKey)) {
			// Tạo thẻ <li> cho mỗi category
			const navItem = document.createElement('li');
			navItem.className = 'nav-item';

			// Tạo liên kết category
			const categoryLink = document.createElement('a');
			categoryLink.href = `gender?gender=${categoryKey}`;
			categoryLink.style.textDecoration = 'none';
			categoryLink.style.color = 'inherit';
			categoryLink.innerText = categoryKey.toUpperCase();

			// Chèn liên kết category vào <li>
			navItem.appendChild(categoryLink);

			// Tạo thẻ <hr>
			const hr = document.createElement('hr');
			navItem.appendChild(hr);

			// Tạo div chứa subcategories
			const subcategoriesDiv = document.createElement('div');
			subcategoriesDiv.className = 'subcategories';
			subcategoriesDiv.style.margin = '0';
			subcategoriesDiv.style.width = '30rem';


			// Duyệt qua subcategories của category hiện tại
			const subcategories = dropListCategory[categoryKey];
			for (const subCategoryKey in subcategories) {
				if (subcategories.hasOwnProperty(subCategoryKey)) {
					// Tạo liên kết cho subcategory
					const subCategoryLink = document.createElement('a');

					const subCategoryParagraph = document.createElement('p');
					subCategoryParagraph.style.display = 'inline';
					subCategoryParagraph.innerText = subCategoryKey.toUpperCase();
					subCategoryParagraph.onclick = () => {
						window.location.href = 'category?category=' + subCategoryKey;
					}
					subCategoryLink.appendChild(subCategoryParagraph);

					// Thêm nút toggle cho subcategory
					const toggleSpan = document.createElement('span');
					toggleSpan.style.color = 'red';
					toggleSpan.innerText = ' + ';
					toggleSpan.onclick = () => toggleSubcategory(subCategoryKey);

					// Thêm liên kết subcategory và nút toggle vào subcategoriesDiv
					subcategoriesDiv.appendChild(subCategoryLink);
					subcategoriesDiv.appendChild(toggleSpan);

					// Tạo div chứa các extra subcategories
					const extraSubcategoriesDiv = document.createElement('div');
					extraSubcategoriesDiv.id = `subcategory${subCategoryKey}-extra`;
					extraSubcategoriesDiv.className = 'subcategory-extra';
					extraSubcategoriesDiv.style.display = 'none';
					extraSubcategoriesDiv.style.marginLeft = '8%';

					// Duyệt qua các extra subcategories
					const extraSubCategories = subcategories[subCategoryKey];
					extraSubCategories.forEach(extraSubCategory => {
						const extraSubCategoryLink = document.createElement('a');
						extraSubCategoryLink.href = '#';
						extraSubCategoryLink.style.textDecoration = 'none';

						const extraSubCategoryParagraph = document.createElement('p');
						extraSubCategoryParagraph.innerText = extraSubCategory.toUpperCase();
						extraSubCategoryParagraph.onclick = () => {
							window.location.href = 'subcategory?subCategory=' + extraSubCategory;
						}
						extraSubCategoryLink.appendChild(extraSubCategoryParagraph);

						// Thêm extra subcategory vào extraSubcategoriesDiv
						extraSubcategoriesDiv.appendChild(extraSubCategoryLink);
					});

					// Thêm extraSubcategoriesDiv vào subcategoriesDiv
					subcategoriesDiv.appendChild(extraSubcategoriesDiv);
				}
			}

			// Thêm subcategoriesDiv vào navItem
			navItem.appendChild(subcategoriesDiv);

			// Thêm dấu "|" sau mỗi category
			const separator = document.createElement('span');
			separator.style.fontSize = '2rem';
			separator.style.marginBottom = '2%';
			separator.innerText = '|';

			// Thêm navItem và separator vào navMenu
			navMenu.appendChild(navItem);
			navMenu.appendChild(separator);
		}
	}
}

/*render quantity*/
function renderDataQuantityProductHeader(quantityProduct) {
	// Lấy phần tử HTML có class 'nav-cart-count'
	const navCartCount = document.querySelector('.nav-cart-count');

	// Xóa nội dung cũ nếu có
	navCartCount.innerHTML = '';

	// Thêm số lượng sản phẩm mới vào
	navCartCount.innerText = quantityProduct;

}


/*render ListCartDetail*/
function renderDataListCartDetailHeader(listCartDetail) {
	// Lấy phần tử chứa các item trong giỏ hàng
	const offcanvasBody = document.querySelector('.offcanvas-body');

	// Xóa nội dung cũ nếu có
	offcanvasBody.innerHTML = '';

	if (listCartDetail == null || listCartDetail.length === 0) {
		// Render thông báo khi giỏ hàng trống
		const noProductsMessage = `
            <div style="height: 120px; display: flex; align-items: center; justify-content: center; flex-direction: column; padding: 0px 23px; text-align: center;">
                Bạn chưa có sản phẩm nào trong giỏ hàng
            </div>
        `;
		offcanvasBody.innerHTML = noProductsMessage;
	}

	// Lặp qua từng item trong listCartDetail và tạo phần tử HTML cho mỗi item
	listCartDetail.forEach(item => {

		// Tạo phần tử chứa item
		const cartItem = document.createElement('div');
		cartItem.className = 'cart-item';

		// Tạo phần tử hình ảnh của sản phẩm
		const cartItemImage = document.createElement('div');
		cartItemImage.className = 'cart-item-image';
		const img = document.createElement('img');
		img.src = item.image;
		img.alt = 'Product Image';
		cartItemImage.appendChild(img);

		// Tạo phần tử chi tiết sản phẩm
		const cartItemDetails = document.createElement('div');
		cartItemDetails.className = 'cart-item-details';

		const name = document.createElement('p');
		name.className = 'cart-item-name';
		name.innerText = item.name;

		const description = document.createElement('p');
		description.className = 'cart-item-description';
		description.innerText = item.color + ' , ' + item.size;

		const cartItemActions = document.createElement('div');
		cartItemActions.className = 'cart-item-actions';

		const quantity = document.createElement('span');
		quantity.className = 'cart-item-quantity';
		quantity.innerText = 'SL: ' + item.quantity;

		const removeBtn = document.createElement('button');
		removeBtn.className = 'remove-item-btn';

		const removeIcon = document.createElement('img');
		removeIcon.src = 'https://via.placeholder.com/20x20';
		/*	removeIcon.src = 'img/remove.png';*/
		removeIcon.alt = 'Remove Icon';
		removeBtn.appendChild(removeIcon);

		// Gán sự kiện onclick cho nút xóa
		removeBtn.onclick = function() {
			// Lấy URL hiện tại
			var currentUrl = window.location.href;
			// Mã hóa URL hiện tại để có thể truyền an toàn qua request
			var encodedUrl = encodeURIComponent(currentUrl);
			window.location.href = 'cartdetail?action=remove&cartId=' + item.cartId + '&redirectUrl=' + encodedUrl;
		};

		// Gắn các phần tử con vào cartItemActions
		cartItemActions.appendChild(quantity);
		cartItemActions.appendChild(removeBtn);

		// Gắn các phần tử con vào cartItemDetails
		cartItemDetails.appendChild(name);
		cartItemDetails.appendChild(description);
		cartItemDetails.appendChild(cartItemActions);

		// Tạo phần tử giá sản phẩm
		const cartItemPrice = document.createElement('div');
		cartItemPrice.className = 'cart-item-price';
		cartItemPrice.innerText = `${item.price}`;

		// Gắn các phần tử vào cartItem
		cartItem.appendChild(cartItemImage);
		cartItem.appendChild(cartItemDetails);
		cartItem.appendChild(cartItemPrice);

		// Thêm cartItem vào offcanvasBody
		offcanvasBody.appendChild(cartItem);
	});
}


function redirectCartDetail() {
	window.location.href = 'cartdetail?action=get';
}





