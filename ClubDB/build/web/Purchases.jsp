<%-- 
    Document   : Purchases
    Created on : Apr 17, 2021, 4:26:28 PM
    Author     : KBowe
--%>

<%@page import="java.text.NumberFormat"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix = "c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix = "fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Member Purchases</title>
    </head>
    <body>
        <h1>Transactions on File for: </h1>
        <h2>${m.memid}</h2>
        <h2>${m.firstname} ${m.lastname}</h2>
        <table border ="1"
            <tr>
                <th>Purchase Dt</th>
                <th>Purchase Type</th> 
                <th>Trans. Cd</th> 
                <th>Trans Desc</th> 
                <th>Amount</th> 
            </tr>
            <c:forEach var="p" items="${pur}">
                <tr>
                <td align="right"> ${p.purchasedt}</td>
                <td align="left"> ${p.transtype}</td>    
                <td align="left"> ${p.transCd}</td>
                <td align="right"> ${p.transDesc}</td>
                <td align="center"> <fmt:formatNumber value="${p.amount}" type="currency"/> </td> 
                </tr>
            </c:forEach>
        </table>
        
    </body>
     <br>
    ${msg}
    Displayed Balance = <fmt:formatNumber value="${sqltot}" type="currency"/> <br>
    Account Balance = <fmt:formatNumber value="${runtot}" type="currency"/>
    <br>
    <a href="MemberScreen.jsp">Back to Member Screen </a>
</html>
