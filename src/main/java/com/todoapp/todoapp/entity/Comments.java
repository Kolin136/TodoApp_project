package com.todoapp.todoapp.entity;

import com.todoapp.todoapp.dto.comment.CommentRequestDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name = "comments")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Entity
public class Comments extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 300)
    private String comment;

    //유저랑 댓글의 관계-> 댓글이 주인(N)
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private User user = new User();

    //게시글이랑 댓글의 관계-> 댓글이 주인(N)
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "card_id")
    private Card card = new Card();

    public Comments(CommentRequestDto requestDto, Card card) {
        this.comment = requestDto.getComment();
        this.card = card;
        this.user = card.getUser();
    }

    public void update(CommentRequestDto requestDto) {
        this.comment = requestDto.getComment();
    }


}
