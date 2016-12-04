package com.dinosaur.plat.workflow;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * 流程Controller
 * @Author Alcott Hawk
 * @Date 12/2/2016
 */
@Controller
@RequestMapping(value = "/workflow")
public class ProcessController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 获取流程列表
     * @return
     */
    public List getProcess(Integer pageSize, Integer pageNo){

        return null;
    }

}
