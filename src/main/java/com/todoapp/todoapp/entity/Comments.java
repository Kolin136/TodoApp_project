package com.todoapp.todoapp.entity;

import com.todoapp.todoapp.dto.comment.CommentRequestDto;
import com.todoapp.todoapp.dto.comment.CommentResponseDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Table(name = "comments")
@NoArgsConstructor
@Setter
@Getter
@Entity
public class Comments extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 300)
    private String comment;

    //유저랑 댓글의 관계-> 댓글이 주인(N)
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    //게시글이랑 댓글의 관계-> 댓글이 주인(N)
    @ManyToOne
    @JoinColumn(name = "card_id")
    private Card card;

    public Comments(CommentRequestDto requestDto) {
        this.comment = requestDto.getComment();
    }

    public void update(CommentRequestDto requestDto) {
        this.comment = requestDto.getComment();
    }


}
