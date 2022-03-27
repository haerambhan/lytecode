package util;

import org.json.JSONObject;

import jakarta.servlet.http.HttpSession;

public class AuthorizationUtil {
	
	public static boolean isLoggedIn(HttpSession session) {
		return session != null && session.getAttribute("user") != null;
	}
	
	public static boolean isAuthorized(String path, HttpSession session) {
		if(isLoggedIn(session)) {
			JSONObject loggedInUser = (JSONObject)session.getAttribute("user");
			if(loggedInUser.optInt("userType") == 1) {
				return isAdminPrivateURL(path) || isCommonPrivateURL(path);
			}
			else {
				return isUserPrivateURL(path) || isCommonPrivateURL(path);
			}
		}
		else{
			return isPublicURL(path);
		}
	}

	public static String getRedirectPath(HttpSession session) {		
		if(isLoggedIn(session)) {
			JSONObject user = (JSONObject)session.getAttribute("user");
			return user.optInt("userType") == 1 ? "ui/createTest.html" : "ui/userHome.html";
		}
		else {
			return "index.html";
		}
	}
	
	private static boolean isPublicURL(String path) {
		return path.equals("/api/login") || path.equals("/api/signup") || path.equals("/index.html") || path.equals("/signup.html");
	}
	
	private static boolean isAdminPrivateURL(String path) {
		return path.equals("/ui/createTest.html");
	}
	
	private static boolean isUserPrivateURL(String path) {
		return path.equals("/ui/userHome.html") || path.equals("/ui/userTest.html");
	}
	
	private static boolean isCommonPrivateURL(String path) {
		return path.equals("/api/logout") || path.startsWith("/api/tests");
	}
}
