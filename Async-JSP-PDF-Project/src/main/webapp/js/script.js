function updateFileName() {
    const fileInput = document.getElementById('pdfFile');
    const fileNameDisplay = document.getElementById('fileNameDisplay');
    
    if (fileInput.files.length > 0) {
        fileNameDisplay.textContent = fileInput.files[0].name;
    } else {
        fileNameDisplay.textContent = "Chưa chọn file nào";
    }
}

function uploadFile(event) {
    event.preventDefault(); // Ngăn form submit truyền thống

    const fileInput = document.getElementById('pdfFile');
    const file = fileInput.files[0];
    
    if (!file) {
        alert("Vui lòng chọn file PDF!");
        return;
    }

    // Tạo FormData để gửi dữ liệu
    const formData = new FormData();
    formData.append("pdfFile", file);

    // Hiển thị thanh tiến trình
    const progressContainer = document.getElementById('progressContainer');
    const progressBar = document.getElementById('progressBar');
    const progressText = document.getElementById('progressText');
    const btnSubmit = document.getElementById('btnSubmit');

    progressContainer.style.display = 'block';
    progressText.style.display = 'block';
    btnSubmit.disabled = true; // Khóa nút upload
    btnSubmit.style.opacity = "0.5";

    // Sử dụng XMLHttpRequest để theo dõi tiến trình
    const xhr = new XMLHttpRequest();

    // 1. Theo dõi tiến trình (Upload Progress)
    xhr.upload.addEventListener("progress", function(e) {
        if (e.lengthComputable) {
            const percentComplete = Math.round((e.loaded / e.total) * 100);
            progressBar.style.width = percentComplete + "%";
            progressText.textContent = percentComplete + "% Đã tải lên...";
        }
    });

    // 2. Xử lý khi hoàn tất
    xhr.onload = function() {
        if (xhr.status === 200) {
            // Upload thành công (hoặc redirect từ server)
            progressText.textContent = "Tải lên thành công! Đang chuyển hướng...";
            // Server sẽ trả về trang status, ta reload hoặc chuyển hướng thủ công
            // Do Servlet dùng sendRedirect, xhr sẽ nhận được nội dung trang đích.
            // Cách tốt nhất là tự chuyển hướng ở client:
            window.location.href = "status"; 
        } else {
            progressText.textContent = "Lỗi tải lên: " + xhr.statusText;
            btnSubmit.disabled = false;
            btnSubmit.style.opacity = "1";
        }
    };

    // 3. Xử lý lỗi mạng
    xhr.onerror = function() {
        progressText.textContent = "Đã xảy ra lỗi mạng!";
        btnSubmit.disabled = false;
        btnSubmit.style.opacity = "1";
    };

    // Gửi request đến Servlet
    xhr.open("POST", "upload", true);
    xhr.send(formData);
}