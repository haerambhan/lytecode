package servlet;

import java.sql.SQLException;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import database.DBUtil;
import model.User;
import util.CommonUtil;
import util.Response;

@WebServlet("/api/signup")
public class SignupServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response){

		try {
			String body = request.getReader().readLine();
			JSONObject obj = new JSONObject(body);
			String userId = obj.getString("userId");
			String userName = obj.getString("userName");
			String password = obj.getString("password");
			User user = DBUtil.getInstance().CreateUser(userId, userName, password);

			if (user != null) {
				CommonUtil.handleResponse(response, Response.SUCCESS, null, CommonUtil.createSession(request, user));
			} else {
				CommonUtil.handleResponse(response, Response.SIGNUP_FAILED, null, null);
			}

		} catch (SQLException e) {
			CommonUtil.handleResponse(response, Response.USER_EXISTS, null, null);
		} catch (Exception e) {
			e.printStackTrace();
			CommonUtil.handleResponse(response, Response.INTERNAL_ERROR, e.getMessage(), null);
		}
	}
}
