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
    <h2>로그인</h2>
    <form method="post" action="${pageContext.request.contextPath}/member/loginMember">
        <table border="1">
            <tr>
                <td>아이디</td>
                <td>
                    <input type="text" name="id" value="">
                </td>
            </tr>
            <tr>
                <td>비밀번호</td>
                <td>
                    <input type="password" name="password" value="">
                </td>
            </tr>
        </table>
        <button type="submit">로그인</button>
        <a type="button" href="${pageContext.request.contextPath}/member/addMember">회원가입</a>
        <a type="button" href="${pageContext.request.contextPath}/member/">ID/PW 찾기</a>
    </form>
</body>
</html>