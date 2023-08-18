package com.example.demo.service;

import com.example.demo.domain.MailDTO;
import com.example.demo.domain.MemberDTO;
import com.example.demo.repository.MemberMapper;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.servlet.http.HttpSession;
import java.util.HashMap;

@Service
@Transactional
@AllArgsConstructor
public class MemberService {
    @Autowired MemberMapper memberMapper;

    private final JavaMailSender mailSender;
    private static final String FROM_ADDRESS = "cyw960714@naver.com";

    // 아이디 찾기
    public MemberDTO findMemberId(MemberDTO memberDTO) {
        return memberMapper.findMemberId(memberDTO);
    }

    // 이메일 발송
    public void mailSend(MailDTO mailDTO) {
        System.out.println("이메일 전송 완료!");

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(mailDTO.getAddress());
        message.setFrom(MemberService.FROM_ADDRESS);
        message.setSubject(mailDTO.getTitle());
        message.setText(mailDTO.getMessage());

        mailSender.send(message);
    }

    // 메일 생성 및 임시 비밀번호 업데이트
    public MailDTO createMailAndChangePassword(MemberDTO memberDTO) {
        String str = getTempPassword();
        MailDTO mailDTO = new MailDTO();
        mailDTO.setAddress(memberDTO.getEmail());
        mailDTO.setTitle(memberDTO.getId() + "님의 임시비밀번호 안내 이메일입니다.");
        mailDTO.setMessage("안녕하세요. 임시비밀번호 안내 관련 이메일입니다." + "[" + memberDTO.getId() + "]" +
                "님의 임시 비밀번호는 " + str + " 입니다.");
        // 임시 비밀번호로 변경
        memberDTO.setPassword(str);
        modifyMemberPassword(memberDTO);

        return mailDTO;
    }

    // 임시 비밀번호 생성
    public String getTempPassword(){
        char[] charSet = new char[] { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F',
                'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z' };

        String str = "";

        int idx = 0;
        for (int i = 0; i < 10; i++) {
            idx = (int) (charSet.length * Math.random());
            str += charSet[idx];
        }
        return str;
    }

    // Email과 id의 일치여부를 check
    public boolean getEmailCheck(MemberDTO memberDTO) {

        MemberDTO member = memberMapper.selectEmailCheck(memberDTO);
        if(member != null) {
            return true;
        } else {
            return false;
        }
    }

    // 회원가입
    public int addMember(MemberDTO memberDTO) {
        return memberMapper.insertMember(memberDTO);
    }

    // ID 중복체크
    public MemberDTO getIdCheck(String id) {
        return memberMapper.selectIdCheck(id);
    }

    // 로그인
    public MemberDTO login(MemberDTO memberDTO) {
        return memberMapper.login(memberDTO);
    }

    // ID, PW 찾기

    // 회원 비밀번호 수정
    public int modifyMemberPassword(MemberDTO memberDTO) {
        return memberMapper.updateMemberPassword(memberDTO);
    }

    // 회원 정보 수정
    public int modifyMember(HttpSession session, MemberDTO memberDTO, String oldId) {
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("id", memberDTO.getId());
        map.put("name", memberDTO.getName() );
        map.put("birth", memberDTO.getBirth());
        map.put("gender", memberDTO.getGender());
        map.put("phone", memberDTO.getPhone());
        map.put("email", memberDTO.getEmail());
        map.put("oldId", oldId);

        /*세션 삭제*/
        session.removeAttribute("loginMember");
        /*세션 수정*/
        session.setAttribute("loginMember", memberDTO);

        return memberMapper.updateMember(map);
    }

    // 회원 탈퇴
    public int removeMember(MemberDTO memberDTO) {
        return memberMapper.deleteMember(memberDTO);
    }

    // 세션 저장
}
