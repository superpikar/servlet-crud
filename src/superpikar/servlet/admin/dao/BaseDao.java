package superpikar.servlet.admin.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import superpikar.servlet.admin.model.BaseModel;
import superpikar.servlet.admin.model.FilterAndSort;
import superpikar.servlet.admin.model.PaginationResult;
import superpikar.servlet.util.DbUtil;

/**
 * Following this answer http://stackoverflow.com/posts/18778307/revisions
 * @author http://twitter.com/superpikar
 *
 */
public class BaseDao {
	protected Connection connection;
	protected String tableName;
	protected String tablePrefix;
	
	public BaseDao(){
		tablePrefix = DbUtil.getdbPrefix();
	}
	
	protected PreparedStatement setPreparedStatementGetRows(boolean isDeleted, String pageNum, String postPerPag, FilterAndSort filterAndSort, boolean includeUserData) {
		PreparedStatement preparedStatement = null;
		int postPerPage = Integer.parseInt(postPerPag);
		int pageNumber = pageNum==null?1:Integer.parseInt(pageNum);
		int offset = (pageNumber-1)*postPerPage;
		String condition = filterAndSort.getCondition()==null? "": filterAndSort.getCondition();
		String keyword = filterAndSort.getKeyword()==null? "": filterAndSort.getKeyword();
		String sortColumn = filterAndSort.getSortColumn()==null? "": filterAndSort.getSortColumn();
		String sortOrder = filterAndSort.getSortOrder()==null? "": filterAndSort.getSortOrder();
		String conditionDefault = filterAndSort.getConditionDefault();
		String keywordDefault = filterAndSort.getKeywordDefault();
		
		try {
			// IF IT DOESNT HAVE DEFAULT WHERE QUERY
			if(conditionDefault==null){
				if(condition.equalsIgnoreCase("") && sortColumn.equalsIgnoreCase("")){
					if(includeUserData){
						preparedStatement = connection.prepareStatement("SELECT tab.*, us.username, us.email FROM "+tableName+" tab LEFT JOIN "+tablePrefix+"user us ON tab.registerUserId=us.id WHERE tab.deleted=? ORDER BY tab.id DESC LIMIT ? OFFSET ?");
					}
					else {
						preparedStatement = connection.prepareStatement("SELECT * FROM "+tableName+" WHERE deleted=? ORDER BY id DESC LIMIT ? OFFSET ?");					
					}
					preparedStatement.setBoolean(1, isDeleted);
					preparedStatement.setInt(2, postPerPage);
					preparedStatement.setInt(3, offset);
				}
				else if(!condition.equalsIgnoreCase("") && sortColumn.equalsIgnoreCase("")){
					if(includeUserData){					
						preparedStatement = connection.prepareStatement("SELECT tab.*, us.username, us.email FROM "+tableName+" tab LEFT JOIN "+tablePrefix+"user us ON tab.registerUserId=us.id  WHERE tab.deleted=? AND tab."+condition+" LIKE ? ORDER BY tab.id DESC LIMIT ? OFFSET ?");
					}
					else {
						preparedStatement = connection.prepareStatement("SELECT * FROM "+tableName+" WHERE deleted=? AND "+condition+" LIKE ? ORDER BY id DESC LIMIT ? OFFSET ?");
					}
					preparedStatement.setBoolean(1, isDeleted);
					preparedStatement.setString(2, "%"+keyword+"%");
					preparedStatement.setInt(3, postPerPage);
					preparedStatement.setInt(4, offset);
				}
				else if(condition.equalsIgnoreCase("") && !sortColumn.equalsIgnoreCase("")){
					if(includeUserData){
						preparedStatement = connection.prepareStatement("SELECT tab.*, us.username, us.email FROM "+tableName+" tab LEFT JOIN "+tablePrefix+"user us ON tab.registerUserId=us.id WHERE tab.deleted=? ORDER BY tab."+sortColumn+" "+sortOrder+" LIMIT ? OFFSET ?");
					}
					else {
						preparedStatement = connection.prepareStatement("SELECT * FROM "+tableName+" WHERE deleted=? ORDER BY "+sortColumn+" "+sortOrder+" LIMIT ? OFFSET ?");					
					}
					preparedStatement.setBoolean(1, isDeleted);
					preparedStatement.setInt(2, postPerPage);
					preparedStatement.setInt(3, offset);
				}
				else {
					if(includeUserData){
						preparedStatement = connection.prepareStatement("SELECT tab.*, us.username, us.email FROM "+tableName+" tab LEFT JOIN "+tablePrefix+"user us ON tab.registerUserId=us.id WHERE tab.deleted=? AND tab."+condition+" LIKE ? ORDER BY "+sortColumn+" "+sortOrder+" LIMIT ? OFFSET ?");
					}
					else {
						preparedStatement = connection.prepareStatement("SELECT * FROM "+tableName+" WHERE deleted=? AND "+condition+" LIKE ? ORDER BY "+sortColumn+" "+sortOrder+" LIMIT ? OFFSET ?");					
					}
					preparedStatement.setBoolean(1, isDeleted);
					preparedStatement.setString(2, "%"+keyword+"%");
					preparedStatement.setInt(3, postPerPage);
					preparedStatement.setInt(4, offset);
				}
			}
			// IF IT HAS DEFAULT WHERE QUERY
			else {
				if(condition.equalsIgnoreCase("") && sortColumn.equalsIgnoreCase("")){
					if(includeUserData){
						preparedStatement = connection.prepareStatement("SELECT tab.*, us.username, us.email FROM "+tableName+" tab LEFT JOIN "+tablePrefix+"user us ON tab.registerUserId=us.id WHERE tab.deleted=? AND tab."+conditionDefault+" LIKE ? ORDER BY tab.id DESC LIMIT ? OFFSET ?");
					}
					else {
						preparedStatement = connection.prepareStatement("SELECT * FROM "+tableName+" WHERE deleted=? AND "+conditionDefault+" LIKE ? ORDER BY id DESC LIMIT ? OFFSET ?");					
					}
					preparedStatement.setBoolean(1, isDeleted);
					preparedStatement.setString(2, "%"+keywordDefault+"%");
					preparedStatement.setInt(3, postPerPage);
					preparedStatement.setInt(4, offset);
				}
				else if(!condition.equalsIgnoreCase("") && sortColumn.equalsIgnoreCase("")){
					if(includeUserData){					
						preparedStatement = connection.prepareStatement("SELECT tab.*, us.username, us.email FROM "+tableName+" tab LEFT JOIN "+tablePrefix+"user us ON tab.registerUserId=us.id  WHERE tab.deleted=? AND tab."+conditionDefault+" LIKE ? AND tab."+condition+" LIKE ? ORDER BY tab.id DESC LIMIT ? OFFSET ?");
					}
					else {
						preparedStatement = connection.prepareStatement("SELECT * FROM "+tableName+" WHERE deleted=? AND "+conditionDefault+" LIKE ? AND "+condition+" LIKE ? ORDER BY id DESC LIMIT ? OFFSET ?");
					}
					preparedStatement.setBoolean(1, isDeleted);
					preparedStatement.setString(2, "%"+keywordDefault+"%");
					preparedStatement.setString(3, "%"+keyword+"%");
					preparedStatement.setInt(4, postPerPage);
					preparedStatement.setInt(5, offset);
				}
				else if(condition.equalsIgnoreCase("") && !sortColumn.equalsIgnoreCase("")){
					if(includeUserData){
						preparedStatement = connection.prepareStatement("SELECT tab.*, us.username, us.email FROM "+tableName+" tab LEFT JOIN "+tablePrefix+"user us ON tab.registerUserId=us.id WHERE tab.deleted=? AND tab."+conditionDefault+" LIKE ? ORDER BY tab."+sortColumn+" "+sortOrder+" LIMIT ? OFFSET ?");
					}
					else {
						preparedStatement = connection.prepareStatement("SELECT * FROM "+tableName+" WHERE deleted=? AND "+conditionDefault+" LIKE ? ORDER BY "+sortColumn+" "+sortOrder+" LIMIT ? OFFSET ?");					
					}
					preparedStatement.setBoolean(1, isDeleted);
					preparedStatement.setString(2, "%"+keywordDefault+"%");
					preparedStatement.setInt(3, postPerPage);
					preparedStatement.setInt(4, offset);
				}
				else {
					if(includeUserData){
						preparedStatement = connection.prepareStatement("SELECT tab.*, us.username, us.email FROM "+tableName+" tab LEFT JOIN "+tablePrefix+"user us ON tab.registerUserId=us.id WHERE tab.deleted=? AND tab."+conditionDefault+" LIKE ? AND tab."+condition+" LIKE ? ORDER BY "+sortColumn+" "+sortOrder+" LIMIT ? OFFSET ?");
					}
					else {
						preparedStatement = connection.prepareStatement("SELECT * FROM "+tableName+" WHERE deleted=? AND tab."+conditionDefault+" LIKE ? AND "+condition+" LIKE ? ORDER BY "+sortColumn+" "+sortOrder+" LIMIT ? OFFSET ?");					
					}
					preparedStatement.setBoolean(1, isDeleted);
					preparedStatement.setString(2, "%"+keywordDefault+"%");
					preparedStatement.setString(3, "%"+keyword+"%");
					preparedStatement.setInt(4, postPerPage);
					preparedStatement.setInt(5, offset);
				}
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return preparedStatement;
	}
	
	protected PreparedStatement setPreparedStatementGetPaginations(boolean isDeleted, FilterAndSort filterAndSort) {
		PreparedStatement preparedStatement = null;
		
		String condition = filterAndSort.getCondition()==null? "": filterAndSort.getCondition();
		String keyword = filterAndSort.getKeyword()==null? "": filterAndSort.getKeyword();
		String sortColumn = filterAndSort.getSortColumn()==null? "": filterAndSort.getSortColumn();
		String sortOrder = filterAndSort.getSortOrder()==null? "": filterAndSort.getSortOrder();
		String conditionDefault = filterAndSort.getConditionDefault();
		String keywordDefault = filterAndSort.getKeywordDefault();
		
		try {
			if(conditionDefault==null){
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
			}
			else {
				if(condition.equalsIgnoreCase("") && sortColumn.equalsIgnoreCase("")){
					preparedStatement = connection.prepareStatement("SELECT count(id) as total FROM "+tableName+" WHERE deleted=? AND "+conditionDefault+" LIKE ? ORDER BY id DESC");
					preparedStatement.setBoolean(1, isDeleted);
					preparedStatement.setString(2, "%"+keywordDefault+"%");
				}
				else if(!condition.equalsIgnoreCase("") && sortColumn.equalsIgnoreCase("")){
					preparedStatement = connection.prepareStatement("SELECT count(id) as total FROM "+tableName+" WHERE deleted=? AND "+conditionDefault+" LIKE ? AND "+condition+" LIKE ? ORDER BY id DESC");
					preparedStatement.setBoolean(1, isDeleted);
					preparedStatement.setString(2, "%"+keywordDefault+"%");
					preparedStatement.setString(3, "%"+keyword+"%");
				}
				else if(condition.equalsIgnoreCase("") && !sortColumn.equalsIgnoreCase("")){
					preparedStatement = connection.prepareStatement("SELECT count(id) as total FROM "+tableName+" WHERE deleted=? AND "+conditionDefault+" LIKE ? ORDER BY "+sortColumn+" "+sortOrder);
					preparedStatement.setBoolean(1, isDeleted);
					preparedStatement.setString(2, "%"+keywordDefault+"%");
				}
				else {
					preparedStatement = connection.prepareStatement("SELECT count(id) as total FROM "+tableName+" WHERE deleted=? AND "+conditionDefault+" LIKE ? AND "+condition+" LIKE ? ORDER BY "+sortColumn+" "+sortOrder);
					preparedStatement.setBoolean(1, isDeleted);
					preparedStatement.setString(2, "%"+keywordDefault+"%");
					preparedStatement.setString(3, "%"+keyword+"%");
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return preparedStatement;
	}
	
	public PaginationResult getPaginationResult(boolean isDeleted, String pageNum, String postPerPag, FilterAndSort filterAndSort){
		int postPerPage = Integer.parseInt(postPerPag);
		int pageNumber = pageNum==null?1:Integer.parseInt(pageNum);
		
		PaginationResult paginationResult = new PaginationResult();
		
		try{
			PreparedStatement preparedStatement = setPreparedStatementGetPaginations(isDeleted, filterAndSort);
			
			System.out.println("pagination query " + preparedStatement.toString());
			ResultSet rs = preparedStatement.executeQuery();
			while (rs.next()) {
				int total = rs.getInt("total");
				int stepCount = total/postPerPage;
				int prevStepCount = stepCount;
				// add 1 if number of stepCount is odd
				if(stepCount==0){
					stepCount = 1;
				}
				else if(total%postPerPage>0){	// if has remaining post on the last page
					stepCount = stepCount+1;					
				}
				System.out.println("total:"+total+" perpage:"+postPerPage+" stepcount"+prevStepCount+" pages:"+stepCount);
				
				ArrayList<Integer> paginations = new ArrayList<Integer>();
				for(int i=0; i<stepCount; i++){
					paginations.add(i+1);
				}
				
				int firstRowNumber = ((pageNumber-1)*postPerPage) + 1;
				int lastRowNumber = firstRowNumber-1+total;
				
				paginationResult.setFirstRowNumber(firstRowNumber);
				paginationResult.setLastRowNumber(lastRowNumber);
				paginationResult.setTotalRows(total);
				paginationResult.setPaginations(paginations);
				paginationResult.setPostPerPage(postPerPage);
				paginationResult.setPageNumber(pageNumber);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return paginationResult;		
	}
	
	public int delete(int id, boolean isDeleted){
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
	
	public int forceDelete(int id){
		try {
			PreparedStatement preparedStatement = connection.prepareStatement("delete from "+tableName+" where id=?");
			preparedStatement.setInt(1, id);
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return id;
	}
	
}
