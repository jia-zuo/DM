package dao;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
/**
 * @author jzuo
 * DAO interface
 */
public interface IDAO {
	public abstract Connection getConnection(String jdbc_driver,String db_url,String user,String pass) throws IOException, ClassNotFoundException, SQLException;
	public abstract void insert(Connection conn,Event event) throws Exception;
	public abstract void deleteByName(Connection conn,String name) throws Exception;
	public abstract void updateParentByName(Connection conn,String name,String parent) throws Exception;
	public abstract String queryParentByName(Connection conn,String name)  throws Exception;
	//public Vector queryNamesByParent(Connection conn,String parent) throws Exception;
}
