package Servlet;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;
import Model.User;

@WebServlet("/Session/*")
public class SessionServlet extends HttpServlet{

	private static final long serialVersionUID = 1L;
	public Gson gson = new Gson();
	
	public void sendAsJson(HttpServletResponse response, Object obj) throws IOException
    {
    	String res = gson.toJson(obj);
    	response.setContentType("application/json");
    	PrintWriter out = response.getWriter(); 
		out.print(res);
		out.flush();
    }
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		HttpSession session = request.getSession();
		User u = (User)session.getAttribute("user");
		sendAsJson(response, u);
	 
	}
}
