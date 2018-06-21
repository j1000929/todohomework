package org.study.Dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

//DB연동

/*
 * 
 * 
 * 
 * */
public class DiaryDAO {
	
	//연결객체
	private Connection conn;
	
	//쿼리문장 전송객체
	private final String DRIVER="com.mysql.jdbc.Driver";
	//드라이버
	private static final String URL="jdbc:mysql://192.168.0.212:3306/member_todo?characterEncoding=utf8";
	//URL	
	private static final String USER = "root";
	private static final String PW = "Password1!";
	
	public DiaryDAO() {//드라이버 등록(생성자함i
		
		try {
			Class.forName(DRIVER); //메모리 할당(리플렉션)
			System.out.println("클래ㅛㅡ...");
		}catch(Exception ex) {
			ex.printStackTrace();
		}
	}//생성자
	
	
	public Connection getConnection() {
		
		
			try {
				
				conn = DriverManager.getConnection(URL, USER, PW);
				
				System.out.println("conn");
			} catch (SQLException e) {
				
				System.out.println("Diary getConnection() sdfsafsda:"+e.getMessage());
			}
		
			return conn;
	}//연결
	
	/*public void disConnection() {
			
		
		try {
			
			if(ps != null) ps.close();
			if(conn != null) conn.close();
			
		}catch(Exception e) {
			
			System.out.println("Diary disConnection():"+e.getMessage());
		}
	}//해제*/
	
	/** 기능 ***************************************************/
	
