package web.pages.Customer;

import domain.items.Cake;
import domain.order.Order;
import domain.user.User;
import infrastructure.DBOrder;
import web.pages.BaseServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;

@WebServlet("/CreateOrder")
public class NewOrder extends BaseServlet {
    
    private HashMap<Cake, Integer> cakes = new HashMap<>();
    
    /**
     * Create the order and redirects to order confirmation page.
     * @see NewOrder
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        HttpSession session = req.getSession();
        
        cakes = (HashMap<Cake, Integer>) session.getAttribute("cakes");
        
        
        Order tmpOrder = createNewOrder(req);
        
        req.setAttribute("order",tmpOrder);
        req.setAttribute("cakes", cakes);
        
        log(req,"Got: " + tmpOrder);
        
        render("NewOrder confirmation", "/WEB-INF/orderconfirmation.jsp", req, resp);
        
        clearCart(session,req); //TODO: Fix bugs
        
    }
    
    private Order createNewOrder(HttpServletRequest req){
        User curUser = (User) req.getSession().getAttribute("currentUser");
        String comment = req.getParameter("comment");
        if(comment == null){
            comment = "";
        }
    
        return new DBOrder(api.getDatabase()).createOrder(curUser, cakes, comment);
    }
    
    /**
     * Sends the user to a error 400 page.
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        resp.sendError(400,"No order sent");
    }
    
    /**
     * Clears the session cart.
     * @param session Current HttpSession
     * @param req Current HttpServletRequest
     * @see api.Cupcake
     */
    private void clearCart(HttpSession session, HttpServletRequest req){
        api.clearCart();
        session.removeAttribute("cakes");
        req.removeAttribute("cakes");
        cakes.clear();
        session.removeAttribute("totalprice");
        session.removeAttribute("lastcake");
    }
}