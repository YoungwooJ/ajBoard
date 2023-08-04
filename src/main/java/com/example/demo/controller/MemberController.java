package com.example.demo.controller;

import com.example.demo.domain.MemberDTO;
import com.example.demo.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;

@Controller
public class MemberController {
    @Autowired MemberService memberService;

    // 홈
    @RequestMapping("/home")
    public String home() {
        return "/home";
    }

    // 회원가입
    @GetMapping("/member/addMember")
    public String addMember() {
        return "/member/addMember";
    }
    @PostMapping("/member/addMember")
    public String addMember(Model model, MemberDTO memberDTO
                                , @RequestParam(value="address", required = true) String address){
        String idCheck = memberService.getIdCheck(memberDTO.getId());
        if(idCheck != null) {
            model.addAttribute("errodMsg", "중복된ID입니다.");
            return "/member/addMember";
        }

        String gender = memberDTO.getGender();
        if(gender.equals("M")) {
            memberDTO.setGender("남자");
        } else {
            memberDTO.setGender("여자");
        }

        String email = memberDTO.getEmail() + "@" + address;
        memberDTO.setEmail(email);

        int row = memberService.addMember(memberDTO);
        // row == 1 이면 입력 성공
        if(row == 0) {
            model.addAttribute("errorMsg", "시스템 에러로 등록 실패하였습니다.");
            return "/member/addMember";
        }
        return "redirect:/home";
    }

    // 로그인
    @GetMapping("/member/loginMember")
    public String login(){
        return "/member/loginMember";
    }
    @PostMapping("/member/loginMember")
    public String login(HttpSession session, MemberDTO memberDTO){
        MemberDTO resultMember = memberService.login(memberDTO);
        session.setAttribute("loginMember", resultMember);
        return "/home";
    }

    // 로그아웃
    @GetMapping("/member/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/home";
    }

    // 회원 정보
    @GetMapping("/member/memberOne")
    public String memberOne(HttpSession session, Model model){
        System.out.println("sessionAtt : "+session.getAttribute("loginMember"));
        MemberDTO loginMember = (MemberDTO) session.getAttribute("loginMember");
        model.addAttribute("member", loginMember);

        return "/member/memberOne";
    }

    // 회원 비밀번호 수정
    @GetMapping("/member/modifyMemberPw")
    public String modifyMemberPw(HttpSession session, Model model){
        MemberDTO loginMember = (MemberDTO) session.getAttribute("loginMember");
        model.addAttribute("member", loginMember);

        return "/member/modifyMemberPw";
    }
    @PostMapping("/member/modifyMemberPw")
    public String modifyMemberPw(HttpSession session, Model model
                                    , @RequestParam(value="newPassword", required = true) String newPassword
                                    , @RequestParam(value="oldPassword", required = true) String oldPassword) {
        MemberDTO loginMember = (MemberDTO) session.getAttribute("loginMember");
        MemberDTO paramMember = new MemberDTO();
        
        // 비밀번호 확인
        if(oldPassword.equals(loginMember.getPassword())){
            paramMember.setId(loginMember.getId());
            paramMember.setPassword(newPassword);

            int row = memberService.modifyMemberPassword(paramMember);
            // row == 1 이면 입력 성공
            if(row == 0) {
                model.addAttribute("errorMsg", "시스템 에러로 변경 실패하였습니다.");
                return "/member/modifyMemberPw";
            } else {
                /*로그아웃*/
                session.invalidate();
                model.addAttribute("msg", "비밀번호가 변경되었습니다.");
                return "/home";
            }
        } else {
            model.addAttribute("errorMsg", "비밀번호가 틀렸습니다.");
            return "/member/modifyMemberPw";
        }
    }

    // 회원 정보 수정
    @GetMapping("/member/modifyMember")
    public String modifyMember(HttpSession session, Model model){
        MemberDTO loginMember = (MemberDTO) session.getAttribute("loginMember");
        model.addAttribute("member", loginMember);

        return "/member/modifyMember";
    }
    @PostMapping("/member/modifyMember")
    public String modifyMember(Model model, MemberDTO memberDTO) {

        int row = memberService.modifyMember(memberDTO);
        // row == 1 이면 입력 성공
        if(row == 0) {
            model.addAttribute("errorMsg", "시스템 에러로 변경 실패하였습니다.");
            return "member/modifyMember";
        }
        return "/member/memberOne";
    }

    // 회원 탈퇴
    @GetMapping("/member/removeMember")
    public String removeMember(HttpSession session) {

        MemberDTO loginMember = (MemberDTO) session.getAttribute("loginMember");
        memberService.removeMember(loginMember);

        /*로그아웃*/
        session.invalidate();

        return "redirect:/home";
    }
}
