package com.sh.board.domain;

import lombok.*;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

@Getter
@ToString
@Table(indexes = {
        @Index(columnList = "title"),
        @Index(columnList = "hashtag"),
        @Index(columnList = "createdAt"),
        @Index(columnList = "createdBy")
})
@EntityListeners(AuditingEntityListener.class)
@Entity
public class Article {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter @Column(nullable = false)
    private String title; //제목
    @Setter @Column(nullable = false, length = 10000)
    private String content; //내용
    @Setter
    private String hashtag; //해시태그

    @OrderBy("id")
    @OneToMany(mappedBy = "article", cascade = CascadeType.ALL)
    @ToString.Exclude
    private final Set<ArticleComment> articleComments = new LinkedHashSet<>();

    //TODO: 엔티티가 많아질시 Auditing 상속받게 수정
    @CreatedDate @Column(nullable = false)
    private LocalDateTime createdAt;
    @CreatedBy @Column(nullable = false, length = 100)
    private String createdBy;
    @LastModifiedDate @Column(nullable = false)
    private LocalDateTime modifiedAt;
    @LastModifiedBy @Column(nullable = false, length = 100)
    private String modifiedBy;

    protected Article(){}

    public Article(String title, String content, String hashtag) {
        this.title = title;
        this.content = content;
        this.hashtag = hashtag;
    }
    public static Article of(String title, String content, String hashtag){
        return new Article(title,content,hashtag);
    }

    //TODO lombok @EqualsAndHashCode 랑 리소스 비교
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Article article = (Article) o;
        return id != null && id.equals(article.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
