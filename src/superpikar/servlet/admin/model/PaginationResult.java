package superpikar.servlet.admin.model;

import java.util.ArrayList;

public class PaginationResult {
	private int totalRows;
	private int pageNumber;
	private int postPerPage;
	private int firstRowNumber;
	private int lastRowNumber;
	private ArrayList<Integer> paginations;
	
	public int getTotalRows() {
		return totalRows;
	}
	public void setTotalRows(int totalRows) {
		this.totalRows = totalRows;
	}
	public int getPageNumber() {
		return pageNumber;
	}
	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}
	public int getPostPerPage() {
		return postPerPage;
	}
	public void setPostPerPage(int postPerPage) {
		this.postPerPage = postPerPage;
	}
	public ArrayList<Integer> getPaginations() {
		return paginations;
	}
	public void setPaginations(ArrayList<Integer> paginations) {
		this.paginations = paginations;
	}
	public int getFirstRowNumber() {
		return firstRowNumber;
	}
	public void setFirstRowNumber(int firstRowNumber) {
		this.firstRowNumber = firstRowNumber;
	}
	public int getLastRowNumber() {
		return lastRowNumber;
	}
	public void setLastRowNumber(int lastRowNumber) {
		this.lastRowNumber = lastRowNumber;
	}
}
