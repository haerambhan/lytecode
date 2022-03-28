package util;

import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import jakarta.servlet.http.HttpServletResponse;
import model.Test;
import model.TestCase;

public class TestExecutionUtil {
	
	
	public static final String API = "https://api.jdoodle.com/v1/execute";
	public static final String CLIENTID = "5b7c03069c7a101655f614ac836bc7b4";
	public static final String CLIENTSECRET = "bbb5989dd71f6fb9f96a4bd2c6735a8f33685c15b06eccbd1be32886f59817e";
												
	public static void runCode(JSONObject reqBody, HttpServletResponse res, Test test) throws Exception {		

		String code = reqBody.optString("script");
		String language = reqBody.optString("language");
		String input = test.getPublicTc().getInput();

		JSONObject result = executeCode(code, language, input);
		JSONObject response = new JSONObject();
		
		String expected = test.getPublicTc().getOutput();
		String output = result.optString("output");

		response.put("expected_op", expected);
		response.put("obtained_op", output);
		
		CommonUtil.handleResponse(res, Response.SUCCESS, null, response);
	}
	
	public static void submitCode(JSONObject reqBody, HttpServletResponse res, Test test) throws Exception {
		
		String code = reqBody.optString("script");
		String language = reqBody.optString("language");
		int passedTestCases = 0;
		for(TestCase testCase : test.getTestcases()){
			
			String input = testCase.getInput();
			JSONObject result = executeCode(code, language, input);
			
			String expected = testCase.getOutput();
			String output = result.optString("output");
			
			if(expected.trim().equals(output.trim())) {
				passedTestCases++;
			}	
		}
		JSONObject response = new JSONObject();
		response.put("passedTestCases", passedTestCases);
		response.put("totalTestCases", test.getTestcases().size());
		CommonUtil.handleResponse(res, Response.SUCCESS, null, response);
	}
	
	public static JSONObject executeCode(String code, String language, String input) throws Exception {
		
		JSONObject body = new JSONObject();
		body.put("clientId", CLIENTID);
		body.put("clientSecret", CLIENTSECRET);
		body.put("stdin", input);
		body.put("script", code);
		body.put("language", language);
		body.put("versionIndex", 3);
		
		return httpPost(API,body.toString());
	}
	
	public static JSONObject httpPost(String url, String body) throws Exception {
		
		HttpClient client = HttpClients.createDefault();
		HttpPost request = new HttpPost(url);
		request.setHeader("Content-type", "application/json");
		request.setEntity(new StringEntity(body));
		String response = EntityUtils.toString(client.execute(request).getEntity());
		return new JSONObject(response);
		
	}
}
