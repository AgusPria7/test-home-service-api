
package com.agusp.service;

import com.agusp.dao.UsersDao;
import com.agusp.exeption.CustomException;
import com.agusp.model.Users;
import com.agusp.repo.UserRepository;
import com.agusp.utils.AppUtils;
import static java.lang.Math.log;
import static java.lang.StrictMath.log;
import static java.rmi.server.LogStream.log;
import static org.apache.xmlbeans.impl.common.XBeanDebug.log;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties.User;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    
    @Autowired
    private UserRepository repo;
    private final static Logger log = LoggerFactory.getLogger(UserService.class);
    
    @Autowired
    private UsersDao dao;

    public long checkData(String id, Class<?> module) {
        try {
            return dao.checkExiting(id, module);
        } catch (Exception e) {
            throw new CustomException(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
    
    public Users register(Users model,String flag) {
        try {
            repo.register(model);
            log.info(" " + flag + " User Regist Success #" + AppUtils.modelToJson(model));
            return model;
        } catch (Exception e) {
            throw new CustomException(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
    
    public Users login(Users model,String flag) {
        try {
            repo.save(model);
            log.info(" " + flag + " User Login Success #" + AppUtils.modelToJson(model));
            return model;
        } catch (Exception e) {
            throw new CustomException(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
    
    public Object profile(String id, Class<?> module) {
        try {
            JSONObject obj = new JSONObject("{'id':'" + id + "'}");
            return dao.profile(obj, module);
        } catch (Exception e) {
            throw new CustomException(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}

