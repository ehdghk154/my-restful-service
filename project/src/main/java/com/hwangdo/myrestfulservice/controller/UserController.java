package com.hwangdo.myrestfulservice.controller;

import com.hwangdo.myrestfulservice.bean.User;
import com.hwangdo.myrestfulservice.dao.UserDaoService;
import com.hwangdo.myrestfulservice.exception.CustomNotFoundException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@Tag(name = "user-controller", description = "일반 사용자 서비스를 위한 컨트롤러입니다.")
public class UserController {
    private final UserDaoService service;

    // 의존성 주입 (생성자 주입)
    public UserController(UserDaoService service) {
        this.service = service;
    }

    @GetMapping("/users")
    public List<User> retrieveAllUsers() {
        return service.findAll();
    }

    @Operation(summary = "사용자 정보 조회 API", description = "사용자 ID를 이용하여 사용자 상세 정보 조회를 합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK !!"),
            @ApiResponse(responseCode = "400", description = "BAD REQUEST !!"),
            @ApiResponse(responseCode = "404", description = "USER NOT FOUND !!"),
            @ApiResponse(responseCode = "500", description = "INTERNAL SERVER ERROR !!")
        }
    )
    @GetMapping("/users/{id}")
    public EntityModel<User> retrieveUser(@Parameter(description = "사용자 ID", required = true, example = "1") @PathVariable int id) {
        User user = service.findOne(id);

        if(user == null) {
            throw new CustomNotFoundException(String.format("ID[%s] not found", id));
        }

        EntityModel<User> entityModel = EntityModel.of(user);

        WebMvcLinkBuilder link = linkTo(methodOn(this.getClass()).retrieveAllUsers());
        entityModel.add(link.withRel("all-users")); // all-users --> http://localhost:8088/users

        return entityModel;
    }

    @PostMapping("/users") // @Valid = 유효성 체크 적용 대상 설정
    public ResponseEntity<User> createUser(@Valid @RequestBody User user) {
        User savedUser = service.save(user);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedUser.getId())
                .toUri();

        return ResponseEntity.created(location).build();
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity deleteUser(@PathVariable int id) {
        User deletedUser = service.deleteById(id);

        if(deletedUser == null) {
            throw new CustomNotFoundException(String.format("ID[%s] not found", id));
        }

        return ResponseEntity.noContent().build();
    }
}
