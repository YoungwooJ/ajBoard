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
    <h1>게시판입니다.</h1>
    <div class="container">
        <h3>게시판</h3>
        <br>
        <%--보기, 검색창--%>
        <form method="get" action="${pageContext.request.contextPath}/board/getBoardList">
            <select name="category">
                <option value="title"selected="selected">제목</option>
                <option value="content">내용</option>
                <option value="writer">작성자</option>
            </select>
            <input type="text" name="search" value="" placeholder="검색바를 이용해보세요.">
            <button type="submit">검색</button>
        </form>

        <%--게시물--%>
        <table border="1">
            <tr>
                <th>번호</th>
                <th>제목</th>
                <th>작성자</th>
                <th>조회수</th>
                <th>작성일</th>
                <th>수정일</th>
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
        <a href="${pageContext.request.contextPath}/board/addBoard">게시물 입력</a>
        <%--페이징--%>
        <div style="text-align:center;">
            <a href="${pageContext.request.contextPath}/board/getBoardList?currentPage=${startPage}&category=${category}&search=${search}">처음</a>
            <c:if test="${currentPage > 1}">
                <a href="${pageContext.request.contextPath}/board/getBoardList?currentPage=${currentPage-1}&category=${category}&search=${search}">이전</a>
            </c:if>
            <c:choose>
                <c:when test="${endPage > 10}">
                    <c:forEach begin="${blockStartNum}" end="${blockLastNum}" step="1" varStatus="status">
                        <a href="${pageContext.request.contextPath}/board/getBoardList?currentPage=${status.current}&category=${category}&search=${search}">${status.current}</a>
                    </c:forEach>
                </c:when>
                <c:otherwise>
                    <c:forEach begin="${startPage}" end="${endPage}" step="1" varStatus="status">
                        <a href="${pageContext.request.contextPath}/board/getBoardList?currentPage=${status.current}&category=${category}&search=${search}">${status.current}</a>
                    </c:forEach>
                </c:otherwise>
            </c:choose>
            <c:if test="${currentPage < endPage}">
                <a href="${pageContext.request.contextPath}/board/getBoardList?currentPage=${currentPage+1}&category=${category}&search=${search}">다음</a>
            </c:if>
            <a href="${pageContext.request.contextPath}/board/getBoardList?currentPage=${endPage}&category=${category}&search=${search}">마지막</a>
        </div>
    </div>
</body>
</html>