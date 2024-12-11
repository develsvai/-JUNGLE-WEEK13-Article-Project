package com.example.userdomain.domain.Post;
package com.example.board.entity;

import com.example.userdomain.domain.user.UserEntity;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "posts")
public class PostEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 게시글 ID

    private String title; // 게시글 제목
    private String content; // 게시글 내용

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user; // 작성자 (UserEntity와 연관)

    private LocalDateTime createdAt; // 작성 시간
    private LocalDateTime updatedAt; // 수정 시간

    private boolean enabled; // 게시글 활성화 여부 (삭제 여부)
    private boolean pinned; // 게시글 고정 여부

    // 생성자
    public PostEntity() {
    }

    public PostEntity(String title, String content, UserEntity user) {
        this.title = title;
        this.content = content;
        this.user = user;
        this.enabled = true; // 게시글은 기본적으로 활성화
        this.pinned = false; // 기본적으로 고정되지 않음
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

    // Getter, Setter
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public boolean isPinned() {
        return pinned;
    }

    public void setPinned(boolean pinned) {
        this.pinned = pinned;
    }
}
