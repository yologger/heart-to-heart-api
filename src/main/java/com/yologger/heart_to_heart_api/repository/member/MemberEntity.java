package com.yologger.heart_to_heart_api.repository.member;

import com.yologger.heart_to_heart_api.repository.base.BaseEntity;
import com.yologger.heart_to_heart_api.repository.post.PostEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name= "member")
@Getter
@NoArgsConstructor
public class MemberEntity extends BaseEntity {

    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 200, nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String nickname;

    @Column(nullable = false)
    private String password;

    @Column(length = 500, nullable = true)
    private String accessToken;

    @Column(length = 500, nullable = true)
    private String refreshToken;

    @Column(length = 1000, nullable = true)
    private String avatarUrl;

    @OneToMany(mappedBy = "writer", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<PostEntity> posts = new ArrayList<>();

    @Builder
    public MemberEntity(String email, String name, String nickname, String password) {
        this.email = email;
        this.name = name;
        this.nickname = nickname;
        this.password = password;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public void setAvatarUrl(String url) { this.avatarUrl = url; }

    public void clearAccessToken() {
        this.accessToken = null;
    }

    public void clearRefreshToken() {
        this.refreshToken = null;
    }

    public void addPost(PostEntity post) {
        this.posts.add(post);
    }

    public void addPosts(List<PostEntity> posts) {
        this.posts.addAll(posts);
    }
}
