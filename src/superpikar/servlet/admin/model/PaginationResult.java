package superpikar.servlet.admin.model;

import java.util.ArrayList;

public class PaginationResult {
	private int totalRows;
	private int postPerPage;
	private ArrayList<Integer> paginations;
	public int getTotalRows() {
		return totalRows;
	}
	public void setTotalRows(int totalRows) {
		this.totalRows = totalRows;
	}
	public ArrayList<Integer> getPaginations() {
		return paginations;
	}
	public void setPaginations(ArrayList<Integer> paginations) {
		this.paginations = paginations;
	}
	public int getPostPerPage() {
		return postPerPage;
	}
	public void setPostPerPage(int postPerPage) {
		this.postPerPage = postPerPage;
	}
}
