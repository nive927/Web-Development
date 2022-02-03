
// To save as "<TOMCAT_HOME>\webapps\calculator\WEB-INF\classes\CalcServlet.java".
import java.io.*;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

@WebServlet("/calcajax") // Configure the request URL for this servlet (Tomcat 7/Servlet 3.0 upwards)
public class CalculatorServlet extends HttpServlet {

   // The doGet() runs once per HTTP GET request to this servlet.
   @Override
   public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      // Set the MIME type for the response message
      response.setContentType("text/html; charset=UTF-8");
      // Get a output writer to write the response message into the network socket
      PrintWriter out = response.getWriter();

      // // Print an HTML page as the output of the query
      // out.println("<!DOCTYPE html>");
      // out.println("<html>");
      // out.println("<head><title>Calculator Response</title></head>");
      // out.println("<body>");

      int num1 = Integer.parseInt(request.getParameter("num1"));
      int num2 = Integer.parseInt(request.getParameter("num2"));
      String op = request.getParameter("operation");

      int ans = 0;

      switch (op) {
         case "add":
            ans = num1 + num2;
            break;

         case "sub":
            ans = num1 - num2;
            break;

         case "mul":
            ans = num1 * num2;
            break;

         case "div":
            ans = num1 / num2;
            break;
      }

      out.println("Result: " + ans);
      out.close();
   }
}