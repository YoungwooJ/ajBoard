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
<h2>회원가입</h2>
<form method="post" action="${pageContext.request.contextPath}/member/addMember">
    <table border="1">
        <tr>
            <td>아이디</td>
            <td>
                <input type="text" name="id" value="">
                <button type="button">중복확인</button>
            </td>
        </tr>
        <tr>
            <td>비밀번호</td>
            <td>
                <input type="password" name="password" value="">
            </td>
        </tr>
        <tr>
            <td>이름</td>
            <td>
                <input type="text" name="name" value="">
            </td>
        </tr>
        <tr>
            <td>생년월일</td>
            <td>
                <input type="date" name="birth" value="">
            </td>
        </tr>
        <tr>
            <td>성별</td>
            <td>
                <input type="radio" name="gender" value="M">남
                <input type="radio" name="gender" value="F">여
            </td>
        </tr>
        <tr>
            <td>핸드폰</td>
            <td>
                <input type="text" name="phone" value="" placeholder="ex) 010-1234-1234">
            </td>
        </tr>
        <tr>
            <td>이메일 주소</td>
            <td>
                <input type="text" name="email" value="">
                @
                <select name="address">
                    <option selected="selected">선택하세요.</option>
                    <option value="naver.com">naver.com</option>
                    <option value="daum.net">daum.net</option>
                    <option value="kakao.com">kakao.com</option>
                    <option value="gmail.com">gmail.com</option>
                    <option value="">직접 입력</option>
                </select>
            </td>
        </tr>
    </table>
    <button type="submit">회원가입</button>
</form>
</body>
</html>