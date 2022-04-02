package util;

import org.json.JSONObject;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.User;

public class CommonUtil {
	
	private static final String projectName = "lytecode";
	
	public static String getProjectName() {
		return projectName;
	}

	public static JSONObject createSession(HttpServletRequest request, User user) throws Exception {
		
		JSONObject userData = new JSONObject();
		userData.put("userId", user.getUserId());
		userData.put("userName", user.getUserName());
		userData.put("userType", user.getUserType());
		
		HttpSession session = request.getSession();
		session.setAttribute("authenticated", true);
		session.setAttribute("user", userData);
		return userData;
	}
	
	public static void handleResponse(HttpServletResponse httpResponse, Response responseObj, String message, Object data) {

		JSONObject response = new JSONObject();
		response.put("code", responseObj.getResponseCode());
		response.put("message", message == null ? responseObj.getResponseMessage() : message);
		if(data != null) response.put("body", data);	
		httpResponse.setContentType("application/json");
		try {
			httpResponse.getWriter().print(response.toString());
			httpResponse.getWriter().flush();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
