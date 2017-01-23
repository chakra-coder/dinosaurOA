package com.dinosaur.module.classroom;

import com.dinosaur.core.util.DateUtil;
import com.dinosaur.module.classroom.dao.ClassroomDao;
import com.dinosaur.module.classroom.entity.Classroom;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    /**
     * 创建一个教室资源
     * @param classroom
     * @return
     */
    public boolean create(Classroom classroom){
        classroom.setCreateTime(DateUtil.getCurrentTime());
        classroomDao.save(classroom);
        return true;
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

    /**
     * 添加一个班级信息
     * @param classroom
     * @return
     */
    public Classroom add(Classroom classroom){
        classroom.setCreateTime(DateUtil.getCurrentTime());
        return classroomDao.save(classroom);
    }

}
