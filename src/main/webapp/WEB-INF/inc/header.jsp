<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<%--Header--%>
<nav class="navbar navbar-expand-lg bg-dark" data-bs-theme="dark">
    <div class="container-fluid">
        <a class="navbar-brand" href="${pageContext.request.contextPath}/home">
            <img class="scroll_logo fixed_transform" src="https://cdn.imweb.me/upload/S202307115b6fc32c3d2fe/df7c7ea2600c9.png" alt="AJ 새로운 가치와 편리함을 제공하는 종합 렌탈 서비스 그룹" width="89" style="max-width: 100%;height: auto;">
        </a>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarColor01" aria-controls="navbarColor01" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarColor01">
            <ul class="navbar-nav me-auto">
                <li class="nav-item">
                    <a class="nav-link" href="${pageContext.request.contextPath}/home">홈
                        <span class="visually-hidden">(current)</span>
                    </a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="${pageContext.request.contextPath}/member/getMemberList">회원관리</a>
                </li>
                <li class="nav-item dropdown">
                    <a class="nav-link dropdown-toggle" data-bs-toggle="dropdown" href="#" role="button" aria-haspopup="true" aria-expanded="false">마이페이지</a>
                    <div class="dropdown-menu">
                        <a class="dropdown-item" href="${pageContext.request.contextPath}/member/memberOne">회원정보</a>
                        <a class="dropdown-item" href="${pageContext.request.contextPath}/member/modifyMember">회원정보변경</a>
                        <a class="dropdown-item" href="${pageContext.request.contextPath}/member/modifyMemberPw">비밀번호변경</a>
                        <div class="dropdown-divider"></div>
                        <a class="dropdown-item" href="${pageContext.request.contextPath}/member/logout">로그아웃</a>
                    </div>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="${pageContext.request.contextPath}/board/getBoardList">게시판</a>
                </li>
            </ul>
            <ul class="navbar-nav ms-md-auto">
                <li class="nav-item">
                    <a target="_blank" rel="noopener" class="nav-link" href="https://github.com/YoungwooJ">GitHub</a>
                </li>
            </ul>
        </div>
    </div>
</nav>