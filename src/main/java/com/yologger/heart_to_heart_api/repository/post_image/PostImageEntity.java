package com.yologger.heart_to_heart_api.repository.post_image;

import com.yologger.heart_to_heart_api.repository.base.BaseEntity;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name= "post_image")
@ToString
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PostImageEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 1000, nullable = true)
    private String imageUrl;
}
