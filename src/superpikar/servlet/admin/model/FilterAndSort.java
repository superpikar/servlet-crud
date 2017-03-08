package superpikar.servlet.admin.model;

import java.util.HashMap;

public class FilterAndSort {
	private String keyword;
	private String condition;
	private HashMap<String, String> searchConditions;
	private HashMap<String, String> sortColumns;
	private String sortColumn;
	private String sortOrder;
	
	public FilterAndSort(){
		setSearchConditions(new HashMap<String, String>());
		setSortColumns(new HashMap<String, String>());
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public String getCondition() {
		return condition;
	}

	public void setCondition(String condition) {
		this.condition = condition;
	}

	public HashMap<String, String> getSearchConditions() {
		return searchConditions;
	}

	public void setSearchConditions(HashMap<String, String> searchConditions) {
		this.searchConditions = searchConditions;
	}

	public String getSortColumn() {
		return sortColumn;
	}

	public void setSortColumn(String sortColumn) {
		this.sortColumn = sortColumn;
	}

	public String getSortOrder() {
		return sortOrder;
	}

	public void setSortOrder(String sortOrder) {
		this.sortOrder = sortOrder;
	}

	public HashMap<String, String> getSortColumns() {
		return sortColumns;
	}

	public void setSortColumns(HashMap<String, String> sortColumns) {
		this.sortColumns = sortColumns;
	}
}
