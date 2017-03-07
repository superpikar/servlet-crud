package superpikar.servlet.admin.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import superpikar.servlet.admin.model.BaseModel;
import superpikar.servlet.admin.model.PaginationResult;

/**
 * Following this answer http://stackoverflow.com/posts/18778307/revisions
 * @author http://twitter.com/superpikar
 *
 */
public class BaseDao {
	protected Connection connection;
	protected String tableName;
	
	protected PreparedStatement setPreparedStatementNormal(boolean isDeleted, int postPerPage, int offset){
		PreparedStatement preparedStatement = null;
		try {
			preparedStatement = connection.prepareStatement("SELECT * FROM "+tableName+" WHERE deleted=? ORDER BY id DESC LIMIT ? OFFSET ?");
			preparedStatement.setBoolean(1, isDeleted);
			preparedStatement.setInt(2, postPerPage);
			preparedStatement.setInt(3, offset);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return preparedStatement;
	}
	
	protected PreparedStatement setPreparedStatementSearchByKeyword(boolean isDeleted, int postPerPage, int offset, String condition, String keyword){
		PreparedStatement preparedStatement = null;
		keyword = keyword==null? "": keyword;
		try {
			preparedStatement = connection.prepareStatement("SELECT * FROM "+tableName+" WHERE deleted=? AND "+condition+" LIKE ? ORDER BY id DESC LIMIT ? OFFSET ?");
			preparedStatement.setBoolean(1, isDeleted);
			preparedStatement.setString(2, "%"+keyword+"%");
			preparedStatement.setInt(3, postPerPage);
			preparedStatement.setInt(4, offset);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return preparedStatement;
	}
	
	public PaginationResult getPaginationResult(boolean isDeleted, int postPerPage, String condition, String keyword){
		PaginationResult result = new PaginationResult();
		try{
			PreparedStatement preparedStatement = null;
			if(condition==null && keyword==null){
				preparedStatement = connection.prepareStatement("SELECT COUNT(id) AS total FROM "+tableName+" WHERE deleted=?");
				preparedStatement.setBoolean(1, isDeleted);
			}
			else if(condition!=null) {
				preparedStatement = connection.prepareStatement("SELECT COUNT(id) AS total FROM "+tableName+" WHERE deleted=? AND "+condition+" LIKE ?");
				preparedStatement.setBoolean(1, isDeleted);
				preparedStatement.setString(2, "%"+keyword+"%");
			}
			
			System.out.println("pagination query " + preparedStatement.toString());
			ResultSet rs = preparedStatement.executeQuery();
			while (rs.next()) {
				int total = rs.getInt("total");
				int stepCount = total/postPerPage;
				
				// add 1 if number of stepCount is odd
				if(stepCount%2==0 && stepCount!=0){
					stepCount = stepCount+1;
				}
								 
				ArrayList<Integer> paginations = new ArrayList<Integer>();
				for(int i=0; i<stepCount; i++){
					paginations.add(i+1);
				}
				result.setTotalRows(total);
				result.setPaginations(paginations);
				result.setPostPerPage(postPerPage);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;		
	}
	
}
