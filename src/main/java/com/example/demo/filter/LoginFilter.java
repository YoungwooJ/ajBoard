package com.example.demo.filter;

import com.example.demo.domain.MemberDTO;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

@WebFilter(value = {"/member/getMemberList", "/member/memberOne", "/member/modifyMemberPw", "/member/modifyMember", "/member/removeMember", "/member/logout", "/board/*"})
public class LoginFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        /*System.out.println("LoginFilter 작동 시작");*/
        if(request instanceof HttpServletRequest) {
            HttpServletRequest req = (HttpServletRequest)request;
            HttpSession session = ((HttpServletRequest) request).getSession();
            MemberDTO loginMember = (MemberDTO)session.getAttribute("loginMember");
            if(loginMember == null || "".equals(loginMember)) {
                // 로그인 안되어 있으면 alert창 출력 후 home으로 이동
                response.setContentType("text/html; charset=UTF-8");
                PrintWriter out = response.getWriter();
                out.println("<script>alert('로그인 후 이용 가능합니다.'); location.href='/home'</script>");
                out.flush();
                out.close();

                //((HttpServletResponse)response).sendRedirect(req.getContextPath()+"/home");
                return;
            }
        } else {
            return;
        }

        // controller 전
        chain.doFilter(request, response);
        // controller 후
    }

    @Override
    public void destroy() {
    }
}
