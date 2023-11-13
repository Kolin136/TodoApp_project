package com.todoapp.todoapp.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Table(name = "users")
@NoArgsConstructor
@Setter
@Getter
@Entity
public class User  {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 10, unique = true)
    private String username;

    @Column(nullable = false, length = 15)
    private String password;

    @Column(nullable = false, length = 30)
    private String email;

    //유저랑 게시글 관계-> 유저는 게시글의 관계에서 주인이 아니고(1)
    @OneToMany(mappedBy = "user")
    private List<Card> cardList = new ArrayList<>();

    //유저랑 댓글의 관계-> 유저는 댓글과의 관계에서 주인이 아니고(1)
    @OneToMany(mappedBy = "user")
    private List<Comments> commentList = new ArrayList<>();




    public User(String username, String password, String email) {

        this.username = username;
        this.password = password;
        this.email = email;

    }


}
