/**
 *
 * @author KBowe
 */
package servlets;

import business.ConnectionPool;
import business.Member;
import business.Purchase;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class ShowPurchasesServlet extends HttpServlet 
{
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException 
    {
        response.setContentType("text/html;charset=UTF-8");
        String URL="/MemberScreen.jsp";
        String sql = "", msg = "", mo ="", dy="", yr="", sqlwhere="", lsq = "";
        double runtot = 0, sqltot = 0, ctot = 0, dtot=0, addr=0;
        
               
        try
        {
            Member m = (Member) request.getSession().getAttribute("m");
            mo = request.getParameter("month");
            dy = request.getParameter("day");
            yr = request.getParameter("year");
            
            if(mo.isEmpty() || dy.isEmpty() || yr.isEmpty())
            {
                sqlwhere = "";
            }
            else
            {
                sqlwhere = yr + "-" + mo + "-" + dy;
            }
            
            
            sql = "select p.MemID, p.PurchaseDt, p.TransType, p.TransCd, " + "c.TransDesc, p.Amount " + " From tblPurchases p, tblCodes c " + " Where p.TransCd = c.TransCd " + " AND p.MemID = ? ";
            
                       
            if(!sqlwhere.isEmpty())
            {
                sql += " AND p.purchasedt >= ? ";
            }
            
            
            sql+= " Order by p.PurchaseDt ";
            
            ConnectionPool pool = ConnectionPool.getInstance();
            Connection conn = pool.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
                        
            ps.setString(1, m.getMemid());
            if(!sqlwhere.isEmpty())
            {
                ps.setString(2, sqlwhere);
            }
            ResultSet r = ps.executeQuery();
            
            
            
            ArrayList<Purchase> pur = new ArrayList<>();
            while(r.next())
            {
                Purchase p = new Purchase();
                p.setMemid(m.getMemid());
                p.setPurchasedt(r.getString("PurchaseDt"));
                p.setTranstype(r.getString("TransType"));
                p.setTransCd(r.getString("TransCd"));
                addr = Integer.parseInt(p.getTransCd());
                p.setTransDesc(r.getString("TransDesc"));
                p.setAmount(r.getDouble("Amount"));
                pur.add(p);
                if(addr ==0)
                {
                    ctot+= p.getAmount();
                }
                else
                {
                    dtot+= p.getAmount();
                }
            }
            
            sqltot = dtot- ctot;
            
            request.setAttribute("pur", pur);
            request.setAttribute("sqltot", sqltot);
            request.setAttribute("runtot", runtot);
            
            r.last();
            msg += "Total records = " + r.getRow() + "<br>";
            
            
            
            //homework goes here, probably
            
            r.close(); 
            pool.freeConnection(conn);
            URL = "/Purchases.jsp";
        }
        catch (SQLException e)
        {
            msg+= "SQL error: " + e.getMessage() + "<br>";
        }
        catch (Exception e)
        {
            msg+= "Servlet error: " + e.getMessage() + "<br>";
        }
        
        //last three lines of servlets
        request.setAttribute("msg", msg);
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
