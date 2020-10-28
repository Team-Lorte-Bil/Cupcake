package ui.Customer;

import domain.items.Cake;
import domain.items.CakeOptions;
import domain.user.User;
import domain.user.UserExists;
import infrastructure.DBUser;
import ui.BaseServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
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
        render("Register", "/WEB-INF/register.jsp", req, resp);
    }
    
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        try {
            registerNewUser(req, req.getSession());
            resp.sendRedirect(req.getContextPath() + "/");
        } catch (Exception e){
            resp.sendError(400);
        }
    }
    
    private void registerNewUser(HttpServletRequest req, HttpSession session){
        
        String usrName = req.getParameter("inputName");
        String usrMail = req.getParameter("inputEmail");
        int usrPhone = Integer.parseInt(req.getParameter("inputPhone"));
        String usrPsw = req.getParameter("inputPsw");
    
        User curUsr = new DBUser(api.getDatabase()).createUser(usrName,usrPsw,usrMail,usrPhone,0);
        System.out.println(curUsr);
        
        session.setAttribute("currentUser", curUsr);
        session.setAttribute("isAdmin", curUsr.isAdmin());
    }
    
    
}