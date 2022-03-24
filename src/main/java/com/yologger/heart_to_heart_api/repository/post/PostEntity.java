package com.yologger.heart_to_heart_api.repository.post;

import com.yologger.heart_to_heart_api.repository.base.BaseEntity;
import com.yologger.heart_to_heart_api.repository.member.MemberEntity;
import com.yologger.heart_to_heart_api.repository.post_image.PostImageEntity;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name= "post")
@Getter
@Builder
@NoArgsConstructor
public class PostEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "content", nullable = false, columnDefinition = "TEXT")
    private String content;

    @ManyToOne
    @JoinColumn(name = "writer_id")
    private MemberEntity writer;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<PostImageEntity> imageUrls = new ArrayList<>();

    @Builder
    public PostEntity(Long id, String content, MemberEntity writer, List<PostImageEntity> imageUrls) {
        this.id = id;
        this.content = content;
        this.writer = writer;
        this.imageUrls = imageUrls;
    }

    public void addImageUrls(List<PostImageEntity> imageUrls) {
        this.imageUrls.addAll(imageUrls);
    }

    public void addImageUrl(PostImageEntity imageUrl) {
        this.imageUrls.add(imageUrl);
    }
}
