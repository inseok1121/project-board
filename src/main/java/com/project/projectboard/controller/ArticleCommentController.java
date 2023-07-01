package com.project.projectboard.controller;

import com.project.projectboard.dto.UserAccountDto;
import com.project.projectboard.dto.request.ArticleCommentRequest;
import com.project.projectboard.service.ArticleCommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequiredArgsConstructor
@Controller
@RequestMapping("/comments")
public class ArticleCommentController {

    private final ArticleCommentService articleCommentService;

    @PostMapping("/new")
    public String postNewArticleComment(
            ArticleCommentRequest articleCommentRequest
    ){

        articleCommentService.saveArticleComment(articleCommentRequest.toDto(
                UserAccountDto.of("uno", "pw", "nickname@gamil.com", "nickname", "memo")
        ));

        return "redirect:/articles/"+articleCommentRequest.articleId();
    }

    @PostMapping("/{articleCommentId}/delete")
    public String deleteArticleComment(
        @PathVariable Long articleCommentId,
        Long articleId
    ){
        articleCommentService.deleteArticleComment(articleCommentId);

        return "redirect:/articles/"+articleId;
    }
}
