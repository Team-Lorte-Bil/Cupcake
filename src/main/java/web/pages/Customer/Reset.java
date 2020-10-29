package web.pages.Customer;

import web.pages.BaseServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/Reset")
public class Reset extends BaseServlet {
    
    /**
     * Renders the Reset password page
     * @see BaseServlet
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        
        render("Reset password", "/WEB-INF/resetpassword.jsp", req, resp);
    }
}