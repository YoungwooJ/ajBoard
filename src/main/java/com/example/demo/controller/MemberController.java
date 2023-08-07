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
        // 중복 아이디 체크
        String idCheck = memberService.getIdCheck(memberDTO.getId());
        if(idCheck != null) {
            model.addAttribute("errodMsg", "중복된ID입니다.");
            return "redirect:/member/addMember";
        }
        // 성별 한글로 바꿔주기
        String gender = memberDTO.getGender();
        if(gender.equals("M")) {
            memberDTO.setGender("남자");
        } else {
            memberDTO.setGender("여자");
        }
        // 이메일 주소가 직접 입력이 아니라면
        if(! address.equals("noAddress")) {
            String email = memberDTO.getEmail() + "@" + address;
            memberDTO.setEmail(email);
        }
        int row = memberService.addMember(memberDTO);
        // row == 1 이면 입력 성공
        if(row == 0) {
            model.addAttribute("errorMsg", "시스템 에러로 등록 실패하였습니다.");
            return "redirect/member/addMember";
        }
        return "redirect:/member/loginMember";
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
    public String memberOne(HttpSession session, Model model){
        /*디버깅 코드*/
        // System.out.println("sessionAtt : "+session.getAttribute("loginMember"));
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
                return "redirect:/member/modifyMemberPw";
            } else {
                /*로그아웃*/
                session.invalidate();
                model.addAttribute("msg", "비밀번호가 변경되었습니다.");
                return "redirect:/home";
            }
        } else {
            model.addAttribute("errorMsg", "비밀번호가 틀렸습니다.");
            return "redirect:/member/modifyMemberPw";
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
    public String modifyMember(HttpSession session, Model model, MemberDTO memberDTO
                                    , @RequestParam(value="address", required = true) String address
                                    , @RequestParam(value="oldId", required = true) String oldId
                                    , @RequestParam(value="oldName", required = true) String oldName
                                    , @RequestParam(value="oldBirth", required = true) String oldBirth
                                    , @RequestParam(value="oldGender", required = true) String oldGender
                                    , @RequestParam(value="oldPhone", required = true) String oldPhone
                                    , @RequestParam(value="oldEmail", required = true) String oldEmail) {
        MemberDTO loginMember = (MemberDTO) session.getAttribute("loginMember");
        MemberDTO paramMember = new MemberDTO();

        // 비밀번호 확인
        if(memberDTO.getPassword().equals(loginMember.getPassword())){
            // 비밀번호가 맞다면 서비스 실행

            // 아이디 체크
            if(memberDTO.getId() == null || memberDTO.getId().equals("")) {
                memberDTO.setId(oldId);
            }

            // 이름 체크
            if(memberDTO.getName() == null || memberDTO.getName().equals("")) {
                memberDTO.setName(oldName);
            }

            // 생년월일 체크
            if(memberDTO.getBirth() == null || memberDTO.getBirth().equals("")) {
                memberDTO.setBirth(oldBirth);
            }

            // 성별 체크
            String gender = null;
            gender = memberDTO.getGender();

            /*성별 한글로 바꿔주기*/
            if(gender == null || gender.equals("")) {
                memberDTO.setGender(oldGender);
            } else if(gender.equals("F")) {
                memberDTO.setGender("여자");
            } else if(gender.equals("M")){
                memberDTO.setGender("남자");
            }

            // 핸드폰 번호 체크
            if(memberDTO.getPhone() == null || memberDTO.getPhone().equals("")) {
                memberDTO.setPhone(oldPhone);
            }

            // 이메일 주소가 직접 입력이 아니라면
            if (memberDTO.getEmail() == null || memberDTO.getEmail().equals("")) {
                memberDTO.setEmail(oldEmail);
            } else if(! address.equals("noAddress") || memberDTO.getEmail() != null || !memberDTO.getEmail().equals("")) {
                String email = memberDTO.getEmail() + "@" + address;
                memberDTO.setEmail(email);
            }
            // 디버깅 코드
            System.out.println(memberDTO);
            
            int row = memberService.modifyMember(session, memberDTO, oldId);
            // row == 1 이면 입력 성공
            if(row == 0) {
                model.addAttribute("errorMsg", "시스템 에러로 변경 실패하였습니다.");
                return "redirect:/member/modifyMember";
            }
        } else {
            // 비밀번호가 틀리면 return
            model.addAttribute("errorMsg", "비밀번호가 틀렸습니다.");
            return "redirect:/member/modifyMember";
        }

        return "redirect:/member/memberOne";
    }

    // 회원 탈퇴
    @GetMapping("/member/removeMember")
    public String removeMember(HttpSession session) {

        MemberDTO loginMember = (MemberDTO) session.getAttribute("loginMember");
        memberService.removeMember(loginMember);

        /*로그아웃*/
        session.invalidate();

        return "/home";
    }
}
