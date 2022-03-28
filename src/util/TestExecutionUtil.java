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
		
		JSONObject body = new JSONObject();
		body.put("clientId", CLIENTID);
		body.put("clientSecret", CLIENTSECRET);
		body.put("stdin", test.getPublicTc().getInput());
		body.put("script", reqBody.get("script"));
		body.put("language", reqBody.get("language"));
		body.put("versionIndex", 3);
		
		JSONObject result = httpPost(API,body.toString());
		JSONObject response = new JSONObject();
		
		String expected = test.getPublicTc().getOutput();
		String output = result.optString("output");

		response.put("expected_op", expected);
		response.put("obtained_op", output);
		
		CommonUtil.handleResponse(res, Response.SUCCESS, null, response);
	}
	

	public static JSONObject httpPost(String url, String body) throws Exception {
		
		HttpClient client = HttpClients.createDefault();
		HttpPost request = new HttpPost(url);
	    request.setHeader("Content-type", "application/json");
		request.setEntity(new StringEntity(body));
		String response = EntityUtils.toString(client.execute(request).getEntity());
		return new JSONObject(response);
		
	}
	
	public static void submitCode(JSONObject reqBody, HttpServletResponse response, Test test) throws Exception {
		
		for(TestCase testCase : test.getTestcases()){
			
		}
	}

}
