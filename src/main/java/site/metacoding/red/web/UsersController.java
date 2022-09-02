package site.metacoding.red.web;


import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import lombok.RequiredArgsConstructor;
import site.metacoding.red.domain.users.Users;
import site.metacoding.red.domain.users.UsersDao;
import site.metacoding.red.web.dto.request.users.JoinDto;
import site.metacoding.red.web.dto.request.users.LoginDto;

@RequiredArgsConstructor
@Controller
public class UsersController {

	private final HttpSession session;	
	// 스프링이 서버 시작시에 ioC 컨테이너에 보관함.
	// ds에서 받아서 세션에 접근할 필요가 없음
	private final UsersDao usersDao;
	
	@GetMapping("/logout")// 로그아웃은 해당사용자의 키값을 다 날려버리는것
	public String logout() {
		session.invalidate();	// 세션 영역의 키값에 있는 데이터 다 날리는 메서드
		return "redirect:/";
	}
	
	@PostMapping("/login")	// 로그인만 예외로 select인데 post로 함
	public String login(LoginDto loginDto) {// 직접 username, password 변수들을 입력하지 않고 dto 만들어서 넣기
		Users usersPS = usersDao.login(loginDto);
		if(usersPS != null) {	// 인증됨
			session.setAttribute("principal", usersPS);	
			// 키값 principal(인증된 유저라는 의미), 세션에 principal 값이 있으면 로그인 된거
			return "redirect:/";	
			// 로그인하면 main 페이지로 가도록 설정
			// redirect해서 메서드 다시 때려야함
		}else {	// 인증안됨.
			return "redirect:/loginForm"; // 로그인 안되면 다시 로그인폼으로 돌아감
		}
	}
	@PostMapping("/join")	// 인증에 필요한 페이지는 앞에 테이블명을 안붙임
	public String join(JoinDto joinDto) {
		usersDao.insert(joinDto);
		return "redirect:/loginForm";		
		// 회원가입이 완료되면 로그인페이지로 가도록 설정
		// 이미 있는 로그인 폼 다시 때림 redirect
	}
	@GetMapping("/loginForm")
	public String loginForm() {
		return "users/loginForm";
	}
	
	@GetMapping("/joinForm")
	public String joinForm() {
		return "users/joinForm";
	}
}
