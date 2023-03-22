package com.karma.community.controller;


import com.karma.community.controller.request.ModifyArticleRequest;
import com.karma.community.controller.request.SearchArticleRequest;
import com.karma.community.controller.request.WriteArticleRequest;
import com.karma.community.model.dto.ArticleDto;
import com.karma.community.model.dto.CustomPrincipal;
import com.karma.community.model.util.CustomResponse;
import com.karma.community.service.ArticleService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/article")
@RequiredArgsConstructor
public class ArticleController {
    private final ArticleService articleService;

    /**
     * 게시글 전체조회
     * @param pageable
     * @return Page of article dto
     */
    @GetMapping
    public CustomResponse<Page<ArticleDto>> findAll(@PageableDefault Pageable pageable){
        return CustomResponse.success(articleService.findAll(pageable));
    }

    /**
     * 게시글 검색
     * @param req 게시글 검색 조건 (검색유형, 검색어)
     * @param pageable
     * @return  Page of article dto
     */
    @GetMapping("/search")
    public CustomResponse<Page<ArticleDto>> searchArticle(
            @RequestBody SearchArticleRequest req,
            @PageableDefault Pageable pageable
    ){
        return CustomResponse.success(articleService.searchArticle(req.getSearchType(), req.getSearchValue(), pageable));
    }


    /**
     * 게시글 쓰기
     * @param req 게시글 쓰기 요청 (title, content, hashtags)
     * @param principal 인증 context
     * @return 저장된 article id
     */
    @PostMapping
    public CustomResponse<Long> writeArticle(
            @RequestBody WriteArticleRequest req,
            @AuthenticationPrincipal CustomPrincipal principal
    ){
        return CustomResponse.success(articleService.writeArticle(req.toDto(), principal.toDto().toEntity()).articleId());
    }

    /**
     * 게시글 수정
     * @param req 게시글 수정 요청 (author, articleId, title, content, hashtags)
     * @param principal 인증 context
     * @return 수정된 article dto
     */
    @PutMapping
    public CustomResponse<ArticleDto> modifyArticle(
            @RequestBody ModifyArticleRequest req,
            @AuthenticationPrincipal CustomPrincipal principal
    ){
        return CustomResponse.success(articleService.modifyArticle(req.toDto(), principal.toDto().toEntity()));
    }

    /**
     * 게시글 삭제
     * @param req 게시글 삭제 요청 (article id)
     * @param principal 인증 context
     * @return nothing
     */
    @DeleteMapping
    public CustomResponse<Void> deleteArticle(
            @RequestBody ModifyArticleRequest req,
            @AuthenticationPrincipal CustomPrincipal principal
    ){
        articleService.deleteArticle(req.getArticleId(), principal.toDto().toEntity());
        return CustomResponse.success();
    }
}
