
// Handle quantity increment and decrement
document.getElementById('increaseQuantity').addEventListener('click', function() {
	const quantityInput = document.getElementById('quantity');
	quantityInput.value = parseInt(quantityInput.value) + 1;
});

document.getElementById('decreaseQuantity').addEventListener('click', function() {
	const quantityInput = document.getElementById('quantity');
	if (quantityInput.value > 1) {
		quantityInput.value = parseInt(quantityInput.value) - 1;
	}
});


function selectImage(productColorImgId, imgSrc, sizeAndStockString, typeProduct, color) {

	// Cập nhật hình ảnh chính
	document.getElementById('mainImage').src = imgSrc;
	document.getElementById('color-name').innerText = color.toUpperCase();

	// Cập nhật kích thước dựa trên sizeAndStock
	const sizeOptionsDiv = document.getElementById('size-options');
	sizeOptionsDiv.innerHTML = ''; // Xóa các tùy chọn kích thước trước đó

	// Chuyển đổi sizeAndStock từ chuỗi sang đối tượng
	const sizeAndStock = {};
	try {
		sizeAndStockString.replace(/{|}/g, '').split(',').forEach(item => {
			const [size, stock] = item.split('=');
			if (size && stock) {
				sizeAndStock[size.trim()] = parseInt(stock.trim(), 10);
			}
		});
	} catch (error) {
		console.error('Lỗi khi phân tích cú pháp sizeAndStock:', error);
		return;
	}

	// Tạo danh sách kích thước dựa trên loại sản phẩm
	let sizeList = [];
	if (typeProduct === 'áo') {
		sizeList = ['s', 'm', 'l', 'xl', 'xxl'];
	} else if (typeProduct === 'quần') {
		sizeList = ['28', '29', '30', '31', '32'];
	}

	const sizeButtonsDiv = document.createElement('div');
	sizeButtonsDiv.classList.add('size-buttons', 'd-flex');

	// Duyệt qua danh sách kích thước
	sizeList.forEach(size => {
		const stock = sizeAndStock[size] || 0;
		const sizeButton = document.createElement('button');

		sizeButton.className = stock > 0 ? 'btn btn-outline-primary size-btn' : 'btn btn-outline-primary size-btn size-unavailable';
		sizeButton.textContent = size.toUpperCase();

		if (stock > 0) {
			sizeButton.onclick = (event) => chooseSize(event, size, stock);
		}

		sizeButtonsDiv.appendChild(sizeButton);
	});
	sizeOptionsDiv.appendChild(sizeButtonsDiv)

	// Cập nhật thuộc tính data-color-img-id của nút đặt hàng
	const addToCartButton = document.getElementById('addToCart');
	if (addToCartButton) {
		addToCartButton.dataset.colorImgId = productColorImgId;
	}
}

function chooseSize(event, size, stock) {
	const stockDiv = document.querySelector('.items-center.gap-1.text-xs.text-muted.mb-3');
	let stockSpan = stockDiv.querySelector('span');
	
	const spanSizeMessage = document.querySelector('.size-message span');

	if (stock <= 10) {
		if (!stockSpan) {
			stockSpan = document.createElement('span');
			stockDiv.appendChild(stockSpan);
		}
		stockSpan.innerText = `Chỉ còn ${stock} sản phẩm`;
	} else {
		if (stockSpan) {
			stockSpan.remove();
		}
	}
	
	if(spanSizeMessage){
		spanSizeMessage.remove();
	}

	// Cập nhật chính xác `data-selected-size` và `data-stock`
	const addToCartButton = document.getElementById('addToCart');
	if (addToCartButton) {
		addToCartButton.setAttribute('data-selected-size', size);
		addToCartButton.setAttribute('data-stock', stock);
	}

	// Loại bỏ "active" khỏi tất cả các nút và đặt lại trạng thái
	const buttons = document.querySelectorAll('.btn.btn-outline-primary.size-btn');
	buttons.forEach((button) => button.classList.remove('active'));
	event.target.classList.add('active');
}


function addToCart(event) {
	const addToCartButton = event.target;

	// Lấy giá trị từ các thuộc tính data-*
	const productColorImgId = addToCartButton.dataset.colorImgId;
	const stockData = addToCartButton.dataset.stock || 0;
	const selectedSize = addToCartButton.dataset.selectedSize;
	const quantity = parseInt(document.getElementById("quantity").value, 0);

	// Kiểm tra xem kích thước đã được chọn chưa
	if (!selectedSize) {
		const sizeMessage = document.querySelector('.size-message');
		span = document.createElement('span');
		span.style.color = "red";
		sizeMessage.appendChild(span);
		span.innerText = "Vui lòng chọn kích thước";
		return;
	}

	if (quantity > stockData) {
		// Tìm modal và cập nhật nội dung
		const modal = document.getElementById('outOfStockModal');
		const modalBody = modal.querySelector('.modal-body p');
		modalBody.innerHTML = `Số lượng không đủ! Chỉ còn <strong>${stockData}</strong> sản phẩm cho kích thước <strong>${selectedSize.toUpperCase()}</strong>.`;

		// Hiển thị modal
		const bootstrapModal = new bootstrap.Modal(modal);
		bootstrapModal.show();
		return;
	}

	// Lấy URL hiện tại
	var currentUrl = window.location.href;

	// Mã hóa URL hiện tại để có thể truyền an toàn qua request
	var encodedUrl = encodeURIComponent(currentUrl);

	// Tạo URL chuyển hướng đến controller với tham số redirectUrl
	window.location.href = 'cartdetail?action=add&id=' + productColorImgId + '&size=' + selectedSize + '&quantity=' + quantity + '&redirectUrl=' + encodedUrl;
}

