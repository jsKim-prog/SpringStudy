<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!-- jstl core태그 -->
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!-- jstl formatting태그 -->
<%@ include file="../includes/header.jsp"%>
<!--    <div id="page-wrapper"> 헤더에 있다.-->
<div class="row">
	<div class="col-lg-12">
		<h1 class="page-header">MBC 아카데미 게시판 리스트</h1>
	</div>
	<!-- /.col-lg-12 -->
</div>
<!-- /.row -->
<div class="row">
	<div class="col-lg-12">
		<div class="panel panel-default">
			<div class="panel-heading">
				Board Controller List
				<button id="regBtn" type="button" class="btn btn-primary btn-xs pull-right">새 게시물 등록</button>
			</div><!-- /.panel-heading -->
			<div class="panel-body">
				<table width="100%"
					class="table table-striped table-bordered table-hover"
					id="dataTables-example">
					<thead>
						<tr>
							<th>번호</th>
							<th>제목</th>
							<th>작성자</th>
							<th>작성일</th>
							<th>수정일</th>
						</tr>
					</thead>
					<c:forEach items="${list}" var="boardlist">
						<!-- 객체를 반복적으로 돌린다.(list객체) -->
						<tr><!--1행 추가  -->
							<td><c:out value="${ boardlist.bno }" /></td><!--1열   -->
							<td><a href='/board/get?bno=<c:out value="${boardlist.bno}"/>'><c:out value="${ boardlist.title }" /></a></td><!--2열   -->
							<td><c:out value="${ boardlist.writer }" /></td><!--3열  -->
							<td><fmt:formatDate pattern="yyyy-MM-dd" value="${ boardlist.regdate }" /></td><!--4열   -->
							<td><fmt:formatDate pattern="yyyy-MM-dd"  value="${ boardlist.updateDate }" /></td><!--5열   -->
						</tr>
					</c:forEach>

				</table>
				
				<!-- Modal 요즘 트렌드는 alert  대신에 modal!!-->
				<div class="modal fade" id="myModal" tabindex="-1" role="dialog"
					aria-labelledby="myModalLabel" aria-hidden="true">
					<div class="modal-dialog">
						<div class="modal-content">
							<div class="modal-header">
								<button type="button" class="close" data-dismiss="modal"
									aria-hidden="true">&times;</button>
								<h4 class="modal-title" id="myModalLabel">알립니다.</h4>
							</div>
							<div class="modal-body">처리가 완료되었습니다.</div>
							<div class="modal-footer">
								<button type="button" class="btn btn-default"
									data-dismiss="modal">Close</button>
								<button type="button" class="btn btn-primary">Save changes</button>
							</div>
						</div>
						<!-- /.modal-content -->
					</div>
					<!-- /.modal-dialog -->
				</div>
				<!-- /.modal -->
				
			</div>
			<!-- /.panel-body -->
		</div>
		<!-- /.panel -->
	</div>
	<!-- /.col-lg-12 -->
</div>
<!-- /.row -->
<script type="text/javascript">
	/* 자바스크립트 코드임을 명시 */
	$(document).ready(function() { /* 문서의 준비단계에서의 함수(기능) */
				var result = '<c:out value="${result}"/>'; /* controller에서 FlashAttribute를 통해 1회용으로 넘어오는 값 */
				/*  console.log(result);
				alert(result);  */
				checkModal(result); /* 아래쪽의 function 실행 */	
				history.replaceState({}, null, null); /*뒤로가기 버튼 동작에 대한 값 -> history 초기화 ->**안됨*/				
				function checkModal(result) { /*bno받음  */					
					if (result === '' || history.state) { /* result 값이 null이거나 history.state가 true면 */
						return;
					}
					
					if (parseInt(result) > 0) {
						$(".modal-body").html("게시글" + parseInt(result) + " 번이 등록되었습니다.");
						/* modal-body(class)의 값을 변경하는 코드 */
					}
					$("#myModal").modal("show"); /* id=myModal 변경된 모달창을 띄움 */
				}
				
				$("#regBtn").on("click", function() { /* 18행의 id="regBtn" 클립 동작(기능) */
					self.location = "/board/register"; /* 현재 문서를 /board/register 로 이동 */
				});
			});
</script>

<%@ include file="../includes/footer.jsp"%>