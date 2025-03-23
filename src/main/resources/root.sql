create user book_ex identified by book_ex default tablespace users temporary tablespace temp;

grant connect, dba to book_ex;

select dbms_xdb.gethttpport() from dual;  -- 8080 포트 사용 중임을 확인 한다.

exec dbms_xdb.sethttpport(9090); -- 이클립스에서 지원되지 않는 명령어 -> sqlplus에서 함.