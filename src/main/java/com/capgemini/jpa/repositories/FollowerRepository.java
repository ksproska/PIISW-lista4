package com.capgemini.jpa.repositories;

import com.capgemini.jpa.entities.Follower;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FollowerRepository extends JpaRepository<Follower, Long> {
    @EntityGraph(value = "follower-graph")
    List<Follower> findAllByUserId(String userId);
}
