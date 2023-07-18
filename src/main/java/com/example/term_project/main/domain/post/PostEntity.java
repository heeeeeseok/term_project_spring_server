package com.example.term_project.main.domain.post;

import com.example.term_project.main.domain.user.entity.UserEntity;
import com.example.term_project.main.global.entity.BaseEntityWithEditor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@EntityListeners({AuditingEntityListener.class})
@Table(name = "POSTS")
public class PostEntity extends BaseEntityWithEditor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long postId;

    private String title;

    private String content;

    @ElementCollection
    private List<String> urlList;

    private int recommendCount;
    private int commentCount;

    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private UserEntity user;
}
