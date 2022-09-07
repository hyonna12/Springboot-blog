package site.metacoding.red.web;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import lombok.RequiredArgsConstructor;
import site.metacoding.red.domain.boards.Boards;
import site.metacoding.red.domain.boards.BoardsDao;
import site.metacoding.red.domain.users.Users;
import site.metacoding.red.web.dto.request.boards.WriteDto;
import site.metacoding.red.web.dto.response.boards.MainDto;
import site.metacoding.red.web.dto.response.boards.PagingDto;

@RequiredArgsConstructor
@Controller
public class BoardsController {
	
	private final HttpSession session;
	// session에 접근해야 로그인 했는지 확인할 수 있으니까 session 필요
	// 롬복으로 생성자 주입받기 편할려고 final로 작성
	// session을 DI, dependency injection 생성자 주입 해야함
	private final BoardsDao boardsDao;
	// BoardsDao DI
	
	@PostMapping("/boards/{id}/delete")
	public String deleteBoards(@PathVariable Integer id) {	// pk니까 pathvariable로 받기/pk아닌건 쿼리스트링으로
		// 영속화하는건 select해서 있는지 확인해보는거
		// 무조건 delete하는것보다 영속화해서 조건에 따라서 delete를 하는게 유리
		// 트랜잭션때문에 하나 안되면 계속 대기해야하기 때문에
		// 먼저 영속화해야함
		Boards boardsPS = boardsDao.findById(id);
		Users principal = (Users) session.getAttribute("principal");
		
		// 비정상 요청 체크
		if(boardsPS == null) {	// if는 비정상 로직을 타게해서 걸러내는 필터 역할을 하는게 좋다. /영속화가 안되면 쫓아내는 코드, 절대 null이 나올수없게
			return "redirect:/boards/"+id; 	// 삭제버튼 클릭했을 때 없으면 상세보기로 리턴
		}
		
		// 인증 체크
		if(principal == null) {
			return "redirect:/loginForm";
		}
		
		// 권한 체크 (세션 principal.getId()와 boardsPS의 userId를 비교) / 게시글 쓴 user의 id와 현재 id를 비교
		if(principal.getId() != boardsPS.getUsersId()) {
			return "redirect:/boards/"+id;
		}
		
		boardsDao.delete(id);
		return "redirect:/";
		
	}
	
	@PostMapping("/boards")
	public String writeBoard(WriteDto writeDto) {	// 메서드 안에 들어갈 dto 만들어야함/title, content 두개만 들어갈 dto
		// 1번 세션에 접근해서 세션값을 확인한다. 그때 Users로 다운캐스팅하고 키값은 principal로 한다.
		Users principal = (Users) session.getAttribute("principal");
		
		// 2번 principal null인지 확인하고 null이면 loginForm 리다이랙션해준다.
		if(principal == null) {
			return "redirect:/loginForm";
		}
		
		// 3번 BoardsDao에 접근해서 insert 메서드를 호출한다.
		boardsDao.insert(writeDto.toEntity(principal.getId()));
		// 조건 : dto를 entity로 변환해서 인수로 담아준다.
		// 조건 : entity에는 세션의 principal에 getId가 필요하다.
	
		// boards 테이블에 insert요청들어옴
		// writeDto 는 글쓰기할때 필요한 값을 받음
		// 두개 값을 받아서 insert하는데 session값으로 인증됐는지 먼저 확인
		// dao에서 insert할건데 사용자로부터 받은 dto로 entity로 boards 클래스로 insert할거고 이때 필요한건 usersId값
		// writeDto로 작성한 내용을 저장하려면 boardsDao에 넣어야하니까 di해야함 -> 위에 final로 적어줌
		return "redirect:/";
				
		// 본 코드
//		Boards boards = new Boards();
//		boards.setTitle(writeDto.getTitle());
//		boards.setContent(writeDto.getContent());
//		boards.setUsersId(principal.getId());
		// 값을 직접 다 넣어줌
		// toentity() 메서드를 만들어놓고 entity로 변환하는 게 편함
	}
	
	// http://localhost:8000/		// main은 /, page의 디폴트 값을 0으로 해줘야함
	// http://localhost:8000/?page=0
	@GetMapping({"/","/boards"})	// 두개 넣기
	public String getBoardList(Model model, Integer page) {	// pk가 아니면 다 쿼리스트링으로 받기 / 0->0, 1->10, 2->20 페이지 받아서 startNum만들기
		if(page == null) page = 0;
		int startNum = page * 3;	// 1. 수정함
		
		List<MainDto> boardsList = boardsDao.findAll(startNum);	// 전체 데이터
		PagingDto paging = boardsDao.paging(page);
		
		paging.makeBlockInfo();
		
		model.addAttribute("boardsList", boardsList);
		model.addAttribute("paging", paging);
		
		return "boards/main";
	}
	
	@GetMapping("/boards/{id}")
	public String getBoardDetail(@PathVariable Integer id, Model model) {
		model.addAttribute("boards", boardsDao.findById(id));
		return "boards/detail";
	}
	
	@GetMapping("/boards/writeForm")
	public String writeForm() {
		// 인증 코드
		Users principal = (Users) session.getAttribute("principal");
		// 타입을 오브젝트 타입으로 받았기 때문에 꺼낼때는 Users로 다운캐스팅해서 받아야함 
		if(principal==null) {
			return "redirect:/loginForm";
		}
		// 본 코드
		return "boards/writeForm";
		// 로그인이 됐으면 글쓰기 페이지로, 로그인이 안됐으면 로그인 페이지로 돌아감
	}// 컨트롤러 요청하면 model에 저장하는게 아니라 바로 들고가니까 cv패턴
	
//	@PostMapping("/boards/{id}/delete")
//	@PostMapping("/boards/{id}/update")
}
