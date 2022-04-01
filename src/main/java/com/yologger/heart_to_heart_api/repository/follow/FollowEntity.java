package com.yologger.heart_to_heart_api.repository.follow;

import com.yologger.heart_to_heart_api.repository.member.MemberEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(
    name = "follow",
    uniqueConstraints = {
        @UniqueConstraint(
                columnNames = {"follower_id", "following_id"}
        )
    }
)
@Getter
@NoArgsConstructor
public class FollowEntity {

    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "follower_id")
    private MemberEntity follower;

    @ManyToOne
    @JoinColumn(name = "following_id")
    private MemberEntity following;

    @Builder
    public FollowEntity(MemberEntity follower, MemberEntity following) {
        this.follower = follower;
        this.following = following;
    }
}
