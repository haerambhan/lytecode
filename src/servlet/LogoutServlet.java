package servlet;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import util.CommonUtil;
import util.Response;


@WebServlet("/api/logout")
public class LogoutServlet extends HttpServlet{

	private static final long serialVersionUID = 1L;
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) {
		try {
			HttpSession session = request.getSession();
			session.invalidate();
			CommonUtil.handleResponse(response, Response.SUCCESS, null, null);			
		}
		catch(Exception ex) {
			ex.printStackTrace();
			CommonUtil.handleResponse(response, Response.INTERNAL_ERROR, ex.getMessage(), null);
		}
	}
}