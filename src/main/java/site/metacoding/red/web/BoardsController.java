package site.metacoding.red.web;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import lombok.RequiredArgsConstructor;
import site.metacoding.red.domain.users.Users;

@RequiredArgsConstructor
@Controller
public class BoardsController {
	
	private final HttpSession session;
	// session에 접근해야 로그인 했는지 확인할 수 있으니까 session 필요
	// 롬복으로 생성자 주입받기 편할려고 final로 작성
	// session을 DI, dependency injection 생성자 주입 해야함
	@GetMapping({"/","/boards"})	// 두개 넣기
	public String getBoardList() {
		return "boards/main";
	}
	
	@GetMapping("/boards/{id}")
	public String getBoardList(@PathVariable Integer id) {
		return "boards/detail";
	}
	
	@GetMapping("/boards/writeForm")
	public String writeForm() {
		Users principal = (Users) session.getAttribute("principal");
		// 타입을 오브젝트 타입으로 받았기 때문에 꺼낼때는 Users로 다운캐스팅해서 받아야함 
		if(principal==null) {
			return "redirect:/loginForm";
		} else {
			return "boards/writeForm";
		}
		// 로그인이 됐으면 글쓰기 페이지로, 로그인이 안됐으면 로그인 페이지로 돌아감
	}
	
//	@PostMapping("/boards/{id}/delete")
//	@PostMapping("/boards/{id}/update")
}
