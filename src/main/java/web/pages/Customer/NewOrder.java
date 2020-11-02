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
import java.util.ArrayList;
import java.util.List;

@WebServlet("/CreateOrder")
public class NewOrder extends BaseServlet {
    
    private List<Order.Item> cakes = new ArrayList<>();
    
    /**
     * Create the order and redirects to order confirmation page.
     * @see NewOrder
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        HttpSession session = req.getSession();
        
        cakes = (List<Order.Item>) session.getAttribute("cakes");
        User curUser = (User) req.getSession().getAttribute("currentUser");
        String comment = req.getParameter("comment");
        
        
        Order tmpOrder = api.createNewOrder(curUser, cakes, comment);
        
        
        
        req.setAttribute("order",tmpOrder);
        req.setAttribute("cakes", cakes);
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
        session.removeAttribute("cakes");
        req.removeAttribute("cakes");
        cakes.clear();
        session.removeAttribute("totalprice");
        session.removeAttribute("lastcake");
    }
}