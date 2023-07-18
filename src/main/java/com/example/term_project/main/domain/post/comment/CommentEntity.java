package com.example.term_project.main.domain.post.comment;

import com.example.term_project.main.domain.post.PostEntity;
import com.example.term_project.main.global.entity.BaseEntityWithEditor;
import lombok.*;

import javax.persistence.*;

@MappedSuperclass
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CommentEntity extends BaseEntityWithEditor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long commentId;

    private String content;

    private String nameInPost;

    @ManyToOne
    @JoinColumn(name = "POST_ID")
    private PostEntity post;
}
