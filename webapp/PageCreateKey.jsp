<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Xác thực & Ký số Đơn hàng - Thiên Lý</title>
    
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
    
    <link rel="stylesheet" href="../css/PageCreateKey.css">
    <style>
        /* CSS Cơ bản */
        body, button, input, textarea, select, label, h1, h5, h6 {
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif !important;
        }
        
        body {
            background-color: #f4f6f9;
        }

        /* Cấu trúc Khung Card */
        .modern-card {
            background: #ffffff;
            border: 1px solid #dee2e6;
            border-radius: 6px !important;
            box-shadow: 0 2px 4px rgba(0, 0, 0, 0.05) !important;
            overflow: hidden;
        }

        .rounded-4 {
            border-radius: 6px !important;
        }

        /* Khung hiển thị cố định thông tin đơn */
        .order-info-vault {
            background-color: #f8f9fa;
            border-radius: 4px;
            border: 1px solid #ccc;
        }

        /* Vùng dán chữ ký số */
        .signature-input-box {
            background-color: #ffffff;
            border-radius: 4px;
            border: 1px solid #ccc;
            font-family: 'Courier New', Courier, monospace !important;
            font-size: 14px;
            color: #333333;
        }

        .signature-input-box:focus {
            border-color: #003da5;
            box-shadow: 0 0 0 0.2rem rgba(0, 61, 165, 0.25);
        }

        /* Thành phần nút bấm chính màu xanh dương hệ thống */
        .btn-modern-primary {
            background-color: #003da5;
            color: white;
            border: none;
            border-radius: 4px;
            padding: 10px 24px;
            font-weight: 600;
            transition: all 0.2s ease;
        }

        .btn-modern-primary:hover {
            background-color: #002566;
            color: #ffffff !important;
            box-shadow: 0 4px 12px rgba(0, 61, 165, 0.2);
        }

        .btn {
            border-radius: 4px !important;
        }

        /* Trạng thái xác thực */
        .status-text-box {
            display: none;
            font-weight: 500;
            border-radius: 4px;
        }
        
        .Trademark_logo {
            display: block !important;
            max-height: 80px !important;
            width: auto !important;
        }

        /* Định dạng danh sách hướng dẫn cách dòng rõ ràng */
        .instruction-list li {
            margin-bottom: 12px;
            list-style-type: none;
            position: relative;
        }

        /* Thành phần thông báo Toast góc màn hình */
        .custom-toast {
            position: fixed;
            bottom: 20px;
            right: 20px;
            background-color: #e0e0e0;
            color: #333333;
            padding: 12px 24px;
            border-radius: 6px;
            box-shadow: 0 4px 12px rgba(0,0,0,0.15);
            font-size: 15px;
            z-index: 9999;
            display: none;
            border: 1px solid #d0d0d0;
        }
    </style>
