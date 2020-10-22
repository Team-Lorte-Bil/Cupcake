package ui;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class BaseServlet extends HttpServlet {
    
    protected void render(String title, String content, HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.setAttribute("title", title);
        req.setAttribute("content", content);
        req.getRequestDispatcher("/includes/base.jsp").forward(req, resp);
    }
}
