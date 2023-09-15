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
import java.io.PrintWriter;
import java.util.List;

@Controller
public class MemberController {
    @Autowired MemberService memberService;

    // 관리자 : 강제 탈퇴
    @PostMapping("/member/removeMemberForced")
    public String removeMember(MemberDTO memberDTO) {
        String address = memberService.removeMemberForced(memberDTO);
        return address;
    }

    // 관리자 : 회원 권한 수정
    @PostMapping("/member/modifyMemberGrade")
    public String modifyMemberGrade(HttpSession session, MemberDTO memberDTO) {
        String address = memberService.modifyMemberGrade(session, memberDTO);
        return address;
    }

    // 관리자 : 회원 목록
    @GetMapping("/member/getMemberList")
    public String getMemberList(HttpSession session, Model model
                                    , @RequestParam(value="msg", required = false) String msg) {
        MemberDTO loginMember = (MemberDTO)session.getAttribute("loginMember");
        if(loginMember.getLevel() == 1) {
            List<MemberDTO> list = memberService.getMemberList();
            model.addAttribute("list", list);
            // 메시지가 있을시
            if (msg != null) {
                model.addAttribute("msg", msg);
            }
            return "/member/getMemberList";
        } else {
            model.addAttribute("msg", "관리자만 사용할 수 있습니다.");
            return "/home";
        }
    }

    // 비밀번호 찾기
    @GetMapping("/member/findMemberPw")
    public String findMemberPw() {
        return "/member/findMemberPw";
    }

    // 아이디 찾기
    @GetMapping("/member/findMemberId")
    public String findMemberId() {
        return "/member/findMemberId";
    }
    @PostMapping("/member/findMemberId")
    public String findMemberId(MemberDTO memberDTO) {
        String address = memberService.findMemberId(memberDTO);
        return address;
    }
    @GetMapping("/member/findMemberIdResult")
    public String findMemberIdResult(Model model
                                    , @RequestParam(value="memberId", required = true) String memberId) {
        model.addAttribute("memberId", memberId);
        return "/member/findMemberIdResult";
    }

    // 아이디 및 비밀번호 찾기 홈
    @GetMapping("/member/findMemberIdPw")
    public String findMemberIdPw() {
        return "/member/findMemberIdPw";
    }

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
    public String addMember(MemberDTO memberDTO
                                , @RequestParam(value="address", required = true) String address) {
        String returnAddress = memberService.addMember(memberDTO, address);
        return returnAddress;
    }

    // 로그인
    @GetMapping("/member/loginMember")
    public String login(Model model, @RequestParam(value="msg", required = false) String msg){
        // 메시지가 있을시
        if(msg != null) {model.addAttribute("msg", msg);}
        return "/member/loginMember";
    }
    @PostMapping("/member/loginMember")
    public String login(HttpSession session, MemberDTO memberDTO){
        String address = memberService.login(session, memberDTO);
        return address;
    }

    // 로그아웃
    @GetMapping("/member/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "/home";
    }

    // 회원 정보
    @GetMapping("/member/memberOne")
    public String memberOne(HttpSession session, Model model
                                    , @RequestParam(value="msg", required = false) String msg){
        MemberDTO loginMember = (MemberDTO) session.getAttribute("loginMember");
        model.addAttribute("member", loginMember);
        // 메시지가 있을시
        if(msg != null) {model.addAttribute("msg", msg);}
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
    public String modifyMemberPw(HttpSession session
                                    , @RequestParam(value="newPassword", required = true) String newPassword
                                    , @RequestParam(value="oldPassword", required = true) String oldPassword) {
        String address = memberService.modifyMemberPassword(session, newPassword, oldPassword);
        return address;
    }

    // 회원 정보 수정
    @GetMapping("/member/modifyMember")
    public String modifyMember(HttpSession session, Model model
                                    , @RequestParam(value="msg", required = false) String msg){
        MemberDTO loginMember = (MemberDTO) session.getAttribute("loginMember");
        model.addAttribute("member", loginMember);
        // 메시지가 있을시
        if(msg != null) {model.addAttribute("msg", msg);}
        return "/member/modifyMember";
    }
    @PostMapping("/member/modifyMember")
    public String modifyMember(HttpSession session, MemberDTO memberDTO
                                    , @RequestParam(value="address", required = true) String address
                                    , @RequestParam(value="oldId", required = true) String oldId
                                    , @RequestParam(value="oldName", required = true) String oldName
                                    , @RequestParam(value="oldBirth", required = true) String oldBirth
                                    , @RequestParam(value="oldGender", required = true) String oldGender
                                    , @RequestParam(value="oldPhone", required = true) String oldPhone
                                    , @RequestParam(value="oldEmail", required = true) String oldEmail) {
        String returnAddress = memberService.modifyMember(session, memberDTO, address, oldId, oldName, oldBirth, oldGender, oldPhone, oldEmail);
        return returnAddress;
    }

    // 회원 탈퇴
    @GetMapping("/member/removeMember")
    public String removeMember(HttpSession session) {
        String address = memberService.removeMember(session);
        return address;
    }
}
