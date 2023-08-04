<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title></title>
</head>
<body>
    <div class="container">
        <h3>게시판 상세보기</h3>
        <br>
        <%--게시물--%>
        <table border="1">
            <tr>
                <td>번호</td>
                <td>${board.boardNo}</td>
            </tr>
            <tr>
                <td>제목</td>
                <td>${board.boardTitle}</td>
            </tr>
            <tr>
                <td>제목</td>
                <td>${board.boardContent}</td>
            </tr>
            <tr>
                <td>작성자</td>
                <td>${board.writer}</td>
            </tr>
            <tr>
                <td>작성일</td>
                <td>${board.createdate}</td>
            </tr>
            <tr>
                <td>수정일</td>
                <td>${board.updatedate}</td>
            </tr>
        </table>
        <a href="${pageContext.request.contextPath}/board/modifyBoard">게시물 수정</a>
        <a type="button" href="${pageContext.request.contextPath}/board/removeBoard">게시물 삭제</a>
        <%--페이징--%>
    </div>
</body>
</html>