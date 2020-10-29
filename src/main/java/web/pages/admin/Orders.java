package web.pages.admin;

import domain.order.Order;
import web.pages.BaseServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

@WebServlet("/AdminOrders")
public class Orders extends BaseServlet {
    
    /**
     * Renders the index.jsp page
     * @see BaseServlet
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        
        ArrayList<Order> orders = api.getOrders();
        
        req.setAttribute("orders", orders);
        
        render("Administrator - Vis ordre", "/WEB-INF/admin/orders.jsp", req, resp);
    }
}