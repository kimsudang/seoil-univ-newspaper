package kr.seoil.controller;

import java.util.*;

import kr.seoil.vo.TempVO;

public class TempUserPageController {
	private int startPage;
	private int endPage;
	private boolean prev, next;
	
	private int messageTotalCount; 
	private int currentPageNumber; 
	private ArrayList<TempVO> messageList; 
	private int pageTotalCount; 
	private int messageCountPerPage;
	private int firstRow; //ù��
	private int endRow; //������ ��



	public TempUserPageController(ArrayList<TempVO> messageList, int messageTotalCount, 
			int currentPageNumber, int messageCountPerPage, 
			int startRow, int endRow) {
		this.messageList = messageList;
		//현재 보고있는 곳의 마지막으로 보여야하는 페이지
		this.setEndPage((int)(Math.ceil(currentPageNumber/10.0))*10);
		this.setStartPage(endPage - 9);

		this.messageTotalCount = messageTotalCount;
		this.currentPageNumber = currentPageNumber;
		this.messageCountPerPage = messageCountPerPage;
		this.setFirstRow(startRow);
		this.setEndRow(endRow);
		calculatePageTotalCount();
		
		if(calculatePageTotalCount() < this.getEndPage()) {
			this.setEndPage(calculatePageTotalCount());
		}
		
		this.setPrev(this.getStartPage() > 1);
		this.setNext(this.getEndPage() < calculatePageTotalCount());
	}
	
	
	
	//������ �� ����
	private int calculatePageTotalCount() {
		if (messageTotalCount == 0) {
			pageTotalCount = 0;
		} else {
			pageTotalCount = messageTotalCount / messageCountPerPage;
			if (messageTotalCount % messageCountPerPage > 0) {
				pageTotalCount++;
			}
		}
		return pageTotalCount;
	}

	public int getMessageTotalCount() {
		return messageTotalCount;
	}

	public int getCurrentPageNumber() {
		return currentPageNumber;
	}

	public ArrayList<TempVO> getMessageList() {
		return messageList;
	}

	public int getPageTotalCount() {
		return pageTotalCount;
	}

	public int getMessageCountPerPage() {
		return messageCountPerPage;
	}

	public boolean isEmpty() {
		return messageTotalCount == 0;
	}

	public int getFirstRow() {
		return firstRow;
	}

	public void setFirstRow(int firstRow) {
		this.firstRow = firstRow;
	}

	public int getEndRow() {
		return endRow;
	}

	public void setEndRow(int endRow) {
		this.endRow = endRow;
	}

	public void setMessageTotalCount(int messageTotalCount) {
		this.messageTotalCount = messageTotalCount;
	}


	public void setCurrentPageNumber(int currentPageNumber) {
		this.currentPageNumber = currentPageNumber;
	}


	public void setMessageList(ArrayList<TempVO> messageList) {
		this.messageList = messageList;
	}



	public void setPageTotalCount(int pageTotalCount) {
		this.pageTotalCount = pageTotalCount;
	}



	public void setMessageCountPerPage(int messageCountPerPage) {
		this.messageCountPerPage = messageCountPerPage;
	}

	public boolean isPrev() {
		return prev;
	}

	public void setPrev(boolean prev) {
		this.prev = prev;
	}

	public boolean isNext() {
		return next;
	}

	public void setNext(boolean next) {
		this.next = next;
	}

	public int getStartPage() {
		return startPage;
	}

	public void setStartPage(int startPage) {
		this.startPage = startPage;
	}

	public int getEndPage() {
		return endPage;
	}

	public void setEndPage(int endPage) {
		this.endPage = endPage;
	}

}


