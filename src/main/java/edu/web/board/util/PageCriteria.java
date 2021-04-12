package edu.web.board.util;

// 브라우저에서 보여질 페이지 번호와
// 한 페이지에서 보여질 게시글의 개수를 저장하는 클래스
// -> paging 처리에 필요한 start 와 end 번호를 알수 있음
public class PageCriteria {
	private int page;
	private int numsPerPage; 
	
	public PageCriteria() {
		this.page = 1;
		this.numsPerPage = 5;
	}
	
	public PageCriteria(int page, int numsPerPage) {
		this.page = page;
		this.numsPerPage = numsPerPage;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getNumsPerPage() {
		return numsPerPage;
	}

	public void setNumsPerPage(int numsPerPage) {
		this.numsPerPage = numsPerPage;
	}
	
	public int getStart() {
		return (this.page - 1) * this.numsPerPage + 1;
	}
	
	public int getEnd() {
		return this.page * this.numsPerPage;
	}
	
	
}
