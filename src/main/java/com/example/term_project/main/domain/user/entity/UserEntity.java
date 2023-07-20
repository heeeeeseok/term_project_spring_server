package com.example.term_project.main.domain.user.entity;

import com.example.term_project.main.domain.post.PostEntity;
import com.example.term_project.main.global.entity.BaseEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Entity
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@EntityListeners({AuditingEntityListener.class})
@Table(name = "USERS")
public class UserEntity extends BaseEntity {
    @OneToMany(mappedBy = "user")
    @Builder.Default
    private List<PostEntity> postList = new ArrayList<>();

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    private Long androidId;

    @Column(unique = true)
    private String email;

    private String password;

    @Column(unique = true)
    private String userName;
}
