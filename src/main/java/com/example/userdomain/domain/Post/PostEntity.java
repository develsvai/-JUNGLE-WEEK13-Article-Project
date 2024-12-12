package com.example.userdomain.domain.Post;

import com.example.userdomain.domain.user.UserEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;


@Entity
@Table(name = "posts")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PostEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 게시글 ID

    private String title; // 게시글 제목
    private String content; // 게시글 내용

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @JsonIgnore // 이 필드를 직렬화에서 제외
    private UserEntity user; // 작성자 (UserEntity와 연관)

    private LocalDateTime createdAt; // 작성 시간
    private LocalDateTime updatedAt; // 수정 시간

    private boolean enabled; // 게시글 활성화 여부 (삭제 여부)
    private boolean pinned; // 게시글 고정 여부

    public PostEntity(String title, String content,UserEntity user ){
        this.title = title;
        this.content = content;
        this.user = user;
    }

    // 엔티티가 처음 생성될 때 createdAt과 updatedAt을 설정
    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    // 게시글 수정 시 updateAt 갱신
    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    // 게시글 활성화 여부 변경
    public void changeEnabled(boolean enabled) {
        this.enabled = enabled;
        this.updatedAt = LocalDateTime.now();
    }

    // 게시글 제목 변경
    public void changeTitle(String newTitle) {
        if (newTitle != null && !newTitle.isEmpty()) {
            this.title = newTitle;
            this.updatedAt = LocalDateTime.now();
        }
    }

    // 게시글 내용 변경
    public void changeContent(String newContent) {
        if (newContent != null && !newContent.isEmpty()) {
            this.content = newContent;
            this.updatedAt = LocalDateTime.now();
        }
    }

    // 게시글 고정 여부 변경
    public void changePinnedStatus(boolean pinned) {
        this.pinned = pinned;
        this.updatedAt = LocalDateTime.now();
    }
}
