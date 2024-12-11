package com.example.userdomain.domain.user;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String email;
    private String password;
    private String role;
    private String socialProvider;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String phoneNumber; // 전화번호


    // 계정 상태 필드
    private boolean enabled; // 계정 활성화 여부
    private boolean accountNonExpired; // 계정 만료 여부
    private boolean accountNonLocked; // 계정 잠금 여부
    private boolean credentialsNonExpired; // 자격 증명(비밀번호) 만료 여부


    // 엔티티가 처음 생성될 때 createdAt과 updatedAt을 설정
    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
        this.enabled = false; // 계정이 기본적으로 활성화되지 않도록 설정
        this.accountNonExpired = true; // 계정이 기본적으로 만료되지 않도록 설정
        this.accountNonLocked = true; // 계정이 기본적으로 잠겨 있지 않도록 설정
        this.credentialsNonExpired = true; // 자격 증명이 기본적으로 만료되지 않도록 설정
    }


    public void createUser(String username , String email,String password, String role, String socialProvider, String phoneNumber, PasswordEncoder passwordEncoder) {
        this.username = username;
        this.email = email;
        this.password = passwordEncoder.encode(password);
        this.role = role;
        this.socialProvider = socialProvider;
        this.phoneNumber = phoneNumber;
    }

    // 비밀번호 암호화 및 설정 로직
    public void changePassword(String newPassword, PasswordEncoder passwordEncoder) {
        this.password = passwordEncoder.encode(newPassword);
        this.updatedAt = LocalDateTime.now();
    }

    // 이름 변경 로직
    public void changeUsername(String newUsername) {
        if (newUsername != null && !newUsername.isEmpty()) {
            this.username = newUsername;
            this.updatedAt = LocalDateTime.now();
        }
    }

    // 소셜 프로바이더 변경 로직
    public void changeSocialProvider(String newSocialProvider) {
        if (newSocialProvider != null && !newSocialProvider.isEmpty()) {
            this.socialProvider = newSocialProvider;
            this.updatedAt = LocalDateTime.now();
        }
    }

    // 전화번호 변경 로직
    public void changePhoneNumber(String newPhoneNumber) {
        if (newPhoneNumber != null && !newPhoneNumber.isEmpty()) {
            this.phoneNumber = newPhoneNumber;
            this.updatedAt = LocalDateTime.now();
        }
    }

}
