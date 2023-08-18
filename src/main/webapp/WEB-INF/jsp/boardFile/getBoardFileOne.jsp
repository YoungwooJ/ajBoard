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
    <h3>게시글 파일 상세보기</h3>
    <br>
    <%--게시물 파일--%>
    <table border="1">
        <tr>
            <td rowspan="5">
                <img alt="첨부파일" src="${pageContext.request.contextPath}/boardFile-upload/${boardFileDTO.fileName}" width="300px" height="150px">
            </td>
            <td>원본 이름</td>
            <td>${boardFileDTO.originName}</td>
        </tr>
        <tr>
            <td>파일 이름</td>
            <td>${boardFileDTO.fileName}</td>
        </tr>
        <tr>
            <td>파일 타입</td>
            <td>${boardFileDTO.fileType}</td>
        </tr>
        <tr>
            <td>파일 크기</td>
            <td>${boardFileDTO.fileSize}</td>
        </tr>
        <tr>
            <td>작성일</td>
            <td>${boardFileDTO.createdate}</td>
        </tr>
    </table>
    <a type="button" href="${pageContext.request.contextPath}/boardFile/removeBoardFile?boardNo=${boardNo}&boardFileNo=${boardFileDTO.boardFileNo}">파일 삭제</a>
</div>
</body>
</html>