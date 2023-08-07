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
    <h1>게시판입니다.</h1>
    <div class="container">
        <h3>게시판</h3>
        <br>
        <%--보기, 검색창--%>
        <form></form>
        <%--게시물--%>
        <table border="1">
            <tr>
                <th>번호</th>
                <th>제목</th>
                <th>작성자</th>
                <th>작성일</th>
                <th>수정일</th>
            </tr>
           <%-- <c:forEach var="board" items="${dtoList}" varStatus="status">
                <c:out value="${board.boardNo}"/>
                <tr>
                    <td>${board.boardNo}</td>
                    <td><a href="${pageContext.request.contextPath}/board/getBoardOne?boardNo=${board.boardNo}">${board.title}</a></td>
                    <td>${board.writer}</td>
                    <td>${board.createdate}</td>
                    <td>${board.updatedate}</td>
                </tr>
            </c:forEach>--%>
            <c:forEach var="b" items="${boardList}" varStatus="status">
                <tr>
                    <td>${b.BOARDNO}</td>
                    <td><a href="${pageContext.request.contextPath}/board/getBoardOne?boardNo=${b.BOARDNO}">${b.TITLE}</a></td>
                    <td>${b.WRITER}</td>
                    <td>${b.CREATEDATE}</td>
                    <td>${b.UPDATEDATE}</td>
                </tr>
            </c:forEach>
        </table>
        <a href="${pageContext.request.contextPath}/board/addBoard">게시물 입력</a>
        <%--페이징--%>

    </div>
</body>
</html>