</head>
<body>

    <header class="bg-white border-bottom p-0">
        <nav class="navbar navbar-light bg-light p-0 m-0">
            <div class="container" style="max-width: 800px;">
                <a class="navbar-brand fw-bold d-flex align-items-center p-0 m-0" href="Mainpage.html">
                    <img class="Trademark_logo" src="D:\HK2-N3\ATBMHTTT\ck\ATBMTT_Thu6_Ca1\remove-photos-background-removed.png" height="50" width="50" alt="Thiên Lý Logo" />
                </a>
            </div>
        </nav>
    </header>

    <main class="py-5">
        <div class="container" style="max-width: 800px;">
            
            <div class="d-flex justify-content-between align-items-center mb-4 pb-2 border-bottom border-light">
                <div>
                    <h1 class="fw-bold h2 mb-1" style="color: #1e293b;">Xác thực & Ký số Đơn hàng</h1>
                    <p class="text-muted small mb-0">Niêm phong thông tin đơn hàng bằng chữ ký điện tử trước khi thanh toán.</p>
                </div>
                <div>
                    <button type="button" class="btn btn-light text-secondary border fw-bold py-2 px-3 small" id="btnDownloadTool">
                        <i class="fas fa-download me-2"></i> Tải Tool ký số
                    </button>
                </div>
            </div>

            <div class="alert bg-warning bg-opacity-10 border-0 p-4 mb-4 rounded-4 text-start">
                <h6 class="text-warning-emphasis mb-3 fs-5">Thông tin hướng dẫn ký số đơn hàng</h6>
                <ul class="text-secondary small d-flex flex-column mb-0 instruction-list" style="padding-left: 0;">
                    <li>bước 1</li>
                    <li>bước 2</li>
                    <li>bước 3</li>
                </ul>
            </div>

            <div class="card modern-card p-4 mb-4">

                <div class="order-info-vault p-4 mb-4" id="orderInfoArea">
                    <h6 class="fw-bold mb-3 text-dark"><i class="fas fa-file-alt me-2"></i> Chi tiết thông tin đơn hàng</h6>
                    <div class="row g-2 small text-secondary">
                        <div class="col-12"><strong>Mã đơn hàng:</strong> <span class="text-dark" id="lblOrderId"></span></div>
                        <div class="col-12"><strong>Sản phẩm:</strong> <span class="text-dark" id="lblOrderProducts"></span></div>
                        <div class="col-12 mb-3"><strong>Tổng tiền đơn hàng:</strong> <span class="text-danger fw-bold" id="lblOrderTotal"></span></div>
                        
                        <div class="col-12 border-top pt-3">
                            <input type="hidden" id="txtOrderHash" value="7e57c667b2d5f0e3b1c44298fc1c149afbf4c8996fb92427ae41e4649b934ca4"> 
                            
                            <button type="button" class="btn btn-outline-secondary fw-bold btn-sm px-3" id="btnCopyHash">
                                <i class="fas fa-copy me-2"></i> Copy mã băm
                            </button>
                        </div>
                    </div>
                </div>

                <div class="row g-3">
                    <div class="col-12">
                        <div class="d-flex justify-content-between align-items-center mb-2">
                            <label class="form-label text-secondary small fw-bold uppercase mb-0">
                                <i class="fas fa-signature me-1"></i> Nhập chữ ký điện tử
                            </label>
                            
                            <div>
                                <input type="file" id="fileSignatureUpload" accept=".txt,.key,.bin" class="d-none">
                                <button type="button" class="btn btn-link p-0 text-decoration-none small fw-semibold text-secondary" id="btnTriggerUpload">
                                    <i class="fas fa-file-upload me-1"></i> Nhập chữ ký bằng file
                                </button>
                            </div>
                        </div>
                        
                        <textarea class="form-control signature-input-box" id="txtSignature" rows="4" placeholder="Chuỗi chữ ký điện tử tạo ra từ Tool của bạn..."></textarea>
                        <div class="mt-2 text-muted small">
                            Lưu ý: Hãy chắc chắn chuỗi chữ ký được sao chép chính xác từ Tool ký số.
                        </div>
                    </div>
                </div>

                <div class="text-center mt-4">
                    <button type="button" class="btn btn-modern-primary px-5" id="btnConfirmSignature">
                        <i class="fas fa-spinner fa-spin d-none me-2" id="iconSignLoading"></i> Xác nhận ký số
                    </button>
                </div>

                <div class="alert alert-info text-center status-text-box mx-auto mt-4 mb-0 p-2 border-0 w-100" id="lblSignStatus">
                    <i class="fas fa-sync-alt fa-spin me-2"></i> Đang xác thực chữ ký... Vui lòng đợi trong giây lát.
                </div>
            </div>

            <div class="text-end mt-4">
                <button type="button" class="btn btn-modern-primary px-5 shadow-sm py-2" id="btnProceedToPayment">
                    Tiếp tục đến bước thanh toán <i class="fas fa-arrow-right ms-2"></i>
                </button>
            </div>
            
        </div>
    </main>

    <div class="custom-toast" id="copyToast">
        Đã sao chép vào bảng nhớ tạm
    </div>

</body>
</html>