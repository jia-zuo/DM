package dao;

import java.sql.*;
import java.io.IOException;

/**
 * @author jzuo implements of interface
 */
public class DaoImpl implements IDAO {

	@Override
	public Connection getConnection(String jdbc_driver,String db_url,String user,String pass) throws IOException, ClassNotFoundException, SQLException {
		Class.forName(jdbc_driver);
		Connection connection = DriverManager.getConnection(db_url,user,pass);
		connection.setAutoCommit(true);
		return connection;
	}
	@Override
	public void insert(Connection conn,Event event) throws Exception {
		// TODO
		String insertsql = "insert into Events(name,parent,edesc,rate) values (?,?,?,?)";
		PreparedStatement preparedStatement = conn.prepareStatement(insertsql);
		preparedStatement.setString(1, event.getName());
		preparedStatement.setString(2, event.getParent());
		preparedStatement.setString(3, event.getDesc());
		preparedStatement.setDouble(4, event.getRate());
		preparedStatement.execute();
		System.out.println("test inserted " + event.getName());
		preparedStatement.close();
	}

	@Override
	public void deleteByName(Connection conn,String name) throws Exception {
		// TODO
		String deletesql = "delete from Events where name = ?";
		PreparedStatement preparedStatement = conn.prepareStatement(deletesql);
		preparedStatement.setString(1, name);
		preparedStatement.execute();
		System.out.println("test deleted " + name);
		preparedStatement.close();
	}

	@Override
	public void updateParentByName(Connection conn,String name,String parent) throws Exception {
		// TODO
		String updatesql = "update Events set parent = ? where name = ?";
		PreparedStatement preparedStatement = conn.prepareStatement(updatesql);
		preparedStatement.setString(1, parent);
		preparedStatement.setString(2, name);
		preparedStatement.execute();
		System.out.println("test updated " + name + "'s parent is " + parent);
		preparedStatement.close();
	}

	@Override
	public String queryParentByName(Connection conn,String name)  throws Exception {
		// TODO 
		String parent="";
		String querysql = "select * from Events where name = ?";
		PreparedStatement preparedStatement = conn.prepareStatement(querysql);
		preparedStatement.setString(1, name);
		ResultSet rs = preparedStatement.executeQuery();
		while (rs.next()) {
			parent = rs.getString("parent");
			System.out.println("test name: " + rs.getString("name") + "\t" + "parent: " + parent);
		}
		rs.close();
		preparedStatement.close();
		return parent;
	}
}
