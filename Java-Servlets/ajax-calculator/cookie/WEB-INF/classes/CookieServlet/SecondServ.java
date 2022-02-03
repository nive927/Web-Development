package CookieServlet;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;


public class SecondServ extends HttpServlet {
public void doPost(HttpServletRequest request, HttpServletResponse response){
try{
response.setContentType("text/html");
PrintWriter out = response.getWriter();
Cookie ck[]=request.getCookies();
out.print("Hello "+ck[0].getValue());
out.close();
}
catch(Exception e){System.out.println(e);}
}
}