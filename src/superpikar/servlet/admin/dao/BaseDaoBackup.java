package superpikar.servlet.admin.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import superpikar.servlet.admin.model.BaseModel;
import superpikar.servlet.admin.model.FilterAndSort;
import superpikar.servlet.admin.model.PaginationResult;

/**
 * Following this answer http://stackoverflow.com/posts/18778307/revisions
 * @author http://twitter.com/superpikar
 *
 */
public class BaseDaoBackup {
	protected Connection connection;
	protected String tableName;
	protected PaginationResult paginationResult;
	protected FilterAndSort filterAndSort;
	
	protected PreparedStatement setPreparedStatementGetRows(boolean isDeleted, int pageNumber, int postPerPage, String condition, String keyword, String sortColumn, String sortOrder) {
		int offset = (pageNumber-1)*postPerPage;
		PreparedStatement preparedStatement = null;
		condition = condition==null? "": condition;
		keyword = keyword==null? "": keyword;
		sortColumn = sortColumn==null? "": sortColumn;
		sortOrder = sortOrder==null? "": sortOrder;
		
		try {
			if(condition.equalsIgnoreCase("") && sortColumn.equalsIgnoreCase("")){
				preparedStatement = connection.prepareStatement("SELECT * FROM "+tableName+" WHERE deleted=? ORDER BY id DESC LIMIT ? OFFSET ?");
				preparedStatement.setBoolean(1, isDeleted);
				preparedStatement.setInt(2, postPerPage);
				preparedStatement.setInt(3, offset);
			}
			else if(!condition.equalsIgnoreCase("") && sortColumn.equalsIgnoreCase("")){
				preparedStatement = connection.prepareStatement("SELECT * FROM "+tableName+" WHERE deleted=? AND "+condition+" LIKE ? ORDER BY id DESC LIMIT ? OFFSET ?");
				preparedStatement.setBoolean(1, isDeleted);
				preparedStatement.setString(2, "%"+keyword+"%");
				preparedStatement.setInt(3, postPerPage);
				preparedStatement.setInt(4, offset);
			}
			else if(condition.equalsIgnoreCase("") && !sortColumn.equalsIgnoreCase("")){
				preparedStatement = connection.prepareStatement("SELECT * FROM "+tableName+" WHERE deleted=? ORDER BY "+sortColumn+" "+sortOrder+" LIMIT ? OFFSET ?");
				preparedStatement.setBoolean(1, isDeleted);
				preparedStatement.setInt(2, postPerPage);
				preparedStatement.setInt(3, offset);
			}
			else {
				preparedStatement = connection.prepareStatement("SELECT * FROM "+tableName+" WHERE deleted=? AND "+condition+" LIKE ? ORDER BY "+sortColumn+" "+sortOrder+" LIMIT ? OFFSET ?");
				preparedStatement.setBoolean(1, isDeleted);
				preparedStatement.setString(2, "%"+keyword+"%");
				preparedStatement.setInt(3, postPerPage);
				preparedStatement.setInt(4, offset);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return preparedStatement;
	}
	
	protected PreparedStatement setPreparedStatementGetPaginations(boolean isDeleted, String condition, String keyword, String sortColumn, String sortOrder) {
		PreparedStatement preparedStatement = null;
		condition = condition==null? "": condition;
		keyword = keyword==null? "": keyword;
		sortColumn = sortColumn==null? "": sortColumn;
		sortOrder = sortOrder==null? "": sortOrder;
		
		try {
			if(condition.equalsIgnoreCase("") && sortColumn.equalsIgnoreCase("")){
				preparedStatement = connection.prepareStatement("SELECT count(id) as total FROM "+tableName+" WHERE deleted=? ORDER BY id DESC");
				preparedStatement.setBoolean(1, isDeleted);
			}
			else if(!condition.equalsIgnoreCase("") && sortColumn.equalsIgnoreCase("")){
				preparedStatement = connection.prepareStatement("SELECT count(id) as total FROM "+tableName+" WHERE deleted=? AND "+condition+" LIKE ? ORDER BY id DESC");
				preparedStatement.setBoolean(1, isDeleted);
				preparedStatement.setString(2, "%"+keyword+"%");
			}
			else if(condition.equalsIgnoreCase("") && !sortColumn.equalsIgnoreCase("")){
				preparedStatement = connection.prepareStatement("SELECT count(id) as total FROM "+tableName+" WHERE deleted=? ORDER BY "+sortColumn+" "+sortOrder);
				preparedStatement.setBoolean(1, isDeleted);
			}
			else {
				preparedStatement = connection.prepareStatement("SELECT count(id) as total FROM "+tableName+" WHERE deleted=? AND "+condition+" LIKE ? ORDER BY "+sortColumn+" "+sortOrder);
				preparedStatement.setBoolean(1, isDeleted);
				preparedStatement.setString(2, "%"+keyword+"%");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return preparedStatement;
	}
	
	public PaginationResult getPaginationResult(boolean isDeleted, int pageNumber, int postPerPage, String condition, String keyword, String sortColumn, String sortOrder){
		PaginationResult result = new PaginationResult();
		try{
			PreparedStatement preparedStatement = setPreparedStatementGetPaginations(isDeleted, condition, keyword, sortColumn, sortOrder);
			
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
				
				int firstRowNumber = ((pageNumber-1)*postPerPage) + 1;
				int lastRowNumber = firstRowNumber-1+total;
				
				result.setFirstRowNumber(firstRowNumber);
				result.setLastRowNumber(lastRowNumber);
				result.setTotalRows(total);
				result.setPaginations(paginations);
				result.setPostPerPage(postPerPage);
				result.setPageNumber(pageNumber);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;		
	}
	
}
