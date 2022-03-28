package util;

import java.util.HashMap;
import java.util.Map;

import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import jakarta.servlet.http.HttpServletResponse;
import model.Test;
import model.TestCase;
import thread.ExecutionTask;

public class TestUtil {
	
	
	public static final String API = "https://api.jdoodle.com/v1/execute";
	public static final String CLIENTID = "5b7c03069c7a101655f614ac836bc7b4";
	public static final String CLIENTSECRET = "bbb5989dd71f6fb9f96a4bd2c6735a8f33685c15b06eccbd1be32886f59817e";
												
	public static void runCode(JSONObject reqBody, HttpServletResponse res, Test test) throws Exception {		

		String code = reqBody.optString("script");
		String language = reqBody.optString("language");
		String input = test.getPublicTc().getInput();
		
		ExecutionTask task = new ExecutionTask(code, language, input);
		task.run();
		String expected = test.getPublicTc().getOutput();
		String output = task.getOutput();
		
		if(output == null) {
			CommonUtil.handleResponse(res, Response.INTERNAL_ERROR, "Code Excecution failed", null);
			return;
		}

		JSONObject response = new JSONObject();
		response.put("expected_op", expected);
		response.put("obtained_op", output);
		CommonUtil.handleResponse(res, Response.SUCCESS, null, response);
	}
	
	public static void submitCode(JSONObject reqBody, HttpServletResponse res, Test test) throws Exception {
		
		String code = reqBody.optString("script");
		String language = reqBody.optString("language");
		int passedTestCases = 0;

		Map<TestCase,ExecutionTask> tasks = new HashMap<TestCase, ExecutionTask>();
		
		for(TestCase testCase : test.getTestcases()) {		
			String input = testCase.getInput();
			ExecutionTask task = new ExecutionTask(code, language, input);
			tasks.put(testCase, task);
			task.start();
		}
		for(TestCase testCase : tasks.keySet()) {
			tasks.get(testCase).join();			
		}
		
		for(TestCase testCase : tasks.keySet()) {
			String expected = testCase.getOutput();
			String output = tasks.get(testCase).getOutput();
			
			if(output == null) {
				CommonUtil.handleResponse(res, Response.INTERNAL_ERROR, "Code Excecution failed", null);
				return;
			}
			
			if(expected.trim().equals(output.trim())) {
				passedTestCases++;
			}
		}
		JSONObject response = new JSONObject();
		response.put("passedTestCases", passedTestCases);
		response.put("totalTestCases", test.getTestcases().size());
		CommonUtil.handleResponse(res, Response.SUCCESS, null, response);
	}
}
