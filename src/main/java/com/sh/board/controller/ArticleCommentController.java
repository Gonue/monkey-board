package com.sh.board.controller;


import com.sh.board.dto.UserAccountDto;
import com.sh.board.dto.request.ArticleCommentRequest;
import com.sh.board.dto.request.ArticleRequest;
import com.sh.board.response.ArticleResponse;
import com.sh.board.service.ArticleCommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequiredArgsConstructor
@RequestMapping("/comments")
@Controller
public class ArticleCommentController {

    private final ArticleCommentService articleCommentService;

    @PostMapping("/new")
    public String postNewArticleComment(ArticleCommentRequest articleCommentRequest){

        //TODO: 인증 정보 넣어야함.
        articleCommentService.saveArticleComment(articleCommentRequest.toDto(UserAccountDto.of(
                "kim","1234","kim@gmail.com",null,null
        )));

        return "redirect:/articles/" + articleCommentRequest.getArticleId();
    }

    @PostMapping("/{commentId}/delete")
    public String deleteArticleComment(@PathVariable Long commentId, Long articleId){
        articleCommentService.deleteArticleComment(commentId);
        return "redirect:/articles/" + articleId;
    }

}
