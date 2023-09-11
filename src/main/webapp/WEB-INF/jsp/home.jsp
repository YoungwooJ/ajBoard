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
        <c:import url="../inc/header.jsp"></c:import>
        <br>
        <div class="container">
            <br>
            <div>
                <img alt="AJ네트웍스" src="https://post-phinf.pstatic.net/20160520_73/1463710379990fQwjr_PNG/%B8%DE%C0%CE_copy.png?type=w800_q75"style="width: 1000px;">
            </div>
            <br>
            <div style="color:red;" id="msg">
                ${msg}
            </div>
            <c:choose>
                <c:when test="${loginMember ne null}">
                    <a class="btn btn-info" type="button" href="${pageContext.request.contextPath}/member/memberOne">회원 정보</a>
                    <a class="btn btn-warning" type="button" href="${pageContext.request.contextPath}/member/logout">로그아웃</a>
                    <a style="float: right; margin-right: 300px;" class="btn btn-dark" type="button" href="${pageContext.request.contextPath}/board/getBoardList">게시판 바로가기</a>
                </c:when>
                <c:otherwise>
                    <a class="btn btn-dark" type="button" href="${pageContext.request.contextPath}/member/loginMember">로그인</a>
                </c:otherwise>
            </c:choose>
        </div>
    </body>
</html>