package servlet;

import java.io.IOException;
import java.sql.SQLException;

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

@WebServlet("/api/signup")
public class SignupServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		DatabaseAccess db = new DatabaseAccess();
		String body = request.getReader().readLine();
		JSONObject obj = new JSONObject(body);
		try {
			String userId = obj.getString("userId");
			String userName = obj.getString("userName");
			String password = obj.getString("password");
			User user = db.CreateUser(userId, userName, password);

			if (user != null) {
				CommonUtil.handleResponse(response, Response.SUCCESS, CommonUtil.createSession(request, user));
			} else {
				CommonUtil.handleResponse(response, Response.SIGNUP_FAILED, null);
			}

		} catch (SQLException e) {
			CommonUtil.handleResponse(response, Response.USER_EXISTS, null);
		} catch (Exception e) {
			CommonUtil.handleResponse(response, Response.INTERNAL_ERROR, null);
		}
	}
}
