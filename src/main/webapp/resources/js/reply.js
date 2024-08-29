/**
 * 댓글 ajax 처리용 javascript 파일
 */

console.log("댓글용 모듈 실행중....");
var replyService = (function() {
	function add(reply, callback, error) { //외부에서 replyService.add(객체, 콜백)을 전달하는 형태
		console.log("댓글 추가용 함수...");

		$.ajax({
			type: 'post', //@PostMapping
			url: '/replies/new', // http://localhost:80/replies/new
			data: JSON.stringify(reply), //json으로 받아서 객체로 넘김
			contentType: "application/json; charset=utf-8",
			success: function(result, status, xhr) { //위의 코드 성공시 함수
				//result : 결과
				//status : 200|500
				//xhr : xmlHTTPRequest 객체(servlet 에서 요청객체와 유사함)
				if (callback) { //callback==true면 아래 코드 실행
					callback(result);
				}//--if()
			},//--success/function()
			error: function(xhr, status, er) {
				if (error) {
					error(er);
				}//--if()
			}//--error/function()
		})//--ajax()
	} //--function add()


	function getList(param, callback, error) { //리스트 보여주기(페이징 추가)
		var bno = param.bno;
		var page = param.page || 1;

		$.getJSON("/replies/pages/" + bno + "/" + page + ".json",
			function(data) {
				if (callback) {
					 //callback(data) ; //댓글 목록만 가져오는 경우
					 callback(data.replyCnt, data.list); //댓글 개수와 목록 가져오는 경우
					 }
			}).fail(function(xhr, status, err) {
				if (error) { error(); }
			}); //--function(data)
	} //--function getList()

	function remove(rno, callback, error) { //댓글 삭제
		console.log("replyService.remove() 실행...");
		$.ajax({
			type: 'delete', //@DeleteMapping
			url: '/replies/' + rno,
			success: function(deleteResult, status, xhr) {
				if (callback) {
					callback(deleteResult);
				}
			},
			error: function(xhr, status, er) {
				if (error) {
					error(er);
				}
			}
		})//--$.ajax
	}//--function remove()

	function update(reply, callback, error) {//댓글 수정
		console.log("replyService.update() 실행...");
		console.log("RNO:" + reply.rno);
		$.ajax({
			type: 'put',
			url: '/replies/' + reply.rno,
			data: JSON.stringify(reply),
			contentType: "application/json; charset=utf-8",
			success: function(result, status, xhr) {
				if (callback) {
					callback(result);
				}
			},
			error: function(xhr, status, er) {
				if (error) {
					error(er);
				}
			}
		});//--$.ajax
	}//--function update()

	function get(rno, callback, error) { //댓글 조회
		console.log("replyService.get() 실행...");
		console.log("RNO:" + rno);
		$.get("/replies/" + rno + ".json", function(result) {
			if (callback) {
				callback(result);
			}
		}).fail(function(xhr, status, err) {
			if (error) {
				error();
			}
		});//--get()
	}//--function get()

	function displayTime(timeValue) { //시간출력
		var today = new Date();
		var gap = today.getTime() - timeValue;
		var dateObj = new Date(timeValue);
		var str = "";
		if (gap < (1000 * 60 * 60 * 24)) { //하루 차이보다 적다면
			var hh = dateObj.getHours();
			var mi = dateObj.getMinutes();
			var ss = dateObj.getSeconds();
			return [(hh > 9 ? '' : '0') + hh, ':', (mi > 9 ? '' : '0') + mi, ':',(ss > 9 ? '' : '0') + ss].join('');
		} else {
			var yy = dateObj.getFullYear();
			var mm = dateObj.getMonth() + 1; //getMonth는 0부터 시작한다!
			var dd = dateObj.getDate();
			return [yy, '/', (mm > 9 ? '' : '0') + mm, '/', (dd > 9 ? '' : '0') + dd].join('');
		}
	};//--function displayTime(timeValue)

	return {
		add: add,
		getList: getList,
		remove: remove,
		update: update,
		get: get,
		displayTime: displayTime
	};
})(); //변수생성