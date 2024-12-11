package com.example.userdomain.domain.user;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Bean;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class UserEntity {

    @Id
    private Long id;
    private String username;
    private String email;
    private String password;


}
