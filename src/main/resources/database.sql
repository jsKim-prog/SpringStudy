--------------------------------------------- 게시판용
create sequence seq_board;

create table tbl_board (
  bno number(10,0),
  title varchar2(200) not null,
  content varchar2(2000) not null,
  writer varchar2(50) not null,
  regdate date default sysdate, 
  updatedate date default sysdate
);

alter table tbl_board add constraint pk_board 
primary key (bno);

select * from TBL_BOARD order by bno desc;

insert into TBL_BOARD (bno,title,content,writer)
		values (seq_board.nextval, '댓글용 제목', '댓글용 내용', 'kkw');

---------------------------------------------- 댓글용
create table tbl_reply (
	rno number(10,0),  -- 댓글 번호
	bno number(10,0),  -- fk(게시판번호)
 	reply varchar2(1000) not null, -- 댓글
 	replyer varchar2(50) not null, -- 댓글 작성자
	replyDate date default sysdate,
	updateDate date default sysdate );

create sequence seq_reply ; -- 댓글용 자동번호객체 추가

alter table tbl_reply add constraint pk_reply primary key (rno); 
-- pk를 rno로 지정(롤이름 : pk_reply)

alter table tbl_reply add constraint fk_reply_board foreign key (bno) references tbl_board (bno); 
-- tbl_reply의 bno(자)와 tbl_board의 bno(부)를 연결 (부모가 있어야 자식이 있다) 

select * from tbl_reply;

-- tbl_board 초기화 -> 더미데이터 -> 댓글 더미데이터
delete from TBL_BOARD; --더미데이터 삭제
drop sequence seq_board; --자동번호 객체 삭제

insert into TBL_BOARD (bno,title,content,writer)
		values (seq_board.nextval, '댓글용 제목', '댓글용 내용', 'kkw'); --11개 삽입

insert into tbl_reply (rno, bno, reply, replyer) values (seq_reply.nextval, 11, '댓글11', 'kkw'); 
insert into tbl_reply (rno, bno, reply, replyer) values (seq_reply.nextval, 10, '댓글10', 'kkw'); 
insert into tbl_reply (rno, bno, reply, replyer) values (seq_reply.nextval, 9, '댓글9', 'kkw'); 
insert into tbl_reply (rno, bno, reply, replyer) values (seq_reply.nextval, 8, '댓글8', 'kkw'); 
insert into tbl_reply (rno, bno, reply, replyer) values (seq_reply.nextval, 7, '댓글7', 'kkw'); 
insert into tbl_reply (rno, bno, reply, replyer) values (seq_reply.nextval, 6, '댓글6', 'kkw'); 

select rno, bno, reply, replyer, replyDate, updateDate from tbl_reply where bno = 10 order by rno asc;

