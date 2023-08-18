<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title></title>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    <script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.1/jquery.min.js"></script>
    <script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/moment.js/2.24.0/moment.min.js"></script>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">

    <script src="https://cdnjs.cloudflare.com/ajax/libs/sweetalert/1.1.3/sweetalert.min.js"></script>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/sweetalert/1.1.3/sweetalert.min.css" />
</head>
<body>
    <h2>비밀번호 찾기</h2>
    <div style="color:red;">입력된 정보로 임시 비밀번호가 전송됩니다.</div>
    <div id="checkMsg" style="color: red"></div>
    <%--<form method="post" action="${pageContext.request.contextPath}/member/findMemberPw">--%>
        <div>
            이메일
            <input type="text" id="email" name="email" placeholder="가입시 등록한 이메일을 입력하세요.">
        </div>
        <div>
            아이디
            <input type="text" id="id" name="id" placeholder="가입시 등록한 아이디를 입력하세요.">
        </div>
        <button type="button" id="checkEmail">OK</button>
    <%--</form>--%>
</body>
<script>
    $("#checkEmail").click(function () {
        let email = $('#email').val();
        let id = $('#id').val();

        $.ajax({
            url : "${pageContext.request.contextPath}/member/getEmailCheck",
            type : "GET",
            data : {
                "email" : email,
                "id" : id
            },
            success : function (res) {
                if(res['check']) {
                    swal("발송 완료!", "입력하신 이메일로 임시비밀번호가 발송되었습니다.", "success")
                    $.ajax({
                        url : "${pageContext.request.contextPath}/member/sendEmail",
                        type : "POST",
                        data : {
                            "email" : email,
                            "id" : id
                        },
                        success : function () {
                            window.location = "${pageContext.request.contextPath}/member/loginMember";
                        },
                        error : function (error) {
                            alert("이메일 전송 실패");
                        }
                    });
                    $('#checkMsg').html('<p style="color:darkblue"></p>');
                } else {
                    $('#checkMsg').html('<p style="color:red">일치하는 정보가 없습니다.</p>');
                }
            },
            error : function (error){
                alert("이메일 체크 에러");
            }
        });
    });
</script>
</html>