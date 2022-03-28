package servlet;

import org.json.JSONObject;

import database.DBUtil;
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
	private static DBUtil db = DBUtil.getInstance();

	protected void doPost(HttpServletRequest request, HttpServletResponse response) {

		try {
			String body = request.getReader().readLine();
			JSONObject obj = new JSONObject(body);
			String userId = obj.getString("userId");
			String password = obj.getString("password");
			User user = db.getUser(userId, password);
			if (user != null) {
				CommonUtil.handleResponse(response, Response.SUCCESS, null, CommonUtil.createSession(request, user));
			} else {
				CommonUtil.handleResponse(response, Response.LOGIN_FAILED, null, null);
			}
		} catch (Exception e) {
			e.printStackTrace();
			CommonUtil.handleResponse(response, Response.INTERNAL_ERROR, e.getMessage(), null);
		}
	}
}
