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
        
        
        Order tmpOrder = api.createNewOrder(curUser, getCart(req.getSession()), comment);
        
        
        
        req.setAttribute("order",tmpOrder);
        req.setAttribute("currentUser", curUser);
        
        log(req,"Got: " + tmpOrder);
        
        try{
            render("Order confirmation", "/WEB-INF/v"+api.getVersion()+"/orderconfirmation.jsp", req, resp);
        } catch (ServletException | IOException e){
            log(e.getMessage());
        }
        
        clearCart(session);
        
    }
    
    /**
     * Sends the user to a error 400 page.
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        try {
            resp.sendError(400, "No order sent");
        } catch (IOException e){
            log(e.getMessage());
        }
    }
    
    /**
     * Clears the session cart.
     * @param session Current HttpSession
     * @see api.Cupcake
     */
    private void clearCart(HttpSession session){
        session.setAttribute("cart", null);
    }
}