package com.example.demo.restController;

import com.example.demo.domain.MailDTO;
import com.example.demo.domain.MemberDTO;
import com.example.demo.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
public class MemberRestController {
        @Autowired MemberService memberService;
        
        // Email과 id의 일치여부를 check
        @GetMapping("/member/getEmailCheck")
        public @ResponseBody Map<String, Boolean> getEmailCheck(MemberDTO memberDTO) {
             Map<String, Boolean> json = new HashMap<>();
             boolean getEmailCheck = memberService.getEmailCheck(memberDTO);

             json.put("check", getEmailCheck);
             return json;
        }
        
        // 등록된 이메일로 임시비밀번호를 발송하고 발송된 임시비밀번호로 사용자의 pw를 변경하는 컨트롤러
        @PostMapping("/member/sendEmail")
        public @ResponseBody void sendEmail(MemberDTO memberDTO) {
            MailDTO mailDTO = memberService.createMailAndChangePassword(memberDTO);
            memberService.mailSend(mailDTO);
        }

        // ID 중복체크
        @GetMapping("/member/getIdCheck")
        public MemberDTO getIdCheck(@RequestParam(value = "id", required = true) String id) {
            return memberService.getIdCheck(id);
        }
}
