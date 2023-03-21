package com.sh.board.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

@NoArgsConstructor
@Getter
@ToString(callSuper = true)
@Entity
@Table(indexes = {
        @Index(columnList = "hashtagName", unique = true),
        @Index(columnList = "createdAt"),
        @Index(columnList = "createdBy")
})
public class Hashtag extends AuditingFields{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter @Column(nullable = false)
    private String hashtagName;

    @ToString.Exclude
    @ManyToMany(mappedBy = "hashtags")
    private Set<Article> articles = new LinkedHashSet<>();

    private Hashtag(String hashtagName){
        this.hashtagName = hashtagName;
    }

    public static Hashtag of(String hashtagName){
        return new Hashtag(hashtagName);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Hashtag that = (Hashtag) o;
        return this.getId() != null && this.getId().equals(that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getId());
    }
}
