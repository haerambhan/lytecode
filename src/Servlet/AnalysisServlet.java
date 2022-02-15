package Servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.stream.Collectors;

import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

import com.google.gson.Gson;

import CPPService.CPP14Lexer;
import CPPService.CPP14Parser;
import CPPService.MyCPPListener;
import JavaService.Java8Lexer;
import JavaService.Java8Parser;
import JavaService.MyJavaListener;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class AnalysisServlet
 */
@WebServlet("/Analysis")
public class AnalysisServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	public Gson gson = new Gson();
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AnalysisServlet() {
        super();
        // TODO Auto-generated constructor stub
    }
    
	public void sendAsJson(HttpServletResponse response, Object obj) throws IOException
    {
    	String res = gson.toJson(obj);
    	response.setContentType("application/json");
    	PrintWriter out = response.getWriter(); 
		out.print(res);
		out.flush();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String line1 = request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
		String language = request.getParameter("language");
		if("java".equals(language)) {
			Java8Lexer lexer = new Java8Lexer(CharStreams.fromString(line1));
			Java8Parser parser = new Java8Parser(new CommonTokenStream(lexer));
			MyJavaListener jl = new MyJavaListener();
			ParseTree tree = parser.compilationUnit();
			ParseTreeWalker	.DEFAULT.walk(jl,tree);
			sendAsJson(response,jl.getData());
		}
		else if("c_cpp".equals(language)) {
			CPP14Lexer lexer = new CPP14Lexer(CharStreams.fromString(line1));
			CPP14Parser parser = new CPP14Parser(new CommonTokenStream(lexer));
			MyCPPListener cl = new MyCPPListener();
			ParseTreeWalker	.DEFAULT.walk(cl, parser.translationUnit());
			sendAsJson(response,cl.getData());		
		}
		else {
			sendAsJson(response,"Analysis not supported");
		}
	}
}
