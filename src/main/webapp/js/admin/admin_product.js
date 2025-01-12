
let deleteProductId = null;

function deleteProduct(productId) {
    deleteProductId = productId; // Lưu productId cho việc xóa
    const deleteModal = new bootstrap.Modal(document.getElementById('deleteConfirmationModal'));
    deleteModal.show();
}

// Khi nhấn "Xóa" trong Modal
document.getElementById('confirmDelete').addEventListener('click', function () {
    if (deleteProductId) {
     	window.location.href = 'adminDeleteProduct?productId='+deleteProductId;
    }
});

function viewProductDetails(action,productId) {
    if (productId) {
		 window.location.href = `adminDetailProduct?action=${action}&id=${productId}`;
    } else {
        alert('Không tìm thấy sản phẩm. Vui lòng thử lại.');
    }
}

function selectImage(imgSrc, sizeAndStockString, color) {
	// Cập nhật hình ảnh chính
	document.getElementById('mainImage').src = imgSrc;
	document.getElementById('color-name').innerText = `Màu sắc: ${color.toUpperCase()}`;


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

	// Lấy sizeTableBody và render sizeAndStock
	const sizeTableBody = document.getElementById('sizeTableBody');
	sizeTableBody.innerHTML = ''; // Xóa các hàng trước đó

	for (const [size, stock] of Object.entries(sizeAndStock)) {
		const row = document.createElement('tr');
		row.classList.add('text-center');

		// Tạo cột cho kích thước
		const sizeCell = document.createElement('td');
		sizeCell.textContent = size.toUpperCase();

		// Tạo cột cho số lượng
		const stockCell = document.createElement('td');
		stockCell.textContent = stock;

		// Thêm các cột vào hàng
		row.appendChild(sizeCell);
		row.appendChild(stockCell);

		// Thêm hàng vào bảng
		sizeTableBody.appendChild(row);
	}

}
