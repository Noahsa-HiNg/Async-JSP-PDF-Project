<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Login - Async Project</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    <!-- BoxIcons CDN -->
    <link href='https://unpkg.com/boxicons@2.1.4/css/boxicons.min.css' rel='stylesheet'>
</head>
<body>
    <div class="wrapper">
        <form action="${pageContext.request.contextPath}/login" method="post">
            <h1>Login</h1>
            
            <!-- Hiển thị lỗi nếu có -->
            <c:if test="${not empty errorMessage}">
                <div class="alert alert-error">
                    <i class='bx bxs-error-circle'></i> ${errorMessage}
                </div>
            </c:if>
            
            <!-- Hiển thị thành công -->
            <c:if test="${not empty successMessage}">
                <div class="alert alert-success">
                    <i class='bx bxs-check-circle'></i> ${successMessage}
                </div>
            </c:if>

            <div class="input-box">
                <input type="text" name="username" placeholder="Username" required>
                <i class='bx bxs-user'></i>
            </div>
            <div class="input-box">
                <input type="password" name="password" placeholder="Password" required>
                <i class='bx bxs-lock-alt'></i>
            </div>

            <button type="submit" class="btn">Login</button>

            <div class="register-link">
                <p>Don't have an account? <a href="${pageContext.request.contextPath}/register">Register</a></p>
            </div>
        </form>
    </div>
</body>
</html>