package ui;

import domain.items.Cake;
import domain.items.CakeOption;
import domain.user.LoginSampleException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

/**
 The purpose of Cart is to...

 @author kasper
 */
public class Cart extends Command {
    
    private HashMap<Cake, Integer> cakes = new HashMap<>();

    @Override
    String execute( HttpServletRequest request, HttpServletResponse response ) throws LoginSampleException {
        HttpSession session = request.getSession();
        
        if(session.getAttribute("cakes") != null){
            cakes = (HashMap<Cake, Integer>) session.getAttribute("cakes");
        }
        
        if(request.getParameter("type") != null){
            if(request.getParameter("type").equalsIgnoreCase("removeitem")){
                removeFromCart(Integer.parseInt(request.getParameter("id")));
                return "cart";
            }
        }
        
        CakeOption cakeOption = new CakeOption();
        
        String bottom = request.getParameter("bunde");
        String topping = request.getParameter("topping");
        String comment = request.getParameter("subject");
        int antal = Integer.parseInt(request.getParameter("antal"));
        
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

        
        return "cart";
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
