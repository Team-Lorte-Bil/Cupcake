package web.pages.admin;

import domain.order.Order;
import web.pages.BaseServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

@WebServlet("/AdminOrders")
public class Orders extends BaseServlet {
    
    /**
     * Renders the index.jsp page
     * @see BaseServlet
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
    
        try {
            if (! api.checkAdminRights(req)) {
                resp.sendError(401);
            }
    
            if (! req.getSession().getAttribute("isAdmin").equals(true) || req.getSession().getAttribute("isAdmin") == null)
                resp.sendError(401);
    
            ArrayList<Order> orders = api.getOrders();
            HashMap<Order, Double> ordersNew = api.getAllOrders();
    
            req.setAttribute("orders", orders);
    
            render("Administrator - Vis ordre", "/WEB-INF/admin/orders.jsp", req, resp);
        } catch (Exception e){
            log(e.getMessage());
        }
    }
}