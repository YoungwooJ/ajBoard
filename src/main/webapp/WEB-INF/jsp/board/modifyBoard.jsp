<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>AJ</title>
    <script src="https://cdn.ckeditor.com/ckeditor5/39.0.1/classic/ckeditor.js"></script>

    <!-- Bootstrap CDN -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM" crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.2/dist/umd/popper.min.js" integrity="sha384-IQsoLXl5PILFhosVNubq5LC7Qb9DXgDA9i+tQ8Zj3iwWAwPtgFTxbJ8NT4GN1R8p" crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.min.js" integrity="sha384-cVKIPhGWiC2Al4u+LWgxfKTRIcfu0JTxR+EQDz/bgldoEyl4H0zUF0QKbrJ0EcQF" crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.2/dist/umd/popper.min.js" integrity="sha384-IQsoLXl5PILFhosVNubq5LC7Qb9DXgDA9i+tQ8Zj3iwWAwPtgFTxbJ8NT4GN1R8p" crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.min.js" integrity="sha384-cVKIPhGWiC2Al4u+LWgxfKTRIcfu0JTxR+EQDz/bgldoEyl4H0zUF0QKbrJ0EcQF" crossorigin="anonymous"></script>

    <!--jQuery-->
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.3/jquery.min.js"></script>
    <!-- Bootstrap Templates-->
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="../css/bootstrap.css">
    <link rel="stylesheet" href="../font/bootstrap-icons.css">
    <link rel="stylesheet" href="../themes/prism-okaidia.css">
    <link rel="stylesheet" href="../css/custom.min.css">
    <!-- Global Site Tag (gtag.js) - Google Analytics -->
    <script async src="https://www.googletagmanager.com/gtag/js?id=UA-23019901-1"></script>
    <script>
        window.dataLayer = window.dataLayer || [];
        function gtag(){dataLayer.push(arguments);}
        gtag('js', new Date());

        gtag('config', 'UA-23019901-1');
    </script>
    <!-- 자바스크립트로 유효성 확인 -->
    <script>
        $(document).ready(function() { // 페이지가 로드되고 나면 실행
            // 수정 버튼 클릭시
            $('#modifyBtn').click(function(){
                let title = $('#title').val();
                if(title === ''){
                    $('#msg').text('제목을 입력해주세요.');
                    $('#title').focus();
                    return false;
                }
            });
        });
    </script>
    <style>
        .ck.ck-editor {
            width: 100%;
            max-width: 800px;
            margin: 0 auto;
        }

        .ck-editor__editable {
            height: 400px;
        }
        th {
            text-align: center;
        }
        table {
            border: 1px #BDBDBD solid;
            font-size: .9em;
            box-shadow: 0 2px 5px #BDBDBD;
            width: 100%;
            border-collapse: collapse;
            border-radius: 20px;
            overflow: hidden;
        }
        a {
            text-decoration: none;
        }
        .box:hover {
            outline: none !important;
            border-color: #747474;
            box-shadow: 0 0 10px #747474;
        }
        .container {
            display-inline : center;
        }
    </style>
</head>
<body>
    <%--Header--%>
    <c:import url="../../inc/header.jsp"></c:import>
    <br>
    <div class="container">
        <h3>게시물 수정</h3>
        <br>
        <div style="color:red;" id="msg">
            ${msg}
        </div>
        <%--게시물--%>
        <form method="post" action="${pageContext.request.contextPath}/board/modifyBoard">
            <table class="table table-bordered w-auto">
                <tr>
                    <td>번호</td>
                    <td>
                        ${board.BOARDNO}
                        <input type="hidden" name="boardNo" value="${board.BOARDNO}">
                    </td>
                </tr>
                <tr>
                    <td>작성자</td>
                    <td>
                        ${board.WRITER}
                    </td>
                </tr>
                <tr>
                    <td>제목</td>
                    <td>
                        <input class="box form-control w-100" type="text" id="title" name="title" value="${board.TITLE}">
                    </td>
                </tr>
                <tr>
                    <td>내용</td>
                    <td>
                        <textarea rows="20" cols="50" class="box" id="content" name="content">${board.CONTENT}</textarea>
                    </td>
                </tr>
                <tr>
                    <td>작성일자</td>
                    <td>
                        ${board.CREATEDATE}
                    </td>
                </tr>
                <tr>
                    <td>수정일자</td>
                    <td>
                        ${board.UPDATEDATE}
                    </td>
                </tr>
            </table>
            <c:forEach var="f" items="${files}">
                <div>
                    <a href="${pageContext.request.contextPath}/boardFile/getBoardFileOne?boardNo=${board.BOARDNO}&boardFileNo=${f.boardFileNo}">
                        <img alt="첨부파일" src="${pageContext.request.contextPath}/boardFile-upload/${f.fileName}" width="300px" height="150px">
                        <input type="hidden" id="boardFileNo" name="boardFileNo" value="${f.boardFileNo}">
                    </a>
                    <a type="button" class="btn btn-danger" id="deleteBtn" href="${pageContext.request.contextPath}/boardFile/removeBoardFile?boardNo=${board.BOARDNO}&boardFileNo=${f.boardFileNo}">삭제</a>
                    <%--<button type="button" data-no="${f.boardFileNo}" class="removeBtn">삭제</button>--%>
                </div>
            </c:forEach>
            <button style="float: right; margin-right: 550px;" class="btn btn-warning" id="modifyBtn" type="submit">게시물 수정</button>
            <a style="float: right;" type="button" class="btn btn-dark" href="${pageContext.request.contextPath}/boardFile/addBoardFile?boardNo=${board.BOARDNO}">파일 추가</a>
        </form>
    </div>
</body>
    <script>
        ClassicEditor
            .create(document.querySelector('#content'))
            .then(editor => {
                console.log(editor);
            })
            .catch(error => {
                console.error(error);
            });
    </script>
    <!-- 자바스크립트로 유효성 확인 -->
    <script>
        $(document).ready(function(){ // 페이지가 로드되고 나면 실행
            // 게시글 파일 삭제 버튼 클릭시 확인 알람
            $('#deleteBtn').click(function(){
                if(!confirm("파일을 삭제하시겠습니까?")){
                    return false;
                } else {
                    $('#deleteBtn').submit();
                }
            });
        });
    </script>
    <script>
        $(document).ready(function (){
            // 게시글 파일 삭제
            $(document).on('click', '.removeBtn', function(){
                $.ajax({
                    url : '${pageContext.request.contextPath}/boardFile/removeBoardFileAJAX'
                    , type : 'GET'
                    , data : {boardFileNo : $('#this').attr('data-no')}
                    , success : function(data){
                        alert(data);

                    }
                    , error : function (error){
                        alert("삭제 실패하였습니다.");
                    }
                })
            });
        });
    </script>
</html>