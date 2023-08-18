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
    <h2>아이디 찾기</h2>
    <div>
        등록된 회원 아이디는 <span style="color:red;">${memberId}</span>입니다.
    </div>
    <a href="${pageContext.request.contextPath}/member/loginMember">로그인하기</a>
</body>
</html>