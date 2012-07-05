/**
 * 
 */
package org.esupportail.commons.dao;

import java.util.List;

/**
 * @author ylecuyer
 *
 */
public class ResultPaginator {

	
	private int rowNumber;
	
	private List visibleItems;
	
	

	/**
	 * Constructor.
	 */
	public ResultPaginator() {
		super();
	}
	
	/**
	 * @return the rowNumber
	 */
	public int getRowNumber() {
		return rowNumber;
	}

	/**
	 * @param rowNumber the rowNumber to set
	 */
	public void setRowNumber(int rowNumber) {
		this.rowNumber = rowNumber;
	}

	/**
	 * @return the visibleItems
	 */
	public List getVisibleItems() {
		return visibleItems;
	}

	/**
	 * @param visibleItems the visibleItems to set
	 */
	public void setVisibleItems(List visibleItems) {
		this.visibleItems = visibleItems;
	}


}
