package com.dinosaur.module.user;

import com.dinosaur.core.util.*;
import com.dinosaur.module.group.dao.GroupDAO;
import com.dinosaur.module.group.entity.Group;
import com.dinosaur.module.user.dao.UserDAO;
import com.dinosaur.module.user.entity.User;
import org.apache.poi.ss.formula.functions.T;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

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

    @Autowired
    private GroupDAO groupDAO;

    /**
     * 通过用户名获取用户
     * @return 用户对象
     */
    public User getUserByUserNmae(String username){
        User user = userDAO.findByNickName(username);
        return user;
    }

    /**
     * 获取系统中用户数量
     * @return
     */
    public int getCount(){
        return (int) userDAO.count();
    }

    /**
     * 保存一个用户实体
     * @param user 用户对象
     */
    public <S extends T> User addUser(User user){
        String salt = EncryptUtil.encodeHex(EncryptUtil.salt(8));
        user.setSalt(salt);
        user.setCreateDate(DateUtil.getCurrentTime());
        SimpleHash simpleHash = new SimpleHash(HashKit.SHA1,user.getPassword(),salt,1024);
        user.setPassword(simpleHash.toString());
        user.setNickName(user.getName());
        return userDAO.save(user);
    }

    /**
     * 批量添加多个用户
     * @param users
     * @return
     */
    public boolean add(List<User> users){
        if (users.size()>0){
            userDAO.save(users);
            return true;
        } else {
          return false;
        }
    }

    /**
     * 删除一个用户
     * @param id
     * @return
     */
    public boolean delete(String id){
        userDAO.delete(id);
        return true;
    }

    public Page<User> getUserByPage(int pageSize,int pageNo){

        //Sort sort = new Sort(Sort.Direction.DESC);
        Pageable page = new PageRequest(pageNo - 1,pageSize);

        Specification<User> spec = new Specification<User>() {
            @Override
            public Predicate toPredicate(Root<User> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                return null;
            }
        };
        Page<User> users = userDAO.findAll(spec,page);
        return users;
    }

    /**
     * 添加一个会员关系
     * @return
     */
    public boolean addRelationship(String userId,String groupId){
        User user = userDAO.findOne(userId);
        Group group = groupDAO.findOne(groupId);
        Set<Group> groups = new HashSet<>();
        groups.add(group);
        user.setGroups(groups);
        userDAO.save(user);
        groupDAO.save(group);
        return true;
    }

    /**
     * 批量查询目标用户id的用户信息
     * @param targetUser
     */
    public List<User> getAllUser(List<String> targetUser){
        List<User> users = userDAO.findAll((Root<User> root, CriteriaQuery<?> query, CriteriaBuilder cb)->{
            List<Predicate> criterias = new ArrayList<Predicate>();
            if (targetUser.size()>0){
                List<Predicate> states = new ArrayList<Predicate>();
                targetUser.forEach(u->{
                    states.add(cb.equal(root.get(u).as(java.lang.String.class),u));
                });
                criterias.add(cb.or(states.toArray(new Predicate[]{})));
            }
            return cb.and(criterias.toArray(new Predicate[]{}));
        });
        return users;
    }

    /**
     * 用户批量导入
     * @param file
     * @return
     * @throws IOException
     */
    public boolean batchAdd(InputStream file) throws IOException {
        try {
            List<Map<String, Object>> data = FileUtil.readerExcel(file);
            List<User> userList = new ArrayList<>();
            data.forEach(n->{
                try {
                    User user = StringUtil.MapToBean(User.class,n);
                    userList.add(user);
                } catch (Exception e) {
                    logger.error("用户批量导入失败："+e.getMessage());
                    throw new RuntimeException(e.getMessage());
                }
            });
            return this.add(userList);
        } catch (IOException e) {
            logger.error("用户批量导入失败："+e.getMessage());
            throw new IOException(e.getMessage());
        }
    }

    /**
     * 导出用户数据到ecel文件
     * @param userId
     * @param response
     * @throws IOException
     */
    public byte[] exportUser(List<? extends Object> userId) throws IOException {
        List<User> userList = userDAO.findAll((Root<User> root, CriteriaQuery<?> query, CriteriaBuilder cb)->{
            return root.get("id").in(userId);
        });
        List<Map<String, Object>> data = new ArrayList<>();
        userList.forEach(u->{
            try {
                Map<String, Object> userMap = StringUtil.beanToMap(u);
                data.add(userMap);
            } catch (Exception e) {
               throw new RuntimeException(e.getMessage());
            }
        });
        ByteArrayOutputStream outputStream = FileUtil.exportExcel(data);
        byte[] content = outputStream.toByteArray();
        return content;
    }

}
