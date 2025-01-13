// Lấy các phần tử DOM cần thiết
const productType = document.getElementById('productType');
const subCategory = document.getElementById('subCategory');
const productSizeType = document.getElementById('productSizeType');
const productSize = document.getElementById('productSize');

// Lắng nghe sự kiện thay đổi trên `productType`
productType.addEventListener('change', function() {
	// Nếu `productType` được chọn, kích hoạt `subCategory`
	if (productType.value) {
		subCategory.disabled = false; // Bật lựa chọn danh mục phụ
	} else {
		subCategory.disabled = true; // Vô hiệu hóa nếu chưa chọn loại sản phẩm
		subCategory.value = ""; // Đặt lại giá trị của `subCategory`
	}
});

// Lắng nghe sự kiện thay đổi trên `productSizeType`
productSizeType.addEventListener('change', function() {
	// Nếu `productSizeType` được chọn, kích hoạt `productSize`
	if (productSizeType.value) {
		productSize.disabled = false; // Bật lựa chọn kích thước
	} else {
		productSize.disabled = true; // Vô hiệu hóa nếu chưa chọn
		productSize.value = ""; // Đặt lại giá trị của `productSize`
	}
});

// Hiển thị modal tự động khi tải trang nếu modal tồn tại
window.onload = function() {
	const modalElement = document.getElementById('message');
	if (modalElement) {
		// Sử dụng Bootstrap Modal để hiển thị thông báo
		const modal = new bootstrap.Modal(modalElement);
		modal.show(); // Hiển thị modal
	}
};


// Biến lưu thông tin sản phẩm
let productSpu = null;

// Xử lý sự kiện khi nhấn nút lưu SPU (thông tin sản phẩm cơ bản)
document.getElementById('saveSpu').addEventListener('click', function() {
	// Kiểm tra tính hợp lệ của form SPU
	const isValid = document.getElementById('spuForm').reportValidity();
	if (isValid) {
		// Lấy giá trị từ các trường nhập
		const productName = document.getElementById('productName').value;
		const productDescription = document.getElementById('productDescription').value;
		const productTypeId = document.getElementById('productType').value;
		const subCategory = document.getElementById('subCategory').value;
		const productPrice = document.getElementById('productPrice').value;

		// Tạo đối tượng SPU
		productSpu = {
			name: productName,
			subCategory: subCategory,
			price: productPrice,
			description: productDescription,
			skus: [] // Danh sách SKU (biến thể sản phẩm)
		};

		// Bật tab SKU (nếu bị khóa)
		const skuTab = document.getElementById('sku-tab');
		if (skuTab.classList.contains('disabled')) {
			skuTab.classList.remove('disabled');
		}

		// Chuyển sang tab SKU
		new bootstrap.Tab(skuTab).show();
	}
});

// Xử lý sự kiện khi thêm SKU mới
document.getElementById('addSku').addEventListener('click', function() {
	const form = document.getElementById('skuForm');
	if (form.reportValidity()) {
		// Lấy thông tin SKU
		const color = document.getElementById('productColor').value;
		const fileInput = document.getElementById('productImage'); // Lấy input file
		const size = document.getElementById('productSize').value;
		const stock = parseInt(document.getElementById('productStock').value);

		if (fileInput.files && fileInput.files[0]) {
			const file = fileInput.files[0]; // Lấy tệp đầu tiên
			const reader = new FileReader();

			// Đọc tệp và chuyển thành Base64
			reader.onload = function(event) {
				const imageUrl = event.target.result; // Đây là chuỗi Base64

				// Cập nhật hình ảnh xem trước
				const imagePreview = document.getElementById('imagePreview');
				if (imagePreview) {
					imagePreview.src = imageUrl;
					imagePreview.style.display = 'block';
				}

				// Kiểm tra SKU có trùng màu và ảnh không
				const existingProductColorImg = productSpu.skus.find(
					sku => sku.color === color && sku.image === imageUrl
				);

				if (existingProductColorImg) {
					// Nếu SKU tồn tại, cập nhật số lượng theo kích thước
					existingProductColorImg.sizeAndStock[size] = stock;
				} else {
					// Nếu SKU chưa tồn tại, thêm mới
					const productColorImg = {
						color: color,
						image: imageUrl,
						sizeAndStock: { [size]: stock }
					};
					productSpu.skus.push(productColorImg);
				}

				// Cập nhật nút và render bảng SKU
				updateDetailButton();
				renderSkuTable();
			};

			// Đọc file dưới dạng Base64
			reader.readAsDataURL(file);
		} else {
			alert("Vui lòng chọn hình ảnh!"); // Hiển thị cảnh báo nếu chưa chọn ảnh
		}
	}
});




