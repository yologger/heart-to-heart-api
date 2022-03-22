package com.yologger.heart_to_heart_api.repository.post;

import com.yologger.heart_to_heart_api.repository.base.BaseEntity;
import com.yologger.heart_to_heart_api.repository.member.MemberEntity;
import com.yologger.heart_to_heart_api.repository.post_image.PostImageEntity;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name= "post")
@ToString
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PostEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "content", nullable = false, columnDefinition = "TEXT")
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "writer_id")
    private MemberEntity writer;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    private List<PostImageEntity> imageUrls;
}
