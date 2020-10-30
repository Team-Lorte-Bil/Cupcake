package web.pages.Customer;

import web.pages.BaseServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("")
public class Index extends BaseServlet {
    
    /**
     * Renders the index.jsp page
     * @see BaseServlet
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        
        req.setAttribute("toppings", api.getCakeOptions().getToppings());
        req.setAttribute("bottoms", api.getCakeOptions().getBottoms());
    
        render("Cart", "/WEB-INF/v"+api.getVersion()+"/index.jsp", req, resp);
    }
}