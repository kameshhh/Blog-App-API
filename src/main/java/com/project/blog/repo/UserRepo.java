package com.project.blog.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.blog.entities.User;

public interface UserRepo extends JpaRepository<User, Integer> {

	Optional<User> findByUsername(String username);

}
