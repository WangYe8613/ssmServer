package com.lw.controller;

import com.lw.common.Response;
import com.lw.common.ResponseCode;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
@RequestMapping("/api")
public class CheckLogin {

    private Logger logger = Logger.getLogger ( CheckLogin.class );
    @RequestMapping ( value = "/checkLogin", method = RequestMethod.GET )
    @ResponseBody
    @CrossOrigin
    public Response check (String userName, String password) {
        logger.info("CheckLogin run");
        Response response=new Response();
        Integer responseCode= ResponseCode.DEFUALT.value();
        JSONObject data=new JSONObject();
        String message=new String();

        //这里先用模拟数据写死，等到接入数据库后即可更换
        if(userName.equals("admin") && password.equals("123456")){
            responseCode=ResponseCode.SUCCESS.value();
            data.put("isValidate","true");
            message="userName and password are Validate";
            logger.info("CheckLogin result is : success");
        }else {
            responseCode=ResponseCode.USER_NOT_EXISTS.value();
            data.put("isValidate","false");
            message="userName and password are Invalidate";
            logger.info("CheckLogin result is : failed");
        }
        response.setCode(responseCode);
        response.setData(data);
        response.setMessage(message);
        return response;
    }
}
