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
    <h3>게시물 수정</h3>
    <br>
    <%--게시물--%>
    <form method="post" action="${pageContext.request.contextPath}/board/modifyBoard">
        <table border="1">
            <tr>
                <td>번호</td>
                <td>
                    <input type="text" name="boardNo" value="${board.BOARDNO}" readonly="readonly">
                </td>
            </tr>
            <tr>
                <td>작성자</td>
                <td>
                    <input type="text" name="writer" value="${board.WRITER}" readonly="readonly">
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
                    <input type="text" name="createdate" value="${board.CREATEDATE}" readonly="readonly">
                </td>
            </tr>
            <tr>
                <td>수정일자</td>
                <td>
                    <input type="text" name="updatedate" value="${board.UPDATEDATE}" readonly="readonly">
                </td>
            </tr>
        </table>
        <button type="submit">게시물 수정</button>
    </form>
</div>
</body>
</html>