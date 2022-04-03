package servlet;

import java.util.Set;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONObject;

import database.DBUtil;
import model.Test;
import util.CommonUtil;
import util.Response;
import util.TestUtil;

@WebServlet("/api/tests/*")
public class TestServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp){
		
		try {
			String path = req.getPathInfo();
			if (path == null || path.equals("/")) {
				Set<Test> tests = DBUtil.getInstance().getTests();
				CommonUtil.handleResponse(resp, Response.SUCCESS, null, new JSONArray(tests));
			} else {
				String[] tokens = path.split("/");
				int testId = Integer.parseInt(tokens[1]);		
				Test test = DBUtil.getInstance().getTest(testId, false);
				CommonUtil.handleResponse(resp, Response.SUCCESS, null, new JSONObject(test));
	
			}
		} catch (Exception e) {
			e.printStackTrace();
			CommonUtil.handleResponse(resp, Response.INTERNAL_ERROR, e.getMessage(), null);
		}
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) {
		
		try {
			String body = request.getReader().readLine();
			String path = request.getPathInfo();
			JSONObject reqBody = new JSONObject(body);
			
			if (path == null || path.equals("/")) {
				int testId = createTest(reqBody);
				response.sendRedirect("tests/" + testId);
			} else {
				String[] tokens = path.split("/");
				int testId = Integer.parseInt(tokens[1]);		
				if(tokens.length == 3 && "run".equals(tokens[2])) {	
					Test test = DBUtil.getInstance().getTest(testId, false);
					TestUtil.runCode(reqBody, response, test);	
				}
				if(tokens.length == 3 && "submit".equals(tokens[2])) {
					Test test = DBUtil.getInstance().getTest(testId, true);
					TestUtil.submitCode(reqBody, response, test);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			CommonUtil.handleResponse(response, Response.INTERNAL_ERROR, e.getMessage(), null);
		}
	}


	private int createTest(JSONObject reqBody) throws Exception {
		String title = reqBody.getString("title");
		String description = reqBody.getString("description");
		String difficulty = reqBody.getString("difficulty");
		int testId = DBUtil.getInstance().createTest(title, description, difficulty);
		JSONObject publicTc = reqBody.getJSONObject("publicTc");
		String ip = publicTc.getString("ip");
		String op = publicTc.getString("op");

		DBUtil.getInstance().addTestCase(ip, op, testId, 1);
		JSONArray testcases = reqBody.getJSONArray("testcases");
		for (int i = 0; i < testcases.length(); i++) {
			String input = testcases.getJSONObject(i).getString("input");
			String output = testcases.getJSONObject(i).getString("output");
			DBUtil.getInstance().addTestCase(input, output, testId, 2);
		}	
		return testId;
	}
}
