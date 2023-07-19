package com.example.term_project.main.domain.post.comment;

import com.example.term_project.main.domain.post.PostEntity;
import com.example.term_project.main.global.entity.BaseEntityWithEditor;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

@Entity
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@EntityListeners({AuditingEntityListener.class})
@Table(name = "COMMENTS")
public class CommentEntity extends BaseEntityWithEditor {
    @ManyToOne
    @JoinColumn(name = "POST_ID")
    private PostEntity post;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long commentId;

    private String content;

    private String nameInPost;

}
