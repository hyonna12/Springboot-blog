package site.metacoding.red.util;

import site.metacoding.red.web.dto.response.boards.PagingDto;

public class PagingUtil {
	public static Integer getcurrentBlock(Integer currentBlock, Integer lastPageNum, Integer blockCount) {
		
		for(int i=0; ; i=i++) {
			if(lastPageNum==blockCount*i) {		// 마지막 페이지가 5면 1
				return currentBlock = 1+i;
			}
		}
	}
	// 몇번째 블락인지 -> 1~5페이지 사이
	
	private static Integer getBlockCount(Integer blockCount) {
		return blockCount=5;
	}
	
	private static Integer getStartPageNum(Integer lastPageNum, Integer startPageNum, Integer currentPage, Integer blockCount) {
		for(int i=0; ; i=i+5) {
			if(currentPage<lastPageNum+blockCount) {
				return startPageNum = 1+i;
			}
		}
	}
	// 페이지 시작하는 숫자 1->6->11
	
	private static Integer getLastPageNum() {
		return 1;
	}
	// 페이지 끝나는 숫자 5->10->15
	
	// 변수 세팅
	// 연산된 결과는 컨트롤러에서 
}
