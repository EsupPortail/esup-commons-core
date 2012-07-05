/**
 * ESUP-Portail Commons - Copyright (c) 2006-2009 ESUP-Portail consortium.
 */
package org.esupportail.commons.services.paginator; 

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.esupportail.commons.web.controllers.Resettable;



/** 
 * An abstract paginator, independant from the underlying database implementation.
 * @param <E> the class of the visibleItems
 */ 
@SuppressWarnings("serial")
public abstract class AbstractPaginator<E> implements Paginator<E>, Resettable {
	
	/**
     * The default values for pageSizeItems.
     */
    protected static final Integer [] DEFAULT_PAGE_SIZE_ITEM_VALUES = {5, 10, 15, 20};
    
	/**
     * The default value of the default page size.
     */
    protected static final int DEFAULT_DEFAULT_PAGE_SIZE = 10;
    
	/**
	 * The maximum number of near pages to print.
	 */
	protected static final int MAX_NEAR_PAGES = 4;

	/**
	 * The current page number, normally set by constructor.
	 */
	private int currentPage;
	
	/**
	 * The page size, i.e. the number of visibleItems to display per page, 
	 * normally set by the constructor.
	 */
	private int pageSize;
	
	/**
	 * The visible items, normally set by the constructor.
	 */
	private List<E> visibleItems;
	
	/**
	 * The total number of items, normally set by the constructor.
	 */
	private int totalItemsCount;
	
    /**
     * The page size items.
     */
    private List<String> pageSizeItems;
    
    /**
     * The default page size.
     */
    private int defaultPageSize;
    
	/**
	 * The time when the data was loaded.
	 */
	private Timestamp loadTime;
	
	/**
	 * The maximum number of near pages to print.
	 */
	private int maxNearPages;
	
	/**
	 * Constructor.
	 */
	protected AbstractPaginator() {
		super();
		setPageSizeValues(Arrays.asList(getDefaultPageSizeItemValues()));
		setDefaultPageSize(getDefaultDefaultPageSize());
		reset();
	}
	

	
	/**
	 * @see org.esupportail.commons.web.controllers.Resettable#reset()
	 */
	public void reset() {
		pageSize = defaultPageSize;
		currentPage = 0;
		visibleItems = null;
		totalItemsCount = 0;
		loadTime = null;
		maxNearPages = MAX_NEAR_PAGES;
	}


	/**
	 * @see org.esupportail.commons.web.beans.Paginator#forceReload()
	 */
	public void forceReload() {
		loadItemsInternal();
	}


	/**
	 * Load the items (internal).
	 */
	protected abstract void loadItemsInternal();

	/**
	 * Set the page size values.
	 * @param pageSizeValues 
	 */
	public void setPageSizeValues(
			final List<Integer> pageSizeValues) {
		pageSizeItems = new ArrayList<String>();
		for (Integer pageSizeValue : pageSizeValues) {
			pageSizeItems.add(pageSizeValue + "");
		}
	}
	
	/**
	 * @return the default page size values.
	 */
	protected Integer [] getDefaultPageSizeItemValues() {
		return DEFAULT_PAGE_SIZE_ITEM_VALUES;
	}

	/**
	 * @return the default default page size.
	 */
	protected int getDefaultDefaultPageSize() {
		return DEFAULT_DEFAULT_PAGE_SIZE;
	}

    /**
	 * @see org.esupportail.commons.web.beans.Paginator#gotoFirstPage()
	 */
	public void gotoFirstPage() {
		setCurrentPage(0);
	}

	/**
	 * @see org.esupportail.commons.web.beans.Paginator#gotoLastPage()
	 */
	public void gotoLastPage() {
		setCurrentPage(getLastPageNumber());
	}

	/**
	 * @see org.esupportail.commons.web.beans.Paginator#gotoNextPage()
	 */
	public void gotoNextPage() {
		setCurrentPage(currentPage + 1);
	}

	/**
	 * @see org.esupportail.commons.web.beans.Paginator#gotoPreviousPage()
	 */
	public void gotoPreviousPage() {
		setCurrentPage(currentPage - 1);
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return getClass().getSimpleName() + "#" + hashCode() + "[pageSize=[" + pageSize
		+ "], currentPage=" + currentPage + "]";
	}
	
	/**
	 * @return the page size.
	 */
	protected int getPageSizeInternal() {
		return pageSize;
	}

	/**
	 * @see org.esupportail.commons.web.beans.Paginator#getPageSize()
	 */
	public final int getPageSize() {
		return getPageSizeInternal();
	}

	/**
	 * @return the current page.
	 */
	protected int getCurrentPageInternal() {
		return currentPage;
	}

	/**
	 * @see org.esupportail.commons.web.beans.Paginator#getCurrentPage()
	 */
	public final int getCurrentPage() {
		return getCurrentPageInternal();
	}

