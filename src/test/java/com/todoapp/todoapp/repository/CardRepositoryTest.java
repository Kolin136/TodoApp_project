package com.todoapp.todoapp.repository;

import com.todoapp.todoapp.entity.Card;
import com.todoapp.todoapp.entity.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class CardRepositoryTest {

    @Autowired
    CardRepository cardRepository;

    @Autowired
    UserRepository userRepository;

    // Card엔티티의 User필드 ManytoOne에 CascadeType.PERSIST 안했는데 "할일 카드 저장" 테스트에서는 영속화 오류 안나고,
    // "모든 할일 카드 유저 이름순 가져오기" 테스트에서 "할일 카드 저장" 테스트 방식처럼 Card에 User객체 생성하고 저장시 영속화 오류 납니다.

    @Test
    @DisplayName("할일 카드 저장")
    void saveCard() {
        // given
        Card card = new Card(1L, "제목", "내용", 0, new User("seok", "qwer"), new ArrayList<>());

        // when
        Card saveCard = cardRepository.save(card);

        // then
        assertNotNull(saveCard);
        assertEquals("제목", saveCard.getTitle());
        assertEquals("내용", saveCard.getContents());

    }

    @Test
    @DisplayName("모든 할일 카드 유저 이름순 가져오기")
    void allFindCard_OrderByUsername_Asc() {
        // given
        User user1 = new User("aaaa", "qwer");
        User user2 = new User("bbbb", "qwer");
        User user3 = new User("cccc", "qwer");

        userRepository.saveAll(Arrays.asList(user1, user2, user3));

        List<Card> cardList = new ArrayList<>(Arrays.asList(
                new Card(1L, "제목1", "내용1", 0, user1, new ArrayList<>()),
                new Card(2L, "제목2", "내용2", 0, user2, new ArrayList<>()),
                new Card(3L, "제목3", "내용3", 0, user3, new ArrayList<>())
        ));

        // 영속화 오류 확인해볼시 위에 부분은 주석하고 ↓이거는 주석 해제
//        List<Card> cardList = new ArrayList<>(Arrays.asList(
//                new Card(1L, "제목1", "내용1", 0,  new User("aaaa", "qwer"), new ArrayList<>()),
//                new Card(2L, "제목2", "내용2", 0,  new User("bbbb", "qwer"), new ArrayList<>()),
//                new Card(3L, "제목3", "내용3", 0,  new User("cccc", "qwer"), new ArrayList<>())
//        ));

        cardRepository.saveAll(cardList);

        // when
        List<Card> resultCardList = cardRepository.findAllByOrderByUserUsernameAscCreateAtDesc();

        // then
        assertEquals(resultCardList.get(1).getUser().getUsername(), "bbbb");
        assertEquals(resultCardList.size(), 3);


    }


}