package dto.response;

import java.util.List;

public class PaginationResponse {
	private List<?> data;
	private int totalPages;
	private int currentPage;

	public PaginationResponse(List<?> data, int totalPages, int currentPage) {
		super();
		this.data = data;
		this.totalPages = totalPages;
		this.currentPage = currentPage;
	}

	public List<?> getData() {
		return data;
	}

	public void setData(List<?> data) {
		this.data = data;
	}

	public int getTotalPages() {
		return totalPages;
	}

	public void setTotalPages(int totalPages) {
		this.totalPages = totalPages;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

}
