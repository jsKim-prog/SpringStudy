<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ include file="../includes/header.jsp"%>

<!--list.jsp에서 /board/register 경로 호출하면 폼 박스가 나옴  -->
<!-- 입력완료를 누르면 vo 객체가 만들어져서  /board/register 에 post 메서드가 실행-->
<div class="row">
	<div class="col-lg-12">
		<h1 class="page-heaher">게시판 글 등록 페이지</h1>
	</div>
	<!--.col-lg-12 end -->
</div>
<!-- .row end -->

<div class="row">
	<div class="col-lg-12">
		<div class="panel panel-default">
			<div class="panel-heading">Board Register</div><!-- .panel-heading end -->
			<div class="panel-body">
				<!-- form 박스 만들고 submit 처리 -->
				<form role="form" action="/board/register" method="post">
					<div class="form-group">
						<label>제목</label> <input class="form-control" name="title" />
					</div><!-- .form-group end -->
					<div class="form-group">
						<label>내용</label>
						<textarea class="form-control" rows="3" name="content"></textarea>
					</div><!-- .form-group end -->
					<div class="form-group">
						<label>작성자</label> <input class="form-control" name="writer" />
					</div><!-- .form-group end -->
					<button type="submit" class="btn btn-default">저장</button>
					<button type="reset" class="btn btn-default">초기화</button>
				</form><!-- form end -->
			</div><!-- .panel-body end -->
		</div><!-- .panel panel-default end -->
	</div><!--.col-lg-12 end -->
</div><!-- .row end -->