package superpikar.servlet.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import superpikar.servlet.model.Post;
import superpikar.servlet.util.DbUtil;

/*
 * inspired by https://danielniko.wordpress.com/2012/04/17/simple-crud-using-jsp-servlet-and-mysql/#comments
 * naming convention https://launchbylunch.com/posts/2014/Feb/16/sql-naming-conventions/
 * */
public class PostDao {
	private Connection connection;
	private String tableName;
	
	public PostDao(){
		connection = DbUtil.getConnection();
		tableName = DbUtil.getTableName("post");
//		System.out.print("table "+tableName);
	}
	
	public int addPost(Post post){
		try{
			PreparedStatement preparedStatement = connection.prepareStatement("insert into "+tableName+"(title, slug, content, image, user_id, created_date, updated_date) values(?,?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
			preparedStatement.setString(1, post.getTitle());
			preparedStatement.setString(2, post.getSlug());
			preparedStatement.setString(3, post.getContent());
			preparedStatement.setString(4, post.getImage());
			preparedStatement.setInt(5, post.getModificationUserId());
			preparedStatement.setDate(6, new java.sql.Date(Calendar.getInstance().getTimeInMillis()));
			preparedStatement.setDate(7, new java.sql.Date(Calendar.getInstance().getTimeInMillis()));
			System.out.print(preparedStatement.toString());
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
			PreparedStatement preparedStatement = connection.prepareStatement("update "+tableName+" set title=?, slug=?, content=?, image=?, modificationUserId=?, modificationDate=? where id=?");
            // Parameters start with 1
			preparedStatement.setString(1, post.getTitle());
			preparedStatement.setString(2, post.getSlug());
			preparedStatement.setString(3, post.getContent());
			preparedStatement.setString(4, post.getImage());
			preparedStatement.setInt(5, post.getModificationUserId());
			preparedStatement.setDate(6, new java.sql.Date(Calendar.getInstance().getTimeInMillis()));
			preparedStatement.setInt(7, post.getId());
			System.out.print(preparedStatement.toString());
			preparedStatement.executeUpdate();
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return post.getId();
	}
	
	public void deletePost(int ID){
		try {
			PreparedStatement preparedStatement = connection.prepareStatement("delete from "+tableName+" where id=?");
			preparedStatement.setInt(1, ID);
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public List<Post> getAllPosts(){
		List<Post> posts = new ArrayList<Post>();
		try {
			Statement statement = connection.createStatement();
			ResultSet rs = statement.executeQuery("select * from "+tableName);
			while(rs.next()) {
				Post post = new Post(rs.getInt("id"), rs.getString("title"), rs.getString("slug"), rs.getString("content"), rs.getString("image"));
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
			PreparedStatement preparedStatement = connection.prepareStatement("select * from "+tableName+" where id=?");
			preparedStatement.setInt(1, ID);
			ResultSet rs = preparedStatement.executeQuery();
			if(rs.next()){
				post.setProperties(rs.getInt("id"), rs.getString("title"), rs.getString("slug"), rs.getString("content"), rs.getString("image"));
			}
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		return post;
	}
}
