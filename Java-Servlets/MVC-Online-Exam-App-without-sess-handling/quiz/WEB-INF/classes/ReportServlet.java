// To save as "<TOMCAT_HOME>\webapps\quiz\WEB-INF\classes\ReportServlet.java".
import java.io.*;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.util.concurrent.TimeUnit;

@WebServlet("/report")   // Configure the request URL for this servlet (Tomcat 7/Servlet 3.0 upwards)
public class ReportServlet extends HttpServlet {

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
      out.println("<title>Report</title>");
      out.println("<style>body{background-color:yellow;} table {font-family: arial, sans-serif; border-collapse: collapse; width: 100%;} td, th {border: 1px solid black; text-align: left; padding: 8px; } tr:nth-child(even) { background-color: #dddddd;} </style>");
      out.println("</head>");
      out.println("<body>");
      out.println("<h1 align='center'>Performance Report</h1>");
      out.println("<table>");
      out.println("<tr>");
      out.println("<th>Question</th>");
      out.println("<th>Chosen Answer</th>");
      out.println("<th>Correct Anwer</th>");
      out.println("<th>Mark Obtained</th>");
      out.println("</tr>");

      int no_of_questions = 5;
      float total = 0;

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
         int count = 1;

         while(count <= no_of_questions)
         {
            float mark_obtained = 0;

            String sqlStr = "select * from questions where question = "
               + "'" + request.getParameter("q"+count) + "'"   // Single-quote SQL string
               + "";

            // out.println("<p>Your SQL statement is: " + sqlStr + "</p>"); // Echo for debugging
            ResultSet rset = stmt.executeQuery(sqlStr);  // Send the query to the server


            // Step 4: Process the query result set
            while(rset.next()) {
               String chosen_ans = request.getParameter("a"+count);
               String correct_ans = rset.getString("answer");
               if(chosen_ans.equals(correct_ans))
                  mark_obtained = 1;

               total += mark_obtained;

               out.println("<tr>");
               out.println("<td>" + rset.getString("question") + "</td>");
               out.println("<td>" + chosen_ans + "</td>");
               out.println("<td>" + correct_ans + "</td>");
               out.println("<td>" + mark_obtained + "</td>");
               out.println("</tr>");
            }

            count++;
         }

      } catch(Exception ex) {
         out.println("<p>Error: " + ex.getMessage() + "</p>");
         out.println("<p>Check Tomcat console for details.</p>");
         ex.printStackTrace();
      }  // Step 5: Close conn and stmt - Done automatically by try-with-resources (JDK 7)

      float percentage = total / no_of_questions * 100;
      String status = "FAIL";
      if(total > 2)
         status = "PASS";

      out.println("</table>");
      out.println("<div align='center'><p><b>Total Marks:</b> " + total + " / 5.0</p>");
      out.println("<p><b>Percentage:</b> " + percentage + " / 100.0</p>");
      out.println("<p><b>Status:</b> " + status + "</p></div>");
      out.println("</body></html>");
      out.close();
   }
}