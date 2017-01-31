package com.dinosaur.module.classroom;

import com.dinosaur.core.util.DateUtil;
import com.dinosaur.core.util.ShiroUtil;
import com.dinosaur.module.classroom.dao.ClassroomDao;
import com.dinosaur.module.classroom.entity.Classroom;
import com.dinosaur.module.user.UserService;
import com.dinosaur.module.user.entity.User;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 教室service
 * @Author Alcott Hawk
 * @Date 1/12/2017
 */
@Service
@Transactional
public class ClassroomService {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private ClassroomDao classroomDao;

    @Autowired
    private UserService userService;

    /**
     * 创建一个班级
     * @param classroom
     * @return
     */
    public boolean create(Classroom classroom){
        classroom.setCreateTime(DateUtil.getCurrentTime());
        classroom.setCreateUser(ShiroUtil.getCurrentUserId().toString());
        classroom.setSuspend(true);
        classroomDao.save(classroom);
        return true;
    }

    /**
     * 查询当前用户所在的班级信息
     * @return
     */
    public List<Classroom> getByCurrentUser(){
        // TODO 具体查询业务待实现
        classroomDao.findAll((Root<Classroom> root, CriteriaQuery<?> query, CriteriaBuilder cb)->{
            return null;
        });
        return null;
    }

    /**
     * 获取创建者所创建的班级信息
     * @param userId 创建者用户id
     * @return
     */
    public List<Classroom> getByCreateUser(String userId){
        List<Classroom> classrooms = classroomDao.findByCreateUser(userId);
        if (null != classrooms){
            return classrooms;
        } else {
            return new ArrayList<>();
        }
    }

    /**
     * 查询特定的班级信息
     * @param classroomId 班级id
     * @return
     */
    public Classroom getById(String classroomId){
        Classroom classroom = classroomDao.findOne(classroomId);
        if (null == classroom){
            return new Classroom();
        } else {
            return classroom;
        }
    }

    /**
     * 添加用户到目标班级中
     * @param targetClass 目标班级
     * @param students 用户集合
     * @return
     */
    public boolean addStudent(String targetClass, List students){
        Classroom classroom = classroomDao.findOne(targetClass);
        Set<User> users = new HashSet<User>();
        List<User> userList = userService.getAllUser(students);
        if (userList.size()>0){
            userList.forEach(u -> {
                users.add(u);
            });
            classroom.setStudents(users);
            classroomDao.save(classroom);
            userService.add(userList);
            return true;
        } else {
            return false;
        }
    }

    /**
     * 删除一个教室资源
     * @param id
     * @return
     */
    public boolean delete(String id){
        if (StringUtils.isBlank(id)){
            return false;
        }
        classroomDao.delete(id);
        return true;
    }

    /**
     * 激活或者挂起一个班级
     * @param id 班级id
     * @param isSuspend 是否是挂起状态
     * @return
     */
    public boolean SuspendOrActive(String id,boolean isSuspend){
        if (StringUtils.isNoneBlank(id)){
            Classroom classroom = classroomDao.findOne(id);
            if (null != classroom){
                if (isSuspend && classroom.isSuspend()){
                    classroomDao.active(id);
                    return true;
                } else if (!isSuspend && !classroom.isSuspend()){
                    classroomDao.suspend(id);
                    return true;
                } else {
                    return false;
                }
            } else {
                return false;
            }
        } else {
            return  false;
        }
    }

}
