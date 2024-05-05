package com.agave.tests.Utilities.JWT;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Date;

@Entity
@Table(name = "profiles")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Profile implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 1000)
    private String profilePictureUrl;
    private String nickName;
    @Column(length = 1000)
    private String selfIntroductoryStatement;
    private String phoneNumber;
    private Date phoneVerifiedAt;
    private String invitationCode;
    private String surNameKanji;
    private String firstNameKanji;
    private String surNameFurigana;
    private String firstNameFurigana;
    private String dateOfBirth;
    private String postalCode;
    private String prefectures;
    private String cityStreetAddress;
    private String buildingNameAndRoomNumber;
    private String email;
    private String password;

    @UpdateTimestamp
    private Date updatedDateAt;

    @CreationTimestamp
    private Date createdDateAt;

    private boolean isFromGoogle;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
