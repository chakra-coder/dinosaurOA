package com.dinosaur.module.user;

import com.dinosaur.core.util.DateUtil;
import com.dinosaur.core.util.EncryptUtil;
import com.dinosaur.module.user.dao.UserDAO;
import com.dinosaur.module.user.entity.User;
import org.apache.poi.ss.formula.functions.T;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 用户service
 * @Author Alcott Hawk
 * @Date 11/21/2016
 */
@Service
@Transactional
public class UserService {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private UserDAO userDAO;

    /**
     * 通过用户名获取用户
     * @return 用户对象
     */
    public User getUserByUserNmae(String username){
        User user = userDAO.findByNickName(username);
        return user;
    }

    /**
     * 保存一个用户实体
     * @param user 用户对象
     */
    public <S extends T> User addUser(User user){
        String salt = EncryptUtil.encodeHex(EncryptUtil.salt(8));
        user.setSalt(salt);
        user.setPassword(EncryptUtil.hash(salt,user.getPassword(),1024));
        user.setCreateDate(DateUtil.getCurrentTime());
        return userDAO.save(user);
    }

}
