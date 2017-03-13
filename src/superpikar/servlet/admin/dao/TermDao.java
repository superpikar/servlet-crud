package superpikar.servlet.admin.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import superpikar.servlet.admin.model.Term;
import superpikar.servlet.admin.model.FilterAndSort;
import superpikar.servlet.util.DbUtil;

/*
 * inspired by https://danielniko.wordpress.com/2012/04/17/simple-crud-using-jsp-servlet-and-mysql/#comments
 * naming convention https://launchbylunch.com/posts/2014/Feb/16/sql-naming-conventions/
 * */
public class TermDao extends BaseDao{	
	public TermDao(){
		super();
		connection = DbUtil.getConnection();
		tableName = DbUtil.getTableName("term");
		
		// TODO please implement TermDao, TermController, and term view to display term management.
	}
	
	public int addTerm(Term term){
		try{
			PreparedStatement preparedStatement = connection.prepareStatement("insert into "+tableName+"(name, slug, taxonomy, lineage, registerIp, registerUserId, registerDate, deleted) values(?,?,?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
			preparedStatement.setString(1, term.getName());
			preparedStatement.setString(2, term.getSlug());
			preparedStatement.setString(3, term.getTaxonomy());
			preparedStatement.setString(4, term.getLineage());
			preparedStatement.setString(5, term.getRegisterIp());
			preparedStatement.setInt(6, term.getRegisterUserId());
			preparedStatement.setDate(7, new java.sql.Date(Calendar.getInstance().getTimeInMillis()));
			preparedStatement.setBoolean(8, false);
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
	
	public int updateTerm(Term term){
		try{
			PreparedStatement preparedStatement = connection.prepareStatement("update "+tableName+" set name=?, slug=?, taxonomy=?, registerIp=?, registerUserId=?, registerDate=? where id=?)", Statement.RETURN_GENERATED_KEYS);
			preparedStatement.setString(1, term.getName());
			preparedStatement.setString(2, term.getSlug());
			preparedStatement.setString(3, term.getTaxonomy());
			preparedStatement.setString(4, term.getModificationIp());
			preparedStatement.setInt(5, term.getModificationUserId());
			preparedStatement.setDate(6, new java.sql.Date(Calendar.getInstance().getTimeInMillis()));
			preparedStatement.setInt(7, term.getId());
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
		return term.getId();
	}
	
	public int deleteTerm(int id, boolean isDeleted){
		return delete(id, isDeleted);
	}
	
	public int forceDeleteTerm(int id){
		return forceDelete(id);
	}
	
	public List<Term> getAllTerms(boolean isDeleted, String pageNumber, String postPerPage, FilterAndSort filterAndSort){
		List<Term> terms = new ArrayList<Term>();		
		try {
			PreparedStatement preparedStatement = setPreparedStatementGetRows(isDeleted, pageNumber, postPerPage, filterAndSort, false);
			System.out.println("list query " + preparedStatement.toString());
			ResultSet rs = preparedStatement.executeQuery();
			while(rs.next()) {
				Term term = new Term(rs.getInt("id"), rs.getString("name"), rs.getString("slug"), rs.getString("taxonomy"), rs.getString("lineage"));
				term.setRegisterProperties(rs.getString("registerIp"), rs.getInt("registerUserId"), rs.getDate("registerDate"));
				term.setModificationProperties(rs.getString("modificationIp"), rs.getInt("modificationUserId"), rs.getDate("modificationDate"));
				terms.add(term);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}		
		return terms;
	}
	
	public Term getTermById(int id){
		Term term = new Term();
		try{
			PreparedStatement preparedStatement = connection.prepareStatement("select * from "+tableName+" where id=?");
			preparedStatement.setInt(1, id);
			
			ResultSet rs = preparedStatement.executeQuery();
			if(rs.next()) {
				term.setProperties(rs.getInt("id"), rs.getString("name"), rs.getString("slug"), rs.getString("taxonomy"), rs.getString("lineage"));
				term.setRegisterProperties(rs.getString("registerIp"), rs.getInt("registerUserId"), rs.getDate("registerDate"));
				term.setModificationProperties(rs.getString("modificationIp"), rs.getInt("modificationUserId"), rs.getDate("modificationDate"));
				term.setDeleted(rs.getBoolean("deleted"));
			}			
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return term;
	}
}
