package web.pages.admin;

import domain.items.Option;
import web.pages.BaseServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/AdminItems")
public class Items extends BaseServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        try {
            switch (req.getParameter("action")){
                case "delete":
                    deleteItem(req, resp);
                    return;
                case "createItem":
                    newItem(req, resp);
                    return;
                default:
                    System.out.println("default reached");
            }
        } catch (Exception e){
            throw new RuntimeException(e);
        }
    }
    
    private void deleteItem(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String type = req.getParameter("type");
        int itemId = Integer.parseInt(req.getParameter("itemId"));
        api.deleteCakeOption(itemId, type);
        resp.sendRedirect(req.getContextPath() + "/AdminItems");
    }
    
    private void newItem (HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            String itemName = req.getParameter("inputItemName");
            int price = Integer.parseInt(req.getParameter("inputPrice"));
            String itemType = req.getParameter("inputType");
            
            api.createCakeOption(itemName, price, itemType);
            
            resp.sendRedirect(req.getContextPath() + "/AdminItems");
        } catch (Exception e){
            log(e.getMessage());
            resp.sendError(400);
        }
    }
    
    /**
     * Renders the index.jsp page
     * @see BaseServlet
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
    
        try {
            if (! api.checkAdminRights(req)) {
                resp.sendError(401);
            }
    
            List<Option> items = api.getAllCakeOptions();
    
            for (Option o : items) {
                System.out.println(o);
            }
    
            req.setAttribute("items", items);
            
            render("Administrator - Vis produkter", "/WEB-INF/v"+api.getVersion()+"/admin/items.jsp", req, resp);
    
        } catch (Exception e){
            log(e.getMessage());
        }
    }
}