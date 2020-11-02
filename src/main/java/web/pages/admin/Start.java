package web.pages.admin;

import web.pages.BaseServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/AdminStart")
public class Start extends BaseServlet {
    
    private void setStats(HttpServletRequest req){
        req.setAttribute("countOrders", api.getAllOrders().size());
        req.setAttribute("countCustomers", api.getAllUsers().size());
        req.setAttribute("totalSale", api.getTotalSales());
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
            setStats(req);
            render("Administrator - Start", "/WEB-INF/v"+api.getVersion()+"/admin/start.jsp", req, resp);
        } catch (Exception e){
            log(e.getMessage());
        }
    }
}