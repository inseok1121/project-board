package com.project.projectboard.controller;

import com.project.projectboard.config.SecurityConfig;
import com.project.projectboard.dto.ArticleCommentDto;
import com.project.projectboard.dto.request.ArticleCommentRequest;
import com.project.projectboard.service.ArticleCommentService;
import com.project.projectboard.util.FormDataEncoder;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Map;

import static org.mockito.BDDMockito.*;
import static org.mockito.Mockito.doNothing;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@DisplayName("View Controller - 댓글")
@WebMvcTest(ArticleCommentController.class)
@Import({SecurityConfig.class, FormDataEncoder.class})
public class ArticleCommentControllerTests {

    private final MockMvc mvc;
    private final FormDataEncoder formDataEncoder;

    @MockBean private ArticleCommentService articleCommentService;

    public ArticleCommentControllerTests(
            @Autowired MockMvc mvc,
            @Autowired FormDataEncoder formDataEncoder
    ){
        this.mvc = mvc;
        this.formDataEncoder = formDataEncoder;
    }

    @DisplayName("[view][POST] 댓글 등록 - 정상 호출")
    @Test
    void givenArticleCommentInfo_whenRequesting_thenSaveNewArticleComment() throws Exception{

        long articleId = 1L;
        ArticleCommentRequest request = ArticleCommentRequest.of(1L, "new comment");
        willDoNothing().given(articleCommentService).saveArticleComment(any(ArticleCommentDto.class));

        mvc.perform(post("/comments/new")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .content(formDataEncoder.encode(request))
                .with(csrf())
                )
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/articles/"+articleId))
                .andExpect(redirectedUrl("/articles/"+articleId));

        then(articleCommentService).should().saveArticleComment(any(ArticleCommentDto.class));
    }

    @DisplayName("[view][POST] 댓글 삭제 - 정상 호출")
    @Test
    void givenArticleCommentId_whenRequestDelete_thenDeleteArticleComment() throws Exception{

        Long articleId = 1L;
        Long articleCommentId = 1L;

        willDoNothing().given(articleCommentService).deleteArticleComment(articleCommentId);

        mvc.perform(post("/comments/"+articleCommentId+"/delete")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .content(formDataEncoder.encode(Map.of("articleId", articleId)))
                .with(csrf())
                )
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/articles/"+articleId))
                .andExpect(redirectedUrl("/articles/"+articleId));

        then(articleCommentService).should().deleteArticleComment(articleCommentId);
    }


}
