package com.sh.board.controller;

import com.sh.board.domain.type.SearchType;
import com.sh.board.response.ArticleResponse;
import com.sh.board.response.ArticleWithCommentResponse;
import com.sh.board.service.ArticleService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
@RequiredArgsConstructor
@RequestMapping("articles")
@Controller
public class ArticleController {

    private final ArticleService articleService;
    @GetMapping
    public String articles(
            @RequestParam(required = false)SearchType searchType,
            @RequestParam(required = false)String searchValue,
            @PageableDefault(size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable,
            ModelMap map
            ){
        map.addAttribute("articles", articleService.searchArticles(searchType, searchValue, pageable)
                .map(ArticleResponse::from));
        return "articles/index";
    }

    @GetMapping("/{articleId}")
        public String article(@PathVariable Long articleId, ModelMap map){
            ArticleWithCommentResponse articleWithCommentResponse =  ArticleWithCommentResponse.from(articleService.getArticle(articleId));
            map.addAttribute("article", articleWithCommentResponse);
            map.addAttribute("articleComments", articleWithCommentResponse.getArticleCommentResponses());
            return "articles/detail";
        }
}
