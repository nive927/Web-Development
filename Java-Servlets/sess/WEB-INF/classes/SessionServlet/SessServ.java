package SessionServlet;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;


public class SessServ extends HttpServlet
{
	int visits=0;

	public void doGet(HttpServletRequest request, HttpServletResponse response)
	{

		try
		{
			visits++;

			//checking if session is new or not

			response.setContentType("text/plain");
			PrintWriter out = response.getWriter();

			HttpSession session = request.getSession(true);
			out.println("\n");
			out.println("\n");
			if(session.isNew())
				out.println("Welcome to the website!");
			else
				out.println("Welcome back!");


			//Print Session Attributes
			out.println("\n");
			out.println("\n");
			out.println("Session Attributes:\n\n");
			Date createTime = new Date(session.getCreationTime());
			Date lastAccessTime = new Date(session.getLastAccessedTime());
			out.println("Session Id:"+session.getId());
			out.println("Date of session creation:"+createTime);
			out.println("Last Accessed Time:"+lastAccessTime);

			// Print number of hits
			out.println("\n");
			out.println("\n");
			out.println("No of hits:"+visits);


			String login=request.getParameter("name");
			String qual=request.getParameter("qual");
			String user=null;
			String username=null;
			Enumeration enm = getServletConfig().getInitParameterNames();
			while (enm.hasMoreElements())
			{ 	username=(String)enm.nextElement();
				user=getInitParameter(username);
				if(login.equals(user))
				{
					session.setAttribute(user,qual);
					break;
				}
			}
			out.println("\n");
			out.println("\n");
			out.println("User: " + user);
			out.println("Qualification: " + qual);

		}
	catch(Exception E)
		{
			System.out.println(E);
		}

	}
}