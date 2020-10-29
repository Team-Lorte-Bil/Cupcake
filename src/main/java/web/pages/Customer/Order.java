package web.pages.Customer;

import domain.items.Cake;
import domain.user.User;
import infrastructure.DBOrder;
import web.pages.BaseServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Random;

@WebServlet("/CreateOrder")
public class Order extends BaseServlet {
    
    /**
     * Create the order and redirects to order confirmation page.
     * @see Order
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        HttpSession session = req.getSession();
        
        
        domain.order.Order tmpOrder = createNewOrder(req);
        
        req.setAttribute("order",tmpOrder);
        
        log(req,"Got: " + tmpOrder);
        
        render("Order confirmation", "/WEB-INF/orderconfirmation.jsp", req, resp);
        
        clearCart(session,req); //TODO: Fix bugs
        
    }
    
    private domain.order.Order createNewOrder(HttpServletRequest req){
        User curUser = (User) req.getAttribute("currentUser");
        HashMap<Cake, Integer> cakesFromPost = (HashMap<Cake, Integer>) req.getAttribute("cakes");
        String comment = (String) req.getAttribute("comment");
    
        return new DBOrder(api.getDatabase()).createOrder(curUser, cakesFromPost, comment);
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
        session.removeAttribute("totalprice");
        session.removeAttribute("lastcake");
        session.invalidate();
        req.getSession().invalidate();
    }
}