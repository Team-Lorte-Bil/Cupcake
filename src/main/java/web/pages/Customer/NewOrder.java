package web.pages.Customer;

import domain.order.Order;
import domain.user.User;
import web.pages.BaseServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/CreateOrder")
public class NewOrder extends BaseServlet {
    
    /**
     * Create the order and redirects to order confirmation page.
     * @see NewOrder
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        HttpSession session = req.getSession();
        
        
        User curUser = (User) req.getSession().getAttribute("currentUser");
        String comment = req.getParameter("comment");
        
        
        Order tmpOrder = api.createNewOrder(curUser, api.getCakes(), comment);
        
        
        
        req.setAttribute("order",tmpOrder);
        req.setAttribute("cart", api.getCart());
        req.setAttribute("currentUser", curUser);
        
        log(req,"Got: " + tmpOrder);
        
        render("Order confirmation", "/WEB-INF/v"+api.getVersion()+"/orderconfirmation.jsp", req, resp);
        
        clearCart(session,req);
        
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
        session.setAttribute("cart", api.getCart());
    }
}