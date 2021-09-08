package com.app.helper.pagination;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Pagination {
	@Getter
	@Setter
	@AllArgsConstructor
	private static class PaginationInfo {
		private Long page;
		private Long limit;
		private Long total;
	}

	private List<?> data;
	private PaginationInfo pagination;

	public Pagination(List<?> data, Long page, Long limit, Long totalElement) {
		super();
		this.data = data;
		long total = (long) Math.ceil((double) totalElement / limit);
		this.pagination = new PaginationInfo(page + 1, limit, total);
	}
}
