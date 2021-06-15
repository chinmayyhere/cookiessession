/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Vaibhav
 */
public class logincheck extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            String Username=request.getParameter("userid");
            String Password=request.getParameter("pwd");
            
            Connection c1;
            
           Class.forName("oracle.jdbc.OracleDriver");
          c1=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE","system","Vaibhav##007"); 
           Statement Stm = c1.createStatement();
            ResultSet rs=Stm.executeQuery("select * from registeruser where username='"+Username+"' and password='"+Password+"'");
           if(rs.next())
           {
               HttpSession mysess=request.getSession(true);
               mysess.setAttribute("username", Username);
               if(request.getParameter("rem")!=null)
               {
                   Cookie mycook=new Cookie("uname", Username);
                   mycook.setMaxAge(60*60*24);
                   response.addCookie(mycook);
               }
               response.sendRedirect("loginpage");
           }
           else
           {
               out.println("Username or Password Mismatched...<a href='login'>Try Again</a>");
           }
        }
        catch(ClassNotFoundException a)
        {
         System.out.println(a.getMessage());           
        }
        catch(SQLException b)
        {
            System.out.println(b.getMessage());
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
