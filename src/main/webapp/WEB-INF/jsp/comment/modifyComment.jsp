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
    <h2>댓글 수정</h2>
    <form method="post" action="${pageContext.request.contextPath}/comment/modifyComment">
        댓글 : <input type="text" name="comment" value="${comment.comment}">
        <input type="hidden" name="commentNo" value="${comment.commentNo}">
        <input type="hidden" name="boardNo" value="${comment.boardNo}">
        <button type="submit">수정</button>
    </form>
</body>
</html>