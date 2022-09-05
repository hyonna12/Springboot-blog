package site.metacoding.red.web;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import lombok.RequiredArgsConstructor;
import site.metacoding.red.domain.boards.BoardsDao;
import site.metacoding.red.domain.users.Users;
import site.metacoding.red.web.dto.request.boards.WriteDto;
import site.metacoding.red.web.dto.response.boards.MainDto;

@RequiredArgsConstructor
@Controller
public class BoardsController {
	
	private final HttpSession session;
	// session에 접근해야 로그인 했는지 확인할 수 있으니까 session 필요
	// 롬복으로 생성자 주입받기 편할려고 final로 작성
	// session을 DI, dependency injection 생성자 주입 해야함
	private final BoardsDao boardsDao;
	// BoardsDao DI
	
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
	
	@GetMapping({"/","/boards"})	// 두개 넣기
	public String getBoardList(Model model) {
		List<MainDto> boardsList = boardsDao.findAll();	// 전체 데이터
		model.addAttribute("boardsList", boardsList);
		return "boards/main";
	}
	
	@GetMapping("/boards/{id}")
	public String getBoardList(@PathVariable Integer id) {
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
