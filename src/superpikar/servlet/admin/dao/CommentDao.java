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
import superpikar.servlet.admin.model.Comment;
import superpikar.servlet.admin.model.FilterAndSort;
import superpikar.servlet.admin.model.PaginationResult;
import superpikar.servlet.admin.model.Post;
import superpikar.servlet.admin.model.User;
import superpikar.servlet.util.DbUtil;

/*
 * inspired by https://danielniko.wordpress.com/2012/04/17/simple-crud-using-jsp-servlet-and-mysql/#comments
 * naming convention https://launchbylunch.com/posts/2014/Feb/16/sql-naming-conventions/
 * */
public class CommentDao extends BaseDao{	
	public CommentDao(){
		super();
		connection = DbUtil.getConnection();
		tableName = DbUtil.getTableName("comment");
	}
	
	public int addComment(Comment comment){
		try{
			PreparedStatement preparedStatement = connection.prepareStatement("insert into "+tableName+"(comment, parentId, lineage, channel, channelId, registerIp, registerUserId, registerDate, deleted) values(?,?,?,?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
			preparedStatement.setString(1, comment.getComment());
			preparedStatement.setInt(2, comment.getParentId());
			preparedStatement.setString(3, comment.getLineage());
			preparedStatement.setString(4, comment.getChannel());
			preparedStatement.setInt(5, comment.getChannelId());
			preparedStatement.setString(6, comment.getRegisterIp());
			preparedStatement.setInt(7, comment.getRegisterUserId());
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
	
	public int updateComment(Comment comment){
		try{
			PreparedStatement preparedStatement = connection.prepareStatement("update "+tableName+" set comment=?, parentId=?, lineage=?, channel=?, channelId=?, modificationIp=?, modificationUserId=?, modificationDate=? where id=?");
            // Parameters start with 1
			preparedStatement.setString(1, comment.getComment());
			preparedStatement.setInt(2, comment.getParentId());
			preparedStatement.setString(3, comment.getLineage());
			preparedStatement.setString(4, comment.getChannel());
			preparedStatement.setInt(5, comment.getChannelId());
			preparedStatement.setString(6, comment.getModificationIp());
			preparedStatement.setInt(7, comment.getModificationUserId());
			preparedStatement.setDate(8, new java.sql.Date(Calendar.getInstance().getTimeInMillis()));
			preparedStatement.setInt(9, comment.getId());
			System.out.println(preparedStatement.toString());
			preparedStatement.executeUpdate();
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return comment.getId();
	}
	
	public int deleteComment(int id, boolean isDeleted){
		try{
			PreparedStatement preparedStatement = connection.prepareStatement("update "+tableName+" set deleted=? where id=?");
			preparedStatement.setBoolean(1, isDeleted);
			preparedStatement.setInt(2, id);
			System.out.println(preparedStatement.toString());
			preparedStatement.executeUpdate();
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return id;
	}
	
	public void forceDeleteComment(int id){
		try {
			PreparedStatement preparedStatement = connection.prepareStatement("delete from "+tableName+" where id=?");
			preparedStatement.setInt(1, id);
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public List<Comment> getAllCommentByChannelId(String channel, int channelId, boolean isDeleted){
		List<Comment> comments = new ArrayList<Comment>();
		try {
			// query sample : SELECT * FROM pikarcms_post ORDER BY id LIMIT 2 OFFSET 0
			PreparedStatement preparedStatement = connection.prepareStatement("select com.*, us.username, us.email from "+tableName+" com LEFT JOIN "+tablePrefix+"user us ON com.registerUserId=us.id WHERE com.channel=? AND com.channelId=? AND com.deleted=?");
			preparedStatement.setString(1, channel);
			preparedStatement.setInt(2, channelId);
			preparedStatement.setBoolean(3, isDeleted);
			
			System.out.println("list query " + preparedStatement.toString());
			ResultSet rs = preparedStatement.executeQuery();
			while(rs.next()) {
				
				Comment comment = new Comment(rs.getInt("id"), rs.getString("comment"), rs.getInt("parentId"), rs.getString("lineage"), rs.getString("channel"), rs.getInt("channelId"));
				comment.setRegisterProperties(rs.getString("registerIp"), rs.getInt("registerUserId"), rs.getDate("registerDate"));
				comment.setModificationProperties(rs.getString("modificationIp"), rs.getInt("modificationUserId"), rs.getDate("modificationDate"));
				
				User user = new User();
				user.setUsername(rs.getString("username"));
				user.setEmail(rs.getString("email"));
				comment.setUser(user);
				
				comments.add(comment);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}		
		return comments;
	}
	
	/**
	 * 
	 * @param ID
	 * @return
	 */
	public Comment getCommentById(int ID){
		Comment comment = new Comment();
		try {
			PreparedStatement preparedStatement = connection.prepareStatement("select * from "+tableName+" where id=?");
			preparedStatement.setInt(1, ID);
			ResultSet rs = preparedStatement.executeQuery();
			if(rs.next()){
				comment.setProperties(rs.getInt("id"), rs.getString("comment"), rs.getInt("parentId"), rs.getString("lineage"), rs.getString("channel"), rs.getInt("channelId"));
				comment.setRegisterProperties(rs.getString("registerIp"), rs.getInt("registerUserId"), rs.getDate("registerDate"));
				comment.setModificationProperties(rs.getString("modificationIp"), rs.getInt("modificationUserId"), rs.getDate("modificationDate"));
			}
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return comment;
	}
}
