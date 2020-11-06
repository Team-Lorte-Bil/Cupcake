package web.pages.admin;

import domain.user.User;
import web.pages.BaseServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/AdminStart")
public class Start extends BaseServlet {
    
    private void setStats(HttpServletRequest req){
        req.setAttribute("countOrders", api.getAllOrdersSorted().size());
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
            User usr = (User) req.getSession().getAttribute("currentUser");
            
            log("Trying to log into admin :" + usr);
            
            if (usr == null || !usr.isAdmin()) {
                log("User is not admin: " + usr );
                resp.sendError(401);
            } else {
                log("User is admin: " + usr);
                setStats(req);
                render("Administrator - Start", "/WEB-INF/v"+api.getVersion()+"/admin/start.jsp", req, resp);
            }
            
        } catch (Exception e){
            log(e.getMessage());
        }
    }
}