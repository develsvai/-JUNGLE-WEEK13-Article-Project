package com.example.userdomain.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PostRepository extends JpaRepository<PostEntity, Long> {
    // 제목에 특정 문자열이 포함된 게시글을 조회
    List<PostEntity> findByTitleContaining(String title);

    // 사용자 이메일로 해당 사용자의 모든 게시글 조회
    List<PostEntity> findByUserEmail(String email);

    // 모든 게시글 조회 (페이징이나 정렬이 필요하면 추가)
    List<PostEntity> findAll();

    // 포스트 ID로 게시물 조회 (Optional 사용)
    Optional<PostEntity> findById(Long id);
}
