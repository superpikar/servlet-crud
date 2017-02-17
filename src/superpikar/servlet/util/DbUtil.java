package superpikar.servlet.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/*
 * inspired by https://danielniko.wordpress.com/2012/04/17/simple-crud-using-jsp-servlet-and-mysql/#comments
 * */
public class DbUtil {
	private static Connection connection = null;
	
	private static String username = "servlet-crud";
	private static String password = "servlet-crud";
	private static String driver = "com.mysql.jdbc.Driver";
	private static String url = "jdbc:mysql://localhost:3306/servlet-crud";
	private static String dbPrefix = "pikarcms_";
	
	public static String getdbPrefix(){
		return dbPrefix;
	}
	
	public static String getTableName(String name){
		return dbPrefix+name;
	}
	
	public static Connection getConnection(){
		if(connection!=null){
			return connection;
		}
		else {
			try {
				Class.forName(driver);
				connection = DriverManager.getConnection(url, username, password);
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return connection;
		}
	}
}
