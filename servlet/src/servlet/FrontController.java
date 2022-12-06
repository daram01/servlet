package servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("*.one") // 와일드카드(*) 를 사용하여 url패턴이 "*.one"에 해당되는 요청을 모두 이 서블릿과 매핑.
public class FrontController extends HttpServlet {
	private static final long serialVersionUID = 1L;
    public FrontController() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String uri = request.getRequestURI();
		int lastSlash = uri.lastIndexOf("/");
		String commandStr = uri.substring(lastSlash);
		
		if(commandStr.equals("/regist.one")) {
			registFunc(request);
		}else if(commandStr.equals("/login.one")) {
			loginFunc(request);
		}else if(commandStr.equals("/freeboard.one")) {
			freeboardFunc(request);			
		}
		
		request.setAttribute("uri", uri);
		request.setAttribute("commandStr", commandStr);
		request.getRequestDispatcher("FrontController.jsp").forward(request, response);
	}
	
	void registFunc(HttpServletRequest request) {
		request.setAttribute("resultValue", "<h4>회원가입</h4>");
	}
	
	void loginFunc(HttpServletRequest request) {
		request.setAttribute("resultValue", "<h4>로그인</h4>");
	}
	
	void freeboardFunc(HttpServletRequest request) {
		request.setAttribute("resultValue", "<h4>자유게시판</h4>");
	}

}
