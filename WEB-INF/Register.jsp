<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-sRIl4kxILFvY47J16cr9ZwB07vP4J8+LH7qKQnuqkuIAvNWLzeN8tE5YBujZqJLB" crossorigin="anonymous">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/js/bootstrap.bundle.min.js" integrity="sha384-FKyoEForCGlyvwx9Hj09JcYn3nv7wiPVlz7YYwJrWVcXK/BmnVDxM+D2scQbITxI" crossorigin="anonymous"></script>
    <link rel="stylesheet" href="../css/Register.css">
</head>
<body>
<div class="container">
    <div class="row-1"><b>Tạo tài khoản mới</b>
    </div>
    <div class="row-2">
        <div class="email">
            <label  class="email-field"><b>Email</b></label>
            <input type="text" class="form-control"  placeholder="">
        </div>
        <div class="user">
            <label  class="user-field"><b>Tên đăng nhập</b></label>
            <input type="text" class="form-control"  placeholder="">
        </div>
        <div class="password">
            <label  class="password-field"><b>Mật khẩu</b></label>
            <input type="text" class="form-control"  placeholder="">
        </div>
        <div class="confirm-password">
            <label  class="confirm-password-field"><b>Xác nhận mật khẩu</b></label>
            <input type="text" class="form-control"  placeholder="">
        </div>
    </div>
    <div class="row-3">
        <a href="Login.html"><button type="button" class="Register_button">Đăng ký</button></a>
    </div>
</div>
</body>
</html>