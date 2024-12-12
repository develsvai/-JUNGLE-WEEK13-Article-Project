package com.example.userdomain.domain.Post;

import com.example.userdomain.domain.user.UserEntity;
import com.example.userdomain.domain.user.UserRepository;
import com.example.userdomain.shared.utils.JwtTokenProvider;
import jakarta.persistence.Id;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    // 게시글 생성
    @Transactional
    public PostEntity createPost(String title, String content, String token) {
        String userMail = jwtTokenProvider.getUsername(token);
        UserEntity user = userRepository.findByEmail(userMail)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        PostEntity post = new PostEntity(title, content, user);

        return postRepository.save(post);
    }

    // 게시글 수정
    @Transactional
    public void updatePost(Long post_id, String newTitle, String newContent, boolean pinned, String token) {
        String userMail = jwtTokenProvider.getUsername(token);
        PostEntity post = postRepository.findById(post_id)
                .orElseThrow(() -> new IllegalArgumentException("post_id 를 찾을 수 없습니다."));

        if(userMail.equals(post.getUser().getEmail())) {
            post.changeTitle(newTitle);
            post.changeContent(newContent);
            post.changePinnedStatus(pinned);

            postRepository.save(post);

        }else {
            throw new IllegalArgumentException("해당 사용자의 게시글이 아닙니다.");
        }
    }

    // 게시글 삭제
    @Transactional
    public void deletePost(Long post_id ,String token) {
        String userMail = jwtTokenProvider.getUsername(token);
        PostEntity post = postRepository.findById(post_id)
                .orElseThrow(() -> new IllegalArgumentException("post_id 를 찾을 수 없습니다."));

        if(userMail.equals(post.getUser().getEmail())) {
            postRepository.delete(post);
        }else {
            throw new IllegalArgumentException("해당 사용자의 게시글이 아닙니다.");
        }
    }

    // 게시글 조회 (단일 게시글)
    public PostEntity getPost(Long postId) {
        PostEntity post = postRepository.findById(postId)
            .orElseThrow(() -> new IllegalArgumentException("post_id 를 찾을 수 없습니다."));
        return post;
    }

    // 게시글 리스트 조회 (모든 게시글)
    public List<PostEntity> getAllPosts() {
        return postRepository.findAll();
    }

    // 제목으로 게시글 검색
    public List<PostEntity> searchPostsByTitle(String title) {
        return postRepository.findByTitleContaining(title);
    }
}
