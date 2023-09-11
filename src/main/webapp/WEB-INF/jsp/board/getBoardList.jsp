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
        <h3>게시판</h3>
        <br>
        <div style="color:red;" id="msg">
            ${msg}
        </div>
        <%--보기, 검색창--%>
        <form style="float: right" class="d-flex" method="get" action="${pageContext.request.contextPath}/board/getBoardList">
            <select name="category">
                <option value="title" selected="selected">제목</option>
                <option value="content">내용</option>
                <option value="writer">작성자</option>
            </select>
            <input style="width: 200px; height: 40px;" class="form-control me-sm-2" class="box" type="text" name="search" value="" placeholder="검색바를 이용해보세요.">
            <button class="btn btn-secondary my-2 my-sm-0" type="submit">검색</button>
        </form>

        <%--게시물--%>
        <table class="table table-bordered text-center">
            <tr>
                <th class="bg-dark text-white" style="width:80px; height:25px;">번호</th>
                <th class="bg-dark text-white w-auto">제목</th>
                <th class="bg-dark text-white" style="width:100px; height:25px;">작성자</th>
                <th class="bg-dark text-white" style="width:80px; height:25px;">조회수</th>
                <th class="bg-dark text-white" style="width:200px; height:25px;">작성일</th>
                <th class="bg-dark text-white" style="width:200px; height:25px;">수정일</th>
            </tr>
            <c:forEach var="b" items="${boardList}" varStatus="status">
                <tr>
                    <td>${b.BOARDNO}</td>
                    <td><a href="${pageContext.request.contextPath}/board/getBoardOne?boardNo=${b.BOARDNO}">${b.TITLE}</a></td>
                    <td>${b.WRITER}</td>
                    <td>${b.VIEWS}</td>
                    <td>${b.CREATEDATE}</td>
                    <td>${b.UPDATEDATE}</td>
                </tr>
            </c:forEach>
        </table>
        <a style="float: right;" type="button" class="btn btn-dark" href="${pageContext.request.contextPath}/board/addBoard">게시물 입력</a>
        <%--페이징--%>
        <ul class="pagination justify-content-center">
            <li class="page-item">
                <a type="button" class="page-link text-dark" href="${pageContext.request.contextPath}/board/getBoardList?currentPage=${startPage}&category=${category}&search=${search}">처음</a>
            </li>
            <c:if test="${currentPage > 1}">
                <li class="page-item">
                    <a type="button" class="page-link text-dark" href="${pageContext.request.contextPath}/board/getBoardList?currentPage=${currentPage-1}&category=${category}&search=${search}">이전</a>
                </li>
            </c:if>
            <c:choose>
                <c:when test="${endPage > 10}">
                    <c:forEach begin="${blockStartNum}" end="${blockLastNum}" step="1" varStatus="status">
                        <li class="page-item">
                            <a type="button" class="page-link text-dark" href="${pageContext.request.contextPath}/board/getBoardList?currentPage=${status.current}&category=${category}&search=${search}">${status.current}</a>
                        </li>
                    </c:forEach>
                </c:when>
                <c:otherwise>
                    <c:forEach begin="${startPage}" end="${endPage}" step="1" varStatus="status">
                        <li class="page-item">
                            <a type="button" class="page-link text-dark" href="${pageContext.request.contextPath}/board/getBoardList?currentPage=${status.current}&category=${category}&search=${search}">${status.current}</a>
                        </li>
                    </c:forEach>
                </c:otherwise>
            </c:choose>
            <c:if test="${currentPage < endPage}">
                <li class="page-item">
                    <a type="button" class="page-link text-dark" href="${pageContext.request.contextPath}/board/getBoardList?currentPage=${currentPage+1}&category=${category}&search=${search}">다음</a>
                </li>
            </c:if>
            <li class="page-item">
                <a type="button" class="page-link text-dark" href="${pageContext.request.contextPath}/board/getBoardList?currentPage=${endPage}&category=${category}&search=${search}">마지막</a>
            </li>
        </ul>
    </div>
</body>
<script src="../_vendor/bootstrap/dist/js/bootstrap.bundle.min.js"></script>
<script src="../_vendor/prismjs/prism.js" data-manual></script>
<script src="../_assets/js/custom.js"></script>
</html>