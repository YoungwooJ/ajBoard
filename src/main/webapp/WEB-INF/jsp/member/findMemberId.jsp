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

            $('#name').focus();

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
                    $('.gender').focus();
                }
            });

            // 성별 유효성 검사
            $('.gender').blur(function(){
                if($("input[name=gender]:radio:checked").length < 1){
                    $('#msg').text('성별을 선택해주세요.');
                    $('.gender').focus();
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
                    $('#findIdBtn').focus();
                    allCk =true;
                }
            });

            // 회원 가입 버튼
            $('#findIdBtn').click(function(){
                if(allCk == false){
                    $('#msg').text('빈 칸 없이 작성해주세요.');
                    $('#id').focus();
                    return false;
                }
                $('#findIdForm').submit();
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
        <h2>ID 찾기</h2>
        <br>
        <div id="msg" style="color:red;">
            ${msg}
        </div>
        <form id="findIdForm" method="post" action="${pageContext.request.contextPath}/member/findMemberId">
            <div>
                이름
            </div>
            <div>
                <input class="box form-control w-25" type="text" id="name" name="name" placeholder="가입시 등록한 이름을 입력하세요.">
            </div>
            <div>
                생년월일
            </div>
            <div>
                <input class="box form-control w-25" type="date" id="birth" name="birth" placeholder="가입시 등록한 생년월일을 입력하세요.">
            </div>
            <div>
                성별
            </div>
            <div>
                <input class="box" type="radio" class="gender" name="gender" value="M"> 남자
                <input class="box" type="radio" class="gender" name="gender" value="F"> 여자
            </div>
            <div>
                핸드폰번호
            </div>
            <div>
                <input class="box form-control w-25" type="text" id="phone" name="phone" placeholder="ex) 010-1234-1234">
            </div>
            <br>
            <button id="findIdBtn" class="btn btn-success" type="button">OK</button>
        </form>
    </div>
</body>
</html>