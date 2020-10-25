package ui.Customer;

import api.Cupcake;
import domain.items.Cake;
import domain.items.CakeOption;
import ui.BaseServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;

@WebServlet("/Cart")
public class Cart extends BaseServlet {
    
    /**
     * Renders the cart when POST is sent.
     * Parameter "action" should be set to "add" or "remove".
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        HttpSession session = req.getSession();
    
        setup(session);
        
        switch (req.getParameter("action")){
            case "add":
                addToCart(req,session);
            case "remove":
                removeFromCart(req,resp,session);
            default:
                log(req,"default reached");
                resp.sendError(400);
        }
        
        render("Cart", "/WEB-INF/cart.jsp", req, resp);
        
    }
    
    /**
     * Makes sure the list is created and ready for use.
     */
    private void setup(HttpSession session){
        if(session.getAttribute("cakes") != null){
            api.setCakes((HashMap<Cake, Integer>) session.getAttribute("cakes"));
        }
    }
    
    /**
     * Removes the desired cake from the List.
     * @see api.Cupcake
     */
    private void removeFromCart(HttpServletRequest req, HttpServletResponse resp, HttpSession session) throws IOException {
        int id = 0;
        try {
            id = Integer.parseInt(req.getParameter("id"));
        } catch (Exception e){
            log(req, e.getMessage());
            resp.sendError(400);
        }
        
        api.removeFromCart(id);
        session.setAttribute("cakes",api.getCakes());
    }
    
    /**
     * Adds the desired cake to the list
     * @see api.Cupcake
     */
    private void addToCart(HttpServletRequest req, HttpSession session){
        CakeOption cakeOption = new CakeOption();
    
        String bottom = req.getParameter("bund");
        String topping = req.getParameter("topping");
        String antalStr = req.getParameter("antal");
    
        final int[] bottomPrice = {0};
        final int[] toppingPrice = {0};
    
        cakeOption.getBottoms().forEach((k, v) -> {
            if(k.equalsIgnoreCase(bottom)){
                bottomPrice[0] = v;
            }
        });
        cakeOption.getToppings().forEach((k, v) -> {
            if(k.equalsIgnoreCase(topping)){
                toppingPrice[0] = v;
            }
        });
    
        int price = bottomPrice[0]+toppingPrice[0];
        int antal = Integer.parseInt(antalStr);
    
        Cake tmpCake = new Cake(bottom,topping,price);
            System.out.println(tmpCake);
            api.addCake(tmpCake, antal);
    
        session.setAttribute("lastcake", tmpCake);
        session.setAttribute("totalprice", String.format("%d", api.getCartValue()));
        session.setAttribute("cakes",api.getCakes());
    }
    
    /**
     * Renders the cart if no POST is sent.
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        
        HttpSession session = req.getSession();
        setup(session);
        
        
        render("Cart", "/WEB-INF/cart.jsp", req, resp);
    }
}
