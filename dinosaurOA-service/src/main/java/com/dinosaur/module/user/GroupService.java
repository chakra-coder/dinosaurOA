package com.dinosaur.module.user;

import com.dinosaur.module.group.dao.GroupDAO;
import com.dinosaur.module.group.entity.Group;
import org.apache.poi.ss.formula.functions.T;
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
import java.util.List;

/**
 * 用户组service
 * @Author Alcott Hawk
 * @Date 12/2/2016
 */
@Service
@Transactional
public class GroupService {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private GroupDAO groupDAO;

    /**
     * 查询一个组通过特定的id
     * @param id 组id
     * @return
     */
    public Group findById(String id){
        return groupDAO.findOne(id);
    }

    /**
     * 保存一个组信息
     * @param group
     * @param <S>
     * @return
     */
    public <S extends T> Group add(Group group){
         return groupDAO.save(group);
    }

    /**
     * 查询用户组信息，并提供分类
     * @param pageSize
     * @param pageNo
     * @return
     */
    public Page<Group> getGroupByPage(int pageSize,int pageNo){
        //Sort sort = new Sort(Sort.Direction.DESC);
        Pageable page = new PageRequest(pageNo - 1,pageSize);

        Specification<Group> spec = new Specification<Group>() {
            @Override
            public Predicate toPredicate(Root<Group> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                return null;
            }
        };
        Page<Group> groups = groupDAO.findAll(spec,page);
        return groups;
    }

    /**
     * 查询所有的用户组信息
     * @return
     */
    public List<Group> getAll(){
        Specification<Group> spec = new Specification<Group>() {
            @Override
            public Predicate toPredicate(Root<Group> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                return null;
            }
        };
        List<Group> groups = groupDAO.findAll(spec);
        return  groups;
    }

    /**
     * 删除一个用户组
     * @param id 用户组id
     * @return
     */
    public boolean delete(String id){
        groupDAO.delete(id);
        return true;
    }

    /**
     * 通过用户id获取用户所在的组
     * @param userId 用户id
     * @return
     */
    public List<Group> getGroupByUser(String userId){
        return groupDAO.getGroupByUser(userId);
    }

}
