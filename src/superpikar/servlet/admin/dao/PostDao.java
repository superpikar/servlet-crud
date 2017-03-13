package superpikar.servlet.admin.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import superpikar.servlet.admin.model.BaseModel;
import superpikar.servlet.admin.model.FilterAndSort;
import superpikar.servlet.admin.model.PaginationResult;
import superpikar.servlet.admin.model.Post;
import superpikar.servlet.admin.model.User;
import superpikar.servlet.util.DbUtil;

/*
 * inspired by https://danielniko.wordpress.com/2012/04/17/simple-crud-using-jsp-servlet-and-mysql/#comments
 * naming convention https://launchbylunch.com/posts/2014/Feb/16/sql-naming-conventions/
 * */
public class PostDao extends BaseDao{	
	public PostDao(){
		super();
		connection = DbUtil.getConnection();
		tableName = DbUtil.getTableName("post");
	}
	
	public int addPost(Post post){
		try{
			PreparedStatement preparedStatement = connection.prepareStatement("insert into "+tableName+"(title, slug, content, summary, image, registerIp, registerUserId, registerDate, deleted) values(?,?,?,?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
			preparedStatement.setString(1, post.getTitle());
			preparedStatement.setString(2, post.getSlug());
			preparedStatement.setString(3, post.getContent());
			preparedStatement.setString(4, post.getSummary());
			preparedStatement.setString(5, post.getImage());
			preparedStatement.setString(6, post.getRegisterIp());
			preparedStatement.setInt(7, post.getRegisterUserId());
			preparedStatement.setDate(8, new java.sql.Date(Calendar.getInstance().getTimeInMillis()));
			preparedStatement.setBoolean(9, false);
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
	
	public int updatePost(Post post){
		try{
			PreparedStatement preparedStatement = connection.prepareStatement("update "+tableName+" set title=?, slug=?, content=?, summary=?, image=?, modificationIp=?, modificationUserId=?, modificationDate=? where id=?");
            // Parameters start with 1
			preparedStatement.setString(1, post.getTitle());
			preparedStatement.setString(2, post.getSlug());
			preparedStatement.setString(3, post.getContent());
			preparedStatement.setString(4, post.getSummary());
			preparedStatement.setString(5, post.getImage());
			preparedStatement.setString(6, post.getModificationIp());
			preparedStatement.setInt(7, post.getModificationUserId());
			preparedStatement.setDate(8, new java.sql.Date(Calendar.getInstance().getTimeInMillis()));
			preparedStatement.setInt(9, post.getId());
			System.out.println(preparedStatement.toString());
			preparedStatement.executeUpdate();
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return post.getId();
	}
	
	public int deletePost(int id, boolean isDeleted){
		return delete(id, isDeleted);
	}
	
	public int forceDeletePost(int id){
		return forceDelete(id);
	}
	
	public List<Post> getAllPosts(boolean isDeleted, String pageNumber, String postPerPage, FilterAndSort filterAndSort){
		
		List<Post> posts = new ArrayList<Post>();
		try {
			// query sample : SELECT * FROM pikarcms_post ORDER BY id LIMIT 2 OFFSET 0
			PreparedStatement preparedStatement = setPreparedStatementGetRows(isDeleted, pageNumber, postPerPage, filterAndSort, true);
			
			System.out.println("list query " + preparedStatement.toString());
			ResultSet rs = preparedStatement.executeQuery();
			while(rs.next()) {
				Post post = new Post(rs.getInt("id"), rs.getString("title"), rs.getString("slug"), rs.getString("content"), rs.getString("summary"), rs.getString("image"));
				post.setRegisterProperties(rs.getString("registerIp"), rs.getInt("registerUserId"), rs.getDate("registerDate"));
				post.setModificationProperties(rs.getString("modificationIp"), rs.getInt("modificationUserId"), rs.getDate("modificationDate"));
				
				User user = new User();
				user.setUsername(rs.getString("username"));
				user.setEmail(rs.getString("email"));
				post.setUser(user);
				
				posts.add(post);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}		
		return posts;
	}
	
	/**
	 * 
	 * @param ID
	 * @return
	 */
	public Post getPostById(int ID){
		Post post = new Post();
		try {
			PreparedStatement preparedStatement = connection.prepareStatement("select tab.*, us.username, us.email from "+tableName+" tab LEFT JOIN "+tablePrefix+"user us ON tab.registerUserId=us.id WHERE tab.id=?");
			preparedStatement.setInt(1, ID);
			ResultSet rs = preparedStatement.executeQuery();
			if(rs.next()){
				post.setProperties(rs.getInt("id"), rs.getString("title"), rs.getString("slug"), rs.getString("content"), rs.getString("summary"), rs.getString("image"));
				post.setRegisterProperties(rs.getString("registerIp"), rs.getInt("registerUserId"), rs.getDate("registerDate"));
				post.setModificationProperties(rs.getString("modificationIp"), rs.getInt("modificationUserId"), rs.getDate("modificationDate"));
			}
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		return post;
	}
}
