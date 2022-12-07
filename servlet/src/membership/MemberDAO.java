package membership;
import common.JDBConnect;

public class MemberDAO extends JDBConnect{

	public MemberDAO(String driver, String url, String id, String pw) {
		super(driver, url, id, pw); // 부모의 생성자를 호출하여 매개변수를 넘겨준다.
	}
	
	public MemberDTO getMemberDTO(String uid, String upass) {
		
		// 1. 조회한 데이터를 담을 객체 생성 
		MemberDTO dto = new MemberDTO();
		// 2. 쿼리문을 구성한다
		String query = "select * from member where id=? and pass=?";
		
		try {
			
			psmt = con.prepareStatement(query);
			psmt.setString(1, uid);
			psmt.setString(2, upass);
			rs = psmt.executeQuery();
			
			if(rs.next()) {
				dto.setId(rs.getString("id"));
				dto.setPass(rs.getString("pass"));
				dto.setName(rs.getString(3));
				dto.setRegidate(rs.getString(4));
			}
			
		}
		catch(Exception e){
			e.printStackTrace();
		}
		
		return dto;
	}
	

}
