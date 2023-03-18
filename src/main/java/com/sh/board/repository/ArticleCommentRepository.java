package com.sh.board.repository;

import com.querydsl.core.types.dsl.DateTimeExpression;
import com.querydsl.core.types.dsl.StringExpression;
import com.sh.board.domain.Article;
import com.sh.board.domain.ArticleComment;
import com.sh.board.domain.QArticle;
import com.sh.board.domain.QArticleComment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource
public interface ArticleCommentRepository extends
        JpaRepository<ArticleComment, Long>,
        QuerydslPredicateExecutor<ArticleComment>,
        QuerydslBinderCustomizer<QArticleComment>
{
    List<ArticleComment> findByArticle_Id(Long articleId);

    void deleteByIdAndUserAccount_UserId(Long articleCommentId, String userId);
    @Override
    default void customize(QuerydslBindings bindings, QArticleComment root){
        bindings.excludeUnlistedProperties(true);
        bindings.including(root.content, root.createdAt, root.createdBy);
        bindings.bind(root.content).first(StringExpression::containsIgnoreCase);
        bindings.bind(root.createdAt).first(DateTimeExpression::eq);
        bindings.bind(root.createdBy).first(StringExpression::containsIgnoreCase);
    }
}
