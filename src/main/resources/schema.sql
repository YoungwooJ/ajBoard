/**
  데이터 베이스 생성 스크립트 입력
 */
create table sample (
    id int identity ,
    USER_NAME varchar(20),
    RMK varchar(100),
    INST_DATE datetime
);

create table member (
    id varchar(50) PRIMARY KEY NOT NULL
    , password varchar(100) NOT NULL
    , name varchar(20) NOT NULL
    , birth DATE
    , gender varchar(3) NOT NULL
    , phone varchar(50) NOT NULL
    , email varchar(100) NOT NULL
);

create table board (
    board_no int PRIMARY KEY IDENTITY NOT NULL
    , title varchar(100)
    , content varchar(1000)
    , writer varchar(50)
    , createdate datetime
    , updatedate datetime
);

INSERT INTO member (id, password, name, birth, gender, phone, email) VALUES ('abc', 1234, 'abc', '1996-08-04', '남자', '010-1234-1234', 'abc@naver.com');

INSERT INTO board (board_no, title, content, writer, createdate, updatedate) VALUES (1, '제목', '안녕하세요.', 'abc', current_date, current_date);