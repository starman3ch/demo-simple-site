package com.sandman.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * Created by lch131 on 2017. 4. 18..
 */

@Entity
@Table(name = "Member")
@Data
@NoArgsConstructor// JPA 명세에 따르면 엔티티 클래스에는 인자를 받지 않는 기본 생성자를 만들어야 한다
@AllArgsConstructor
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "member_key", nullable = false)
    private long memberKey;

    @Column(name = "member_id", nullable = false)
    private String memberId;

    @JsonIgnore
    @Column(name = "password", nullable = false)
    private String password;

}
