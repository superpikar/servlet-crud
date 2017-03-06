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
	
	public PaginationResult getPaginationResult(boolean isDeleted, int postPerPage){
		PaginationResult result = new PaginationResult();
		try{
			PreparedStatement preparedStatement = connection.prepareStatement("SELECT COUNT(id) AS total FROM "+tableName+" WHERE deleted=?");
			preparedStatement.setBoolean(1, isDeleted);
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
