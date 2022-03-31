package com.yologger.heart_to_heart_api.repository.block;

import com.yologger.heart_to_heart_api.repository.member.MemberEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(
    name = "block",
    uniqueConstraints = {
        @UniqueConstraint(
            columnNames = {"member_id", "blocking"}
        )
    })
@Getter
@NoArgsConstructor
public class BlockEntity {

    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private MemberEntity member;

    @ManyToOne
    @JoinColumn(name = "blocking")
    private MemberEntity blocking;

    @Builder
    public BlockEntity(MemberEntity member, MemberEntity blocking) {
        this.member = member;
        this.blocking = blocking;
    }
}
