package Servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.Gson;

import Database.DatabaseAccess;
import Model.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/Signup/*")
public class SignupServlet extends HttpServlet{

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

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		DatabaseAccess db = new DatabaseAccess();
	    String body = request.getReader().readLine();
	    try 
	    {
			JSONObject obj = new JSONObject(body);
			String userId = obj.getString("userId");
			String userName = obj.getString("userName");
			String password = obj.getString("password");
			User user = db.CreateUser(userId, userName, password);
			if(user!=null)
			{
				HttpSession session = request.getSession();
				session.setAttribute("user", user);
				sendAsJson(response,"true");
			}
			else
			{
				sendAsJson(response,"false");
			}
		} catch (JSONException e) {
			sendAsJson(response,e.getMessage());
		}catch (SQLException e) {
			System.out.println(e);
			System.out.println(e.getErrorCode());
			sendAsJson(response,"exists");
		}
	}
}
