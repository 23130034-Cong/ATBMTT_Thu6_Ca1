<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Khởi tạo Cặp Khóa Bảo Mật - Thiên Lý</title>

    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
    <link rel="stylesheet" href="../css/PageCreateKey.css">

    <style>
        .code-vault-box { background: #1e293b; border-radius: 6px; padding: 12px; }
        .key-display-area { background: transparent !important; border: none; color: #34d399 !important; font-family: monospace; resize: none; }
        .status-text-box, #lblSuccessStatus { display: none; }
        .custom-toast { position: fixed; bottom: 20px; right: 20px; background-color: #333; color: white; padding: 12px 24px; border-radius: 6px; display: none; z-index: 9999; }

        /* CSS bổ sung để textarea tự bẻ dòng khi chuỗi khóa quá dài (tránh bị tràn hàng ngang) */
        .signature-input-box {
            word-break: break-all !important;
            white-space: pre-wrap !important;
        }
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

            <div class="mb-4">
                <h1 class="fw-bold h2 mb-2" style="color: #1e293b;">Cặp Khóa Bảo Mật</h1>
                <p class="text-muted small">Khởi tạo và quản lý mật mã bảo mật để thực hiện niêm phong đơn hàng.</p>
            </div>

            <div class="alert bg-warning bg-opacity-10 border-0 p-4 mb-4 rounded-4 text-start">
                <h6 class="text-warning-emphasis mb-3 fs-5"><i class="fas fa-exclamation-triangle me-2"></i>Thông tin lưu ý quan trọng về cặp khóa</h6>
                <ul class="text-secondary small d-flex flex-column gap-2 mb-0" style="padding-left: 1rem;">
                    <li><strong>Public key</strong> (khóa công khai) sẽ được lưu tự động trên hệ thống website sau khi tạo thành công.</li>
                    <li><strong>Private key</strong> (khóa bí mật) dùng để ký trên Tool — <strong>website tuyệt đối không lưu giữ</strong>.</li>
                    <li>Bạn phải tự sao lưu và bảo mật private key. Nếu mất hoặc lộ, hãy <strong>báo mất khóa ngay lập tức</strong> để tránh bị kẻ gian giả mạo chữ ký.</li>
                </ul>
            </div>

            <div class="card modern-card p-4 mb-4">
                <div class="text-center pb-4 mb-4 border-bottom border-light">
                    <button type="button" class="btn btn-primary px-5 btn-modern-primary" id="btnGenerateKeyPair">
                        <i class="fas fa-cog fa-spin d-none me-2" id="iconLoading"></i>
                        <i class="fas fa-key me-2" id="iconDefault"></i> Tạo Cặp Khóa Mới
                    </button>
                </div>

                <div class="row g-4">
                    <div class="col-12">
                        <label class="form-label text-secondary small fw-bold uppercase">
                            <i class="fas fa-unlock me-1"></i> Public Key (Khóa Công Khai)
                        </label>
                        <textarea class="form-control signature-input-box" id="txtPublicKey" rows="4" placeholder="Nhấn nút phía trên để tự động tạo chuỗi khóa công khai..." readonly></textarea>
                    </div>

                    <div class="col-12">
                        <label class="form-label text-secondary small fw-bold">
                            <i class="fas fa-lock me-1"></i> Private Key (Khóa Bí Mật)
                        </label>
                        <textarea class="form-control signature-input-box" id="txtPrivateKey" rows="5" placeholder="Nhấn nút phía trên để tự động tạo chuỗi khóa bí mật..." readonly></textarea>
                        <div class="mt-2 d-flex align-items-center text-danger small">
                            <i class="fas fa-exclamation-circle me-1"></i> Lưu ý: Hãy chắc chắn là bạn đã tải xuống hoặc sao lưu khóa Private Key trước khi rời khỏi trang này!
                        </div>
                    </div>
                </div>

                <div class="alert alert-info text-center status-text-box mx-auto mt-4 mb-0 p-2 border-0 w-100" id="lblSignStatus">
                    <i class="fas fa-sync-alt fa-spin me-2"></i> Đang đồng bộ Public Key lên hệ thống... Vui lòng đợi.
                </div>

                <div class="row g-3 justify-content-center mt-4">
                    <div class="col-6 col-md-4">
                        <button type="button" class="btn btn-light w-100 fw-bold text-secondary border py-2 small" id="btnCopyPrivateKey">
                            <i class="fas fa-copy me-2"></i> Sao chép Private Key
                        </button>
                    </div>
                    <div class="col-6 col-md-4">
                        <button type="button" class="btn btn-success w-100 fw-bold py-2 small" id="btnSavePrivateKeyFile">
                            <i class="fas fa-download me-2"></i> Tải về (.key)
                        </button>
                    </div>
                </div>

                <div class="alert alert-success text-center mx-auto mt-4 mb-0 p-2 border-0 w-100" id="lblSuccessStatus">
                    <i class="fas fa-check-circle me-1"></i> Đã đồng bộ khóa lên hệ thống và lưu file thành công! Bạn có thể tiếp tục.
                </div>
            </div>

            <div class="d-flex justify-content-between align-items-center mt-4">
                <button type="button" class="btn btn-link text-decoration-none text-secondary p-0 fw-semibold" id="btnBackToOrder">
                    <i class="fas fa-arrow-left me-1"></i> Quay lại giỏ hàng
                </button>
                <button type="button" class="btn btn-success px-5 shadow-sm py-2" id="btnProceedToPayment" disabled>
                    Tiếp tục đến trang Ký số <i class="fas fa-arrow-right ms-2"></i>
                </button>
            </div>
        </div>
    </main>

    <div class="custom-toast" id="copyToast">
        Đã sao chép vào bảng nhớ tạm!
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>

    <script>
        function arrayBufferToBase64(buffer) {
            var binary = '';
            var bytes = new Uint8Array(buffer);
            var len = bytes.byteLength;
            for (var i = 0; i < len; i++) {
                binary += String.fromCharCode(bytes[i]);
            }
            return window.btoa(binary);
        }

        $(document).ready(function() {
            $('#btnGenerateKeyPair').click(async function() {
                $('#iconDefault').addClass('d-none');
                $('#iconLoading').removeClass('d-none');
                $('#lblSignStatus').fadeIn();
                $('#lblSuccessStatus').hide();

                try {
                    // 1. Sinh cặp khóa
                    const keyPair = await window.crypto.subtle.generateKey(
                        {
                            name: "RSASSA-PKCS1-v1_5",
                            modulusLength: 2048,
                            publicExponent: new Uint8Array([0x01, 0x00, 0x01]),
                            hash: {name: "SHA-256"}
                        },
                        true,
                        ["sign", "verify"]
                    );

                    const publicExported = await window.crypto.subtle.exportKey("spki", keyPair.publicKey);
                    const privateExported = await window.crypto.subtle.exportKey("pkcs8", keyPair.privateKey);

                    const publicKeyBase64 = arrayBufferToBase64(publicExported);
                    const privateKeyBase64 = arrayBufferToBase64(privateExported);

                    $('#txtPublicKey').val(publicKeyBase64);
                    $('#txtPrivateKey').val(privateKeyBase64);

                    // 2. Gửi lên Server
                    // Dùng Promise wrapper để có thể await $.ajax
                    await new Promise((resolve, reject) => {
                        $.ajax({
                            url: '${pageContext.request.contextPath}/SavePublicKeyController',
                            type: 'POST',
                            dataType: 'json',
                            data: { publicKey: publicKeyBase64 },
                            success: (res) => resolve(res),
                            error: (xhr) => reject(xhr)
                        });
                    }).then(response => {
                        if(response && response.status === "success") {
                            $('#lblSuccessStatus').fadeIn();
                            $('#btnProceedToPayment').prop('disabled', false);
                        } else {
                            throw new Error(response.message || "Lỗi lưu khóa.");
                        }
                    });

                } catch (err) {
                    console.error("Lỗi xảy ra:", err);
                    alert("Đã xảy ra lỗi: " + err.message + ". Kiểm tra Console (F12) để biết chi tiết.");
                } finally {
                    // Tắt icon loading dù thành công hay thất bại
                    $('#iconLoading').addClass('d-none');
                    $('#iconDefault').removeClass('d-none');
                    $('#lblSignStatus').hide();
                }
            });

            // Đặt các hàm này ra ngoài click handler của btnGenerateKeyPair
            $('#btnProceedToPayment').click(function() {
                window.location.href = "PageSign.jsp";
            });

            $('#btnBackToOrder').click(function() {
                window.location.href = "Cart.jsp";
            });
        });
    </script>
</body>
</html>