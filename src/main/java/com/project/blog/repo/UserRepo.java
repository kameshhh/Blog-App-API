package com.project.blog.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.blog.entities.User;

public interface UserRepo extends JpaRepository<User, Integer> {

}
