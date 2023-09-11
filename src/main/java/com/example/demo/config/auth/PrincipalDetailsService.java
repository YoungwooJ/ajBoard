/*package com.example.demo.config.auth;

import com.example.demo.domain.MemberDTO;
import com.example.demo.repository.MemberMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

// 시큐리티 설정에서 loginProcessingUrl("/login");
// /login 요청이 오면 자동으로 UserDetailsSerivce 타입으로 IoC되어 있는 loadUserByUsername 함수가 실행
@Service
public class PrincipalDetailsService implements UserDetailsService {

    @Autowired
    private MemberMapper memberMapper;

    // 시큐리티 session(내부 Authentication(내부 UserDetails))
    @Override
    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
        MemberDTO memberDTO = memberMapper.findByUsername(name);
        if(memberDTO != null) {
            return new PrincipalDetails(memberDTO);
        }
        return null;
    }
}*/
