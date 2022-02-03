package CookieServlet2;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;


public class ReadCookies extends HttpServlet
{
public void doGet(HttpServletRequest request, HttpServletResponse response)
{
try{
Cookie cookie = null;
Cookie[] cookies = null;

//set cookies
Cookie firstName = new Cookie("first_name",request.getParameter("first_name"));
Cookie lastName = new Cookie("last_name",request.getParameter("last_name"));

// Set expiry date after 24 Hrs for both the cookies.
firstName.setMaxAge(60*60*24);
lastName.setMaxAge(60*60*24);

// Add both the cookies in the response header.
response.addCookie(firstName );
response.addCookie(lastName );

// Get an array of Cookies associated with this domain
cookies = request.getCookies();
// Set response content type
response.setContentType("text/html");
PrintWriter out = response.getWriter();
String title = "Reading Cookies Example";
String docType = "<!doctype html public \"-//w3c//dtd html 4.0 " + "transitional//en\">\n";
out.println(docType +"<html>\n" +"<head><title>" + title +"</title></head>\n" +"<body bgcolor=\"#f0f0f0\">\n" );
if( cookies != null ){
out.println("<h2>Found Cookies Name and Value</h2>");
for (int i = 0; i < cookies.length; i++)
{
cookie = cookies[i];
if (cookie.getName().equals("first_name") || cookie.getName().equals("last_name"))
	{
	out.println("Name : " + cookie.getName( ));
	out.println("<br>");
	out.println("Value: " + cookie.getValue( ));
	out.println("<br>");
	}
}
}else{
out.println(
"<h2>No cookies found</h2>");
}
//out.println("</body>");
//out.println("</html>");
}
catch(Exception e){System.out.println(e);}
}
}