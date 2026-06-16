
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Đăng nhập</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-sRIl4kxILFvY47J16cr9ZwB07vP4J8+LH7qKQnuqkuIAvNWLzeN8tE5YBujZqJLB" crossorigin="anonymous">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/Login.css">
</head>
<body>
<div class="container">
    <div class="row-1">
        <b>Đăng nhập</b>
    </div>

    <form action="${pageContext.request.contextPath}/LoginController" method="POST">
        <div class="row-2">
            <div class="email">
                <label class="email-field"><b>Tên đăng nhập</b></label>
                <input type="text" name="username" class="form-control" placeholder="Nhập tên đăng nhập" required>
            </div>
            <div class="login mt-3">
                <label class="password-field"><b>Mật khẩu</b></label>
                <input type="password" name="password" class="form-control" placeholder="Nhập mật khẩu" required>
            </div>
        </div>

        <div class="row-3 mt-2">
            <a href="${pageContext.request.contextPath}/page/ForgotPassword.jsp" class="forgot-password">Quên mật khẩu?</a>
        </div>

        <div class="row-4 mt-3">
            <button type="submit" class="Login_button btn btn-primary"><b>Đăng nhập</b></button>
        </div>
    </form>

    <div class="row-5 mt-3">
        <a href="${pageContext.request.contextPath}/page/Register.jsp" class="create_account">Chưa có tài khoản?</a>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/js/bootstrap.bundle.min.js" integrity="sha384-FKyoEForCGlyvwx9Hj09JcYn3nv7wiPVlz7YYwJrWVcXK/BmnVDxM+D2scQbITxI" crossorigin="anonymous"></script>
</body>
</html>