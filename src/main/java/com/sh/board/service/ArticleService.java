package com.sh.board.service;

import com.sh.board.domain.Article;
import com.sh.board.domain.UserAccount;
import com.sh.board.domain.type.SearchType;
import com.sh.board.dto.ArticleDto;
import com.sh.board.dto.ArticleWithCommentsDto;
import com.sh.board.repository.ArticleRepository;
import com.sh.board.repository.UserAccountRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;

import static com.sh.board.domain.type.SearchType.CONTENT;
import static com.sh.board.domain.type.SearchType.TITLE;

@Slf4j
@RequiredArgsConstructor
@Transactional
@Service
public class ArticleService {

    private final ArticleRepository articleRepository;
    private final UserAccountRepository userAccountRepository;

    //TODO: 나중에 다시 리팩토링 예정,,****
    @Transactional(readOnly = true)
    public Page<ArticleDto> searchArticles(SearchType searchType, String searchKeyword, Pageable pageable) {
        if(searchKeyword == null || searchKeyword.isBlank()){
            return articleRepository.findAll(pageable).map(ArticleDto::from);
        }

        Page<ArticleDto> result = Page.empty();
                switch (searchType){
                case TITLE:
                    result = articleRepository.findByTitleContaining(searchKeyword, pageable).map(ArticleDto::from);
                    break;
                case CONTENT:
                    result = articleRepository.findByContentContaining(searchKeyword, pageable).map(ArticleDto::from);
                    break;
                case ID:
                    result = articleRepository.findByUserAccount_UserIdContaining(searchKeyword, pageable).map(ArticleDto::from);
                    break;
                case NICKNAME:
                    result = articleRepository.findByUserAccount_NicknameContaining(searchKeyword, pageable).map(ArticleDto::from);
                    break;
                case HASHTAG:
                    result = articleRepository.findByHashtag("#" + searchKeyword, pageable).map(ArticleDto::from);
                    break;
                default:
                    break;
            }
            return result;
    }

    @Transactional(readOnly = true)
    public ArticleWithCommentsDto getArticleWithComments(Long articleId) {
        return articleRepository.findById(articleId)
                .map(ArticleWithCommentsDto::from)
                .orElseThrow(() -> new EntityNotFoundException("게시글이 없습니다 - articleId: " + articleId));
    }
    @Transactional(readOnly = true)
        public ArticleDto getArticle(Long articleId) {
            return articleRepository.findById(articleId)
                    .map(ArticleDto::from)
                    .orElseThrow(() -> new EntityNotFoundException("게시글이 없습니다 - articleId: " + articleId));
        }

    public void saveArticle(ArticleDto dto) {
        UserAccount userAccount = userAccountRepository.getReferenceById(dto.getUserAccountDto().getUserId());
        articleRepository.save(dto.toEntity(userAccount));
    }

    public void updateArticle(Long articleId, ArticleDto dto) {
        try {
            Article article = articleRepository.getReferenceById(articleId);
            if(dto.getTitle() != null){
                article.setTitle(dto.getTitle());
            }
            if(dto.getContent() != null){
                article.setContent(dto.getContent());
            }
            article.setHashtag(dto.getHashtag());
        } catch (EntityNotFoundException e) {
            log.warn("게시글 업데이트 실패, 게시글 찾지 못함 dto :" +  dto);
        }
    }

    public void deleteArticle(long articleId) {
        articleRepository.deleteById(articleId);
    }

    public long getArticleCount(){
        return articleRepository.count();
    }

}
