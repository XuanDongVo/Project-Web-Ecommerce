function addProductToCart(productColorImgId, size, quantity) {
	// Lấy URL hiện tại
	var currentUrl = window.location.href;

	// Mã hóa URL hiện tại để có thể truyền an toàn qua request
	var encodedUrl = encodeURIComponent(currentUrl);

	// Tạo URL chuyển hướng đến controller với tham số redirectUrl
	window.location.href = 'cartdetail?action=add&id=' + productColorImgId + '&size=' + size + '&quantity=' + quantity + '&redirectUrl=' + encodedUrl;
}


function selectImage(productColorImgId, imgSrc, sizeAndStockString, typeProduct) {
	// Cập nhật hình ảnh chính
	document.getElementById('image-main').src = imgSrc;
	document.getElementById('image-hover').src = imgSrc;

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

	// Duyệt qua danh sách kích thước
	sizeList.forEach(size => {
		const stock = sizeAndStock[size] || 0;
		const sizeButton = document.createElement('button');

		sizeButton.className = stock > 0 ? 'size-btn' : 'size-btn size-unavailable';
		sizeButton.textContent = size.toUpperCase();

		if (stock > 0) {
			sizeButton.onclick = () => addProductToCart(productColorImgId, size, '1');
		}

		sizeOptionsDiv.appendChild(sizeButton);
	});
}

document.addEventListener('DOMContentLoaded', function() {
	// Chọn tất cả các biểu tượng plus
	const plusIcons = document.querySelectorAll('.collection-item .plus');

	plusIcons.forEach(function(plus) {
		plus.addEventListener('click', function(event) {
			event.preventDefault(); // Ngăn hành động mặc định nếu có

			// Tìm phần tử cha .collection-item
			const collectionItem = plus.closest('.collection-item');

			// Tìm .size-container trong .collection-item này
			const sizeContainer = collectionItem.querySelector('.size-container');

			// Đóng tất cả các size-container khác
			document.querySelectorAll('.size-container.active').forEach(function(container) {
				if (container !== sizeContainer) {
					container.classList.remove('active');
				}
			});

			// Toggle lớp 'active' để hiển thị/ẩn
			sizeContainer.classList.toggle('active');
		});
	});

	// Đóng size-container khi nhấp ra ngoài
	document.addEventListener('click', function(event) {
		plusIcons.forEach(function(plus) {
			const collectionItem = plus.closest('.collection-item');
			const sizeContainer = collectionItem.querySelector('.size-container');

			// Nếu click không phải là trong .collection-item, đóng size-container
			if (!collectionItem.contains(event.target)) {
				sizeContainer.classList.remove('active');
			}
		});
	});

	// Thêm sự kiện cho nút đóng
	const closeButtons = document.querySelectorAll('.size-container .close-btn');

	closeButtons.forEach(function(btn) {
		btn.addEventListener('click', function(event) {
			event.stopPropagation(); // Ngăn sự kiện lan truyền
			btn.closest('.size-container').classList.remove('active');
		});
	});

	// Xử lý chọn size
	const sizeButtons = document.querySelectorAll('.size-btn');

	sizeButtons.forEach(function(btn) {
		btn.addEventListener('click', function() {
			// Xóa lớp 'selected' khỏi tất cả các nút trong cùng size-container
			const sizeContainer = btn.closest('.size-container');
			const buttons = sizeContainer.querySelectorAll('.size-btn');
			buttons.forEach(function(button) {
				button.classList.remove('selected');
			});

			// Thêm lớp 'selected' vào nút được nhấp
			btn.classList.add('selected');
		});
	});
});
