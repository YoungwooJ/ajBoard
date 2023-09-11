<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>AJ</title>

    <!-- Bootstrap CDN -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM" crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.2/dist/umd/popper.min.js" integrity="sha384-IQsoLXl5PILFhosVNubq5LC7Qb9DXgDA9i+tQ8Zj3iwWAwPtgFTxbJ8NT4GN1R8p" crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.min.js" integrity="sha384-cVKIPhGWiC2Al4u+LWgxfKTRIcfu0JTxR+EQDz/bgldoEyl4H0zUF0QKbrJ0EcQF" crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.2/dist/umd/popper.min.js" integrity="sha384-IQsoLXl5PILFhosVNubq5LC7Qb9DXgDA9i+tQ8Zj3iwWAwPtgFTxbJ8NT4GN1R8p" crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.min.js" integrity="sha384-cVKIPhGWiC2Al4u+LWgxfKTRIcfu0JTxR+EQDz/bgldoEyl4H0zUF0QKbrJ0EcQF" crossorigin="anonymous"></script>

    <!--jQuery-->
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.3/jquery.min.js"></script>
    <!-- Bootstrap Templates-->
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="../css/bootstrap.css">
    <link rel="stylesheet" href="../font/bootstrap-icons.css">
    <link rel="stylesheet" href="../themes/prism-okaidia.css">
    <link rel="stylesheet" href="../css/custom.min.css">
    <!-- Global Site Tag (gtag.js) - Google Analytics -->
    <script async src="https://www.googletagmanager.com/gtag/js?id=UA-23019901-1"></script>
    <script>
        window.dataLayer = window.dataLayer || [];
        function gtag(){dataLayer.push(arguments);}
        gtag('js', new Date());

        gtag('config', 'UA-23019901-1');
    </script>

    <!-- 자바스크립트로 유효성 확인 -->
    <script>
        $(document).ready(function (){
            let allCk = false;

            $('#oldPassword').focus();

            // 비밀번호 유효성 검사
            $('#oldPassword').blur(function(){
                if($('#oldPassword').val() == ''){
                    $('#msg').text('현재 비밀번호를 입력해주세요.');
                    $('#oldPassword').focus();
                } else {
                    $('#msg').text('');
                    $('#newPassword').focus();
                }
            });

            // 비밀번호 유효성 검사
            $('#newPassword').blur(function(){
                if($('#newPassword').val() == ''){
                    $('#msg').text('변경할 비밀번호를 입력해주세요.');
                    $('#newPassword').focus();
                } else {
                    $('#msg').text('');
                    $('#newPasswordConfirm').focus();
                }
            });

            // 비밀번호 유효성 검사
            $('#newPasswordConfirm').blur(function(){
                if($('#newPasswordConfirm').val() == ''){
                    $('#msg').text('확인용 비밀번호를 입력해주세요.');
                    $('#newPasswordConfirm').focus();
                } else {
                    $('#msg').text('');
                    $('#changePwBtn').focus();
                    allCk =true;
                }
            });

            // 회원 가입 버튼
            $('#changePwBtn').click(function(){
                if(allCk == false){
                    $('#msg').text('빈 칸 없이 작성해주세요.');
                    $('#oldPassword').focus();
                    return false;
                }
                $('#changePwForm').submit();
            });
        });
    </script>
    <style>
        th {
            text-align: center;
        }
        table {
            border: 1px #BDBDBD solid;
            font-size: .9em;
            box-shadow: 0 2px 5px #BDBDBD;
            width: 100%;
            border-collapse: collapse;
            border-radius: 20px;
            overflow: hidden;
        }
        a {
            text-decoration: none;
        }
        .box:hover {
            outline: none !important;
            border-color: #747474;
            box-shadow: 0 0 10px #747474;
        }
        .container {
            display-inline : center;
        }
    </style>
</head>
<body>
    <%--Header--%>
    <c:import url="../../inc/header.jsp"></c:import>
    <br>
    <div class="container">
        <h2>비밀번호 변경</h2>
        <br>
        <div id="msg" style="color:red;">
            ${msg}
        </div>
        <form id="changePwForm" method="post" action="${pageContext.request.contextPath}/member/modifyMemberPw">
            <table class="table table-bordered w-auto">
                <tr>
                    <td>현재 비밀번호</td>
                    <td>
                        <input class="box form-control" type="password" id="oldPassword" name="oldPassword" value="">
                    </td>
                </tr>
                <tr>
                    <td>변경할 비밀번호</td>
                    <td>
                        <input class="box form-control" type="password" id="newPassword" name="newPassword" value="">
                    </td>
                </tr>
                <tr>
                    <td>변경할 비밀번호 확인</td>
                    <td>
                        <input class="box form-control" type="password" id="newPasswordConfirm" name="newPasswordConfirm" value="">
                    </td>
                </tr>
            </table>
            <button class="btn btn-success" id="changePwBtn" type="submit">비밀번호 변경</button>
        </form>
    </div>
</body>
</html>