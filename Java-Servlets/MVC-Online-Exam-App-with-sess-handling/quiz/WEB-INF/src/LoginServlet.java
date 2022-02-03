// To save as "<TOMCAT_HOME>\webapps\quiz\WEB-INF\src\LoginServlet.java".
import java.io.*;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

@WebServlet("/login")   // Configure the request URL for this servlet (Tomcat 7/Servlet 3.0 upwards)
public class LoginServlet extends HttpServlet {

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
         Statement insStmt = conn.createStatement();
      ) {
         // Step 3: Execute a SQL SELECT query
         String sqlStr = "select * from uaccounts where username = "
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

               // Start a session and set a status attribute
               // Create a session object if it is already not created.
               HttpSession session = request.getSession();

               session.setAttribute("uname", request.getParameter("uname"));
               session.setAttribute("status", "Logged in");

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

               // active users
               if(session.isNew())
               {
                  // tracking active users
                  sqlQuery = "insert into activeusers (username, sessid, starttime, lastaccesstime, status) values("
                  + "'" + request.getParameter("uname") + "', " // Single-quote SQL string
                  + "'" + session.getId() + "', "
                  + "'" + new java.util.Date(session.getCreationTime()) + "', "
                  + "'" + new java.util.Date(session.getLastAccessedTime()) + "', "
                  + "'" + session.getAttribute("status") + "'"
                  + ");";

                  // out.println(sqlQuery); // for debugging

                  insStmt.executeUpdate(sqlQuery);
               }


               // Redirecting to the dashboard on successful login
               // request.sendRedirect("dashboard.html");
               out.println("<script type='text/javascript'>confirm('Login Successful')</script>");
               out.println("<h1 align='center'>Redirect to Dashboard</h1>");
               out.println("<form method='post' action='dashboard'>");
               out.println("<fieldset style='text-align: center;'><legend>Go to Dashboard</legend>");
               out.println("<input type='hidden' name='uname' value='"+ request.getParameter("uname") +"'>");
               out.println("<br><input type='submit' value='Go'>");
               out.println("</fieldset>");
               out.println("</form>");

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