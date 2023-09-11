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
import java.net.URLEncoder;
import java.util.List;

@Controller
public class MemberController {
    @Autowired MemberService memberService;

    // 관리자 : 강제 탈퇴
    @PostMapping("/member/removeMemberForced")
    public String removeMember(Model model, MemberDTO memberDTO) throws Exception {

        memberService.removeMember(memberDTO);
        String msg = URLEncoder.encode("강제 탈퇴가 완료 되었습니다.", "UTF-8");
        return "redirect:/member/getMemberList?msg="+msg;
    }

    // 관리자 : 회원 권한 수정
    @PostMapping("/member/modifyMemberGrade")
    public String modifyMemberGrade(HttpSession session, Model model, MemberDTO memberDTO) throws Exception {

        int row = memberService.modifyMemberGrade(session, memberDTO);
        // row == 1 이면 입력 성공
        if(row == 0) {
            model.addAttribute("msg", "시스템 에러로 변경 실패하였습니다.");
            return "/member/getMemberList";
        }

        String msg = URLEncoder.encode("회원 권한이 변경되었습니다.", "UTF-8");
        return "redirect:/member/getMemberList?msg="+msg;
    }

    // 관리자 : 회원 목록
    @GetMapping("/member/getMemberList")
    public String getMemberList(Model model
                                    , @RequestParam(value="msg", required = false) String msg) {
        List<MemberDTO> list =  memberService.getMemberList();
        model.addAttribute("list", list);
        // 메시지가 있을시
        if(msg != null) {
            model.addAttribute("msg", msg);
        }
        return "/member/getMemberList";
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
    public String findMemberId(Model model, MemberDTO memberDTO) {

        MemberDTO member = memberService.findMemberId(memberDTO);

        // 오류 메시지 및 알림
        if(member == null ){
            model.addAttribute("msg", "등록된 회원정보가 없습니다.");
            return "/member/findMemberId";
        }
        return "redirect:/member/findMemberIdResult?memberId="+member.getId();
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
    public String addMember(Model model, MemberDTO memberDTO
                                , @RequestParam(value="address", required = true) String address) throws Exception {
        // 중복 아이디 체크
        MemberDTO idCheck = memberService.getIdCheck(memberDTO.getId());
        // 오류 메시지 및 알림
        if(idCheck != null) {
            model.addAttribute("msg", "중복된ID입니다.");
            return "redirect:/member/addMember";
        }
        // 회원가입
        int row = memberService.addMember(memberDTO, address);
        // row == 1 이면 입력 성공
        if(row == 0) {
            // 오류 메시지 및 알림
            model.addAttribute("msg", "시스템 에러로 등록 실패하였습니다.");
            return "redirect:/member/addMember";
        }
        String msg = URLEncoder.encode("회원 가입이 완료되었습니다.", "UTF-8");
        return "redirect:/member/loginMember?msg="+msg;
    }

    // 로그인
    @GetMapping("/member/loginMember")
    public String login(Model model, @RequestParam(value="msg", required = false) String msg){
        // 메시지가 있을시
        if(msg != null) {model.addAttribute("msg", msg);}
        return "/member/loginMember";
    }
    @PostMapping("/member/loginMember")
    public String login(Model model, HttpSession session, MemberDTO memberDTO){
        MemberDTO resultMember = memberService.login(memberDTO);
        session.setAttribute("loginMember", resultMember);
        if("".equals(resultMember) || resultMember == null) {
            model.addAttribute("msg", "아이디와 비밀번호가 틀렸습니다.");
            return "/member/loginMember";
        }
        return "redirect:/home";
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
    public String modifyMember(HttpSession session, Model model, MemberDTO memberDTO
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
    public String removeMember(HttpSession session, Model model) {
        MemberDTO loginMember = (MemberDTO) session.getAttribute("loginMember");
        memberService.removeMember(loginMember);
        /*로그아웃*/
        session.invalidate();
        model.addAttribute("msg", "회원탈퇴가 완료되었습니다.");
        return "/home";
    }
}
