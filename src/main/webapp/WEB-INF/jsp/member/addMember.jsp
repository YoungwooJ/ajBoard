<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title></title>

    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.1/jquery.min.js"></script>
    <!-- 자바스크립트로 유효성 확인 -->
    <script>
        $(document).ready(function (){
            let allCk = false;
            let idCk = false;

            $('#id').focus();

            // 아이디 유효성 검사
            $('#id').blur(function(){
                if($('#id').val() == ''){
                    $('#msg').text('ID를 입력해주세요.');
                    $('#id').focus();
                } else {
                    $('#msg').text('');
                    $('#id').val($(this).val());
                    $('#idCheckBtn').focus();
                    if(idCk == true) {
                        $('#password').focus();
                    }
                }
            });

            // 비밀번호 유효성 검사
            $('#password').blur(function(){
                if($('#password').val() == ''){
                    $('#msg').text('비밀번호를 입력해주세요.');
                    $('#password').focus();
                } else {
                    $('#msg').text('');
                    $('#name').focus();
                }
            });

            // 이름 유효성 검사
            $('#name').blur(function(){
                if($('#name').val() == ''){
                    $('#msg').text('이름을 입력해주세요.');
                    $('#name').focus();
                } else {
                    $('#msg').text('');
                    $('#birth').focus();
                }
            });

            // 생년월일 유효성 검사
            $('#birth').blur(function(){
                if($('#birth').val() == ''){
                    $('#msg').text('생년월일을 입력해주세요.');
                    $('#birth').focus();
                } else {
                    $('#msg').text('');
                    $('#gender').focus();
                }
            });

            // 성별 유효성 검사
            $('#gender').blur(function(){
                if($("input[name=gender]:radio:checked").length < 1){
                    $('#msg').text('성별을 선택해주세요.');
                    $('#gender').focus();
                } else {
                    $('#msg').text('');
                    $('#phone').focus();
                }
            });

            // 핸드폰 유효성 검사
            $('#phone').blur(function(){
                if($('#phone').val() == ''){
                    $('#msg').text('핸드폰 번호를 입력해주세요.');
                    $('#phone').focus();
                } else {
                    $('#msg').text('');
                    $('#email').focus();
                }
            });

            // 이메일 주소 유효성 검사
            $('#email').blur(function(){
                if($('#email').val() == ''){
                    $('#msg').text('이메일 주소를 입력해주세요.');
                    $('#email').focus();
                } else {
                    $('#msg').text('');
                    $('#address').focus();
                }
            });

            // 이메일 주소2 유효성 검사
            $('#address').blur(function(){
                if($('#address').val() == ''){
                    $('#msg').text('이메일 주소를 선택해주세요.');
                    $('#address').focus();
                } else {
                    $('#msg').text('');
                    $('#addBtn').focus();
                    allCk =true;
                }
            });

            // 회원 가입 버튼
            $('#addBtn').click(function(){
                if(allCk == false || idCk == false){
                    $('#msg').text('빈 칸 없이 작성하시고 아이디 중복 체크를 해주세요.');
                    $('#id').focus();
                    return false;
                }
                $('#addForm').submit();
            });

            // ID 중복체크
            $(document).on('click', '#idCheckBtn', function(){
                $.ajax({
                    url : '${pageContext.request.contextPath}/member/getIdCheck'
                    , type : 'GET'
                    , data : {"id" : $('#id').val()}
                    , success : function(data){
                        if(data == null || data == ""){
                            alert("사용가능한 ID입니다.");
                            $('#id').focus();
                            idCk = true;
                        } else if(data != null || data != "") {
                            alert("중복된 ID입니다.");
                            $('#id').focus();
                        }
                    }
                    , error : function (error){
                        alert("ID 중복체크 실패");
                    }
                })
            });
        });
    </script>
</head>
<body>
<h2>회원가입</h2>
<div style="color:red;" id="msg">
    ${msg}
</div>
<form method="post" id="addForm" action="${pageContext.request.contextPath}/member/addMember">
    <table border="1">
        <tr>
            <td>아이디</td>
            <td>
                <input type="text" id="id" name="id" value="">
                <button id="idCheckBtn" type="button">중복확인</button>
            </td>
        </tr>
        <tr>
            <td>비밀번호</td>
            <td>
                <input type="password" id="password" name="password" value="">
            </td>
        </tr>
        <tr>
            <td>이름</td>
            <td>
                <input type="text" id="name" name="name" value="">
            </td>
        </tr>
        <tr>
            <td>생년월일</td>
            <td>
                <input type="date" id="birth" name="birth" value="">
            </td>
        </tr>
        <tr>
            <td>성별</td>
            <td>
                <input type="radio" id="gender" name="gender" value="M">남
                <input type="radio" name="gender" value="F">여
            </td>
        </tr>
        <tr>
            <td>핸드폰</td>
            <td>
                <input type="text" id="phone" name="phone" value="" placeholder="ex) 010-1234-1234">
            </td>
        </tr>
        <tr>
            <td>이메일 주소</td>
            <td>
                <input type="text" id="email" name="email" value="">
                @
                <select id="address" name="address">
                    <option selected="selected" value="">선택하세요.</option>
                    <option value="naver.com">naver.com</option>
                    <option value="daum.net">daum.net</option>
                    <option value="kakao.com">kakao.com</option>
                    <option value="gmail.com">gmail.com</option>
                    <option value="noAddress">직접 입력</option>
                </select>
            </td>
        </tr>
    </table>
    <button id="addBtn" type="button">회원가입</button>
</form>
</body>
</html>