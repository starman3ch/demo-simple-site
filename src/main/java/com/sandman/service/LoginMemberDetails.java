package com.sandman.service;

import com.sandman.domain.Member;
import lombok.Data;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;

/**
 * Created by lch131 on 2017. 4. 20..
 */

@Data
public class LoginMemberDetails extends org.springframework.security.core.userdetails.User {

    private final Member member;

    public LoginMemberDetails(Member member) {
        super(member.getMemberId(), member.getPassword(), AuthorityUtils.createAuthorityList("ROLE_USER"));
        this.member = member;
    }
}
