package web.pages.admin;

import domain.user.User;
import web.pages.BaseServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

@WebServlet("/AdminStart")
public class Start extends BaseServlet {
    
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
            render("Administrator - Start", "/WEB-INF/admin/start.jsp", req, resp);
        } catch (Exception e){
            log(e.getMessage());
        }
    }
}