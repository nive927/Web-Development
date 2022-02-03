// To save as "<TOMCAT_HOME>\webapps\quiz\WEB-INF\src\LogoutServlet.java".
import java.io.*;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.util.concurrent.TimeUnit;

@WebServlet("/logout")   // Configure the request URL for this servlet (Tomcat 7/Servlet 3.0 upwards)
public class LogoutServlet extends HttpServlet {

   // The doGet() runs once per HTTP GET request to this servlet.
   @Override
   public void doGet(HttpServletRequest request, HttpServletResponse response)
               throws ServletException, IOException {
      // Set the MIME type for the response message
      response.setContentType("text/html");

      // Get a output writer to write the response message into the network socket
      PrintWriter out = response.getWriter();

      // session tracking
      HttpSession session = request.getSession();

      try (
         // Step 1: Allocate a database 'Connection' object
         Connection conn = DriverManager.getConnection(
               "jdbc:mysql://localhost:3306/equizapp?allowPublicKeyRetrieval=true&useSSL=false&serverTimezone=UTC",
               "myuser", "xxxx");   // For MySQL
               // The format is: "jdbc:mysql://hostname:port/databaseName", "username", "password"

         // Step 2: Allocate a 'Statement' object in the Connection
         Statement insStmt = conn.createStatement();
      )
      {
         session.setAttribute("status", "Logged out");

         // Store session details in database
         String sqlQuery = "insert into sesslog values("
         + "'" + request.getParameter("uname") + "', " // Single-quote SQL string
         + "'" + session.getId() + "', "
         + "'" + new java.util.Date(session.getCreationTime()) + "', "
         + "'" + new java.util.Date(session.getLastAccessedTime()) + "', "
         + "'" + session.getAttribute("status") + "'"
         + ");";

         // out.println(sqlQuery); // for debugging

         insStmt.executeUpdate(sqlQuery);

         // tracking active users
         sqlQuery = "delete from activeusers where username="
         + "'" + request.getParameter("uname") + "';";

         // out.println(sqlQuery); // for debugging

         insStmt.executeUpdate(sqlQuery);
      }

      catch(Exception ex) {
         out.println("<p>Error: " + ex.getMessage() + "</p>");
         out.println("<p>Check Tomcat console for details.</p>");
         ex.printStackTrace();
      }
      
      // invalidate the session
      session.invalidate();
      response.sendRedirect("index.html");
   }

   // doPost() handles POST request
   @Override
   public void doPost(HttpServletRequest request, HttpServletResponse response)
               throws IOException, ServletException {
      doGet(request, response);  // call doGet()
   }
}