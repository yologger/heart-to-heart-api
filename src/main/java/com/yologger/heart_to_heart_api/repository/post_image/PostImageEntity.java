package com.yologger.heart_to_heart_api.repository.post_image;

import com.yologger.heart_to_heart_api.repository.base.BaseEntity;
import com.yologger.heart_to_heart_api.repository.post.PostEntity;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name= "post_image")
@Getter
@NoArgsConstructor
public class PostImageEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 1000, nullable = true)
    private String imageUrl;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private PostEntity post;

    @Builder
    public PostImageEntity(Long id, String imageUrl, PostEntity post) {
        this.id = id;
        this.imageUrl = imageUrl;
        this.post = post;
    }

    public void setPost(PostEntity post) {
        this.post = post;
    }
}