	/**
	 * @return true if the first page.
	 */
	protected boolean isFirstPageInternal() {
		return currentPage <= 0; 
	}

	/**
	 * @see org.esupportail.commons.web.beans.Paginator#isFirstPage()
	 */
	public final boolean isFirstPage() {
		return isFirstPageInternal(); 
	}

	/**
	 * @return true if the last page.
	 */
	protected boolean isLastPageInternal() {
		return currentPage >= getLastPageNumberInternal(); 
	} 

	/**
	 * @see org.esupportail.commons.web.beans.Paginator#isLastPage()
	 */
	public final boolean isLastPage() {
		return isLastPageInternal(); 
	} 

	/**
	 * @return the previous page.
	 */
	protected int getPreviousPageInternal() {
		return currentPage - 1;
	}

	/**
	 * @see org.esupportail.commons.web.beans.Paginator#getPreviousPage()
	 */
	public final int getPreviousPage() {
		return getPreviousPageInternal();
	}

	/**
	 * @return the next page.
	 */
	public int getNextPageInternal() {
		return currentPage + 1;
	}

	/**
	 * @see org.esupportail.commons.web.beans.Paginator#getNextPage()
	 */
	public final int getNextPage() {
		return getNextPageInternal();
	}

	/**
	 * @return the first page number.
	 */
	protected int getFirstPageNumberInternal() {
		return 0; 
	}

	/**
	 * @see org.esupportail.commons.web.beans.Paginator#getFirstPageNumber()
	 */
	public final int getFirstPageNumber() {
		return getFirstPageNumberInternal(); 
	}

	/**
	 * @return the last page number.
	 */
	protected int getLastPageNumberInternal() {
		return (getTotalItemsCountInternal() - 1) / getPageSize(); 
	}

	/**
	 * @see org.esupportail.commons.web.beans.Paginator#getLastPageNumber()
	 */
	public final int getLastPageNumber() {
		return getLastPageNumberInternal(); 
	}

	/**
	 * @return the first visible item number.
	 */
	protected int getFirstVisibleNumberInternal() {
		return currentPage * getPageSize(); 
	}

	/**
	 * @see org.esupportail.commons.web.beans.Paginator#getFirstVisibleNumber()
	 */
	public final int getFirstVisibleNumber() {
		return getFirstVisibleNumberInternal(); 
	}

	/**
	 * @return the last visible item number.
	 */
	protected int getLastVisibleNumberInternal() {
		return Math.min(getFirstVisibleNumberInternal() + getPageSize() - 1, getTotalItemsCountInternal() - 1);
	}

	/**
	 * @see org.esupportail.commons.web.beans.Paginator#getLastVisibleNumber()
	 */
	public final int getLastVisibleNumber() {
		return getLastVisibleNumberInternal();
	}

	/**
	 * Set the current page (internal, do not force reload).
	 * @param currentPage 
	 */
	protected void setCurrentPageInternal(final int currentPage) {
		this.currentPage = currentPage;
	}

	/**
	 * @see org.esupportail.commons.web.beans.Paginator#setCurrentPage(int)
	 */
	public final void setCurrentPage(final int currentPage) {
		if (currentPage < 0) {
			setCurrentPageInternal(0);
		} else {
			setCurrentPageInternal(currentPage);
		}
		forceReload();
	}

	/**
	 * @param visibleItems the visibleItems to set
	 */
	protected void setVisibleItems(final List<E> visibleItems) {
		this.visibleItems = visibleItems;
	}

	/**
	 * @return the visible items.
	 */
	protected List<E> getVisibleItemsInternal() {
		return visibleItems;
	}

	/**
	 * @see org.esupportail.commons.web.beans.Paginator#getVisibleItems()
	 */
	public final List<E> getVisibleItems() {
		return getVisibleItemsInternal();
	}

	/**
	 * @return the near pages.
	 */
	public List<Integer> getNearPagesInternal() {
		List<Integer> result = new ArrayList<Integer>();
		if (getLastPageNumber() <= getMaxNearPages()) {
			int first = getFirstPageNumber();
			int last = getLastPageNumber();
			for (int i = first; i <= last; i++) {
				result.add(i);
			}
		}
		return result;
	}

	/**
	 * @see org.esupportail.commons.web.beans.Paginator#getNearPages()
	 */
	public final List<Integer> getNearPages() {
		return getNearPagesInternal();
	}

	/**
	 * @return the number of visible items.
	 */
	protected int getVisibleItemsCountInternal() {
		return visibleItems.size();
	}

	/**
	 * @see org.esupportail.commons.web.beans.Paginator#getVisibleItemsCount()
	 */
	public final int getVisibleItemsCount() {
		return getVisibleItemsCountInternal();
	}

	/**
	 * @return the total items count.
	 */
	protected int getTotalItemsCountInternal() {
		return totalItemsCount;
	}

