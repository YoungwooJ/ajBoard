package com.example.demo.service;

import com.example.demo.domain.MailDTO;
import com.example.demo.domain.MemberDTO;
import com.example.demo.repository.MemberMapper;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
/*import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;*/
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;

@Service
@Transactional
@AllArgsConstructor
public class MemberService {
    @Autowired MemberMapper memberMapper;
    /*@Autowired BCryptPasswordEncoder bCryptPasswordEncoder;*/

    private final JavaMailSender mailSender;
    private static final String FROM_ADDRESS = "cyw960714@naver.com";

    // 관리자 : 회원 권한 수정
    public int modifyMemberGrade(HttpSession session, MemberDTO memberDTO) {
        MemberDTO loginMember = (MemberDTO) session.getAttribute("loginMember");
        loginMember.setLevel(memberDTO.getLevel());

        /*세션 삭제*/
        session.removeAttribute("loginMember");
        /*세션 수정*/
        session.setAttribute("loginMember", loginMember);

        return memberMapper.updateMemberGrade(memberDTO);
    }

    // 관리자 : 회원 목록
    public List<MemberDTO> getMemberList() {
        return memberMapper.selectMemberList();
    }

    // 아이디 찾기
    public MemberDTO findMemberId(MemberDTO memberDTO) {
        if(memberDTO.getGender().equals("M")){
            memberDTO.setGender("남자");
        } else {
            memberDTO.setGender("여자");
        }
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
        memberMapper.updateMemberPassword(memberDTO);

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
    public int addMember(MemberDTO memberDTO, String address) {
        // 성별 한글로 바꿔주기
        String gender = memberDTO.getGender();
        if("M".equals(gender)) {
            memberDTO.setGender("남자");
        } else {
            memberDTO.setGender("여자");
        }
        // 이메일 주소가 직접 입력이 아니라면
        if(! "noAddress".equals(address)) {
            String email = memberDTO.getEmail() + "@" + address;
            memberDTO.setEmail(email);
        }
        // 비밀번호 암호화
        /*String rawPassword = memberDTO.getPassword();
        String encPassword = bCryptPasswordEncoder.encode(rawPassword);
        memberDTO.setPassword(encPassword);*/
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

    // 회원 비밀번호 수정
    public String modifyMemberPassword(HttpSession session, String newPassword, String oldPassword) {
        String address = null;
        try {
            MemberDTO loginMember = (MemberDTO) session.getAttribute("loginMember");
            MemberDTO memberDTO = new MemberDTO();
            // 비밀번호 확인
            if (oldPassword.equals(loginMember.getPassword())) {
                memberDTO.setId(loginMember.getId());
                memberDTO.setPassword(newPassword);
                int row = memberMapper.updateMemberPassword(memberDTO);
                // row == 1 이면 입력 성공
                if (row == 0) {
                    String msg = URLEncoder.encode("시스템 에러로 변경 실패하였습니다.", "UTF-8");
                    address = "redirect:/member/modifyMemberPw?msg="+msg;
                } else {
                    /*로그아웃*/
                    session.invalidate();
                    String msg = URLEncoder.encode("비밀번호가 변경되었습니다.", "UTF-8");
                    address = "redirect:/home?msg="+msg;
                }
            } else {
                String msg = URLEncoder.encode("비밀번호를 확인하세요.", "UTF-8");
                address = "redirect:/member/modifyMemberPw?msg="+msg;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return address;
    }

    // 회원 정보 수정
    public String modifyMember(HttpSession session, MemberDTO memberDTO, String address, String oldId, String oldName, String oldBirth, String oldGender, String oldPhone, String oldEmail) {
        String returnAddress = null;
        try {
            MemberDTO loginMember = (MemberDTO) session.getAttribute("loginMember");
            // 비밀번호 확인
            if (memberDTO.getPassword().equals(loginMember.getPassword())) {
                // 아이디 체크
                if (memberDTO.getId() == null || "".equals(memberDTO.getId())) {
                    memberDTO.setId(oldId);
                }

                // 이름 체크
                if (memberDTO.getName() == null || "".equals(memberDTO.getName())) {
                    memberDTO.setName(oldName);
                }

                // 생년월일 체크
                if (memberDTO.getBirth() == null || "".equals(memberDTO.getBirth())) {
                    memberDTO.setBirth(oldBirth);
                }

                // 성별 체크
                String gender = memberDTO.getGender();

                /*성별 한글로 바꿔주기*/
                if (gender == null || "".equals(gender)) {
                    memberDTO.setGender(oldGender);
                } else if ("F".equals(gender)) {
                    memberDTO.setGender("여자");
                } else if ("M".equals(gender)) {
                    memberDTO.setGender("남자");
                }

                // 핸드폰 번호 체크
                if (memberDTO.getPhone() == null || "".equals(memberDTO.getPhone())) {
                    memberDTO.setPhone(oldPhone);
                }

                // 이메일 주소가 직접 입력이 아니라면
                if (memberDTO.getEmail() == null || "".equals(memberDTO.getEmail())) {
                    memberDTO.setEmail(oldEmail);
                } else if (!"noAddress".equals(address) || memberDTO.getEmail() != null || !"".equals(memberDTO.getEmail())) {
                    String email = memberDTO.getEmail() + "@" + address;
                    memberDTO.setEmail(email);
                }

                HashMap<String, Object> map = new HashMap<String, Object>();
                map.put("id", memberDTO.getId());
                map.put("name", memberDTO.getName());
                map.put("birth", memberDTO.getBirth());
                map.put("gender", memberDTO.getGender());
                map.put("phone", memberDTO.getPhone());
                map.put("email", memberDTO.getEmail());
                map.put("oldId", oldId);

                /*세션 삭제*/
                session.removeAttribute("loginMember");
                /*세션 수정*/
                session.setAttribute("loginMember", memberDTO);
                int row = memberMapper.updateMember(map);
                // row == 1 이면 입력 성공
                if (row == 0) {
                    String msg = URLEncoder.encode("시스템 에러로 변경 실패하였습니다.", "UTF-8");
                    returnAddress = "redirect:/member/modifyMember?msg=" + msg;
                }
            } else {
                // 비밀번호가 틀리면 return
                String msg = URLEncoder.encode("비밀번호가 틀렸습니다.", "UTF-8");
                returnAddress = "redirect:/member/modifyMember?msg=" + msg;
            }
            String msg = URLEncoder.encode("회원정보가 수정되었습니다.", "UTF-8");
            returnAddress = "redirect:/member/memberOne?msg=" + msg;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return returnAddress;
    }

    // 회원 탈퇴
    public int removeMember(MemberDTO memberDTO) {
        return memberMapper.deleteMember(memberDTO);
    }
}
