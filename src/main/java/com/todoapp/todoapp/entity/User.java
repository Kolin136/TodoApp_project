package com.todoapp.todoapp.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Table(name = "users")
@NoArgsConstructor
@Getter
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 10, unique = true)
    private String username;

    @Column(nullable = false, length = 100)
    private String password;

    //지금은 회원 탈퇴기능없지만 있을경우 cardList,commentsList에 cascade = CascadeType.REMOVE 적용
    //유저랑 게시글 관계-> 유저는 게시글의 관계에서 주인이 아니고(1)
    @OneToMany(mappedBy = "user")
    private List<Card> cardList = new ArrayList<>();

    //유저랑 댓글의 관계-> 유저는 댓글과의 관계에서 주인이 아니고(1)
    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
    private List<Comments> commentsList = new ArrayList<>();


    public User(String username, String password) {

        this.username = username;
        this.password = password;
    }

    public void cardListAdd(Card card) {
        this.cardList.add(card);
    }

    public void commentsListAdd(Comments comments) {
        this.commentsList.add(comments);
    }

}
