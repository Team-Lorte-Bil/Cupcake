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

@WebServlet("/Logout")
public class Logout extends BaseServlet {
    /**
     * Renders the Register new account page
     * @see BaseServlet
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.getSession().invalidate();
        log(req, "logged out");
        resp.sendRedirect(req.getContextPath() + "/");
    }

}