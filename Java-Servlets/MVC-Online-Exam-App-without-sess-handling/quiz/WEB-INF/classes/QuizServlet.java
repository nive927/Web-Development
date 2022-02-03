// To save as "<TOMCAT_HOME>\webapps\quiz\WEB-INF\classes\QuizServlet.java".
import java.io.*;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.util.concurrent.TimeUnit;

@WebServlet("/equiz")   // Configure the request URL for this servlet (Tomcat 7/Servlet 3.0 upwards)
public class QuizServlet extends HttpServlet {

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
      out.println("<head>");
      out.println("<title>Online Quiz</title>");
      out.println("<style type='text/css'>body{background-color:yellow;} fieldset{ border: 1px solid black; width: 600px; margin:auto;}</style>");
      out.println("</head>");
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
         int no_of_questions = 5;
         String sqlStr = "select * from questions order by rand() limit " + no_of_questions + ";" + "";

         // out.println("<p>Your SQL statement is: " + sqlStr + "</p>"); // Echo for debugging
         ResultSet rset = stmt.executeQuery(sqlStr);  // Send the query to the server

         out.println("<h1 align='center'>Quiz</h1>");
         out.println("<form method='get' action='report'>");
         out.println("<fieldset><legend>Questions</legend>");

         // Step 4: Process the query result set
         Integer count = 1;
         while(rset.next()) {
            // Print a paragraph <p>...</p> for each record to test the code
            // out.println("<p>" + rset.getString("question")
            //       + " " + rset.getString("answer") + " " + rset.getString("option1") + " " + rset.getString("option2") + " " + rset.getString("option3") + " " + rset.getString("option4") + "</p>");
            out.println("<span>"+ count + ".</span><input type'text' name=" + "'q" + count + "'" + " value=" + "'" + rset.getString("question") + "'" + " size=" + "'" + rset.getString("question").trim().length() + "'" + " readonly='readonly'><br><br>");
            out.println("<input type='radio' name=" + "'a" + count + "'" + " value=" + "'" + rset.getString("option1") + "'" + ">");
            out.println("<span>" + rset.getString("option1") + "</span><br>");
            out.println("<input type='radio' name=" + "'a" + count + "'" + " value=" + "'" + rset.getString("option2") + "'" + ">");
            out.println("<span>" + rset.getString("option2") + "</span><br>");
            out.println("<input type='radio' name=" + "'a" + count + "'" + " value=" + "'" + rset.getString("option3") + "'" + ">");
            out.println("<span>" + rset.getString("option3") + "</span><br>");
            out.println("<input type='radio' name=" + "'a" + count + "'" + " value=" + "'" + rset.getString("option4") + "'" + ">");
            out.println("<span>" + rset.getString("option4") + "</span><br><br>");
            count++;
         }

         out.println("<br><input type='submit' value='Submit'>");
         out.println("</fieldset>");
         out.println("</form>");

      } catch(Exception ex) {
         out.println("<p>Error: " + ex.getMessage() + "</p>");
         out.println("<p>Check Tomcat console for details.</p>");
         ex.printStackTrace();
      }  // Step 5: Close conn and stmt - Done automatically by try-with-resources (JDK 7)

      out.println("</body></html>");
      out.close();
   }
}