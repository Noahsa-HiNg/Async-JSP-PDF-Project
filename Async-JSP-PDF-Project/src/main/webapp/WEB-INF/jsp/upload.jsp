<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Upload File</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    <link href='https://unpkg.com/boxicons@2.1.4/css/boxicons.min.css' rel='stylesheet'>
</head>
<body>
    <div class="wrapper">
        <!-- Dùng JS để xử lý upload -->
        <form id="uploadForm" onsubmit="uploadFile(event)">
            <h1>Upload PDF</h1>
            
            <p style="text-align: center; margin-bottom: 20px; color: rgba(255,255,255,0.8)">
                Chọn file PDF để hệ thống xử lý ngầm.
            </p>

            <!-- Khu vực chọn file tùy chỉnh -->
            <div class="file-upload-wrapper">
                <label for="pdfFile" class="file-upload-label">
                    <i class='bx bxs-cloud-upload'></i>
                    <span id="fileNameDisplay">Choose a PDF file</span>
                </label>
                <!-- Input ẩn -->
                <input type="file" id="pdfFile" name="pdfFile" accept=".pdf" required onchange="updateFileName()">
            </div>

            <!-- Thanh tiến trình (Ẩn mặc định) -->
            <div id="progressContainer" class="progress-container">
                <div id="progressBar" class="progress-bar"></div>
            </div>
            <div id="progressText" class="progress-text">0%</div>

            <button type="submit" id="btnSubmit" class="btn">Upload & Process</button>

            <div class="register-link">
                <p><a href="${pageContext.request.contextPath}/status">View Dashboard &rarr;</a></p>
            </div>
        </form>
    </div>

    <!-- Nhúng file JS -->
    <script src="${pageContext.request.contextPath}/js/script.js"></script>
</body>
</html>