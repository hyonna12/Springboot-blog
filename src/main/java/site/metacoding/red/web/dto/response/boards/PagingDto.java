package site.metacoding.red.web.dto.response.boards;

public class PagingDto {
	private Integer startNum;	// = 0/ 10/ 20
	private Integer totalCount;	// = 23
	private Integer totalPage;	// 23(totalPage) / 10(한페이지당 개수) = 2 + 1
	private Integer currentPage;	// 0/ 1/ 2
	private boolean isLast;	// false/ false/ true
	private boolean isFirst;	// true/ false/ false
}
