package com.capgemini.jpa.tasks;

import com.capgemini.jpa.entities.Comment;
import com.capgemini.jpa.entities.Follower;
import com.capgemini.jpa.repositories.CommentRepository;
import com.capgemini.jpa.repositories.EventRepository;
import com.capgemini.jpa.repositories.FollowerRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import java.time.LocalDateTime;
import java.util.HashMap;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;


@DataJpaTest
class Task6 {
    @Autowired
    private FollowerRepository followerRepository;
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private EventRepository eventRepository;

    @PersistenceContext
    EntityManager entityManager;

    @Test
    void shouldGetFollowerUsingNamedGraph() {
        commentRepository.saveAndFlush(
                new Comment(1L, "some comment",
                        eventRepository.findById(11L).orElse(null)
                )
        );
        commentRepository.saveAndFlush(
                new Comment(2L, "some comment",
                        eventRepository.findById(12L).orElse(null)
                )
        );
        commentRepository.saveAndFlush(
                new Comment(3L, "some comment",
                        eventRepository.findById(13L).orElse(null)
                )
        );
        assertThat("comments should be added", commentRepository.findAll().size(), is(3));

        followerRepository.saveAndFlush(new Follower(1L, "user_1", LocalDateTime.now(), commentRepository.getById(2L)));
        followerRepository.saveAndFlush(new Follower(2L, "user_2", LocalDateTime.now(), commentRepository.getById(3L)));
        followerRepository.saveAndFlush(new Follower(3L, "user_3", LocalDateTime.now(), commentRepository.getById(1L)));
        assertThat("Followers should be added", followerRepository.findAll().size(), is(3));

        var followers = followerRepository.findAllByUserId("user_2");
        assertThat(followers.size(), is(1));
    }
}
