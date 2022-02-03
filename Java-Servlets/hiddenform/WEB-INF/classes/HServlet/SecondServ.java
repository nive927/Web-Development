package HServlet;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;


public class SecondServ extends HttpServlet {
public void doPost(HttpServletRequest request, HttpServletResponse response){
try{
response.setContentType("text/html");
PrintWriter out = response.getWriter();
//Getting the value from the hidden field
String n=request.getParameter("uname");
out.print("Hello "+n);
}
catch(Exception e){System.out.println(e);}
}
}