function toggleFilter() {
	const filterPanel = document.getElementById('filterPanel');
	const isVisible = filterPanel.style.display === 'block';
	filterPanel.style.display = isVisible ? 'none' : 'block';

	// Nếu filterPanel đang hiển thị, thêm sự kiện click ngoài để đóng
	if (!isVisible) {
		document.addEventListener('click', outsideClickListener);
	} else {
		document.removeEventListener('click', outsideClickListener);
	}
}

function outsideClickListener(event) {
	const filterPanel = document.getElementById('filterPanel');
	const filterIcon = document.querySelector('.filter-icon');

	// Kiểm tra nếu nhấp chuột không nằm trong filterPanel và filterIcon
	if (!filterPanel.contains(event.target) && !filterIcon.contains(event.target)) {
		filterPanel.style.display = 'none';  // Đóng filterPanel
		document.removeEventListener('click', outsideClickListener);  // Xóa sự kiện khi đóng
	}
}

function showTab(tabId) {
	const tabs = document.querySelectorAll('.tab-content');
	tabs.forEach(tab => {
		tab.style.display = 'none';
	});

	const buttons = document.querySelectorAll('.tab-btn');
	buttons.forEach(btn => {
		btn.classList.remove('active');
	});

	document.getElementById(tabId).style.display = 'block';
	document.querySelector(`[onclick="showTab('${tabId}')"]`).classList.add('active');
}

function renderFilter(colors, sizes) {
	// Render màu sắc
	const colorContainer = document.querySelector('.color-options');
	colorContainer.innerHTML = ''; // Xóa nội dung cũ

	colors.forEach(color => {
		// Tạo phần tử div ngoài cùng với các lớp tương ứng
		const colorItem = document.createElement('div');
		colorItem.className = 'ant-col ant-col-6 item-color';
		colorItem.style = 'padding-left: 5px; padding-right: 5px; padding-bottom: 10px; width: 100px;';

		// Tạo phần tử div bao bọc với các lớp và kiểu đã cho
		const colorWrapper = document.createElement('div');
		colorWrapper.className = 'border-border p-2.5 py-3 border-[1px] cursor-pointer flex flex-col items-center justify-center';
		colorWrapper.onclick = () => updateColorFilter(color.name);

		// Tạo phần tử div cho hình tròn màu sắc
		const colorCircle = document.createElement('div');
		colorCircle.className = 'mb-2 w-7 h-7';
		colorCircle.style.backgroundColor = color.code;
		colorCircle.style.border = ' 1px solid #808080'

		// Tạo phần tử div cho tên màu sắc
		const colorName = document.createElement('div');
		colorName.className = 'ellipsis-t w-full text-xs font-medium text-center';
		colorName.textContent = color.name;

		// Ghép các phần tử con vào các phần tử cha
		colorWrapper.appendChild(colorCircle);
		colorWrapper.appendChild(colorName);
		colorItem.appendChild(colorWrapper);

		// Thêm phần tử item vào container
		colorContainer.appendChild(colorItem);
	});

	// Render kích cỡ
	const sizeContainer = document.querySelector('.size-options');
	sizeContainer.innerHTML = ''; // Xóa nội dung cũ
	sizes.forEach(size => {
		const sizeButton = document.createElement('button');
		sizeButton.className = 'size-box';
		sizeButton.textContent = size.name; // Hiển thị kích cỡ trên nút
		sizeButton.dataset.sizeName = size; // Lưu kích cỡ trong thuộc tính data
		sizeButton.onclick = () => updateSizeFilter(size.name); // Gọi hàm updateFilter với kích cỡ
		sizeContainer.appendChild(sizeButton);
	});
}

function updateColorFilter(color) {
	// Lấy URL hiện tại
	let url = new URL(window.location.href);
	let colors = url.searchParams.get("color");

	// Chuyển chuỗi màu sắc thành mảng
	let colorArray = colors ? colors.split(",") : [];

	// Nếu màu sắc đã có trong mảng, loại bỏ nó
	if (colorArray.includes(color)) {
		colorArray = colorArray.filter(c => c !== color);
	} else {
		// Nếu màu sắc chưa có, thêm vào mảng
		colorArray.push(color);
	}

	// Cập nhật lại tham số màu sắc trong URL
	if (colorArray.length > 0) {
		url.searchParams.set("color", colorArray.join(","));
	} else {
		url.searchParams.delete("color"); // Xóa tham số nếu không còn màu sắc nào
	}

	// Điều hướng đến URL mới
	history.replaceState(null, "", url.toString()); // Cập nhật URL mà không tải lại trang
	fetchFilteredProducts();
}

function updateSizeFilter(size) {
	// Lấy URL hiện tại
	let url = new URL(window.location.href);
	let sizes = url.searchParams.get("size");

	// Chuyển chuỗi kích cỡ thành mảng
	let sizeArray = sizes ? sizes.split(",") : [];

	// Nếu kích cỡ đã có trong mảng, loại bỏ nó
	if (sizeArray.includes(size)) {
		sizeArray = sizeArray.filter(s => s !== size);
	} else {
		// Nếu kích cỡ chưa có, thêm vào mảng
		sizeArray.push(size);
	}

	// Cập nhật lại tham số kích cỡ trong URL
	if (sizeArray.length > 0) {
		url.searchParams.set("size", sizeArray.join(","));
	} else {
		url.searchParams.delete("size"); // Xóa tham số nếu không còn kích cỡ nào
	}

	// Điều hướng đến URL mới
	history.replaceState(null, "", url.toString()); // Cập nhật URL mà không tải lại trang
	fetchFilteredProducts();
}

