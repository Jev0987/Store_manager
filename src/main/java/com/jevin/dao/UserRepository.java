package com.jevin.dao;

import com.jevin.pojo.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/*
 *  @项目名：  Store_manager 
 *  @包名：    com.jevin.dao
 *  @文件名:   UserRepository
 *  @创建者:   85169
 *  @创建时间:  2019/9/11 16:13
 *  @描述：    TODO
 */
@Repository
public interface UserRepository extends JpaRepository<User , Long> {
    List<User> findUsersByUsername(String username);
    User findUsersById(Long id);
}
