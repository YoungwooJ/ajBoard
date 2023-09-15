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
        $(document).ready(function(){ // 페이지가 로드되고 나면 실행

            // 댓글 입력 버튼 클릭시
            $('#addCommentBtn').click(function(){
                let comment = $('#comment').val();
                if(comment === ''){
                    $('#commentMsg').text('댓글을 입력해주세요.');
                    $('#comment').focus();
                    return false;
                }
            });

            // 댓글 개수
            /*$.ajax({
                url : '${pageContext.request.contextPath}/comment/getCommentCount'
                , type : 'GET'
                , data : {"boardNo" : $('#boardNo').val()}
                , success : function (data) {
                    $('#commentCount').html(data);
                }
                , error : function (error) {
                    alert("댓글 개수 조회 실패");
                }
            })*/

            // 게시글 삭제 버튼 클릭시 확인 알람
            $('#deleteBtn').click(function(){
                if(!confirm("게시글을 삭제하시겠습니까?")){
                    return false;
                } else {
                    $('#deleteBtn').submit();
                }
            });

            // 댓글 삭제 버튼 클릭시 확인 알람
            $('.deleteComBtn').click(function(){
                if(!confirm("댓글을 삭제하시겠습니까?")){
                    return false;
                } else {
                    $('.deleteComBtn').submit();
                }
            });

            // 게시물 좋아요 클릭시 좋아요 등록 및 증가
            $('#likesBtn').click(function () {
               $.ajax({
                   url : '${pageContext.request.contextPath}/likes/getLikesOne'
                   , type : 'POST'
                   , data : {"id" : $('#id').val(), "boardNo" : $('#boardNo').val(), "commentNo" : 0}
                   , success : function (data) {
                       if(data === undefined || data === '') {
                           $.ajax({
                               url : '${pageContext.request.contextPath}/likes/addLikes'
                               , type : 'POST'
                               , data : {"id" : $('#id').val(), "boardNo" : $('#boardNo').val(), "commentNo" : 0}
                               , success : function (data) {
                                   $.ajax({
                                       url : '${pageContext.request.contextPath}/likes/getLikesCount'
                                       , type : 'POST'
                                       , data : {"boardNo" : $('#boardNo').val(), "commentNo" : 0}
                                       , success : function (data) {
                                           location.reload();
                                       }
                                       , error : function (error) {
                                           alert("좋아요 등록 후 개수 체크 실패");
                                       }
                                   })
                               }
                               , error : function (error) {
                                   alert("좋아요 등록 실패");
                               }
                           })
                       } else {
                           $.ajax({
                               url : '${pageContext.request.contextPath}/likes/removeLikes'
                               , type : 'POST'
                               , data : {"likesNo" : $('#likesNo').val()}
                               , success : function (data) {
                                   $.ajax({
                                       url : '${pageContext.request.contextPath}/likes/getLikesCount'
                                       , type : 'POST'
                                       , data : {"boardNo" : $('#boardNo').val(), "commentNo" : 0}
                                       , success : function (data) {
                                           location.reload();
                                       }
                                       , error : function (error) {
                                           alert("좋아요 취소 후 개수 체크 실패");
                                       }
                                   })
                               }
                               , error : function (error) {
                                   alert("좋아요 취소 실패");
                               }
                           })
                       }

                   }
                   , error : function (error) {
                       alert("좋아요 조회 실패");
                   }
               })
            });

            /*// 댓글 좋아요 클릭시 좋아요 등록 및 증가
            $('.cLikesBtn').click(function () {
                $.ajax({
                    url : '${pageContext.request.contextPath}/likes/getLikesOne'
                    , type : 'POST'
                    , data : {"id" : $('#id').val(), "boardNo" : 0, "commentNo" : $(this).data('value')}
                    , success : function (data) {
                        alert($(this).data('value'));
                        if(data === undefined || data === '') {
                            $.ajax({
                                url : '${pageContext.request.contextPath}/likes/addLikes'
                                , type : 'POST'
                                , data : {"id" : $('#id').val(), "boardNo" : 0, "commentNo" : $(this).data('value')}
                                , success : function (data) {
                                    location.reload();
                                }
                                , error : function (error) {
                                    alert("좋아요 등록 실패");
                                }
                            })
                        } else {
                            $.ajax({
                                url : '${pageContext.request.contextPath}/likes/removeLikes'
                                , type : 'POST'
                                , data : {"likesNo" : $(this).val()}
                                , success : function (data) {
                                    location.reload();
                                }
                                , error : function (error) {
                                    alert("좋아요 취소 실패");
                                }
                            })
                        }

                    }
                    , error : function (error) {
                        alert("좋아요 조회 실패");
                    }
                })
            });*/
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
        <h3>게시글 상세보기</h3>
        <br>
        <div style="color:red;" id="msg">
            ${msg}
        </div>
        <%--게시물--%>
        <table class="table table-bordered w-50">
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
            <div>
                <img alt="첨부파일" src="${pageContext.request.contextPath}/boardFile-upload/${f.fileName}" width="300px" height="150px">
            </div>
            <a type="button" class="btn btn-success" href="${pageContext.request.contextPath}/boardFile/fileDownload/${f.fileName}">
                파일 다운로드 : ${f.fileName}
            </a>
        </c:forEach>
        <c:if test="${loginMember.id eq board.WRITER || loginMember.level eq 1}">
            <a style="float:right; margin-right: 600px;" type="button" class="btn btn-danger" id="deleteBtn" href="${pageContext.request.contextPath}/board/removeBoard?boardNo=${board.BOARDNO}">게시물 삭제</a>
            <a style="float:right;" type="button" class="btn btn-warning" href="${pageContext.request.contextPath}/board/modifyBoard?boardNo=${board.BOARDNO}">게시물 수정</a>
        </c:if>

        <br><br>
        <%--좋아요--%>
        <div>
            <a type="button" id="likesBtn">
                좋아요
                <c:choose>
                    <c:when test="${boardLikes eq null}">
                        <span>
                            ♡&nbsp;${boardLikesCount}&nbsp;
                        </span>
                    </c:when>
                    <c:otherwise>
                        <span class="heart" style="color:red;">
                            ♥&nbsp;${boardLikesCount}&nbsp;
                            <input type="hidden" name="likesNo" id="likesNo" value="${boardLikes.likesNo}">
                        </span>
                    </c:otherwise>
                </c:choose>
            </a>
        <%--댓글--%>
        <%--댓글 개수--%>
            댓글 <span id="commentCount">${countComment}</span>
        </div>
        <div style="color:red;" id="commentMsg">
            ${commentMsg}
        </div>
        <%--댓글 입력--%>
        <form method="post" action="${pageContext.request.contextPath}/comment/addComment">
            <div>
                ${loginMember.id}
                <input type="hidden" name="writer" value="${loginMember.id}">
                <input type="hidden" name="boardNo" id="boardNo" value="${board.BOARDNO}">
                <input type="hidden" name="id" id="id" value="${id}">
                <input class="box form-control w-25" type="text" id="comment" name="comment" value="" placeholder="댓글을 입력하세요.">
                <button class="btn btn-dark" id="addCommentBtn" type="submit">입력</button>
            </div>
        </form>
        <%--댓글 목록--%>
        <c:forEach var="c" items="${commentList}" varStatus="cs">
            <div>
                <div style="font-size: small;">${c.writer}    <span style="float:right;">${c.createdate}</span></div>
                <div>
                    ${c.comment}
                    <input type="hidden" name="commentNo" class="commentNo" value="${c.commentNo}">
                    <%--<c:forEach var="l" items="${commentLikesList}" varStatus="status">
                        <c:if test="${c.commentNo eq l.commentNo}">
                            <a type="button" class="cLikesBtn" data-value="${c.commentNo}">
                                <span class="heart" style="color:red;">
                                    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;♥&nbsp;&lt;%&ndash;${commentLikesCount}&ndash;%&gt;&nbsp;
                                    <input type="hidden" name="cLikesNo" class="cLikesNo" value="${l.likesNo}">
                                </span>
                            </a>
                        </c:if>
                        <c:if test="${c.commentNo ne l.commentNo && cs.index eq status.index}">
                            <a type="button" class="cLikesBtn" data-value="${c.commentNo}">
                                <span>
                                    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;♡&nbsp;&lt;%&ndash;${commentLikesCount}&ndash;%&gt;&nbsp;
                                </span>
                            </a>
                        </c:if>
                    </c:forEach>--%>
                </div>
                <c:if test="${c.writer eq loginMember.id || loginMember.level eq 1}">
                    <div>
                        <a type="button" class="btn btn-warning" href="${pageContext.request.contextPath}/comment/modifyComment?commentNo=${c.commentNo}&boardNo=${board.BOARDNO}">수정</a>
                        <a type="button" class="deleteComBtn btn btn-danger" href="${pageContext.request.contextPath}/comment/removeComment?commentNo=${c.commentNo}&boardNo=${board.BOARDNO}">삭제</a>
                    </div>
                </c:if>
            </div>
            <%--대댓글 목록--%>
            <c:forEach var="r" items="${replyList}" varStatus="rs">
                <c:if test="${replyList[rs.index].cgroup eq c.commentNo}">
                    <div style="margin-left: 20px;">
                        <div style="font-size: small;">${r.writer}    <span style="float:right;">${r.createdate}</span></div>
                        <div>
                            ${r.comment}
                            <a type="button" class="rLikesBtn">
                                <input type="hidden" name="commentNo" class="commentNo" value="${r.commentNo}">
                                <%--<c:forEach var="l" items="${replyLikesList}" varStatus="status">
                                    <c:if test="${r.commentNo eq l.commentNo}">
                                        <span class="heart" style="color:red;">
                                            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;♥&nbsp;&lt;%&ndash;${commentLikesCount}&ndash;%&gt;&nbsp;
                                            <input type="hidden" name="rLikesNo" class="rLikesNo" value="${l.likesNo}">
                                        </span>
                                    </c:if>
                                    <c:if test="${r.commentNo ne l.commentNo && rs.index eq status.index}">
                                        <span>
                                            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;♡&nbsp;&lt;%&ndash;${commentLikesCount}&ndash;%&gt;&nbsp;
                                        </span>
                                    </c:if>
                                </c:forEach>--%>
                            </a>
                        </div>
                        <c:if test="${r.writer eq loginMember.id || loginMember.level eq 1}">
                            <div>
                                <a type="button" class="btn btn-warning" href="${pageContext.request.contextPath}/comment/modifyComment?commentNo=${r.commentNo}&boardNo=${board.BOARDNO}">수정</a>
                                <a type="button" class="deleteComBtn btn btn-danger" href="${pageContext.request.contextPath}/comment/removeComment?commentNo=${r.commentNo}&boardNo=${board.BOARDNO}">삭제</a>
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
                        <input class="box form-control w-25 reply" type="text" name="comment" value="" placeholder="댓글을 입력하세요.">
                        <button class="btn btn-dark addReplyBtn" type="submit">입력</button>
                    </div>
                </form>
            </div>
        </c:forEach>
    </div>
</body>
</html>