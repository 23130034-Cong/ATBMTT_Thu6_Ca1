<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Admin Dashboard</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
    <link rel="stylesheet" href="../css/AdminDashboard.css">
</head>
<body>

<div class="dashboard-container">

    <aside class="sidebar">
        <div class="sidebar-header">
            <img class="Trademark_logo" src="remove-photos-background-removed (2).png" height="70" width="80" alt="Thiên Lý Logo" />
        </div>
        <nav class="sidebar-nav">
            <ul>
                <li><a href="#" class="active"><i class="fas fa-home"></i> Dashboard</a></li>
                <li><a href="#"><i class="fas fa-users"></i> Người Dùng</a></li>
                <li><a href="#"><i class="fas fa-box"></i> Sản Phẩm</a></li>
                <li><a href="#"><i class="fas fa-file-alt"></i> Báo Cáo</a></li>
                <li><a href="#"><i class="fas fa-cog"></i> Cài Đặt</a></li>
            </ul>
        </nav>
    </aside>

    <main class="main-content">
        <section class="cards-section">
            <div class="card">
                <div class="card-icon"><i class="fas fa-dollar-sign"></i></div>
                <div class="card-details">
                    <p class="card-label">Tổng Doanh Thu</p>
                    <p class="card-value">1,500,000</p>
                </div>
            </div>

            <div class="card">
                <div class="card-icon"><i class="fas fa-users"></i></div>
                <div class="card-details">
                    <p class="card-label">Người Dùng Mới</p>
                    <p class="card-value">1,250</p>
                </div>
            </div>

            <div class="card">
                <div class="card-icon"><i class="fas fa-shopping-cart"></i></div>
                <div class="card-details">
                    <p class="card-label">Đơn Hàng</p>
                    <p class="card-value">5,800</p>
                </div>
            </div>

            <div class="card">
                <div class="card-icon"><i class="fas fa-eye"></i></div>
                <div class="card-details">
                    <p class="card-label">Lượt Truy Cập</p>
                    <p class="card-value">45,000</p>
                </div>
            </div>
        </section>
    </main>
</div>
</body>
</html>