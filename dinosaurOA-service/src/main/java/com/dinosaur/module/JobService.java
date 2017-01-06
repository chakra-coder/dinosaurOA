package com.dinosaur.module;

import com.dinosaur.module.job.dao.JobDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

}
