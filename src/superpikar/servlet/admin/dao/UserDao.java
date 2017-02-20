package superpikar.servlet.admin.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import superpikar.servlet.admin.model.User;
import superpikar.servlet.util.DbUtil;

public class UserDao {
	private Connection connection;
	private String tableName;
	
	public UserDao(){
		connection = DbUtil.getConnection();
		tableName = DbUtil.getTableName("user");
	}
	
	public int addUser(User user) {
		// not implemented yet
		return 0;
	}
	
	// mapping resultset to pojo object http://stackoverflow.com/questions/21462099/mapping-resultset-to-pojo-objects
	public User getUserByUsernamePassword(String username, String password){
		User user = new User();
		try{
			PreparedStatement preparedStatement = connection.prepareStatement("select * from "+tableName+" where username=? and password=?");
			preparedStatement.setString(1, username);
			preparedStatement.setString(2, password);
			
			ResultSet rs = preparedStatement.executeQuery();
			if(rs.next()) {
				user.setProperties(rs.getInt("id"), rs.getString("username"), rs.getString("password"), rs.getString("email"), rs.getString("website"), rs.getString("image"));
				user.setRegisterProperties(rs.getString("registerIp"), rs.getInt("registerUserId"), rs.getDate("registerDate"));
				user.setModificationProperties(rs.getString("modificationIp"), rs.getInt("modificationUserId"), rs.getDate("modificationDate"));
				user.setDeleted(rs.getBoolean("deleted"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return user;
	}
}
