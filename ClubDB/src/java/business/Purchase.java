/**
 *
 * @author KBowe
 */
package business;

import java.text.NumberFormat;


public class Purchase 
{
    private String memid, purchasedt, transtype, transCd, transdesc;
    //private int ID;
    private double Amount;
    
    
    
    public Purchase() //mt con
    {
        this.memid = "";
        this.purchasedt = "";
        this.transtype = "";
        this.transCd = "";
        this.transdesc = "";
        //this.ID = 0;
        this.Amount = 0;
    }

    public String getMemid() 
    {
        return memid;
    }

    public void setMemid(String memid) 
    {
        this.memid = memid;
    }

    public String getPurchasedt() 
    {
        return purchasedt;
    }

    public void setPurchasedt(String purchasedt) 
    {
        this.purchasedt = purchasedt;
    }

    public String getTranstype() 
    {
        return transtype;
    }

    public void setTranstype(String transtype) 
    {
        this.transtype = transtype;
    }

    public String getTransCd() 
    {
        return transCd;
    }

    public void setTransCd(String transCd) 
    {
        this.transCd = transCd;
    }

    public double getAmount() 
    {
        return Amount;
    }

    public void setAmount(double Amount) 
    {
        this.Amount = Amount;
    }
            
    public String getTransDesc()
    {
        return this.transdesc;
    }
    
    public void setTransDesc(String transdesc)
    {
        this.transdesc = transdesc;
    }
    
}
