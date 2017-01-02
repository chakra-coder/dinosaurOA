package com.dinosaur.module.menu;

import com.dinosaur.module.menu.dao.MenuDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 菜单业务类
 * @Author Alcott Hawk
 * @Date 1/2/2017
 */
@Service
public class MenuService {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private MenuDao menuDao;

}
