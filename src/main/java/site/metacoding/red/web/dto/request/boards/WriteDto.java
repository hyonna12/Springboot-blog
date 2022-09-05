package site.metacoding.red.web.dto.request.boards;

import java.sql.Timestamp;

import lombok.Getter;
import lombok.Setter;
import site.metacoding.red.domain.boards.Boards;

@Setter
@Getter
public class WriteDto {
	private String title;
	private String content;
	
	// dto가 entity로 변하는 메서드
	public Boards toEntity(Integer usersId) {
		// 생성자로 넣어줌
		Boards boards = new Boards(this.title, this.content, usersId);
		// setter로 넣어줌
//		boards.setTitle(this.title);
//		boards.setContent(this.content);
//		boards.setUsersId(usersId);
		return boards;
	}
	// setter로 값을 넣는것과 생성자로 넣는것과 같은 코드
	// 생성자로 넣을 때
}
