package web.pages.customer;

import web.pages.BaseServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/Shop")
public class Shop extends BaseServlet {
    
    /**
     * Renders the index.jsp page
     * @see BaseServlet
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        
        try {
            req.setAttribute("toppings", api.getCakeOptions().getToppings());
            req.setAttribute("bottoms", api.getCakeOptions().getBottoms());
        } catch (Exception e){
            log(e.getMessage());
        } finally {
            render("Shop", "/WEB-INF/v" + api.getVersion() + "/shop.jsp", req, resp);
        }
    }
}