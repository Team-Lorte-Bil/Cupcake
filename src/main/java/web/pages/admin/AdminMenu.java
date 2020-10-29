package web.pages.admin;


import domain.items.Cake;
import web.pages.BaseServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

@WebServlet("/AdminMenu")
public class AdminMenu extends BaseServlet {

    /**
     * Renders the index.jsp page
     * @see BaseServlet
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

                ArrayList<Cake> adminmenu = api.createCakeOption();

        System.out.println(adminmenu);

        req.setAttribute("adminmenu", adminmenu);

        render("Administrator - Vis admin menu", "/WEB-INF/admin/adminmenu.jsp", req, resp);
    }
}