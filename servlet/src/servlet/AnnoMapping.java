package servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/AnnoMapping.do") // jsp 파일의 주소와 같아야 한다.
public class AnnoMapping extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

    public AnnoMapping() {
        super();
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setAttribute("message", "@AnnoMapping.do처리");
		request.getRequestDispatcher("AnnoMapping.jsp").forward(request, response);
	}


}
