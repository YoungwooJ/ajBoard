<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title></title>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.1/jquery.min.js"></script>
</head>
<body>
    <div class="container">
        <h3>게시글 상세보기</h3>
        <br>
        <%--게시물--%>
        <table border="1">
            <tr>
                <td>번호</td>
                <td>${board.BOARDNO}</td>
            </tr>
            <tr>
                <td>제목</td>
                <td>${board.TITLE}</td>
            </tr>
            <tr>
                <td>내용</td>
                <td>${board.CONTENT}</td>
            </tr>
            <tr>
                <td>작성자</td>
                <td>${board.WRITER}</td>
            </tr>
            <tr>
                <td>작성일</td>
                <td>${board.CREATEDATE}</td>
            </tr>
            <tr>
                <td>수정일</td>
                <td>${board.UPDATEDATE}</td>
            </tr>
        </table>
        <c:forEach var="f" items="${files}">
            <a href="${pageContext.request.contextPath}/boardFile/fileDownload/${f.fileName}">
                파일 다운로드 : ${f.fileName}
            </a>
            <div>
                <img alt="첨부파일" src="${pageContext.request.contextPath}/boardFile-upload/${f.fileName}" width="300px" height="150px">
            </div>
        </c:forEach>
        <a href="${pageContext.request.contextPath}/board/modifyBoard?boardNo=${board.BOARDNO}">게시물 수정</a>
        <a type="button" href="${pageContext.request.contextPath}/board/removeBoard?boardNo=${board.BOARDNO}">게시물 삭제</a>

        <%--댓글--%>
        <br><br>
        <%--댓글 개수--%>
        <div>댓글 ${countComment}</div>
        <%--댓글 입력--%>
        <form method="post" action="${pageContext.request.contextPath}/comment/addComment">
            <div>
                ${loginMember.id}
                <input type="hidden" name="writer" value="${loginMember.id}">
                <input type="hidden" name="boardNo" value="${board.BOARDNO}">
                <input type="text" name="comment" value="" placeholder="댓글을 입력하세요.">
                <button type="submit">입력</button>
            </div>
        </form>
        <%--댓글 목록--%>
        <c:forEach var="c" items="${commentList}">
            <div>
                <div style="font-size: small;">${c.writer}    <span style="float:right;">${c.createdate}</span></div>
                <div>${c.comment}</div>
                <c:if test="${c.writer eq loginMember.id}">
                    <div>
                        <a type="button" href="${pageContext.request.contextPath}/comment/modifyComment?commentNo=${c.commentNo}&boardNo=${board.BOARDNO}">수정</a>
                        <a type="button" href="${pageContext.request.contextPath}/comment/removeComment?commentNo=${c.commentNo}&boardNo=${board.BOARDNO}">삭제</a>
                    </div>
                </c:if>
            </div>
            <%--대댓글 목록--%>
            <c:forEach var="r" items="${replyList}" varStatus="status">
                <c:if test="${replyList[status.index].cgroup eq c.commentNo}">
                    <div style="margin-left: 20px;">
                        <div style="font-size: small;">${r.writer}    <span style="float:right;">${r.createdate}</span></div>
                        <div>${r.comment}</div>
                        <c:if test="${r.writer eq loginMember.id}">
                            <div>
                                <a type="button" href="${pageContext.request.contextPath}/comment/modifyComment?commentNo=${r.commentNo}&boardNo=${board.BOARDNO}">수정</a>
                                <a type="button" href="${pageContext.request.contextPath}/comment/removeComment?commentNo=${r.commentNo}&boardNo=${board.BOARDNO}">삭제</a>
                            </div>
                        </c:if>
                    </div>
                </c:if>
            </c:forEach>
            <%--대댓글 입력--%>
            <div style="margin-left: 20px;">
                <form method="post" action="${pageContext.request.contextPath}/comment/addReply">
                    <div>
                        ${loginMember.id}
                        <input type="hidden" name="writer" value="${loginMember.id}">
                        <input type="hidden" name="boardNo" value="${board.BOARDNO}">
                        <input type="hidden" name="cgroup" value="${c.commentNo}">
                        <input type="text" name="comment" value="" placeholder="댓글을 입력하세요.">
                        <button type="submit">입력</button>
                    </div>
                </form>
            </div>
        </c:forEach>
    </div>
</body>
</html>