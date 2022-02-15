package Servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.Gson;

import Database.DatabaseAccess;
import Model.Test;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/Test/*")
public class TestServlet extends HttpServlet{

	private static final long serialVersionUID = 1L;
	public Gson gson = new Gson();
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		String path = req.getPathInfo();
		System.out.println("Path "+path);
		DatabaseAccess db = new DatabaseAccess();
		
		if(path==null || path.equals("/") )
		{
			Set<Test> tests = db.getTests();
			sendAsJson(resp,tests);	
		}
		else
		{
			int testId = Integer.parseInt(path.substring(1));
			Test test = db.getTest(testId);
			sendAsJson(resp,test);
		}
	}

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
//	    	System.out.println("Hello");
			JSONObject obj = new JSONObject(body);
			String title = obj.getString("title");
			String description = obj.getString("description");
			String difficulty = obj.getString("difficulty");
			String specialTc = obj.getString("specialTc");
//			System.out.println(obj);
//			System.out.println(specialTc);
			int testId = db.createTest(title, description, difficulty, specialTc);
			JSONObject publicTc = obj.getJSONObject("publicTc");
			String ip = publicTc.getString("ip");
			String op = publicTc.getString("op");

			db.addTestCase(ip,op,testId,1);
			JSONArray testcases = obj.getJSONArray("testcases");
			for(int i = 0 ; i < testcases.length(); i++)
			{
				String input = testcases.getJSONObject(i).getString("input");
				String output = testcases.getJSONObject(i).getString("output");
				db.addTestCase(input,output,testId,2);
			}
			sendAsJson(response,"false");
		} catch (JSONException e) {
			sendAsJson(response,e.getMessage());
		}catch (SQLException e) {
			System.out.println(e);
			System.out.println(e.getErrorCode());
			sendAsJson(response,"exists");
		}
	    catch(Exception e)
	    {
	    	e.printStackTrace();
	    }
	}
}
