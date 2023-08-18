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
    <h2>ID 찾기</h2>
    <form method="post" action="${pageContext.request.contextPath}/member/findMemberId">
        <div>
            이름
            <input type="text" id="name" name="name" placeholder="가입시 등록한 이름을 입력하세요.">
        </div>
        <div>
            생년월일
            <input type="date" id="birth" name="birth" placeholder="가입시 등록한 생년월일을 입력하세요.">
        </div>
        <div>
            성별
            <input type="radio" name="gender" value="M"> 남자
            <input type="radio" name="gender" value="F"> 여자
        </div>
        <div>
            핸드폰번호
            <input type="text" id="phone" name="phone" placeholder="ex) 010-1234-1234">
        </div>
        <button type="submit">OK</button>
    </form>
</body>
</html>