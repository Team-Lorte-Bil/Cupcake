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
import java.util.HashMap;

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
        User curUser = (User) req.getSession().getAttribute("currentUser");
        System.out.println("User: " + curUser);
        HashMap<Cake, Integer> cakesFromSession = (HashMap<Cake, Integer>) req.getSession().getAttribute("cakes");
        System.out.println("Cakes from sess: " + cakesFromSession);
        String comment = (String) req.getAttribute("comment");
        System.out.println("Comment: " + comment);
        if(comment == null){
            comment = "";
        }
    
        return new DBOrder(api.getDatabase()).createOrder(curUser, cakesFromSession, comment);
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