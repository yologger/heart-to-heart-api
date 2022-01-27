//package com.yologger.heart_to_heart_api.repository.user;
//
//import com.yologger.heart_to_heart_api.repository.base.BaseEntity;
//import lombok.AllArgsConstructor;
//import lombok.Builder;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//
//import javax.persistence.*;
//
//@Entity
//@Table(name= "user")
//@Builder
//@Getter
//@AllArgsConstructor
//@NoArgsConstructor
//public class UserEntity extends BaseEntity {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    @Column(length = 200, nullable = false, unique = true)
//    private String email;
//
//    @Column(nullable = false)
//    private String name;
//
//    @Column(nullable = false)
//    private String nickname;
//
//    @Column(nullable = false)
//    private String password;
//}
