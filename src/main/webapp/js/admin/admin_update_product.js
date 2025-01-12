//Hiển thị modal tự động khi tải trang nếu modal tồn tại
window.onload = function() {
	const modalElement = document.getElementById('message');
	if (modalElement) {
		// Sử dụng Bootstrap Modal để hiển thị thông báo
		const modal = new bootstrap.Modal(modalElement);
		modal.show(); // Hiển thị modal
	}
};

document.addEventListener("DOMContentLoaded", function() {
	// Lấy tất cả các tab
	const tabButtons = document.querySelectorAll('#colorTab button');

	// Lắng nghe sự kiện click cho từng tab
	tabButtons.forEach((button) => {
		button.addEventListener('click', function() {
			// Lấy ID của tab đang được chọn
			const tabId = button.getAttribute('data-bs-target');

			// Tìm tab content tương ứng
			const selectedTab = document.querySelector(tabId);

			// Lấy hình ảnh từ tab content
			const imgElement = selectedTab.querySelector('img');
			const newImgSrc = imgElement ? imgElement.getAttribute('src') : null;

			// Cập nhật ảnh chính
			const mainImage = document.getElementById('mainImage');
			if (mainImage && newImgSrc) {
				mainImage.setAttribute('src', newImgSrc);
			}
		});
	});
});

// Hàm để mở cửa sổ chọn file
function openFileChooser(color) {
	const fileInput = document.getElementById('fileInput-' + color);
	if (fileInput) {
		fileInput.click(); // Kích hoạt sự kiện click trên input
	} else {
		console.error(`Không tìm thấy input file với id: fileInput-${color}`);
	}
}

// Hàm xử lý sau khi chọn file
function handleFileChange(color) {
	const fileInput = document.getElementById('fileInput-' + color);
	const imgElement = document.getElementById('skuImage-' + color);
	const mainImageElement = document.getElementById("mainImage");
	const hiddenBase64Input = document.getElementById('hiddenBase64-' + color);
	if (fileInput && fileInput.files.length > 0) {
		const file = fileInput.files[0];

		// Kiểm tra xem file có phải ảnh không
		if (file.type.startsWith("image/")) {
			const reader = new FileReader();
			reader.onload = function() {
				const base64Image = reader.result.split(",")[1]; // Lấy dữ liệu Base64 (bỏ tiền tố)
				if (imgElement) {
					imgElement.src = `data:image/jpg;base64,${base64Image}`; // Hiển thị ảnh
				}
				if (mainImageElement) {
					mainImageElement.src = `data:image/jpg;base64,${base64Image}`; // Cập nhật ảnh chính
				}
				if (hiddenBase64Input) {
                    hiddenBase64Input.value =`data:image/jpg;base64,${base64Image}`; // Gán Base64 vào input ẩn
                }

			};
			reader.readAsDataURL(file); // Đọc file
		} else {
			alert("Vui lòng chọn một file ảnh hợp lệ.");
		}
	} else {
		console.error("Không có file nào được chọn.");
	}
}


function updateHiddenInput(selectElement, originalColor) {
    // Lấy giá trị color mới từ dropdown
    const selectedColor = selectElement.value;

    // Cập nhật id và name của input ẩn
    const hiddenInput = document.getElementById(`hiddenBase64-${originalColor}`);
    if (hiddenInput) {
        hiddenInput.id = `hiddenBase64-${selectedColor}`;
        hiddenInput.name = `image-${selectedColor}`;
    }

    // Cập nhật id và name của file input (nếu cần)
    const fileInput = document.getElementById(`fileInput-${originalColor}`);
    if (fileInput) {
        fileInput.id = `fileInput-${selectedColor}`;
        fileInput.setAttribute("onchange", `handleFileChange('${selectedColor}')`);
    }
    
     const editButton = document.querySelector(`button[onclick="openFileChooser('${originalColor}')"]`);
    if (editButton) {
        editButton.setAttribute("onclick", `openFileChooser('${selectedColor}')`);
    }

}