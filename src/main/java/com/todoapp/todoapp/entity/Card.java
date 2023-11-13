package com.todoapp.todoapp.entity;

import com.todoapp.todoapp.dto.card.CardRequestDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Table(name = "card")
@NoArgsConstructor
@Setter
@Getter
@Entity
public class Card extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 30)
    private String title;

    @Column(nullable = false, length = 800)
    private String contents;

    @Column()
    private Integer finish;

    //유저랑 게시글의 관계-> 게시글이 주인(N)
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    //게시글이랑 댓글의 관계-> 게시글은 댓글과의 관계에서 주인이 아니고(1)
    @OneToMany(mappedBy = "card")
    private List<Comments> commentsList = new ArrayList<>();



    public Card(CardRequestDto requestDto) {
        this.title = requestDto.getTitle();
        this.contents = requestDto.getContents();
        this.finish = 0;   // Db에서는 boolean지원 안하고 숫자 0,1로 true,false 구분 (0은 false)

    }

    public void update(CardRequestDto requestDto) {
        this.title = requestDto.getTitle();
        this.contents = requestDto.getContents();
    }

    public void finishChange(int check) {
        this.finish = check;
    }

}
