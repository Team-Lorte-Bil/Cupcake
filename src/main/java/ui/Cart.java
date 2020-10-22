package ui;

import domain.items.Cake;
import domain.items.CakeOption;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


@WebServlet("/Cart")
public class Cart extends BaseServlet {
    
    private HashMap<Cake, Integer> cakes = new HashMap<>();
    
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        HttpSession session = req.getSession();
    
        if(session.getAttribute("cakes") != null){
            cakes = (HashMap<Cake, Integer>) session.getAttribute("cakes");
        }
    
        if(req.getParameter("type") != null){ //TODO: Fix error when trying to remove item from cart.
            if(req.getParameter("type").equalsIgnoreCase("removeitem")){
                removeFromCart(Integer.parseInt(req.getParameter("id")));
            }
        }
    
        CakeOption cakeOption = new CakeOption();
    
        String target = req.getParameter("target");
        String bottom = req.getParameter("bunde");
        String topping = req.getParameter("topping");
        String comment = req.getParameter("subject");
        int antal = Integer.parseInt(req.getParameter("antal"));
    
        final double[] bottomPrice = {0.0};
        final double[] toppingPrice = {0.0};
    
        cakeOption.getBottoms().forEach((k,v) -> {
            if(k.equalsIgnoreCase(bottom)){
                bottomPrice[0] = v;
            }
        });
        cakeOption.getToppings().forEach((k,v) -> {
            if(k.equalsIgnoreCase(topping)){
                toppingPrice[0] = v;
            }
        });
    
        double price = bottomPrice[0]+toppingPrice[0];
    
        Cake cake = new Cake(bottom,topping,price);
    
        cakes.put(cake,antal);
    
    
        session.setAttribute( "lastcake", cake);
        session.setAttribute("cakes",cakes);
        session.setAttribute("totalprice", String.format("%.0f",calculateTotalPrice(cakes)));
    
        render("Cart", "/WEB-INF/cart.jsp", req, resp);
    }
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
    
        HttpSession session = req.getSession();
    
        if(session.getAttribute("cakes") != null){
            cakes = (HashMap<Cake, Integer>) session.getAttribute("cakes");
        }
    
        if(req.getParameter("type") != null){
            if(req.getParameter("type").equalsIgnoreCase("removeitem")){
                removeFromCart(Integer.parseInt(req.getParameter("id")));
            }
        }
    
        render("Cart", "/WEB-INF/cart.jsp", req, resp);
    }
    
    
    private boolean removeFromCart(int id){
        for(Cake c: cakes.keySet()){
            if(c.getId() == id){
                cakes.remove(c);
                return true;
            }
        }
        return false;
    }
    
    private double calculateTotalPrice(Map<Cake,Integer> cakes){
        double totalprice = 0;
        for (Map.Entry<Cake, Integer> entry : cakes.entrySet()) {
            Cake k = entry.getKey();
            Integer v = entry.getValue();
            totalprice += k.getPrice() * v;
        }
        return totalprice;
    }
}