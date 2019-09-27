package com.lw.controller;

import com.lw.common.Response;
import com.lw.common.ResponseCode;
import com.lw.dao.UserEOMapper;

import com.lw.model.entity.UserConstants;
import com.lw.model.entity.UserEO;
import net.sf.json.JSONObject;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.*;
import org.apache.log4j.Logger;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.io.InputStream;


@Controller
@RequestMapping("/api")
public class CheckLogin {

    public UserEOMapper userEOMapper;

    private Logger logger = Logger.getLogger(CheckLogin.class);

    @RequestMapping(value = "/checkLogin", method = RequestMethod.GET)
    @ResponseBody
    @CrossOrigin
    public Response check(String userName, String password) throws IOException {
        logger.info("CheckLogin run");
        Response response = new Response();
        Integer responseCode = ResponseCode.DEFUALT.value();
        JSONObject data = new JSONObject();
        String message = new String();

        // 指定配置文件
        String resource = "mybatis-config.xml";
        //String resource = "src/main/resources/spring/applicationContext.xml";
        // 读取配置文件
        InputStream inputStream = Resources.getResourceAsStream(resource);
        // 构建sqlSessionFactory
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        // 获取sqlSession
        SqlSession sqlSession = sqlSessionFactory.openSession(true);

        this.userEOMapper = sqlSession.getMapper(UserEOMapper.class);

        UserEO userEO = this.userEOMapper.selectByPrams(userName, password);
        if (userEO != null && userEO.getDeleted() == UserConstants.DELETE_NULL) {
            responseCode = ResponseCode.SUCCESS.value();
            data.put("isValidate", "true");
            message = "userName and password are Validate";
            logger.info("CheckLogin result is : success");
        } else {
            responseCode = ResponseCode.USER_NOT_EXISTS.value();
            data.put("isValidate", "false");
            message = "userName and password are Invalidate";
            logger.info("CheckLogin result is : failed");
        }

        response.setCode(responseCode);
        response.setData(data);
        response.setMessage(message);

        //关闭连接（实际上是将该连接放回连接池内）
        sqlSession.close();
        return response;
    }
}
