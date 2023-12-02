package com.todoapp.todoapp.service;

import com.todoapp.todoapp.customException.uqualsException;
import com.todoapp.todoapp.dto.comment.CommentRequestDto;
import com.todoapp.todoapp.dto.comment.CommentResponseDto;
import com.todoapp.todoapp.entity.Card;
import com.todoapp.todoapp.entity.Comments;
import com.todoapp.todoapp.entity.User;
import com.todoapp.todoapp.repository.CardRepository;
import com.todoapp.todoapp.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
@RequiredArgsConstructor
public class AppCommentService {
    private final CommentRepository commentRepository;
    private final CardRepository cardRepository;

    public CommentResponseDto createComment(Long id, CommentRequestDto requestDto, User user) {
        Card card = findCard(id);

        Comments comments = new Comments(requestDto,card);
        card.commentsListAdd(comments);
        user.commentsListAdd(comments);

        commentRepository.save(comments);
        CommentResponseDto commentResponseDto = new CommentResponseDto(comments);

        return commentResponseDto;
    }

    public CommentResponseDto updateComment(Long cardid, Long commentid, CommentRequestDto requestDto, User user) throws uqualsException {
        Comments comments = findComment(commentid);

        if(!comments.getUser().getUsername().equals(user.getUsername())){
            throw new uqualsException();
        }

        comments.update(requestDto);

//        이렇게 댓글을 수정후 체크해보는데 카드입장에서는 바로 즉시 수정한게 반영되는데 유저입장도 수정된게 반영되긴하나 카드처럼 즉시는 안됩니다.
        //지금 테스트는 한게시글에 대해서 동일 유저가 댓글 여러개 적었을떄.
//         (예시 출력결과 ↓) 영속성컨텍스트랑 관련 있는것같긴한데 정확히 무슨 이유인지 모르겠습니다...
//        카드입장체크 Good다시수정2
//        카드입장체크 Hi수정
//        카드입장체크 oh수정
//        유저입장체크 Good다시수정
//        유저입장체크 Hi수정
//        유저입장체크 oh수정

//        Card card = findCard(cardid);
//        for (Comments comments1 : card.getCommentsList()) {
//            System.out.println("카드입장체크" + comments1.getComment());
//        }
//        for (Comments comments1 : user.getCommentsList()) {
//            System.out.println("유저입장체크" + comments1.getComment());
//        }

        return new CommentResponseDto(comments);
    }

    public void deleteComment(Long id, User user) throws uqualsException {
        Comments comments = findComment(id);

        if(!comments.getUser().getUsername().equals(user.getUsername())){
            throw new uqualsException();
        }

        commentRepository.delete(comments);

    }

    private Comments findComment(Long id) {
        return commentRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("해당 댓글은 없습니다")
        );
    }

    private Card findCard(Long id) {
        return cardRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("해당 앱카드는 없습니다")
        );
    }


}
