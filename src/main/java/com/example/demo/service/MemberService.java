package com.example.demo.service;

import com.example.demo.domain.MemberDTO;
import com.example.demo.repository.MemberMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;
import java.util.HashMap;

@Service
@Transactional
public class MemberService {
    @Autowired MemberMapper memberMapper;

    // 회원가입
    public int addMember(MemberDTO memberDTO) {
        return memberMapper.insertMember(memberDTO);
    }

    // ID 중복체크
    public String getIdCheck(String id) {
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