function fetchFilteredProducts() {
	const currentUrl = window.location.href;
	console.log(currentUrl)
	$.ajax({
		url: currentUrl,
		method: "GET",
		success: function(response) {
			renderProducts(response);
		},
		error: function(xhr, status, error) {
			console.error("Lỗi: ", error);
		}
	});
}

function renderProducts(response) {
	// Lấy phần tử cha nơi bạn sẽ thêm sản phẩm
	const collectionsDiv = document.querySelector('.collections');

	// Xóa nội dung hiện tại trong collectionsDiv nếu cần
	collectionsDiv.innerHTML = '';

	// Lặp qua từng sản phẩm trong phản hồi
	response.forEach(product => {
		// Tạo phần tử cho mỗi sản phẩm
		const collectionItemDiv = document.createElement('div');
		collectionItemDiv.classList.add('collection-item');

		// Tạo liên kết đến sản phẩm
		const productLink = document.createElement('a');
		productLink.href = `productDetail?id=${product.productId}`;

		// Tạo container hình ảnh}
		const imageContainerDiv = document.createElement('div');
		imageContainerDiv.classList.add('image-container');

		// Tạo hình ảnh chính và hình ảnh hover
		const mainImage = document.createElement('img');
		mainImage.id = 'image-main';
		mainImage.classList.add('image-main');
		mainImage.src = product.productSkus[0].img;
		mainImage.alt = `${product.name} Image`;

		const hoverImage = document.createElement('img');
		hoverImage.id = 'image-hover';
		hoverImage.classList.add('image-hover');
		hoverImage.src = product.productSkus[0].img; // Nếu có ảnh hover khác, thay đổi ở đây
		hoverImage.alt = `${product.name} Hover Image`;

		const addImage = document.createElement('img');
		addImage.src = 'img/add.png';
		addImage.alt = '';
		addImage.classList.add('plus');

		// Thêm các hình ảnh vào container
		imageContainerDiv.appendChild(mainImage);
		imageContainerDiv.appendChild(hoverImage);
		imageContainerDiv.appendChild(addImage);

		// Thêm container hình ảnh vào liên kết
		productLink.appendChild(imageContainerDiv);
		collectionItemDiv.appendChild(productLink);

		// Tạo container cho kích thước
		const sizeContainerDiv = document.createElement('div');
		sizeContainerDiv.classList.add('size-container');
		const sizeOptionsDiv = document.createElement('div');
		sizeOptionsDiv.classList.add('size-options');
		sizeOptionsDiv.id = 'size-options';

		// Xác định kích thước dựa trên loại sản phẩm
		let sizeString;
		if (product.typeProduct === 'áo') {
			sizeString = 's,m,l,xl,xxl';
		} else if (product.typeProduct === 'quần') {
			sizeString = '28,29,30,31,32';
		}

		const sizeList = sizeString.split(',');

		// Lặp qua từng kích thước
		sizeList.forEach(size => {
			const stock = product.productSkus[0].sizeAndStock[size];
			const sizeButton = document.createElement('button');
			sizeButton.classList.add('size-btn');
			sizeButton.textContent = size.toUpperCase();

			// Kiểm tra số lượng
			if (stock != null && stock > 0) {
				sizeButton.onclick = () => addProductToCart(product.productSkus[0].productColorImgId, size, '1');
			} else {
				sizeButton.classList.add('size-unavailable');
				sizeButton.disabled = true; // Disable if out of stock
			}

			sizeOptionsDiv.appendChild(sizeButton);
		});

		sizeContainerDiv.appendChild(sizeOptionsDiv);
		collectionItemDiv.appendChild(sizeContainerDiv);

		// Thêm phần hiển thị hình ảnh của sản phẩm
		const imageCateDiv = document.createElement('div');
		imageCateDiv.classList.add('image-cate');

		product.productSkus.forEach(sku => {
			const skuImage = document.createElement('img');
			skuImage.src = sku.img;
			skuImage.alt = `${sku.color} Image`;
			skuImage.onclick = () => selectImage(sku.productColorImgId, sku.img, JSON.stringify(sku.sizeAndStock), product.typeProduct);
			imageCateDiv.appendChild(skuImage);
		});

		collectionItemDiv.appendChild(imageCateDiv);

		// Tạo tên sản phẩm và giá
		const productName = document.createElement('p');
		productName.textContent = product.name;

		const itemPriceDiv = document.createElement('div');
		itemPriceDiv.classList.add('item-price-new');
		itemPriceDiv.textContent = `$${product.price}`;

		// Thêm tên sản phẩm và giá vào collection item
		collectionItemDiv.appendChild(productName);
		collectionItemDiv.appendChild(itemPriceDiv);

		// Thêm collection item vào collectionsDiv
		collectionsDiv.appendChild(collectionItemDiv);
	});
}


