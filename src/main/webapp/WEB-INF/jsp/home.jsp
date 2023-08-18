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
        <h1>Home입니다.</h1>
        <br>
        <c:choose>
            <c:when test="${loginMember ne null}">
                <a type="button" href="${pageContext.request.contextPath}/member/memberOne">회원 정보</a>
                <a type="button" href="${pageContext.request.contextPath}/member/logout">로그아웃</a>
                <br>
                <a type="button" href="${pageContext.request.contextPath}/board/getBoardList">게시판 바로가기</a>
            </c:when>
            <c:otherwise>
                <a type="button" href="${pageContext.request.contextPath}/member/loginMember">로그인</a>
            </c:otherwise>
        </c:choose>
    </body>
</html>