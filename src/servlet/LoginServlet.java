package servlet;

import java.io.IOException;

import org.json.JSONObject;

import database.DatabaseAccess;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.User;
import util.CommonUtil;
import util.Response;

@WebServlet("/api/login")
public class LoginServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		DatabaseAccess db = DatabaseAccess.getInstance();
		String body = request.getReader().readLine();
		JSONObject obj = new JSONObject(body);
		try {
			String userId = obj.getString("userId");
			String password = obj.getString("password");
			User user = db.getUser(userId, password);
			if (user != null) {
				CommonUtil.handleResponse(response, Response.SUCCESS, CommonUtil.createSession(request, user));
			} else {
				CommonUtil.handleResponse(response, Response.LOGIN_FAILED, null);
			}
		} catch (Exception e) {
			CommonUtil.handleResponse(response, Response.INTERNAL_ERROR, null);
		}
	}
}