	/**
	 * @see org.esupportail.commons.web.beans.Paginator#getTotalItemsCount()
	 */
	public final int getTotalItemsCount() {
		return getTotalItemsCountInternal();
	}

	/**
	 * @param totalItemsCount the totalItemsCount to set
	 */
	protected void setTotalItemsCount(final int totalItemsCount) {
		this.totalItemsCount = totalItemsCount;
	}

	
	/** 
	 * @see org.esupportail.commons.web.beans.Paginator#getPageSizeItems()
	 */
	public List<String> getPageSizeItems() {
		return pageSizeItems;
	}

	/**
	 * Set the page size (internal).
	 * @param pageSize 
	 */
	protected void setPageSizeInternal(final int pageSize) {
		this.pageSize = pageSize;
	}

	/**
	 * @see org.esupportail.commons.web.beans.Paginator#setPageSize(int)
	 */
	public final void setPageSize(final int pageSize) {
		if (this.getPageSize() != pageSize) {
			setPageSizeInternal(pageSize);
			setCurrentPage(0);
		}
	}

	/**
	 * @see org.esupportail.commons.web.beans.Paginator#getLoadTime()
	 */
	public Timestamp getLoadTime() {
		return loadTime;
	}

	/**
	 * Update the load time.
	 */
	protected void updateLoadTime() {
		this.loadTime = new Timestamp(System.currentTimeMillis());
	}

	/**
	 * @param defaultPageSize the defaultPageSize to set
	 */
	public void setDefaultPageSize(final int defaultPageSize) {
		this.defaultPageSize = defaultPageSize;
	}

	/**
	 * @see org.esupportail.commons.web.beans.Paginator#getDefaultPageSize()
	 */
	public int getDefaultPageSize() {
		return DEFAULT_DEFAULT_PAGE_SIZE;
	}
	
	/** 
	 * @return the first pages number.
	 */
	protected List<Integer> getFirstPagesNumberInternal() {
		List<Integer> result = new ArrayList<Integer>();
		int intervalleFirst = getFirstPageNumber() + getMaxNearPages();
		if (getFirstPageNumber() <= getCurrentPage() && intervalleFirst > getCurrentPage() 
				&& intervalleFirst < getLastPageNumber()) {
			for (Integer i = 0; i < intervalleFirst; ++i) {
				result.add(i);
			}
		} 
		return result;
	}
	
	/** 
	 * @see org.esupportail.commons.web.beans.Paginator#getFirstPagesNumber()
	 */
	public final List<Integer> getFirstPagesNumber() {
		return getFirstPagesNumberInternal();
	}
	
	/** 
	 * @return the last pages number.
	 */
	protected List<Integer> getLastPagesNumberInternal() {
		List<Integer> result = new ArrayList<Integer>();
		int intervalleLast = getLastPageNumber() - getMaxNearPages();
		if (intervalleLast < getCurrentPage() && getLastPageNumber() >= getCurrentPage()
				&& intervalleLast > getFirstPageNumber()) {
			for (Integer i = getLastPageNumber(); i > intervalleLast; --i) {
				result.add(i);
			}
		}
		Collections.sort(result);
		return result;
	}
	
	/** 
	 * @see org.esupportail.commons.web.beans.Paginator#getLastPagesNumber()
	 */
	public final List<Integer> getLastPagesNumber() {
		return getLastPagesNumberInternal();
	}
	
	/** 
	 * @return the middle pages number
	 * @see org.esupportail.commons.web.beans.Paginator#getMiddlePagesNumber()
	 */
	protected List<Integer> getMiddlePagesNumberInternal() {
		List<Integer> result = new ArrayList<Integer>();
		int intervalleDbt = getCurrentPage() - getMaxNearPages() + 1;
		int intervalleFin = getCurrentPage() + getMaxNearPages() - 1;
		if (intervalleDbt > getFirstPageNumber() && intervalleFin < getLastPageNumber()) {
			for (Integer i = intervalleDbt; i <= intervalleFin; ++i) {
				result.add(i);
			}
		}
		return result;
	}
	
	/** 
	 * @see org.esupportail.commons.web.beans.Paginator#getMiddlePagesNumber()
	 */
	public final List<Integer> getMiddlePagesNumber() {
		return getMiddlePagesNumberInternal();
	}
	
	/**
	 * @return the maxNearPages
	 */
	protected int getMaxNearPagesInternal() {
		return maxNearPages;
	}

	/**
	 * @return the maxNearPages
	 */
	public final int getMaxNearPages() {
		return getMaxNearPagesInternal();
	}

	/**
	 * @param maxNearPages the maxNearPages to set
	 */
	protected void setMaxNearPagesInternal(final int maxNearPages) {
		this.maxNearPages = maxNearPages;
	}

	/**
	 * @param maxNearPages the maxNearPages to set
	 */
	public final void setMaxNearPages(final int maxNearPages) {
		setMaxNearPagesInternal(maxNearPages);
	}

} 

