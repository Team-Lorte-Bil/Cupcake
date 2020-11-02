package web.pages.Customer;

import domain.user.User;
import infrastructure.DBUser;
import web.pages.BaseServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/Register")
public class Register extends BaseServlet {
    
    /**
     * Renders the Register new account page
     * @see BaseServlet
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        render("Register user", "/WEB-INF/v"+api.getVersion()+"/register.jsp", req, resp);
    }
    
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        try {
            registerNewUser(req, req.getSession());
            resp.sendRedirect(req.getContextPath() + "/");
        } catch (Exception e){
            resp.sendError(400);
            throw new RuntimeException(e);
        }
    }
    
    private void registerNewUser(HttpServletRequest req, HttpSession session){
        
        String usrName = req.getParameter("inputName");
        String usrMail = req.getParameter("inputEmail");
        int usrPhone = Integer.parseInt(req.getParameter("inputPhone"));
        String usrPsw = req.getParameter("inputPsw");
    
        User curUsr = api.createNewUser(usrName, usrPsw, usrMail, usrPhone, 0, "User");
        
        session.setAttribute("currentUser", curUsr);
        session.setAttribute("isAdmin", curUsr.isAdmin());
    }
    
    
}