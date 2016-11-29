package com.dinosaur.module.user.dao;

import com.dinosaur.module.user.entity.User;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * 用户访问接口
 * @Author Alcott Hawk
 * @Date 11/21/2016
 */
public interface UserDAO extends JpaSpecificationExecutor<User>,PagingAndSortingRepository<User,String>{

    User findByNickName(String nickname);

}
