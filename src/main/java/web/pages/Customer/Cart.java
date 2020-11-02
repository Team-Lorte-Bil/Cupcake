package web.pages.Customer;

import domain.items.Cake;
import domain.items.CakeOptions;
import domain.items.Option;
import web.pages.BaseServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

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
        req.setCharacterEncoding("UTF-8");
        
        switch (req.getParameter("action")){
            case "add":
                addToCart(req,session);
                break;
            case "remove":
                removeFromCart(req,resp,session);
                break;
            default:
                log(req,"default reached");
                resp.sendError(400);
                return;
        }
        
        render("Cart", "/WEB-INF/v"+api.getVersion()+"/cart.jsp", req, resp);
        
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
        session.setAttribute("cart",api.getCart());
    }
    
    /**
     * Adds the desired cake to the list
     * @see api.Cupcake
     */
    private void addToCart(HttpServletRequest req, HttpSession session){
        CakeOptions cakeOptions = api.getCakeOptions();
    
        String bottom = req.getParameter("bund");
        String topping = req.getParameter("topping");
        String antalStr = req.getParameter("antal");
        
        int bottomPrice = 0;
        for(Option bottoms: cakeOptions.getBottoms()){
            if(bottoms.getName().equalsIgnoreCase(bottom)){
                bottomPrice += bottoms.getPrice();
            }
        }
    
        int toppingPrice = 0;
        for(Option toppings: cakeOptions.getToppings()){
            if(toppings.getName().equalsIgnoreCase(topping)){
                toppingPrice += toppings.getPrice();
            }
        }
    
        int price = bottomPrice + toppingPrice;
        int antal = Integer.parseInt(antalStr);
    
        Cake tmpCake = new Cake(bottom,topping,price);
        
        api.addCake(tmpCake, antal);
        
        session.setAttribute("cart",api.getCart());
    }
    
    /**
     * Renders the cart if no POST is sent.
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        
        req.setAttribute("value", api.getCartValue());
    
        System.out.println(api.getCart());
    
    
        render("Cart", "/WEB-INF/v"+api.getVersion()+"/cart.jsp", req, resp);
    }
}
