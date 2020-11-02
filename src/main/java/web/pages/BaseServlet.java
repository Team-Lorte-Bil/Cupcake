package web.pages;

import api.Cupcake;
import infrastructure.Database;
import web.widgets.Navbar;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;


public class BaseServlet extends HttpServlet {
    protected static final Cupcake api;
    
    static {
        api = createApplication();
    }
    
    private static Cupcake createApplication() {
        Database db = new Database();
        return new Cupcake(db);
    }
    
    
    protected void render(String title, String content, HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        req.setAttribute("navbar", new Navbar(req));
        req.setAttribute("title", title);
        req.setAttribute("content", content);
        req.getRequestDispatcher("/WEB-INF/v" + api.getVersion() + "/includes/base.jsp").forward(req, resp);
    }
    
    
    protected void log(HttpServletRequest req, String str){
        System.err.print("(" + LocalDateTime.now() + ")" + this.getClass().getCanonicalName() + " - " + req.getRequestURI() + " - " + str);
    }
}
