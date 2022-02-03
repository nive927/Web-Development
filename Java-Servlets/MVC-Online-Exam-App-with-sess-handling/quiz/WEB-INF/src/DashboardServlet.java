// To save as "<TOMCAT_HOME>\webapps\quiz\WEB-INF\src\DashboardServlet.java".
import java.io.*;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.util.concurrent.TimeUnit;

@WebServlet("/dashboard")   // Configure the request URL for this servlet (Tomcat 7/Servlet 3.0 upwards)
public class DashboardServlet extends HttpServlet {

   // The doGet() runs once per HTTP GET request to this servlet.
   @Override
   public void doGet(HttpServletRequest request, HttpServletResponse response)
               throws ServletException, IOException {
      // Set the MIME type for the response message
      response.setContentType("text/html");
      // Get a output writer to write the response message into the network socket
      PrintWriter out = response.getWriter();

      // Print an HTML page as the output of the query
      out.print("<!DOCTYPE html>");
      out.println("<html><head><title>Dashboard</title>");
      out.println("<style type='text/css'>fieldset{ border: 1px solid black; width: 400px; margin:auto;} body  { background-color:yellow; }</style>");
      out.println("</head><body>");
      out.println("<h1 align='center'>Dashboard</h1>");

      try (
         // Step 1: Allocate a database 'Connection' object
         Connection conn = DriverManager.getConnection(
               "jdbc:mysql://localhost:3306/equizapp?allowPublicKeyRetrieval=true&useSSL=false&serverTimezone=UTC",
               "myuser", "xxxx");   // For MySQL
               // The format is: "jdbc:mysql://hostname:port/databaseName", "username", "password"

         // Step 2: Allocate a 'Statement' object in the Connection
         Statement stmt = conn.createStatement();
         Statement insStmt = conn.createStatement();
      )
      {

         String sqlStr = "select distinct subject from questions;";

         // out.println("<p>Your SQL statement is: " + sqlStr + "</p>"); // Echo for debugging
         ResultSet rset = stmt.executeQuery(sqlStr);  // Send the query to the server

         out.println("<fieldset style='text-align: center;'>");
         out.println("<legend>What do you want to do?</legend>");
         out.println("<form method='post' action='equiz'>");
         out.println("<select name='subject'>");
         out.println("<option value='' disabled selected>Choose subject</option>");

         // Step 4: Process the query result set
         while(rset.next()) {

            out.println("<option value='" + rset.getString("subject") + "'>" + rset.getString("subject") + "</option>");

         }

         out.println("</select>");
         // using hidden form field
         out.println("<input type='hidden' name='uname' value='"+ request.getParameter("uname") +"'>");
         out.println("<input type='submit' value='Take Quiz'>");
         out.println("</form>");

         out.println("<br><br>");

         out.println("<form method='post' action='logout'>");
         out.println("<input type='hidden' name='uname' value='"+ request.getParameter("uname") +"'>");
         out.println("<input type='submit' value='Logout'>");
         out.println("</form>");
         out.println("</fieldset>");
      }

      catch(Exception ex) {
         out.println("<p>Error: " + ex.getMessage() + "</p>");
         out.println("<p>Check Tomcat console for details.</p>");
         ex.printStackTrace();
      }

      out.println("</body></html>");
      out.close();

   }

   // doPost() handles POST request
   @Override
   public void doPost(HttpServletRequest request, HttpServletResponse response)
               throws IOException, ServletException {
      doGet(request, response);  // call doGet()
   }
}