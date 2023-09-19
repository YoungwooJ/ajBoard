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
        /*$(document).ready(function(){ // 페이지가 로드되고 나면 실행
            let idCk = false;

            // ID 중복체크
            $(document).on('click', '#idCheckBtn', function(){
                if($('#id').val() == ''){
                    $('#msg').text('ID를 입력해주세요.');
                    $('#id').focus();
                } else {
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
                                $('#id').val('');
                                $('#id').focus();
                            }
                        }
                        , error : function (error){
                            alert("ID 중복체크 실패");
                        }
                    })
                }
            });

            // 수정 버튼 클릭시
            $('#modifyBtn').click(function(){
                let id = $('#id').val();
                if(id !== '' && idCk == false){
                    $('#msg').text('아이디 중복 체크를 해주세요.');
                    $('#id').focus();
                    return false;
                }
            });
        });*/
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
        <h2>회원정보 수정</h2>
        <br>
        <div style="color:red;" id="msg">
            ${msg}
        </div>
        <form method="post" action="${pageContext.request.contextPath}/member/modifyMember">
            <table class="table table-bordered w-auto">
                <tr>
                    <td>아이디</td>
                    <td colspan="3">
                        ${member.id}
                        <input type="hidden" name="oldId" value="${member.id}">
                    </td>
                    <%--<td>
                        새로운 아이디
                    </td>
                    <td>
                        <input class="box form-control" type="text" id="id" name="id" value="">
                        <button class="btn btn-info" id="idCheckBtn" type="button">중복확인</button>
                    </td>--%>
                </tr>
                <tr>
                    <td>이름</td>
                    <td>
                        ${member.name}
                        <input type="hidden" name="oldName" value="${member.name}">
                    </td>
                    <td>
                        새로운 이름
                    </td>
                    <td>
                        <input class="box form-control" type="text" name="name" value="">
                    </td>
                </tr>
                <tr>
                    <td>생년월일</td>
                    <td>
                        ${member.birth}
                        <input type="hidden" name="oldBirth" value="${member.birth}">
                    </td>
                    <td>
                        새로운 생년월일
                    </td>
                    <td>
                        <input class="box form-control" type="date" name="birth" value="">
                    </td>
                </tr>
                <tr>
                    <td>성별</td>
                    <td>
                        ${member.gender}
                        <input type="hidden" name="oldGender" value="${member.gender}">
                    </td>
                    <td>
                        변경할 성별
                    </td>
                    <td>
                        <input class="box" type="radio" name="gender" value="M">남
                        <input class="box" type="radio" name="gender" value="F">여
                    </td>
                </tr>
                <tr>
                    <td>핸드폰</td>
                    <td>
                        ${member.phone}
                        <input type="hidden" name="oldPhone" value="${member.phone}">
                    </td>
                    <td>
                        변경할 핸드폰
                    </td>
                    <td>
                        <input class="box form-control" type="text" name="phone" value="" placeholder="ex) 010-1234-1234">
                    </td>
                </tr>
                <tr>
                    <td>이메일 주소</td>
                    <td>
                        ${member.email}
                        <input type="hidden" name="oldEmail" value="${member.email}">
                    </td>
                    <td>
                        변경할 이메일 주소
                    </td>
                    <td>
                        <input class="box form-control" type="text" name="email" value="">
                        @
                        <select class="form-control" name="address" >
                            <option selected="selected">선택하세요.</option>
                            <option value="naver.com">naver.com</option>
                            <option value="daum.net">daum.net</option>
                            <option value="kakao.com">kakao.com</option>
                            <option value="gmail.com">gmail.com</option>
                            <option value="noAddress">직접 입력</option>
                        </select>
                    </td>
                </tr>
                <tr>
                    <td>비밀번호 확인</td>
                    <td colspan="3">
                        <input class="box form-control" type="password" name="password" value="">
                    </td>
                </tr>
            </table>
            <button class="btn btn-success" id="modifyBtn" type="submit">회원정보 수정</button>
        </form>
    </div>
</body>
</html>