package Filter;
import java.io.IOException;

import Model.User;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebFilter("/*")
public class ValidationFilter implements Filter {

    public ValidationFilter() {
        // TODO Auto-generated constructor stub
    }
	public void destroy() {
		// TODO Auto-generated method stub
	}	
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		
		HttpServletRequest req =(HttpServletRequest)request;
		HttpServletResponse res = (HttpServletResponse)response;
	    String path = req.getRequestURI().substring(req.getContextPath().length());
//	    System.out.println(path);
	    HttpSession session = req.getSession();
  		User u = (User)session.getAttribute("user");
  		res.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1
		res.setHeader("Pragma", "no-cache"); // HTTP 1.0
		res.setDateHeader("Expires", 0);
  		if(u==null)
  		{
			if(path.equals("/Login") || path.equals("/Signup") || path.equals("/") || path.equals("/signup.html") || path.equals("/index.html") || path.equals("/Logout") || path.startsWith("/Javascript") || path.equals("/style.css") || path.startsWith("/Images"))
			{
				chain.doFilter(request, response);	    	  
			}
			else
			{
				res.sendRedirect("/SmartEditor/index.html");	  	
			}
  		}
  		else
  		{
  			if(path.equals("/signup.html") || path.equals("/index.html") || path.equals("/"))
  			{
	  			if(u.getUserType()==1)
  				{
  					res.sendRedirect("/SmartEditor/Admin/createTest.html");
  				}
  				else
  				{
  					res.sendRedirect("/SmartEditor/User/userHome.html");
  				}
  			}
  			else
  			{
	  			chain.doFilter(request, res);	
  			}
  		
  		}				
	}
}
