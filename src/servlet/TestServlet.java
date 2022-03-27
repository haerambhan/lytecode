package servlet;

import java.io.IOException;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONObject;

import database.DatabaseAccess;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Test;
import util.CommonUtil;
import util.Response;

@WebServlet("/api/tests/*")
public class TestServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		String path = req.getPathInfo();
		DatabaseAccess db = DatabaseAccess.getInstance();
		if (path == null || path.equals("/")) {
			Set<Test> tests = db.getTests();
			CommonUtil.handleResponse(resp, Response.SUCCESS, new JSONArray(tests));
		} else {
			int testId = Integer.parseInt(path.substring(1));
			Test test = db.getTest(testId);
			CommonUtil.handleResponse(resp, Response.SUCCESS, new JSONObject(test));
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		DatabaseAccess db = DatabaseAccess.getInstance();
		String body = request.getReader().readLine();
		try {
			JSONObject obj = new JSONObject(body);
			String title = obj.getString("title");
			String description = obj.getString("description");
			String difficulty = obj.getString("difficulty");
			int testId = db.createTest(title, description, difficulty);
			JSONObject publicTc = obj.getJSONObject("publicTc");
			String ip = publicTc.getString("ip");
			String op = publicTc.getString("op");

			db.addTestCase(ip, op, testId, 1);
			JSONArray testcases = obj.getJSONArray("testcases");
			for (int i = 0; i < testcases.length(); i++) {
				String input = testcases.getJSONObject(i).getString("input");
				String output = testcases.getJSONObject(i).getString("output");
				db.addTestCase(input, output, testId, 2);
			}		
			response.sendRedirect("tests/" + testId);
		} catch (Exception e) {
			e.printStackTrace();
			CommonUtil.handleResponse(response, Response.INTERNAL_ERROR, null);
		}
	}
}
