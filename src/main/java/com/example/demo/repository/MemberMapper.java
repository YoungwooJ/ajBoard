package com.example.demo.repository;

import com.example.demo.domain.MemberDTO;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public interface MemberMapper {

    // 아이디 찾기
    MemberDTO findMemberId(MemberDTO memberDTO);

    // Email과 id의 일치여부를 check
    MemberDTO selectEmailCheck(MemberDTO memberDTO);

    // 회원가입
    int insertMember(MemberDTO memberDTO);

    // ID 중복체크
    MemberDTO selectIdCheck(String id);

    // 로그인
    MemberDTO login(MemberDTO memberDTO);

    // ID, PW 찾기

    // 회원 비밀번호 수정
    int updateMemberPassword(MemberDTO memberDTO);

    // 회원 정보 수정
    int updateMember(HashMap<String, Object> hashMap);
    
    // 회원 탈퇴
    int deleteMember(MemberDTO memberDTO);
    
    // 세션 저장
}
