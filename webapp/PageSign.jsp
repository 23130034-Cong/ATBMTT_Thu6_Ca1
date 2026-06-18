<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Xác thực & Ký số Đơn hàng - Thiên Lý</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
    <link rel="stylesheet" href="../css/PageSign.css">
    <style>
        .code-vault-box { background: #1e293b; border-radius: 6px; padding: 10px; }
        .key-display-area { background: transparent !important; border: none; color: #34d399 !important; font-family: monospace; resize: none; }
        .success-status-text, .status-text-box { display: none; }
        .custom-toast { position: fixed; bottom: 20px; right: 20px; background: #333; color: #fff; padding: 10px 20px; border-radius: 4px; display: none; z-index: 9999; }
    </style>
</head>
<body>

    <header class="bg-white border-bottom p-0">
        <nav class="navbar navbar-light bg-light p-0 m-0">
            <div class="container" style="max-width: 800px;">
                <a class="navbar-brand fw-bold d-flex align-items-center p-0 m-0" href="Mainpage.jsp">
                    <img class="Trademark_logo" src="../remove-photos-background-removed.png" height="50" width="50" alt="Thiên Lý Logo" />
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
                <h6 class="text-warning-emphasis mb-3 fs-5"><i class="fas fa-info-circle me-2"></i>Quy trình thực hiện ký số đơn hàng</h6>
                <ul class="text-secondary small d-flex flex-column gap-2 mb-0 instruction-list" style="padding-left: 1rem;">
                    <li><strong>Bước 1:</strong> Sao chép <strong>Mã băm SHA-256</strong> của đơn hàng hiển thị ở khung chi tiết bên dưới.</li>
                    <li><strong>Bước 2:</strong> Mở Tool ký số trên máy tính, dán mã băm vừa copy và nạp file <strong>Private Key</strong> của bạn để tạo ra đoạn mã chữ ký số.</li>
                    <li><strong>Bước 3:</strong> Sao chép chuỗi chữ ký điện tử từ Tool, dán vào ô nhập liệu bên dưới và nhấn <strong>Xác nhận ký số</strong>.</li>
                </ul>
            </div>

            <div class="card modern-card p-4 mb-4">
                <div class="order-info-vault p-4 mb-4" id="orderInfoArea">
                    <h6 class="fw-bold mb-3 text-dark"><i class="fas fa-file-alt me-2"></i> Chi tiết thông tin đơn hàng</h6>
                    <div class="row g-2 small text-secondary">
                        <div class="col-12"><strong>Mã đơn hàng:</strong> <span class="text-dark" id="lblOrderId">${order.id}</span></div>
                        <div class="col-12"><strong>Sản phẩm:</strong> <span class="text-dark" id="lblOrderProducts">${order.products}</span></div>
                        <div class="col-12 mb-3"><strong>Tổng tiền đơn hàng:</strong> <span class="text-danger fw-bold" id="lblOrderTotal">${order.totalAmount}đ</span></div>

                        <div class="col-12 border-top pt-3">
                            <input type="hidden" id="txtOrderHash" value="${order.hashSHA256}">
                            <span class="text-muted d-block mb-2 font-monospace small" style="word-break: break-all;">Hash: ${order.hashSHA256}</span>
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
                    </div>
                </div>

                <div class="text-center mt-4">
                    <button type="button" class="btn btn-modern-primary px-5 btn-primary" id="btnConfirmSignature">
                        <i class="fas fa-spinner fa-spin d-none me-2" id="iconSignLoading"></i> Xác nhận ký số
                    </button>
                </div>

                <div class="alert alert-info text-center status-text-box mx-auto mt-4 mb-0 p-2 border-0 w-100" id="lblSignStatus">
                    <i class="fas fa-sync-alt fa-spin me-2"></i> Đang xác thực chữ ký... Vui lòng đợi trong giây lát.
                </div>

                <div class="alert alert-success text-center success-status-text mx-auto mt-4 mb-0 p-2 border-0 w-100" id="lblSuccessStatus">
                    <i class="fas fa-check-circle me-1"></i> Chữ ký hợp lệ! Đơn hàng đã được xác thực an toàn.
                </div>
            </div>

            <div class="d-flex justify-content-between align-items-center mt-4">
                <button type="button" class="btn btn-link text-decoration-none text-secondary p-0 fw-semibold" id="btnBackToOrder">
                    <i class="fas fa-arrow-left me-1"></i> Quay lại giỏ hàng
                </button>
                <button type="button" class="btn btn-success px-5 shadow-sm py-2" id="btnProceedToPayment" disabled>
                    Tiếp tục đến bước thanh toán <i class="fas fa-arrow-right ms-2"></i>
                </button>
            </div>

        </div>
    </main>

    <div class="custom-toast" id="copyToast">Đã sao chép vào bộ nhớ tạm!</div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <script>
        $(document).ready(function() {
            // Copy mã băm SHA-256 đơn hàng
            $('#btnCopyHash').click(function() {
                var hashText = $('#txtOrderHash').val();
                navigator.clipboard.writeText(hashText);
                $('#copyToast').fadeIn(200).delay(1500).fadeOut(200);
            });

            // Trigger upload file chữ ký
            $('#btnTriggerUpload').click(function() {
                $('#fileSignatureUpload').click();
            });

            // Đọc file chữ ký điền vào textarea
            $('#fileSignatureUpload').change(function(e) {
                var file = e.target.files[0];
                if (!file) return;
                var reader = new FileReader();
                reader.onload = function(e) {
                    $('#txtSignature').val(e.target.result.trim());
                };
                reader.readAsText(file);
            });

            // AJAX Xử lý xác nhận và verify chữ ký số gửi lên Servlet nhận diện
            $('#btnConfirmSignature').click(function() {
                var signature = $('#txtSignature').val().trim();
                var orderHash = $('#txtOrderHash').val();

                if (signature === "") {
                    alert("Vui lòng nhập hoặc tải chuỗi chữ ký điện tử!");
                    return;
                }

                $('#iconSignLoading').removeClass('d-none');
                $('#lblSignStatus').fadeIn();
                $('#lblSuccessStatus').hide();

                $.ajax({
                    url: '${pageContext.request.contextPath}/VerifySignatureServlet',
                    type: 'POST',
                    data: {
                        signature: signature,
                        orderHash: orderHash
                    },
                    success: function(response) {
                        $('#iconSignLoading').addClass('d-none');
                        $('#lblSignStatus').hide();

                        if(response.status === "success") {
                            $('#lblSuccessStatus').fadeIn();
                            $('#btnProceedToPayment').prop('disabled', false); // Mở khóa nút thanh toán
                        } else {
                            alert("Xác thực thất bại: " + response.message);
                            $('#btnProceedToPayment').prop('disabled', true);
                        }
                    },
                    error: function() {
                        $('#iconSignLoading').addClass('d-none');
                        $('#lblSignStatus').hide();
                        alert("Hệ thống gặp sự cố trong quá trình xác thực!");
                    }
                });
            });

            $('#btnBackToOrder').click(function() {
                window.location.href = "Cart.jsp";
            });
        });
    </script>
</body>
</html>