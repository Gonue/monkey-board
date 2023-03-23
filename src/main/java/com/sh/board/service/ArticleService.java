package com.sh.board.service;

import com.sh.board.domain.Article;
import com.sh.board.domain.Hashtag;
import com.sh.board.domain.UserAccount;
import com.sh.board.domain.type.SearchType;
import com.sh.board.dto.ArticleDto;
import com.sh.board.dto.ArticleWithCommentsDto;
import com.sh.board.repository.ArticleRepository;
import com.sh.board.repository.HashtagRepository;
import com.sh.board.repository.UserAccountRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.sh.board.domain.type.SearchType.CONTENT;
import static com.sh.board.domain.type.SearchType.TITLE;

@Slf4j
@RequiredArgsConstructor
@Transactional
@Service
public class ArticleService {

    private final HashtagService hashtagService;
    private final ArticleRepository articleRepository;
    private final UserAccountRepository userAccountRepository;
    private final HashtagRepository hashtagRepository;

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
                    result = articleRepository.findByHashtagNames(
                            Arrays.stream(searchKeyword.split(" ")).collect(Collectors.toList()), pageable).map(ArticleDto::from);
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

        Set<Hashtag> hashtags = renewHashtagsFromContent(dto.getContent());
        Article article = dto.toEntity(userAccount);
        article.addHashtags(hashtags);
        articleRepository.save(article);
    }

    public void updateArticle(Long articleId, ArticleDto dto) {
           try {
               Article article = articleRepository.getReferenceById(articleId);
               UserAccount userAccount = userAccountRepository.getReferenceById(dto.getUserAccountDto().getUserId());

               if (article.getUserAccount().equals(userAccount)) {
                   if (dto.getTitle() != null) { article.setTitle(dto.getTitle()); }
                   if (dto.getContent() != null) { article.setContent(dto.getContent()); }

                   Set<Long> hashtagIds = article.getHashtags().stream()
                           .map(Hashtag::getId)
                           .collect(Collectors.toUnmodifiableSet());
                   article.clearHashtags();
                   articleRepository.flush();

                   hashtagIds.forEach(hashtagService::deleteHashtagWithoutArticles);

                   Set<Hashtag> hashtags = renewHashtagsFromContent(dto.getContent());
                   article.addHashtags(hashtags);
               }
           } catch (EntityNotFoundException e) {
               log.warn("게시글 업데이트 실패. 게시글을 수정하는데 필요한 정보를 찾을 수 없습니다 - {}", e.getLocalizedMessage());
           }
       }

    public void deleteArticle(long articleId, String userId) {
        Article article = articleRepository.getReferenceById(articleId);
        Set<Long> hashtagIds = article.getHashtags().stream()
                        .map(Hashtag::getId)
                        .collect(Collectors.toUnmodifiableSet());
        articleRepository.deleteByIdAndUserAccount_UserId(articleId, userId);
        articleRepository.flush();
        hashtagIds.forEach(hashtagService::deleteHashtagWithoutArticles);
    }

    public long getArticleCount(){
        return articleRepository.count();
    }

    @Transactional(readOnly = true)
    public Page<ArticleDto> searchArticlesViaHashtag(String hashtagName, Pageable pageable) {
        if (hashtagName == null || hashtagName.isBlank()) {
            return Page.empty(pageable);
        }

        return articleRepository.findByHashtagNames(List.of(hashtagName), pageable)
                .map(ArticleDto::from);
    }

    public List<String> getHashtags() {
        return hashtagRepository.findAllHashtagNames();
    }

    private Set<Hashtag> renewHashtagsFromContent(String content){
        Set<String> hashtagNamesInContent = hashtagService.parseHashtagNames(content);
        Set<Hashtag> hashtags = hashtagService.findHashtagsByNames(hashtagNamesInContent);
        Set<String> existingHashtagNames = hashtags.stream()
                .map(Hashtag::getHashtagName)
                .collect(Collectors.toUnmodifiableSet());
        hashtagNamesInContent.forEach(newHashtagName -> {
            if (!existingHashtagNames.contains(newHashtagName)) {
                hashtags.add(Hashtag.of(newHashtagName));
            }
        });
        return hashtags;
    }
}
