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
<h2>비밀번호 변경</h2>
<form method="post" action="${pageContext.request.contextPath}/member/modifyMemberPw">
    <table border="1">
        <tr>
            <td>현재 비밀번호</td>
            <td>
                <input type="password" name="oldPassword" value="">
            </td>
        </tr>
        <tr>
            <td>변경할 비밀번호</td>
            <td>
                <input type="password" name="newPassword" value="">
            </td>
        </tr>
        <tr>
            <td>변경할 비밀번호 확인</td>
            <td>
                <input type="password" name="newPasswordConfirm" value="">
            </td>
        </tr>
    </table>
    <button type="submit">비밀번호 변경</button>
</form>
</body>
</html>