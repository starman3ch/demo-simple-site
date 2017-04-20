package com.sandman.service;

import com.sandman.domain.Member;
import com.sandman.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Created by lch131 on 2017. 4. 20..
 */
@Service
public class LoginMemberDetailsService implements UserDetailsService {

    @Autowired
    private MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Member member = memberRepository.findByMemberId(username);
        if (member == null) {
            throw new UsernameNotFoundException("The requested user is not found.");
        }
        return new LoginMemberDetails(member);
    }
}
