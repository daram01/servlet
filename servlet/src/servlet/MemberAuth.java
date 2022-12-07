package servlet;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import membership.MemberDAO;
import membership.MemberDTO;

@WebServlet(urlPatterns = {"/MemberAuth.mvc"}, 
			initParams = {@WebInitParam(name="admin_id", value="nakja")} ) 
// 기본 url패턴 - 파라미터 초기화 하지 않은 것.
// 2개 이상의 속성을 사용할 때는 속성명을 명시해야 한다. initParams = {@WebInitParam()} 사용.
// 서블릿클래스 객체를 이용하여 속성 값을 읽어올 수 있음.
public class MemberAuth extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	MemberDAO dao; // DAO 객체를 멤버변수로 선언. init, service 두 곳에서 dao 객체를 사용하기 때문에 멤버변수로 선언해 놓은 것임.
	
    public MemberAuth() {
        super();
    }

	public void init() throws ServletException {
		ServletContext application = this.getServletContext(); // application 객체 얻어오기. web.xml에 접근하기 위해서다.
		String driver = application.getInitParameter("OracleDriver");
		String connectUrl = application.getInitParameter("OracleURL");
		String oId = application.getInitParameter("OracleId");
		String oPass = application.getInitParameter("OraclePwd");
		
		// DAO생성 
		dao = new MemberDAO(driver, connectUrl, oId, oPass);
	}
	
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String admin_id = this.getInitParameter("admin_id"); // 클래스 자체가 서블릿 객체이기 때문에 this 사용 가능
		
		// 인증을 요청한 id, 패스워드 값을 변수에 담는다.
		String id = request.getParameter("id"); 
		String pass = request.getParameter("pass");
		
		MemberDTO memberDTO = dao.getMemberDTO(id, pass); // getMemberDTO의 리턴타입은 MemberDTO임. 
		// 인증을 요청한 id, 패스워드가 데이터베이스에 없으면 memberDTO는 NULL.
		
		String memberName = memberDTO.getName();
		if(memberName != null) { // 데이터베이스에 값이 있다면, 즉 회원이라면
			request.setAttribute("authMessage", memberName + " 회원님 방가방가 ^^!");
		} else { // 데이터베이스에 값이 없다면, 즉 회원이 아니라면
			if(admin_id.equals(id)) { // 만약에 admin_id(최고관리자)와 아이디가 동일하다면,
				request.setAttribute("authMessage", admin_id + " 는 최고 관리자 입니다."); 
			}else { // 아이디가 동일하지 않다면
				request.setAttribute("authMessage", "귀하는 회원이 아닙니다.");
			}
		}
		request.getRequestDispatcher("./MemberAuth.jsp").forward(request, response);
	}

	public void destroy() {
		dao.close();
	}


} // MemberAuth 끝