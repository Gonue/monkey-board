package com.sh.board.repository.querydsl;

import com.sh.board.domain.Article;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Collection;
import java.util.List;

public interface ArticleRepositoryCustom {
    @Deprecated
    List<String> findAllDistinctHashtags();
    Page<Article> findByHashtagNames(Collection<String> hashtagNames, Pageable pageable);
}
