package com.metacoding.springv2.user;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

//findById, findAll, save, deleteById, count, paging 기능을 기본 탑재
public interface UserRepository extends JpaRepository<User, Integer> { // CRUD를 만들 필요가 없음

    // username으로 정보 찾기
    @Query("select u from User u where u.username = :username")
    public Optional<User> findByUsername(@Param("username") String username);
}