package com.technext.event_bright.Entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;


@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserLogin {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long userLoginId;

    @Column(unique = true)
    public String loginId;

    public String salt;

    public String password;

    @OneToOne
    @JoinColumn(name = "user_id")
    public User user;
}
