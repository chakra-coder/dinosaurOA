package com.dinosaur.module.job;

import com.dinosaur.core.shiro.ShiroUser;
import com.dinosaur.core.util.DateUtil;
import com.dinosaur.module.job.dao.JobDao;
import com.dinosaur.module.job.entity.Job;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 作业service
 * @Author Alcott Hawk
 * @Date 1/6/2017
 */
@Service
@Transactional
public class JobService {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private JobDao jobDao;

    /**
     * 发布一个作业
     * @param job
     * @return
     */
    public boolean deploy(Job job){
        ShiroUser shiroUser = (ShiroUser) SecurityUtils.getSubject().getPrincipal();
        job.setCreateTime(DateUtil.getCurrentTime());
        jobDao.save(job);
        return true;
    }

    /**
     * 删除一个作业
     * @param id 作业id
     * @return
     */
    public boolean delete(String id){
        if (StringUtils.isBlank(id)){
            return false;
        }
        jobDao.delete(id);
        return true;
    }

    /**
     * 获取单条作业记录
     * @param id
     * @return
     */
    public Job findOne(String id){
        if (StringUtils.isBlank(id)){
            return null;
        }
        return jobDao.findOne(id);
    }

    /**
     * 查询属于特定班级的作业的集合
     * @param id
     * @return
     */
    public List<Job> findByClassRoom(String id){
        if (StringUtils.isBlank(id)){
            return null;
        }
        return jobDao.findByClassRoom(id);
    }

    /**
     * 查询指定作者的作业数据
     * @param author 作者id
     * @return
     */
    public List<Job> findByAuthor(String author){
        if (StringUtils.isBlank(author)){
            return null;
        }
        return jobDao.findByAuthor(author);
    }

}
