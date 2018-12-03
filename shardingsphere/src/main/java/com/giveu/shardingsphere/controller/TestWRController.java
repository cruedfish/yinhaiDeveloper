package com.giveu.shardingsphere.controller;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.util.JSONPObject;
import com.giveu.shardingsphere.mapper.UserDOMapper;
import com.giveu.shardingsphere.model.UserDO;
import io.shardingsphere.api.HintManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * @ClassName: TestWRController
 * @Description: TODO
 * @Author: yinhai
 * @Date: 2018/12/3 15:37
 * @Version 1.0
 **/
@Controller
public class TestWRController {
    private static Logger logger = LoggerFactory.getLogger(TestWRController.class);

    @Autowired
    UserDOMapper userDOMapper;

    @RequestMapping(value = "/test/r")
    public void testR(){
        //hintManager强制路由主库
        HintManager hintManager = HintManager.getInstance();
        hintManager.setMasterRouteOnly();
        String name = userDOMapper.selectNameById(Long.valueOf(1));
        logger.info(name);
        UserDO userDO = userDOMapper.selectByPrimaryKey(Long.valueOf(5));
//        for (UserDO userDO:userDOList){
            logger.info("查到的数据为:"+ JSONObject.toJSONString(userDO));
//        }
    }

    @RequestMapping(value = "/test/w")
    public void testW(){
       UserDO userDO = new UserDO();
       userDO.setCity("杭州");
       userDO.setId(Long.valueOf(3));
       userDO.setName("阿里");
       userDOMapper.insert(userDO);
    }
}