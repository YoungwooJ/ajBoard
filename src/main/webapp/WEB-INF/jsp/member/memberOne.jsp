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
    <h2>회원정보</h2>
    <table border="1">
        <tr>
            <td>아이디</td>
            <td>
                ${member.id}
            </td>
        </tr>
        <tr>
            <td>이름</td>
            <td>
                ${member.name}
            </td>
        </tr>
        <tr>
            <td>생년월일</td>
            <td>
                ${member.birth}
            </td>
        </tr>
        <tr>
            <td>성별</td>
            <td>
                ${member.gender}
            </td>
        </tr>
        <tr>
            <td>핸드폰</td>
            <td>
                ${member.phone}
            </td>
        </tr>
        <tr>
            <td>이메일 주소</td>
            <td>
                ${member.email}
            </td>
        </tr>
    </table>
    <a href="${pageContext.request.contextPath}/member/modifyMember">회원정보 수정</a>
    <a href="${pageContext.request.contextPath}/member/modifyMemberPw">비밀번호 변경</a>
    <a href="${pageContext.request.contextPath}/member/logout">로그아웃</a>
    <a href="${pageContext.request.contextPath}/member/removeMember">회원탈퇴</a>
</body>
</html>