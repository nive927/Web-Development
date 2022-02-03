package HServlet;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;

public class FirstServ extends HttpServlet {
public void doPost(HttpServletRequest request, HttpServletResponse response){
try{
response.setContentType("text/html");
PrintWriter out = response.getWriter();
String n=request.getParameter("userName");
out.print("Welcome "+n);
//creating form that have invisible textfield
out.print("<form action='hservlet2' method='post'>");
out.print("<input type='hidden' name='uname' value='"+n+"'>");
out.print("<input type='submit' value='go'>");
out.print("</form>");
out.close();
}catch(Exception e){System.out.println(e);}
}
}