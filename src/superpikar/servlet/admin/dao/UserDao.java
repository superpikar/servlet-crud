package superpikar.servlet.admin.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import superpikar.servlet.admin.model.FilterAndSort;
import superpikar.servlet.admin.model.User;
import superpikar.servlet.util.DbUtil;

public class UserDao extends BaseDao{	
	public UserDao(){
		connection = DbUtil.getConnection();
		tableName = DbUtil.getTableName("user");
	}
	
	public int addUser(User user) {
		try{
			PreparedStatement preparedStatement = connection.prepareStatement("insert into "+tableName+"(username, password, email, website, image, role, registerIp, registerUserId, registerDate, deleted) values(?,?,?,?,?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
			preparedStatement.setString(1, user.getUsername());
			preparedStatement.setString(2, user.getPassword());
			preparedStatement.setString(3, user.getEmail());
			preparedStatement.setString(4, user.getWebsite());
			preparedStatement.setString(5, user.getImage());
			preparedStatement.setString(6, user.getRole());
			preparedStatement.setString(7, user.getRegisterIp());
			preparedStatement.setInt(8,  user.getRegisterUserId());
			preparedStatement.setDate(9, new java.sql.Date(Calendar.getInstance().getTimeInMillis()));
			preparedStatement.setBoolean(10, false);
			System.out.println(preparedStatement.toString());
			preparedStatement.executeUpdate();
			
			// credit http://stackoverflow.com/questions/1376218/is-there-a-way-to-retrieve-the-autoincrement-id-from-a-prepared-statement
			ResultSet rs = preparedStatement.getGeneratedKeys();
			if(rs.next()){
				return rs.getInt(1);
			}
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	public int updateUser(User user) {
		try{
			PreparedStatement preparedStatement = connection.prepareStatement("update "+tableName+" set username=?, password=?, email=?, website=?, image=?, role=?, modificationIp=?, modificationUserId=?, modificationDate=? where id=?");
			preparedStatement.setString(1, user.getUsername());
			preparedStatement.setString(2, user.getPassword());
			preparedStatement.setString(3, user.getEmail());
			preparedStatement.setString(4, user.getWebsite());
			preparedStatement.setString(5, user.getImage());
			preparedStatement.setString(6, user.getRole());
			preparedStatement.setString(7, user.getModificationIp());
			preparedStatement.setInt(8, user.getModificationUserId());
			preparedStatement.setDate(9, new java.sql.Date(Calendar.getInstance().getTimeInMillis()));
			preparedStatement.setInt(10, user.getId());
			System.out.println(preparedStatement.toString());
			preparedStatement.executeUpdate();
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return user.getId();
	}
	
	public int deleteUser(int id, boolean isDeleted){
		return delete(id, isDeleted);
	}
	
	public int forceDeleteUser(int id){
		return forceDelete(id); 
	}
	
	public List<User> getAllUsers(boolean isDeleted, String pageNumber, String postPerPage, FilterAndSort filterAndSort, boolean includeUserData){
		List<User> users = new ArrayList<User>();		
		try {
			PreparedStatement preparedStatement = setPreparedStatementGetRows(isDeleted, pageNumber, postPerPage, filterAndSort, includeUserData);
			System.out.println("list query " + preparedStatement.toString());
			ResultSet rs = preparedStatement.executeQuery();
			while(rs.next()) {
				User user = new User(rs.getInt("id"), rs.getString("username"), rs.getString("password"), rs.getString("email"), rs.getString("website"), rs.getString("image"), rs.getString("role"));
				user.setRegisterProperties(rs.getString("registerIp"), rs.getInt("registerUserId"), rs.getDate("registerDate"));
				user.setModificationProperties(rs.getString("modificationIp"), rs.getInt("modificationUserId"), rs.getDate("modificationDate"));
				users.add(user);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}		
		return users;
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
				user.setProperties(rs.getInt("id"), rs.getString("username"), rs.getString("password"), rs.getString("email"), rs.getString("website"), rs.getString("image"), rs.getString("role"));
				user.setRegisterProperties(rs.getString("registerIp"), rs.getInt("registerUserId"), rs.getDate("registerDate"));
				user.setModificationProperties(rs.getString("modificationIp"), rs.getInt("modificationUserId"), rs.getDate("modificationDate"));
				user.setDeleted(rs.getBoolean("deleted"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return user;
	}
	
	public User getUserById(int id){
		User user = new User();
		try{
			PreparedStatement preparedStatement = connection.prepareStatement("select * from "+tableName+" where id=?");
			preparedStatement.setInt(1, id);
			
			ResultSet rs = preparedStatement.executeQuery();
			if(rs.next()) {
				user.setProperties(rs.getInt("id"), rs.getString("username"), rs.getString("password"), rs.getString("email"), rs.getString("website"), rs.getString("image"), rs.getString("role"));
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
