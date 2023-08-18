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
    <h3>게시물 입력</h3>
    <br>
    <%--게시물--%>
    <form enctype="multipart/form-data" method="post" action="${pageContext.request.contextPath}/board/addBoard">
        <table border="1">
            <tr>
                <td>제목</td>
                <td>
                    <input type="text" name="title" value="">
                </td>
            </tr>
            <tr>
                <td>작성자</td>
                <td>
                    <input type="text" name="writer" value="${loginMember.id}" readonly="readonly">
                </td>
            </tr>
            <tr>
                <td>내용</td>
                <td>
                    <textarea rows="20" cols="50" name="content"></textarea>
                </td>
            </tr>
            <tr>
                <td>파일 업로드</td>
                <td>
                    <input type="file" name="files" multiple>
                </td>
            </tr>
        </table>
        <button type="submit">게시물 입력</button>
    </form>
</div>
</body>
</html>