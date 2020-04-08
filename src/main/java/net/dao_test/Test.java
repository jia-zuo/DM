import java.util.Scanner;
import dao.*;
import java.sql.*;
/**
 * @author jzuo
 * @test
 */
public class Test {
	static final String jd = "com.mysql.jdbc.Driver";
	static final String url = "jdbc:mysql://localhost:3306/FTDB?useSSL=true";
	static final String u = "jzuo";
	static final String p = "jzuo";
	public static void main(String[] args) {
		Connection conn = null;
		Scanner scanner = new Scanner(System.in);
		// TODO 
		try {
			Event event = new Event();
			event.setName("earthquake");
			event.setParent("none");
			event.setDesc("crisis");
			event.setRate(1.0);
			
			IDAO dao = new DaoImpl();
			conn = dao.getConnection(jd,url,u,p);

			dao.insert(conn,event);
			dao.updateParentByName(conn,"earthquake","water");
			dao.queryParentByName(conn,"yard");
      dao.deleteByName(conn,"earthquake");

			System.out.println("success");
			conn.close();
		}catch(SQLException se){
			se.printStackTrace();
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			try{
				if(conn!=null) conn.close();
			}catch(SQLException se){
				se.printStackTrace();
			}
		}
	}

}
