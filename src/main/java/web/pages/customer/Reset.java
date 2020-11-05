package web.pages.customer;

import api.CupcakeRuntimeException;
import domain.user.InvalidPassword;
import domain.user.User;
import domain.user.UserNotFound;
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
        try {
            render("Reset password", "/WEB-INF/v" + api.getVersion() + "/resetpassword.jsp", req, resp);
        } catch (Exception e){
            throw new CupcakeRuntimeException(e.getMessage());
        }
    }
    
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        try {
            String mail = req.getParameter("inputEmail");
            String newPassword = api.resetPassword(mail);
            
            req.setAttribute("msgString", newPassword);
            req.setAttribute("msg", true);
    
            render("Reset password", "/WEB-INF/v" + api.getVersion() + "/resetpassword.jsp", req, resp);
    
        } catch (UserNotFound psw) {
            req.setAttribute("errorMsg", psw.getMessage());
            req.setAttribute("error", true);
            render("Reset password", "/WEB-INF/v" + api.getVersion() + "/resetpassword.jsp", req, resp);
            
        }
    }
}