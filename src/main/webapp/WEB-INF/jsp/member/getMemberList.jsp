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
        $(document).ready(function() { // 페이지가 로드되고 나면 실행
            // 회원 권한 변경 버튼 클릭시 확인 알람
            $('#modifyBtn').click(function () {
                if (!confirm("회원권한을 변경하시겠습니까?")) {
                    return false;
                } else {
                    $('#modifyBtn').submit();
                }
            });

            // 회원 강제 탈퇴 버튼 클릭시 확인 알람
            $('#removeBtn').click(function () {
                if (!confirm("정말로 회원을 강제탈퇴하시겠습니까?")) {
                    return false;
                } else {
                    $('#removeBtn').submit();
                }
            });
        });
    </script>
    <style>
        th,td {
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
        <h3>회원관리</h3>
        <br>
        <div id="msg" style="color:red;">
            ${msg}
        </div>
        <%--멤버 목록--%>
        <table class="table table-bordered text-center">
            <tr>
                <th>아이디</th>
                <th>이름</th>
                <th>생년월일</th>
                <th>성별</th>
                <th>핸드폰</th>
                <th>이메일</th>
                <th>등급</th>
                <th>회원가입일</th>
                <th>회원수정일</th>
                <th>활동 여부</th>
                <th>권한 변경</th>
                <th>강제 탈퇴</th>
            </tr>
            <c:forEach var="m" items="${list}">
                <tr>
                    <td>${m.id}</td>
                    <td>${m.name}</td>
                    <td>${m.birth}</td>
                    <td>${m.gender}</td>
                    <td>${m.phone}</td>
                    <td>${m.email}</td>
                    <td>
                        <c:choose>
                            <c:when test="${m.level eq 1}">
                                관리자
                            </c:when>
                            <c:otherwise>
                                일반
                            </c:otherwise>
                        </c:choose>
                    </td>
                    <td>${m.createdate}</td>
                    <td>${m.updatedate}</td>
                    <td>
                        <c:choose>
                            <c:when test="${m.remvFlag eq 'N'}">
                                활동
                            </c:when>
                            <c:otherwise>
                                탈퇴
                            </c:otherwise>
                        </c:choose>
                    </td>
                    <td>
                        <form method="post" action="${pageContext.request.contextPath}/member/modifyMemberGrade">
                            <input type="hidden" name="id" value="${m.id}">
                            <select name="level" class="form-control-sm">
                                <c:choose>
                                    <c:when test="${m.level eq 1}">
                                        <option value="1" selected="selected">관리자</option>
                                        <option value="0">일반</option>
                                    </c:when>
                                    <c:otherwise>
                                        <option value="0" selected="selected">일반</option>
                                        <option value="1">관리자</option>
                                    </c:otherwise>
                                </c:choose>
                            </select>
                            <button type="submit" id="modifyBtn" class="btn btn-warning">수정</button>
                        </form>
                    </td>
                    <td>
                        <form method="post" action="${pageContext.request.contextPath}/member/removeMemberForced">
                            <input type="hidden" name="id" value="${m.id}">
                            <button type="submit" id="removeBtn" class="btn btn-danger">삭제</button>
                        </form>
                    </td>
                </tr>
            </c:forEach>
        </table>
</body>
</html>