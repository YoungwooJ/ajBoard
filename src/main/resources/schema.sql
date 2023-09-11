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
    , level int NOT NULL
    , createdate datetime
    , updatedate datetime
    , remv_flag varchar(3) NOT NULL
);

create table board (
    board_no int PRIMARY KEY IDENTITY NOT NULL
    , title varchar(100)
    , content varchar(1000)
    , writer varchar(50)
    , views int
    , createdate datetime
    , updatedate datetime
    , remv_flag varchar(3) NOT NULL
);

create table board_file (
    board_file_no int PRIMARY KEY IDENTITY NOT NULL
    , board_no int, FOREIGN KEY (board_no) REFERENCES board(board_no) ON UPDATE CASCADE
    , origin_name varchar(100)
    , file_name varchar(100)
    , file_type varchar(100)
    , file_size int
    , createdate datetime
    , remv_flag varchar(3) NOT NULL
);

create table comment (
    comment_no int PRIMARY KEY IDENTITY NOT NULL
    , board_no int, FOREIGN KEY (board_no) REFERENCES board(board_no) ON UPDATE CASCADE
    , writer varchar(50)
    , comment varchar(300)
    , c_depth int
    , c_group int
    , createdate datetime
    , remv_flag varchar(3) NOT NULL
);

create table likes (
    likes_no int PRIMARY KEY IDENTITY NOT NULL
    , id varchar(50) NOT NULL
    , board_no int NOT NULL
    , comment_no int NOT NULL
    , createdate datetime
);

INSERT INTO member (id, password, name, birth, gender, phone, email, level, createdate, updatedate, remv_flag) VALUES ('abc', 1234, '조영우', '2023-08-04', '남자', '010-1234-1234', 'cyw960714@naver.com', 1, current_date, current_date, 'N');

INSERT INTO board (board_no, title, content, writer, views, createdate, updatedate, remv_flag) VALUES (1, '제목', '안녕하세요.', 'abc', 0, current_date, current_date, 'N');
INSERT INTO board (board_no, title, content, writer, views, createdate, updatedate, remv_flag) VALUES (2, '제목2', '안녕2하세요.', 'abc', 0, current_date, current_date, 'N');
INSERT INTO board (board_no, title, content, writer, views, createdate, updatedate, remv_flag) VALUES (3, '제목3', '안녕3하세요.', 'abc', 0, current_date, current_date, 'N');
INSERT INTO board (board_no, title, content, writer, views, createdate, updatedate, remv_flag) VALUES (4, '제목4', '안녕4하세요.', 'abc', 0, current_date, current_date, 'N');
INSERT INTO board (board_no, title, content, writer, views, createdate, updatedate, remv_flag) VALUES (5, '제목5', '안녕5하세요.', 'abc', 0, current_date, current_date, 'N');
INSERT INTO board (board_no, title, content, writer, views, createdate, updatedate, remv_flag) VALUES (6, '제목6', '안녕6하세요.', 'abc', 0, current_date, current_date, 'N');
INSERT INTO board (board_no, title, content, writer, views, createdate, updatedate, remv_flag) VALUES (7, '제목7', '안녕7하세요.', 'abc', 0, current_date, current_date, 'N');
INSERT INTO board (board_no, title, content, writer, views, createdate, updatedate, remv_flag) VALUES (8, '제목8', '안녕8하세요.', 'abc', 0, current_date, current_date, 'N');
INSERT INTO board (board_no, title, content, writer, views, createdate, updatedate, remv_flag) VALUES (9, '제목9', '안녕9하세요.', 'abc', 0, current_date, current_date, 'N');
INSERT INTO board (board_no, title, content, writer, views, createdate, updatedate, remv_flag) VALUES (10, '제목10', '안녕10하세요.', 'abcd', 0, current_date, current_date, 'N');
INSERT INTO board (board_no, title, content, writer, views, createdate, updatedate, remv_flag) VALUES (11, '제목11', '안녕11하세요.', 'abcd', 0, current_date, current_date, 'N');
INSERT INTO board (board_no, title, content, writer, views, createdate, updatedate, remv_flag) VALUES (12, '제목12', '안녕12하세요.', 'abcd', 0, current_date, current_date, 'N');
INSERT INTO board (board_no, title, content, writer, views, createdate, updatedate, remv_flag) VALUES (13, '제목13', '안녕13하세요.', 'abcd', 0, current_date, current_date, 'N');
INSERT INTO board (board_no, title, content, writer, views, createdate, updatedate, remv_flag) VALUES (14, '제목14', '안녕14하세요.', 'abcd', 0, current_date, current_date, 'N');
INSERT INTO board (board_no, title, content, writer, views, createdate, updatedate, remv_flag) VALUES (15, '제목15', '안녕15하세요.', 'abcd', 0, current_date, current_date, 'N');
INSERT INTO board (board_no, title, content, writer, views, createdate, updatedate, remv_flag) VALUES (16, '제목16', '안녕16하세요.', 'abcd', 0, current_date, current_date, 'N');
INSERT INTO board (board_no, title, content, writer, views, createdate, updatedate, remv_flag) VALUES (17, '제목17', '안녕17하세요.', 'abcd', 0, current_date, current_date, 'N');
INSERT INTO board (board_no, title, content, writer, views, createdate, updatedate, remv_flag) VALUES (18, '제목18', '안녕18하세요.', 'abcd', 0, current_date, current_date, 'N');
INSERT INTO board (board_no, title, content, writer, views, createdate, updatedate, remv_flag) VALUES (19, '제목19', '안녕19하세요.', 'abcd', 0, current_date, current_date, 'N');
INSERT INTO board (board_no, title, content, writer, views, createdate, updatedate, remv_flag) VALUES (20, '제목20', '안녕20하세요.', 'abcd', 0, current_date, current_date, 'N');

INSERT INTO comment (comment_no, board_no, writer, comment, c_depth, c_group, createdate, remv_flag) VALUES (1, 1, 'abc', '안녕하세요.', 0, 0, current_date, 'N');
INSERT INTO comment (comment_no, board_no, writer, comment, c_depth, c_group, createdate, remv_flag) VALUES (2, 1, 'abc', '댓글입니다.', 0, 0, current_date, 'N');

INSERT INTO comment (comment_no, board_no, writer, comment, c_depth, c_group, createdate, remv_flag) VALUES (3, 1, 'abcd', '대댓글입니다.', 1, 1, current_date, 'N');
INSERT INTO comment (comment_no, board_no, writer, comment, c_depth, c_group, createdate, remv_flag) VALUES (4, 1, 'abcd', '안녕하세요.', 1, 1, current_date, 'N');

INSERT INTO likes (likes_no, id, board_no, comment_no, createdate) VALUES (1, 'abc', 1, 0, current_date);
INSERT INTO likes (likes_no, id, board_no, comment_no, createdate) VALUES (2, 'abcd', 1, 0, current_date);
