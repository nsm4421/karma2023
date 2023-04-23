package com.karma.myapp.service;

import com.karma.myapp.domain.dto.ArticleCommentDto;
import com.karma.myapp.domain.dto.CustomPrincipal;
import com.karma.myapp.domain.entity.ArticleCommentEntity;
import com.karma.myapp.domain.entity.ArticleEntity;
import com.karma.myapp.domain.entity.UserAccountEntity;
import com.karma.myapp.exception.CustomErrorCode;
import com.karma.myapp.exception.CustomException;
import com.karma.myapp.repository.ArticleCommentRepository;
import com.karma.myapp.repository.ArticleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
@Transactional
public class ArticleCommentService {

    private final ArticleRepository articleRepository;
    private final ArticleCommentRepository articleCommentRepository;

    /**
     * 댓글 가져오기
     *
     * @param articleId       게시글 id
     * @param parentCommentId 부모 댓글 id
     * @param pageable
     * @return page of comment dto
     */
    @Transactional(readOnly = true)
    public Page<ArticleCommentDto> getComments(Long articleId, Long parentCommentId, Pageable pageable) {
        // parent comments
        if (parentCommentId == null) {
            return articleCommentRepository.getParentCommentsByArticleId(articleId, pageable).map(ArticleCommentDto::from);
        }
        // child comments
        return articleCommentRepository.getChildCommentsByParentCommentId(parentCommentId, pageable).map(ArticleCommentDto::from);
    }

    /**
     * 댓글 작성
     *
     * @param principal       로그인한 유저의 인증정보
     * @param articleId       게시글 id
     * @param parentCommentId 부모댓글 id
     * @param content         댓글내용
     * @return 댓글 dto
     */
    public ArticleCommentDto writeComment(CustomPrincipal principal, Long articleId, Long parentCommentId, String content) {
        UserAccountEntity user = UserAccountEntity.from(principal);
        ArticleEntity article = articleRepository.getReferenceById(articleId);
        return ArticleCommentDto.from(articleCommentRepository.save(ArticleCommentEntity.of(article, user, content, parentCommentId)));
    }

    /**
     * 댓글 수정
     *
     * @param principal 로그인한 유저의 인증정보
     * @param commentId 댓글 id
     * @param content   댓글내용
     * @return 댓글 dto
     */
    public ArticleCommentDto modifyComment(CustomPrincipal principal, Long commentId, String content) {
        UserAccountEntity user = UserAccountEntity.from(principal);
        ArticleCommentEntity comment = articleCommentRepository.getReferenceById(commentId);
        // 로그인한 유저가 작성자가 맞는지 확인
        if (!comment.getUser().equals(user)) {
            throw CustomException.of(CustomErrorCode.NOT_GRANT, "login user and author is not same");
        }
        comment.setContent(content);
        return ArticleCommentDto.from(articleCommentRepository.save(comment));
    }

    /**
     * 댓글 삭제
     * @param principal 로그인한 유저의 인증정보
     * @param commentId 삭제할 댓글의 id
     */
    public void deleteComment(CustomPrincipal principal, Long commentId) {
        UserAccountEntity user = UserAccountEntity.from(principal);
        ArticleCommentEntity comment = articleCommentRepository.getReferenceById(commentId);
        // 로그인한 유저가 작성자가 맞는지 확인
        if (!comment.getUser().equals(user)) {
            throw CustomException.of(CustomErrorCode.NOT_GRANT, "login user and author is not same");
        }
        // 부모댓글인 경우 자식댓글도 제거
        if (comment.getParentCommentId() == null) {
            articleCommentRepository.deleteChildCommentsByParentCommentId(comment.getParentCommentId());
        }
        // 댓글 삭제
        articleCommentRepository.deleteById(commentId);
    }
}