package com.project.projectboard.repository;

import com.project.projectboard.config.JpaConfig;
import com.project.projectboard.domain.Article;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("JPA 연결 Test")
@Import(JpaConfig.class)
@DataJpaTest
public class JpaRepositoryTests {

    private final ArticleRepository articleRepository;
    private final ArticleCommentRepository articleCommentRepository;

    public JpaRepositoryTests(
            @Autowired ArticleRepository articleRepository,
            @Autowired ArticleCommentRepository articleCommentRepository) {
        this.articleRepository = articleRepository;
        this.articleCommentRepository = articleCommentRepository;
    }

    @Test
    void SelectTest() {
        //given

        //when
        List<Article> articles = articleRepository.findAll();
        //then
        assertThat(articles)
                .isNotNull()
                .hasSize(50);
    }

    @Test
    @DisplayName("Insert Test")
    void InsertTest(){
        //given
        int prevCount = articleRepository.findAll().size();
        //when
        Article article = Article.of("new article", "new contents", "new hashtag");
        articleRepository.save(article);
        int newCount = articleRepository.findAll().size();

        //then
        assertThat(newCount)
                .isEqualTo(prevCount+1);

    }

    @Test
    @DisplayName("Update Test")
    void UpdateTest(){

        //given
        String newHashtag = "#new Hashtag";
        Article article = articleRepository.findById(1L).orElseThrow();
        article.setHashtag(newHashtag);
        //when
        Article savedArticle = articleRepository.saveAndFlush(article);
        //then
        assertThat(savedArticle.getHashtag())
                .isEqualTo(newHashtag);
    }

    @Test
    @DisplayName("Delete Test")
    void DeleteTest(){

        //given
        Article article = articleRepository.findById(1L).orElseThrow();
        int prevArticleCount = articleRepository.findAll().size();
        int prevArticleCommentCount = articleCommentRepository.findAll().size();
        int commentCount = article.getArticleComments().size();
        //when
        articleRepository.delete(article);

        //then
        assertThat(articleRepository.findAll().size()).isEqualTo(prevArticleCount-1);
        assertThat(articleCommentRepository.findAll().size()).isEqualTo(prevArticleCommentCount-commentCount);
    }
}