	//	로그인
	public String isLogin(MemberBean mbean) {
			
		/*
		 * 1.id 존재여부
		 * 2.id가 없을 경우
		 * 3. id가 있을 경우
		 * 
		 */
		PreparedStatement ps = null;
		PreparedStatement ps_a = null;
		String result="";
		int count=0;
		System.out.println(mbean.getUserid());
		try {
			
			conn = getConnection();
			String sql="SELECT COUNT(*) FROM todo_user WHERE id=?";
					
			ps = conn.prepareStatement(sql);
			ps.setString(1, mbean.getUserid().trim());//
			
			ResultSet rs = ps.executeQuery();//결과값 가져오기
			
			if(rs.next()) {
				count = rs.getInt(1);
			}//첫번째 위치로 변경
			
		
			rs.close();
			
			

			if(count==0) {//디비에 아이디 존재하지 않음
				result="NOID";
				
			}else {//디비에 아이디 존재
				
				sql = "SELECT name, pass FROM todo_user WHERE id=?";
				ps_a = conn.prepareStatement(sql);
				ps_a.setString(1, mbean.getUserid());
				
				rs = ps_a.executeQuery();
				rs.next();
				ps_a.close();
				
				String db_name = rs.getString(1);
				String db_pass = rs.getString(2);
				
				//비밀번호 검색
				if(mbean.getPassword().equals(db_pass)) {//비밀번호가 같으면
					result = db_name;			
				}else {
					result = "NOPWD";
				}
				
			}
			
		} catch (SQLException e) {
			System.out.println("DiaryDAO isLogin():"+ e.getMessage());
		}finally {
			//disConnection();

			
				try {
					if(ps != null) ps.close();
					if(ps_a != null) ps.close();
					if(conn != null) conn.close();
					
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			
		}
		
		return result;
		
	}//isLogin
	
	//일정보여주기
	public boolean isDate(String id, int year, int month, int day) {
		
		PreparedStatement ps;
		boolean bCheck=false;
		
		try {
			conn = getConnection();
			
			String date = year+"-"+month+"-"+day;
			
			System.out.println(id);
			System.out.println(date.trim());
			
			String sql = "SELECT COUNT(*) FROM todo WHERE id=? AND target_date=?";
			//select count(*) from todo where id='candy' and target_date='2018-6-16';
			ps = conn.prepareStatement(sql);
			ps.setString(1, id);
			ps.setString(2, date.trim());
			
			ResultSet rs = ps.executeQuery();
			rs.next();
			
			int count = rs.getInt(1);
			rs.close();
			
			if(count==0) {
				bCheck=false;
			}else {
				bCheck=true;
			}
			
			
		}catch(Exception ex){
			System.out.println("Diary isDate():"+ex.getMessage());
		}finally {
			//disConnection();
		}
		
		return bCheck;
	}//isData 끝
	
	public int isCount(String id, int year, int month, int day) {
		
		PreparedStatement ps=null;
		int count=0;
		
		try {
			
			conn = getConnection();
			
			String date = year+"-"+month+"-"+day;
			
			String sql = "SELECT COUNT(*) FROM todo WHERE id=? AND target_date=?";
			
			ps = conn.prepareStatement(sql);
			ps.setString(1, id);
			ps.setString(2, date.trim());
			
			ResultSet rs = ps.executeQuery();
			
			if(rs.next()) {
				count = rs.getInt(1);
				System.out.println(count);
			}
			

			rs.close();
			ps.close();
			
			
		}catch(Exception ex) {
			System.out.println("Diary isDate():"+ex.getMessage());
		}finally {
			try {
				
				if(ps != null) ps.close();
				if(conn != null) conn.close();
				
			}catch(Exception e) {
				
				System.out.println("Diary disConnection():"+e.getMessage());
			}
			
		}
		
		return count;
		
	}//isCount 끝
	
	//회원가입
	public String joinMember(MemberBean mbean) {
		
		String result="";
		PreparedStatement ps=null;
		PreparedStatement ps_a=null;
		int count=0;
		
		try {
			
			conn=getConnection();
			
			System.out.println("*"+mbean.getUserid());
			
			String sql = "SELECT COUNT(*) FROM todo_user WHERE id=?";
			ps = conn.prepareStatement(sql);
			ps.setString(1, mbean.getUserid());
			System.out.println(mbean);
			ResultSet rs = ps.executeQuery();
			
			if(rs.next()) {
				count = rs.getInt(1);
			}
			System.out.println(count);
			rs.close();
			
			if(count==1) {
				result="ALREADYID";
			}else {
				System.out.println("ok");
				sql = "INSERT INTO todo_user VALUES(?,?,?,?)";
				ps_a = conn.prepareStatement(sql);
				
				System.out.println(mbean.getUserid());
				
				ps_a.setString(1, mbean.getUserid());
				ps_a.setString(2, mbean.getUsername());
				ps_a.setString(3, mbean.getEmail());
				ps_a.setString(4, mbean.getPassword());
				
				ps_a.executeUpdate();
				
				result="JOINOK";
			}
			
		}catch(Exception ex) {
			
			//System.out.println("DiaryDAO joinMember():"+ex.getMessage());
			ex.printStackTrace();
			
		}finally {
			
			try {
				
				if(ps != null) ps.close();
				if(ps_a != null) ps_a.close();
				if(conn != null) conn.close();
				
			}catch(Exception e) {
				
				System.out.println("Diary disConnection():"+e.getMessage());
			}
		}
		
		return result;
		
	}//joinMember 끝
	
	
	//일정 추가
	public void insert(DiaryVo vo) {
		PreparedStatement ps=null;
		try {
			
			conn = getConnection();
			String sql = "INSERT INTO todo (id, subject, content, target_date) VALUES (?,?,?,?)";
		
			ps= conn.prepareStatement(sql);
			
			ps.setString(1, vo.getId());
			ps.setString(2, vo.getSubject());
			ps.setString(3, vo.getContent());
			String target_date = vo.getYear()+"-"+vo.getMonth()+"-"+vo.getDay();
			ps.setString(4, target_date.trim());
			System.out.println(vo.getContent());
			System.out.println(vo.getSubject());
			
			
			ps.executeUpdate();
			
			
		}catch(Exception ex) {
			
			System.out.println("DiaryVo insert():"+ex.getMessage());
			
		}finally {
			
			try {
				
				if(ps != null) ps.close();
				if(conn != null) conn.close();
				
			}catch(Exception e) {
				
				System.out.println("Diary disConnection():"+e.getMessage());
			}
		}
		
	}//insert 끝
	
	//일정 불러와 리스트에 넣어주기
	public ArrayList<DiaryVo> getDiaryData(String id, int year, int month, int day) {
		
		ArrayList<DiaryVo> list = new ArrayList<DiaryVo>();
		PreparedStatement ps=null;
		
		try {
			
				conn = getConnection();
				
				String date=year+"-"+month+"-"+day;
				String sql = "SELECT no, subject, content FROM todo WHERE id=? AND target_date=?";
				ps = conn.prepareStatement(sql);
				
				ps.setString(1, id);
				ps.setString(2, date.trim());
				
				ResultSet rs = ps.executeQuery();
				
				while(rs.next()) {
					
					DiaryVo vo = new DiaryVo();
					vo.setNo(rs.getInt(1));
					vo.setSubject(rs.getString(2));
					vo.setContent(rs.getString(3));
					
					list.add(vo);
				}
				
				rs.close();
			
		}catch(Exception ex) {
			
			System.out.println("DiaryDAO getDiaryData():"+ ex.getMessage());
			
		}finally {
			
			try {
				
				if(ps != null) ps.close();
				if(conn != null) conn.close();
				
			}catch(Exception e) {
				
				System.out.println("Diary disConnection():"+e.getMessage());
			}
		}
		
		return list;
		
	}//getDiaryData끝
	
	//..일정 자세히 보기..//
	
	public DiaryVo getData(int no){
		
		DiaryVo vo = new DiaryVo();
		PreparedStatement ps=null;
		
		try {
			
			conn = getConnection();
			
			String sql = "SELECT subject, content, target_date FROM todo WHERE no=?";
			
			ps = conn.prepareStatement(sql);
			ps.setInt(1, no);
			
			ResultSet rs = ps.executeQuery();
			rs.next();
			
			String date = rs.getString(3);
			String arr_date[] = date.split("-");
			
			String year = arr_date[0];
			String month = arr_date[1];
			String day = arr_date[2];
			
			vo.setSubject(rs.getString(1));
			vo.setContent(rs.getString(2));
			vo.setYear(Integer.parseInt(year));
			vo.setMonth(Integer.parseInt(month));
			vo.setDay(Integer.parseInt(day));
			
			rs.close();
			
		}catch(Exception ex) {
			
			System.out.println("getData()"+ex.getMessage());
			
		}finally {
			
			try {
				
				if(ps != null) ps.close();
				if(conn != null) conn.close();
				
			}catch(Exception e) {
				
				System.out.println("Diary disConnection():"+e.getMessage());
			}
		}
		return vo;
	}
	
	//..일정수정..//
	public void edit(DiaryVo vo) {
		
		PreparedStatement ps=null;
		
		try {
			
			conn = getConnection();
			String sql = "UPDATE todo SET subject=?, content=? WHERE no=? ";
			
			ps = conn.prepareStatement(sql);
			ps.setString(1, vo.getSubject());
			ps.setString(2, vo.getContent());
			ps.setInt(3, vo.getNo());
			
			ps.executeUpdate();
			
		}catch(Exception ex) {
			System.out.println("DiaryDAO edit():"+ex.getMessage());
		}finally {
			
			try {
				
				if(ps != null) ps.close();
				if(conn != null) conn.close();
				
			}catch(Exception e) {
				
				System.out.println("Diary disConnection():"+e.getMessage());
			}
		}
	}//일정 수정 끝
	
	//..일정 삭제..//
	public void delete(int no) {
		
		PreparedStatement ps=null;
		
		try {
			
			conn = getConnection();
			String sql = "DELETE FROM todo WHERE no=?";
			
			ps=conn.prepareStatement(sql);
			ps.setInt(1, no);
			
			ps.executeUpdate();
			
			
		}catch(Exception ex) {
			
			System.out.println("DiaryDAO delete():"+ex.getMessage());
			
		}finally {
			
			try {
				
				if(ps != null) ps.close();
				if(conn != null) conn.close();
				
			}catch(Exception e) {
				
				System.out.println("Diary disConnection():"+e.getMessage());
			}
			
		}
	}//일정삭제 끝
}

