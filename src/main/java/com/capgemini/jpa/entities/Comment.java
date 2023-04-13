package com.capgemini.jpa.entities;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Comment {

    @Id
    private Long id;

    @Column(nullable = false)
    private String content;

    @OneToMany(mappedBy = "comment")
    private List<Follower> followers;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "EVENT_ID", nullable = false)
    private Event event;

    public Comment(Long id, String content, Event event) {
        this.id = id;
        this.content = content;
        this.event = event;
    }
}
