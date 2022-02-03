// To save as "<TOMCAT_HOME>\webapps\quiz\WEB-INF\src\SessionViewerServlet.java".
import java.io.*;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.util.concurrent.TimeUnit;

@WebServlet("/sessions")   // Configure the request URL for this servlet (Tomcat 7/Servlet 3.0 upwards)
public class SessionViewerServlet extends HttpServlet {

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
      out.println("<html><head><title>Session Viewer</title>");
      out.println("<style>body{background-color:yellow;} table {font-family: arial, sans-serif; border-collapse: collapse; width: 100%;} td, th {border: 1px solid black; text-align: left; padding: 8px; } tr:nth-child(even) { background-color: #dddddd;} fieldset{ border: 1px solid black; width: 600px; margin:auto;}</style>");
      out.println("</head><body>");
      out.println("<h1 align='center'>Session Viewer</h1>");

      out.println("<form method='post' action='logout'>");
      out.println("<fieldset style='text-align: center;'><legend>Logout</legend>");
      out.println("<input type='submit' value='Logout'>");
      out.println("</fieldset>");
      out.println("</form>");

      try (
         // Step 1: Allocate a database 'Connection' object
         Connection conn = DriverManager.getConnection(
               "jdbc:mysql://localhost:3306/equizapp?allowPublicKeyRetrieval=true&useSSL=false&serverTimezone=UTC",
               "myuser", "xxxx");   // For MySQL
               // The format is: "jdbc:mysql://hostname:port/databaseName", "username", "password"

         // Step 2: Allocate a 'Statement' object in the Connection
         Statement stmt = conn.createStatement();
      )
      {
         out.println("<h2 align='center'>Active Users</h2>");

         out.println("<table>");
         out.println("<tr>");
         out.println("<th>User Name</th>");
         out.println("<th>Session ID</th>");
         out.println("<th>Session Start Timestamp</th>");
         out.println("<th>Last Access Timestamp</th>");
         out.println("<th>User Activity Status</th>");
         out.println("</tr>");

         String sqlStr = "select * from activeusers;";

         // out.println("<p>Your SQL statement is: " + sqlStr + "</p>"); // Echo for debugging
         ResultSet rset = stmt.executeQuery(sqlStr);  // Send the query to the server


         // Step 4: Process the query result set
         while(rset.next()) {

            out.println("<tr>");
            out.println("<td>" + rset.getString("username") + "</td>");
            out.println("<td>" + rset.getString("sessid") + "</td>");
            out.println("<td>" + rset.getString("starttime") + "</td>");
            out.println("<td>" + rset.getString("lastaccesstime") + "</td>");
            out.println("<td>" + rset.getString("status") + "</td>");
            out.println("</tr>");
         }

         out.println("</table>");

         sqlStr = "select count(*) from activeusers;";

         // out.println("<p>Your SQL statement is: " + sqlStr + "</p>"); // Echo for debugging
         rset = stmt.executeQuery(sqlStr);  // Send the query to the server

         rset.next();
         out.println("<p align='center'>Total users: " + rset.getInt("count(*)") +"</p>");
         

         out.println("<h2 align='center'>Session Logger</h2>");

         out.println("<table>");
         out.println("<tr>");
         out.println("<th>User Name</th>");
         out.println("<th>Session ID</th>");
         out.println("<th>Session Start Timestamp</th>");
         out.println("<th>Last Access Timestamp</th>");
         out.println("<th>User Activity Status</th>");
         out.println("</tr>");

         sqlStr = "select * from sesslog;";

         // out.println("<p>Your SQL statement is: " + sqlStr + "</p>"); // Echo for debugging
         rset = stmt.executeQuery(sqlStr);  // Send the query to the server


         // Step 4: Process the query result set
         while(rset.next()) {

            out.println("<tr>");
            out.println("<td>" + rset.getString("username") + "</td>");
            out.println("<td>" + rset.getString("sessid") + "</td>");
            out.println("<td>" + rset.getString("starttime") + "</td>");
            out.println("<td>" + rset.getString("lastaccesstime") + "</td>");
            out.println("<td>" + rset.getString("status") + "</td>");
            out.println("</tr>");
         }

         out.println("</table>");         


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