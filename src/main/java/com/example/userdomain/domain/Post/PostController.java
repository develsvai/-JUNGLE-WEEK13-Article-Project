package com.example.userdomain.domain.Post;

import com.example.userdomain.dto.PostDTO;
import com.example.userdomain.dto.PostUpdateDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/posts")
public class PostController {

    private final PostService postService;

    @Autowired
    public PostController(PostService postService) {
        this.postService = postService;
    }

    // 게시글 생성
    @PostMapping("/create")
    public ResponseEntity<Map<String, String>> createPost(@RequestBody PostDTO postDTO , @CookieValue(value = "jwt", required = false) String jwt) {
        try {
            postService.createPost(postDTO.getTitle(), postDTO.getContent(), jwt);
            Map<String, String> response = new HashMap<>();
            response.put("message", "Post Create Success");
            return ResponseEntity.ok(response);
        }catch (IllegalArgumentException e) {
            Map<String, String> response = new HashMap<>();
            response.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
        }
    }

    // 게시글 수정
    @PutMapping("/update")
    public ResponseEntity<Map<String, String>> updatePost(@RequestBody PostUpdateDTO postUpdateDTO , @CookieValue(value = "jwt", required = false) String jwt) {
        try {
            postService.updatePost(
                    postUpdateDTO.getId(),
                    postUpdateDTO.getTitle(),
                    postUpdateDTO.getContent(),
                    postUpdateDTO.isPinned(),
                    jwt
            );

            Map<String, String> response = new HashMap<>();
            response.put("message", "Post Ppdate Success");
            return ResponseEntity.ok(response);

        }catch (IllegalArgumentException e) {
            Map<String, String> response = new HashMap<>();
            response.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
        }

    }

    // 게시글 삭제
    @DeleteMapping("/delete/{postId}")
    public ResponseEntity<Map<String, String>> deletePost(@PathVariable Long postId, @CookieValue(value = "jwt", required = false) String jwt ) {

        try {
            postService.deletePost(postId,jwt);

            Map<String, String> response = new HashMap<>();
            response.put("message", "Post Delete Success");
            return ResponseEntity.ok(response);

        }catch (IllegalArgumentException e) {

            Map<String, String> response = new HashMap<>();
            response.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.CONFLICT).body(response);

        }
    }

    // 게시글 조회
    @GetMapping("/post/{postId}")
    public PostEntity getPost(@PathVariable Long postId) {

        return postService.getPost(postId);
    }

    // 모든 게시글 조회
    @GetMapping("/all")
    public List<PostEntity> getAllPosts() {
        return postService.getAllPosts();
    }

    // 제목으로 게시글 검색
    @GetMapping("/search")
    public List<PostEntity> searchPostsByTitle(@RequestParam String title) {
        return postService.searchPostsByTitle(title);
    }
}
