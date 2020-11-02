package web.pages.admin;

import domain.user.User;
import web.pages.BaseServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

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
    
            List<User> customers = api.getCustomers();
    
    
            req.setAttribute("customers", customers);
            
            render("Administrator - Vis kunder", "/WEB-INF/v"+api.getVersion()+"/admin/customers.jsp", req, resp);
        } catch (Exception e){
            log(e.getMessage());
        }
    }
    
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        try {
            switch (req.getParameter("action")){
                case "deleteUser":
                    deleteUser(req, resp);
                    return;
                case "createUser":
                    registerNewUser(req, resp);
                    return;
                case "changeBalance":
                    changeBalance(req,resp);
                    return;
                default:
                    System.out.println("default reached");
            }
        } catch (Exception e){
            throw new RuntimeException(e);
        }
    }
    
    private void deleteUser(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        int userId = Integer.parseInt(req.getParameter("userId"));
        api.deleteUser(userId);
        resp.sendRedirect(req.getContextPath() + "/AdminCustomers");
    }
    
    private void changeBalance(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        int userId = Integer.parseInt(req.getParameter("userId"));
        double newBalance = Double.parseDouble(req.getParameter("newBalance"));
        api.changeUserBalance(userId, newBalance);
        resp.sendRedirect(req.getContextPath() + "/AdminCustomers");
    }
    
    private void registerNewUser (HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            String usrName = req.getParameter("inputName");
            String usrMail = req.getParameter("inputEmail");
            int usrPhone = Integer.parseInt(req.getParameter("inputPhone"));
            double balance = Integer.parseInt(req.getParameter("inputBalance"));
            String role = req.getParameter("inputRole");
            String usrPsw = req.getParameter("inputPsw");
    
            
            User newUsr = api.createNewUser(usrName, usrPsw, usrMail, usrPhone, balance, role);
            
            if(newUsr != null){
                resp.sendRedirect(req.getContextPath() + "/AdminCustomers");
            } else {
                throw new Exception("Error in creating new user");
            }
    
            
        } catch (Exception e){
            log(e.getMessage());
            resp.sendError(400);
        }
    }
}