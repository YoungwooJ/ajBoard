<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title></title>

    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.1/jquery.min.js"></script>
</head>
<body>
<div class="container">
    <h3>게시물 수정</h3>
    <br>
    <%--게시물--%>
    <form method="post" action="${pageContext.request.contextPath}/board/modifyBoard">
        <table border="1">
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
                    <input type="text" name="title" value="${board.TITLE}">
                </td>
            </tr>
            <tr>
                <td>내용</td>
                <td>
                    <textarea rows="20" cols="50" name="content">${board.CONTENT}</textarea>
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
                <a type="button" href="${pageContext.request.contextPath}/boardFile/removeBoardFile?boardNo=${board.BOARDNO}&boardFileNo=${f.boardFileNo}">삭제</a>
                <%--<button type="button" data-no="${f.boardFileNo}" class="removeBtn">삭제</button>--%>
            </div>
        </c:forEach>
        <a type="button" href="${pageContext.request.contextPath}/boardFile/addBoardFile?boardNo=${board.BOARDNO}">파일 추가</a>
        <button type="submit">게시물 수정</button>
    </form>
</div>
</body>
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