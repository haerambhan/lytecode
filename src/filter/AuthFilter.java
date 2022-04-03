package filter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import util.AuthorizationUtil;
import util.CommonUtil;

@WebFilter("/*")
public class AuthFilter implements Filter {

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) {

		try {
			HttpServletRequest req = (HttpServletRequest) request;
			HttpServletResponse res = (HttpServletResponse) response;

			String path = req.getRequestURI().substring(req.getContextPath().length());
			HttpSession session = req.getSession(false);

			if (isAssetURL(path) || AuthorizationUtil.isAuthorized(path, session)) {
				chain.doFilter(req, res);
			} else {
				res.sendRedirect("/" + CommonUtil.getProjectName() + "/" + AuthorizationUtil.getRedirectPath(session));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private boolean isAssetURL(String path) {
		return path.startsWith("/scripts") || path.startsWith("/styles") || path.startsWith("/resources");
	}
}
