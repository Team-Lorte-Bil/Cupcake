package ui;

import api.Cupcake;
import infrastructure.Database;

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
    
    /**
     * @param title Page title tag
     * @param content Content to be rendered. (Provide JSP path)
     * @param req Servlet request
     * @param resp Servlet response
     */
    protected void render(String title, String content, HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        req.setAttribute("title", title);
        req.setAttribute("content", content);
        req.getRequestDispatcher("/includes/base.jsp").forward(req, resp);
    }
    
    /**
     * Logs desired info
     * @param req Request servlet to log
     * @param str Text to log
     */
    protected void log(HttpServletRequest req, String str){
        System.err.print("(" + LocalDateTime.now() + ")" + this.getClass().getCanonicalName() + " - " + req.getRequestURI() + " - " + str);
    }
}
