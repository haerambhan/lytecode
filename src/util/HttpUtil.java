package util;

import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

public class HttpUtil {
	
	public static JSONObject httpPost(String url, String body) throws Exception {
		
		HttpClient client = HttpClients.createDefault();
		HttpPost request = new HttpPost(url);
		request.setHeader("Content-type", "application/json");
		request.setEntity(new StringEntity(body));
		String response = EntityUtils.toString(client.execute(request).getEntity());
		return new JSONObject(response);
		
	}

}
