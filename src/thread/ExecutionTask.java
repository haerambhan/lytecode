package thread;

import org.json.JSONObject;

import util.HttpUtil;
import util.TestUtil;

public class ExecutionTask extends Thread {
	
	private String code;
	private String language;
	private String input;
	private String output;
	
	public ExecutionTask(String code, String language, String input) {
		this.code = code;
		this.language = language;
		this.input = input;
	}

	public String getOutput() {
		return output;
	}

	@Override
	public void run() {
		
		JSONObject body = new JSONObject();
		body.put("clientId", TestUtil.CLIENTID);
		body.put("clientSecret", TestUtil.CLIENTSECRET);
		body.put("stdin", input);
		body.put("script", code);
		body.put("language", language);
		body.put("versionIndex", 3);
		
		try {
			JSONObject response = HttpUtil.httpPost(TestUtil.API,body.toString());
			if(response.getInt("statusCode") == 200) {
				output = response.optString("output");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
