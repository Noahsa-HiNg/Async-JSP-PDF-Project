<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Register | PDF Processor</title>
    <link href='https://unpkg.com/boxicons@2.1.4/css/boxicons.min.css' rel='stylesheet'>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
    <div class="wrapper">
        <form action="${pageContext.request.contextPath}/register" method="post">
            <h1>Register</h1>
            
            <c:if test="${not empty errorMessage}">
                <div class="alert alert-error">
                    <i class='bx bxs-error-circle'></i> ${errorMessage}
                </div>
            </c:if>

            <div class="input-box">
                <input type="text" name="username" placeholder="Tên đăng nhập" required>
                <i class='bx bxs-user'></i>
            </div>
             <div class="input-box">
                <input type="email" name="email" placeholder="Email" required>
                <i class='bx bxs-envelope'></i>
            </div>
            <div class="input-box">
                <input type="password" name="password" placeholder="Mật khẩu" required>
                <i class='bx bxs-lock-alt'></i>
            </div>
             <div class="input-box">
                <input type="password" name="confirmPassword" placeholder="Xác nhận mật khẩu" required>
                <i class='bx bxs-lock-alt'></i>
            </div>

            <button type="submit" class="btn">Register</button>

            <div class="form-link">
                <p>Already have an account? <a href="${pageContext.request.contextPath}/login">Login</a></p>
            </div>
        </form>
    </div>
</body>
</html>