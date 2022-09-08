<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ include file="../layout/header.jsp"%>

<div class="container">
	<form action="/boards/${boards.id}/update" method="post">	<!-- 동사는 메서드로 구분, boards/write로 적지 않기 -->
		<div class="mb-3 mt-3">
			<input type="text" class="form-control"	 placeholder="Enter title" name="title" value="${boards.title}" required="required" maxlength="50">
		</div>
		<div class="mb-3">
			<textarea class="form-control" rows="8" name="content" required="required">${boards.content}</textarea>
		</div>
		<button type="submit" class="btn btn-primary">수정완료</button>
	</form><!-- 수정하기는 데이터베이스에서 해당게시글을 들고 뿌려야 하니까 mvc 패턴으로  -->
</div><!-- 주소로 id받고 title, content받아서 update -->

<%@ include file="../layout/footer.jsp"%>

