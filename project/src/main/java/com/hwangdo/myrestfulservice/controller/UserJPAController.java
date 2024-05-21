package com.hwangdo.myrestfulservice.controller;

import com.hwangdo.myrestfulservice.bean.CountAndUsers;
import com.hwangdo.myrestfulservice.bean.Post;
import com.hwangdo.myrestfulservice.bean.User;
import com.hwangdo.myrestfulservice.exception.CustomNotFoundException;
import com.hwangdo.myrestfulservice.repository.PostRepository;
import com.hwangdo.myrestfulservice.repository.UserRepository;
import jakarta.validation.Valid;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/jpa")
public class UserJPAController {
    private final UserRepository userRepository;
    private final PostRepository postRepository;

    public UserJPAController(UserRepository userRepository, PostRepository postRepository) {
        this.userRepository = userRepository;
        this.postRepository = postRepository;
    }

    @GetMapping("/users")
    public List<User> retrieveAllUsers() {
        return userRepository.findAll();
    }

    // 강의 과제 - 사용자 전체 목록 보기에서 사용자 수를 함께 반환하도록 API 수정
    @GetMapping("/users/count")
    public CountAndUsers retrieveCountAndAllUsers() {
        List<User> users = userRepository.findAll();
        int count = users.size();
        CountAndUsers countAndUsers = new CountAndUsers(count, users);
        return countAndUsers;
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<EntityModel<User>> retrieveUserById(@PathVariable int id) {
        Optional<User> user = userRepository.findById(id);

        if(!user.isPresent()) {
            throw new CustomNotFoundException("id-" + id);
        }

        EntityModel<User> entityModel = EntityModel.of(user.get());

        WebMvcLinkBuilder link = linkTo(methodOn(this.getClass()).retrieveAllUsers());
        entityModel.add(link.withRel("all-users")); // all-users --> http://localhost:8088/jpa/users

        return ResponseEntity.ok(entityModel);
    }

    @DeleteMapping("/users/{id}")
    public void deleteUser(@PathVariable int id) {
        userRepository.deleteById(id);
    }

    @PostMapping("/users") // @Valid = 유효성 체크 적용 대상 설정
    public ResponseEntity<User> createUser(@Valid @RequestBody User user) {
        User savedUser = userRepository.save(user);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedUser.getId())
                .toUri();

        return ResponseEntity.created(location).build();
    }

    @GetMapping("/users/{id}/posts")
    public List<Post> retrieveAllPostsByUser(@PathVariable int id) {
        Optional<User> user = userRepository.findById(id);

        if(!user.isPresent()) {
            throw new CustomNotFoundException("id-" + id);
        }

        return user.get().getPosts();
    }

    @PostMapping("/users/{id}/posts")
    public ResponseEntity<Post> createPost(@PathVariable int id, @RequestBody Post post) {
        Optional<User> userOptional = userRepository.findById(id);

        if(!userOptional.isPresent()) {
            throw new CustomNotFoundException("id-" + id);
        }

        User user = userOptional.get();

        post.setUser(user);

        postRepository.save(post);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(post.getId())
                .toUri();

        return ResponseEntity.created(location).build();
    }

    @GetMapping("/users/{id}/posts/{postId}")
    public ResponseEntity<EntityModel<Post>> retrievePostByUser(@PathVariable int id, @PathVariable int postId) {
        Optional<User> userOptional = userRepository.findById(id);

        if(!userOptional.isPresent()) {
            throw new CustomNotFoundException("id-" + id);
        }

        User user = userOptional.get();

        List<Post> posts = user.getPosts();
        Optional<Post> post = postRepository.findById(postId);

        // post 존재 여부 및 해당 사용자가 post를 작성했는지 판단
        if(!post.isPresent() || !posts.contains(post.get())) {
            throw new CustomNotFoundException("postId-" + postId);
        }

        EntityModel<Post> entityModel = EntityModel.of(post.get());

        WebMvcLinkBuilder link = linkTo(methodOn(this.getClass()).retrieveAllPostsByUser(id));
        entityModel.add(link.withRel("all-posts")); // all-posts --> http://localhost:8088/jpa/users/{id}/posts/{postId}

        return ResponseEntity.ok(entityModel);
    }

    @DeleteMapping("/users/{id}/posts/{postId}")
    public void deletePostByUser(@PathVariable int id, @PathVariable int postId) {
        Optional<User> userOptional = userRepository.findById(id);

        if(!userOptional.isPresent()) {
            throw new CustomNotFoundException("id-" + id);
        }

        User user = userOptional.get();

        List<Post> posts = user.getPosts();

        Optional<Post> post = postRepository.findById(postId);

        // post 존재 여부 및 해당 사용자가 post를 작성했는지 판단
        if(!post.isPresent() || !posts.contains(post.get())) {
            throw new CustomNotFoundException("postId-" + postId);
        }

        postRepository.deleteById(postId);
    }
}
