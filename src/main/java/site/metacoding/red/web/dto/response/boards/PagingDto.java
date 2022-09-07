package site.metacoding.red.web.dto.response.boards;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PagingDto {
	private Integer blockCount; //  상수  한페이지에 페이지 넘수 개수(5) 1-5, 6-10
  	private Integer currentBlock; // 변수
	private Integer startPageNum;	// 변수 1 -> 6 -> 11
	private Integer lastPageNum;	// 변수 5 -> 10 -> 15	
	private Integer totalCount;	// = 23
	private Integer totalPage;	// 23(totalPage) / 10(한페이지당 개수) = 2 + 1
	private Integer currentPage;	// 0/ 1/ 2
	private boolean isLast;	// false/ false/ true
	private boolean isFirst;	// true/ false/ false
	// getter가 만들어지면 isLast() 이름으로 만들어짐. -> el에서는 last로 찾음
	
	public void makeBlockInfo() {
		this.blockCount = 5;
		
		this.currentBlock = currentPage / blockCount;
		this.startPageNum = 1 + blockCount*currentBlock;
		this.lastPageNum = 5 + blockCount*currentBlock;
			
		if(totalPage < lastPageNum) {
			this.lastPageNum = totalPage;
		}
	}
}
