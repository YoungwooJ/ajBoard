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
    <h3>게시글 파일 추가 업로드</h3>
    <br>
    <form enctype="multipart/form-data" method="post" action="${pageContext.request.contextPath}/boardFile/addBoardFile?boardNo=${boardNo}">
        파일 : <input type="file" name="files" multiple>
        <button type="submit">추가 업로드</button>
    </form>
</body>
</html>