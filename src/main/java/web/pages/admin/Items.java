package web.pages.admin;

import domain.items.CakeOptions;
import domain.items.Option;
import domain.order.Order;
import domain.user.User;
import web.pages.BaseServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

@WebServlet("/AdminItems")
public class Items extends BaseServlet {
    
    /**
     * Renders the index.jsp page
     * @see BaseServlet
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        
        ArrayList<Option> items = api.getAllCakeOptions();
        
        for(Option o: items){
            System.out.println(o);
        }
        
        req.setAttribute("items", items);
        
        render("Administrator - Vis produkter", "/WEB-INF/admin/items.jsp", req, resp);
    }
}