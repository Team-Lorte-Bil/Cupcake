package web.pages.admin;

import web.pages.BaseServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

@WebServlet("/AdminCustommers")
public class Customers extends BaseServlet {


    /**
     * Renders the index.jsp page
     * @see BaseServlet
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

      //  ArrayList<Customers> custommers = api.;

      //  System.out.println(custommers);

      //  req.setAttribute("custommers", custommers);

        render("Administrator - Vis kunder", "/WEB-INF/admin/Costummers.jsp", req, resp);
    }
}