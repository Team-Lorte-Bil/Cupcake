package web.pages.Customer;

import domain.user.InvalidPassword;
import domain.user.User;
import infrastructure.DBUser;
import web.pages.BaseServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/Login")
public class Login extends BaseServlet {
    /**
     * Renders the Register new account page
     * @see BaseServlet
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        render("Login", "/WEB-INF/v"+api.getVersion()+"/logind.jsp", req, resp);
    }
    
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        try {
            login(req, req.getSession());
            if(req.getSession().getAttribute("cakes") != null){
                resp.sendRedirect(req.getContextPath() + "/Cart");
            } else if (req.getSession().getAttribute("isAdmin").equals(true)) {
                    resp.sendRedirect(req.getContextPath() + "/AdminStart");
            } else {
                    resp.sendRedirect(req.getContextPath() + "/");
            }
        } catch (InvalidPassword e){
            req.setAttribute("errorMsg", e.getMessage());
            req.setAttribute("error", true);
            render("Login", "/WEB-INF/v"+api.getVersion()+"/logind.jsp", req, resp);
        }
    }
    
    private void login(HttpServletRequest req, HttpSession session) throws InvalidPassword {
            String usrEmail = req.getParameter("inputEmail");
            String usrPassword = req.getParameter("inputPassword");
    
            log(usrEmail);
            log(usrPassword);
    
            User curUsr = api.checkLogin(usrEmail, usrPassword);
            log("Logged in: " + curUsr);
    
            session.setAttribute("currentUser", curUsr);
            session.setAttribute("isAdmin", curUsr.isAdmin());
    }

}