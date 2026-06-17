package controller;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/ProductController")
public class ProductController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");

        if ("detail".equals(action)) {
            String id = request.getParameter("id");
            // Lấy thông tin sản phẩm dựa vào ID từ database
            // request.setAttribute("product", productObject);

            request.getRequestDispatcher("/ProductDetail.jsp").forward(request, response);
        } else {
            // Mặc định tải toàn bộ danh sách sản phẩm
            // List<Product> list = database.getAllProducts();
            // request.setAttribute("productList", list);

            request.getRequestDispatcher("/Listproducts.jsp").forward(request, response);
        }
    }
}