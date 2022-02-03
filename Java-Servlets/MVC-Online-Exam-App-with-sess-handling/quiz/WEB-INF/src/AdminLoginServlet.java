// To save as "<TOMCAT_HOME>\webapps\quiz\WEB-INF\src\AdminLoginServlet.java".
import java.io.*;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.util.concurrent.TimeUnit;

@WebServlet("/adminlogin")   // Configure the request URL for this servlet (Tomcat 7/Servlet 3.0 upwards)
public class AdminLoginServlet extends HttpServlet {

   // The doGet() runs once per HTTP GET request to this servlet.
   @Override
   public void doGet(HttpServletRequest request, HttpServletResponse response)
               throws ServletException, IOException {
      // Set the MIME type for the response message
      response.setContentType("text/html");
      // Get a output writer to write the response message into the network socket
      PrintWriter out = response.getWriter();

      // Print an HTML page as the output of the query
      out.println("<!DOCTYPE html>");
      out.println("<html>");
      out.println("<head><title>Validation</title><style type='text/css'>fieldset{ border: 1px solid black; width: 400px; margin:auto;} body  { background-color:yellow; }</style></head>");
      out.println("<body>");

      try (
         // Step 1: Allocate a database 'Connection' object
         Connection conn = DriverManager.getConnection(
               "jdbc:mysql://localhost:3306/equizapp?allowPublicKeyRetrieval=true&useSSL=false&serverTimezone=UTC",
               "myuser", "xxxx");   // For MySQL
               // The format is: "jdbc:mysql://hostname:port/databaseName", "username", "password"

         // Step 2: Allocate a 'Statement' object in the Connection
         Statement stmt = conn.createStatement();
      ) {
         // Step 3: Execute a SQL SELECT query
         String sqlStr = "select * from adminaccounts where username = "
               + "'" + request.getParameter("uname") + "'"   // Single-quote SQL string
               + "";

         // out.println("<p>Your SQL statement is: " + sqlStr + "</p>"); // Echo for debugging
         ResultSet rset = stmt.executeQuery(sqlStr);  // Send the query to the server

         // Step 4: Process the query result set
         int count = 0;
         boolean loginSuccess = false;
         while(rset.next() && count == 0) {
            // Print a paragraph <p>...</p> for each record
            // out.println("<p>" + rset.getString("username")
            //       + ", " + rset.getString("password") + "</p>");
            if(request.getParameter("psw").equals(rset.getString("password")))
            {
               loginSuccess = true;

               // Redirecting to the dashboard on successful login
               out.println("<script type='text/javascript'>confirm('Login Successful')</script>");
               response.sendRedirect("sessions");

            }
            else
            {
               out.println("<p>Oops!<br> <b>Unsuccessful</b> Login!</p>");
               out.println("</body></html>");
               out.close();
            }
            // out.println("<p>" + loginSuccess + "</p>");
            count++;
         }

      } catch(Exception ex) {
         out.println("<p>Error: " + ex.getMessage() + "</p>");
         out.println("<p>Check Tomcat console for details.</p>");
         ex.printStackTrace();
      }  // Step 5: Close conn and stmt - Done automatically by try-with-resources (JDK 7)

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