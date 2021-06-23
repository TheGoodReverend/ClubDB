/**
 *
 * @author KBowe
 */
package servlets;

import business.Member;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class ClubLogonServlet extends HttpServlet 
{
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException 
    {
        response.setContentType("text/html;charset=UTF-8");
        String URL = "/Logon.jsp", msg = "", sql=""; //moved SQl based on 2nd video
        String uid = "";
        long patt = 0;
        
        Member m;
        
        String dbURL = "jdbc:mysql://localhost:3306/club";
        String dbUser = "root";
        String dbPass = "sesame";
        
        
        try
        {
            uid = request.getParameter("userid").trim();
            patt = Long.parseLong(request.getParameter("password").trim());
            
            Class.forName("com.mysql.jdbc.Driver");
            
            Connection conn = DriverManager.getConnection(dbURL, dbUser, dbPass);
            Statement s = conn.createStatement();
            sql = "SELECT * from tblMembers where MemID = '" + uid + "'";
            ResultSet r = s.executeQuery(sql);
            
            if(r.next())
            {
                m = new Member();
                m.setMemid(uid);
                m.setPassword(r.getLong("Password"));
                m.setPassattempt(patt);
                if(m.isAuthenticated())
                {
                    m.setLastname(r.getString("Lastname"));
                    m.setFirstname(r.getString("Firstname"));
                    m.setMiddlename(r.getString("Middlename"));
                    m.setStatus(r.getString("Status"));
                    m.setMemdt(r.getString("Memdt"));
                    URL = "/MemberScreen.jsp";
                    msg += "User" + uid + " Authenticatd! <br>";
                    request.getSession().setAttribute("m", m);
                }
                else
                {
                    msg += "User " + uid + " found but not authenticated<br>";
                }
            }
            else
            {
                msg+= "member not found<br>";
            }
            r.close();
            s.close();
            conn.close();
            
        }
        catch(NumberFormatException e)
        {
            msg +="Password not numeric.<br>";
        }
        catch(SQLException e)
        {
            msg += "SQL err: " + e.getMessage() + " " + sql + "<br>";
        }
        catch(Exception e)
        {
            msg += "Servlet error: " + e.getMessage() + "<br>";
        }
          
        request.setAttribute("msg", msg);
        Cookie userid = new Cookie("userid", uid);
        userid.setMaxAge(60*10);
        userid.setPath("/");
        response.addCookie(userid);
        
        RequestDispatcher disp = getServletContext().getRequestDispatcher(URL);
        disp.forward(request,response);
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
