package web.pages.admin;

import domain.order.Order;
import infrastructure.DBOrder;
import web.pages.BaseServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.LinkedHashMap;

@WebServlet("/AdminOrders")
public class Orders extends BaseServlet {
    
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        try {
            switch (req.getParameter("action")){
                case "markDone":
                    markDone(req, resp);
                    return;
                case "deleteOrder":
                    deleteOrder(req, resp);
                    return;
                default:
                    System.out.println("default reached");
            }
        } catch (Exception e){
            throw new RuntimeException(e);
        }
    }
    
    private void deleteOrder(HttpServletRequest req, HttpServletResponse resp) throws IOException{
            int orderId = Integer.parseInt(req.getParameter("orderId"));
            new DBOrder(api.getDatabase()).deleteOrder(orderId);
            resp.sendRedirect(req.getContextPath() + "/AdminOrders");
    }
    
    private void markDone(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        int orderId = Integer.parseInt(req.getParameter("orderId"));
        new DBOrder(api.getDatabase()).markDone(orderId);
        resp.sendRedirect(req.getContextPath() + "/AdminOrders");
    }
    
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
    
            LinkedHashMap<Order, Double> ordersNew = api.getAllOrders();
    
            req.setAttribute("orders", ordersNew);

            render("Administrator - Vis ordre", "/WEB-INF/v"+api.getVersion()+"/admin/orders.jsp", req, resp);
        } catch (Exception e){
            log(e.getMessage());
        }
    }
}