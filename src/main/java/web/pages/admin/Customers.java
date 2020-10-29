package web.pages.admin;

import domain.order.Order;
import domain.user.User;
import web.pages.BaseServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

@WebServlet("/AdminCustomers")
public class Customers extends BaseServlet {
    
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
    
            ArrayList<User> customers = api.getCustomers();
    
            System.out.println(customers);
    
            req.setAttribute("customers", customers);
    
            render("Administrator - Vis kunder", "/WEB-INF/admin/customers.jsp", req, resp);
        } catch (Exception e){
            log(e.getMessage());
        }
    }
}