// Cập nhật trạng thái nút "Xem chi tiết" và "Lưu"
function updateDetailButton() {
	const detailButton = document.getElementById('detailSku');
	const saveButton = document.getElementById('saveSku');

	if (productSpu.skus && productSpu.skus.length > 0) {
		detailButton.classList.remove('fade');
		saveButton.classList.remove('fade');
	} else {
		detailButton.classList.add('fade');
		saveButton.classList.add('fade');
	}
}

// Render bảng SKU
function renderSkuTable() {
	const tableBody = document.getElementById('skuTableBody');
	tableBody.innerHTML = ''; // Xóa nội dung cũ của bảng

	productSpu.skus.forEach(productColorImg => {
		Object.entries(productColorImg.sizeAndStock).forEach(([size, stock], index) => {
			const row = document.createElement('tr');

			if (index === 0) {
				// Tạo ô màu và hình ảnh ở hàng đầu tiên
				const colorCell = document.createElement('td');
				colorCell.rowSpan = Object.keys(productColorImg.sizeAndStock).length;
				colorCell.textContent = productColorImg.color;
				row.appendChild(colorCell);

				const imageCell = document.createElement('td');
				imageCell.rowSpan = Object.keys(productColorImg.sizeAndStock).length;
				const imgElement = document.createElement('img');

				imgElement.src = productColorImg.image;
				imgElement.alt = "Product Image";
				imgElement.style.width = "50px";
				imgElement.style.height = "50px";
				imageCell.appendChild(imgElement);
				row.appendChild(imageCell);
			}

			// Tạo ô kích thước và số lượng
			const sizeCell = document.createElement('td');
			sizeCell.textContent = size;
			row.appendChild(sizeCell);

			const stockCell = document.createElement('td');
			stockCell.textContent = stock;
			row.appendChild(stockCell);

			// Tạo ô hành động (nút xóa)
			const actionCell = document.createElement('td');
			const deleteButton = document.createElement('button');
			deleteButton.textContent = 'Xóa';
			deleteButton.className = 'btn btn-danger btn-sm';
			deleteButton.addEventListener('click', function() {
				deleteSizeAndStock(productColorImg, size);
			});

			actionCell.appendChild(deleteButton);
			row.appendChild(actionCell);

			tableBody.appendChild(row);
		});
	});
}

// Xóa SKU theo kích thước
function deleteSizeAndStock(productColorImg, size) {
	delete productColorImg.sizeAndStock[size];

	if (Object.keys(productColorImg.sizeAndStock).length === 0) {
		// Nếu không còn kích thước nào, xóa SKU khỏi danh sách
		const skuIndex = productSpu.skus.indexOf(productColorImg);
		productSpu.skus.splice(skuIndex, 1);
	}

	// Cập nhật nút và bảng
	updateDetailButton();
	renderSkuTable();
}


function updateSubCategory(selectElement) {
	const subCategorySelect = document.getElementById('subCategory');
	const selectedOption = selectElement.options[selectElement.selectedIndex];

	// Reset danh sách danh mục
	subCategorySelect.innerHTML = '<option value="">Chọn danh mục</option>';
	subCategorySelect.disabled = true;

	// Lấy danh sách danh mục từ thuộc tính data-type-subCatgory
	let subCategories = selectedOption.getAttribute('data-type-subCatgory');
	if (subCategories) {
		// Loại bỏ dấu ngoặc vuông và khoảng trắng thừa
		subCategories = subCategories.replace(/[\[\]"]+/g, '').trim();

		// Tách chuỗi thành mảng theo dấu phẩy
		const subCategoryList = subCategories.split(',').map(item => item.trim());

		subCategoryList.forEach(subCategory => {
			const option = document.createElement('option');
			option.value = subCategory;
			option.textContent = subCategory;
			subCategorySelect.appendChild(option);
		});

		subCategorySelect.disabled = false;
	}
}

function updateSize(selectElement) {
	const sizeSelect = document.getElementById('productSize');
	const selectedOption = selectElement.options[selectElement.selectedIndex];

	// Reset danh sách kích thước
	sizeSelect.innerHTML = '<option value="">Chọn kích thước</option>';
	sizeSelect.disabled = true;

	// Lấy danh sách kích thước từ thuộc tính data-type-size
	let sizes = selectedOption.getAttribute('data-type-size');
	if (sizes) {
		// Loại bỏ dấu ngoặc vuông và khoảng trắng thừa
		sizes = sizes.replace(/[\[\]"]+/g, '').trim();
		// Xử lý chuỗi kích thước thành mảng
		const sizeList = sizes.split(',').map(item => item.trim());

		// Thêm các kích thước vào select
		sizeList.forEach(size => {
			const option = document.createElement('option');
			option.value = size;
			option.textContent = size;
			sizeSelect.appendChild(option);
		});

		sizeSelect.disabled = false;
	}
}

