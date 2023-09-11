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

    <script>
        $(document).ready(function () {
            let allCk = false;

            $('#email').focus();

            // 이메일 주소 유효성 검사
            $('#email').blur(function(){
                if($('#email').val() == ''){
                    $('#msg').text('이메일 주소를 입력해주세요.');
                    $('#email').focus();
                } else {
                    $('#msg').text('');
                    $('#id').focus();
                }
            });

            // 아이디 유효성 검사
            $('#id').blur(function(){
                if($('#id').val() == ''){
                    $('#msg').text('ID를 입력해주세요.');
                    $('#id').focus();
                } else {
                    $('#msg').text('');
                    $('#checkEmail').focus();
                    allCk =true;
                }
            });

            // 이메일 발송
            $("#checkEmail").click(function () {
                let email = $('#email').val();
                let id = $('#id').val();

                if(allCk == false) {
                    $('#msg').text('빈 칸 없이 작성해주세요.');
                    $('#email').focus();
                    return false;
                } else {
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
                                $('#msg').html('<p style="color:darkblue"></p>');
                            } else {
                                $('#msg').html('<p style="color:red">일치하는 정보가 없습니다.</p>');
                            }
                        },
                        error : function (error){
                            alert("이메일 체크 에러");
                        }
                    });
                }
            });
        });
    </script>

    <!--sweetalert-->
    <script src="https://cdnjs.cloudflare.com/ajax/libs/sweetalert/1.1.3/sweetalert.min.js"></script>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/sweetalert/1.1.3/sweetalert.min.css" />

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
        <h2>비밀번호 찾기</h2>
        <br>
        <div style="color:red;">입력된 정보로 임시 비밀번호가 전송됩니다.</div>
        <div id="msg" style="color: red"></div>
        <div>
            이메일
        </div>
        <div>
            <input class="box form-control w-25" type="text" id="email" name="email" placeholder="가입시 등록한 이메일을 입력하세요.">
        </div>
        <div>
            아이디
        </div>
        <div>
            <input class="box form-control w-25" type="text" id="id" name="id" placeholder="가입시 등록한 아이디를 입력하세요.">
        </div>
        <br>
        <button class="btn btn-success" type="button" id="checkEmail">OK</button>
    </div>
</body>
</html>