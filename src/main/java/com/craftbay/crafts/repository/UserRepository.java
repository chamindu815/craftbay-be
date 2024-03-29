package com.craftbay.crafts.repository;

import com.craftbay.crafts.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User,Integer> {
    public User findUserByUsername(String username);
}
