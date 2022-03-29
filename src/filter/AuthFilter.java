package filter;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
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
