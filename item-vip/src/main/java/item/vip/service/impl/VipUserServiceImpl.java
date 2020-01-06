package item.vip.service.impl;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSONObject;
import item.api.vip.VipUserInterface;
import item.api.vip.entity.VipUserEntity;
import item.common.base.BaseApiService;
import item.common.base.BaseRedisService;
import item.common.base.ResponseBase;
import item.common.util.MD5Util;
import item.common.util.TokenUtil;
import item.vip.dao.VipUserDAO;
import item.vip.service.mq.RegisterMailboxProducer;
import lombok.extern.slf4j.Slf4j;
import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class VipUserServiceImpl extends BaseApiService implements VipUserInterface {

    @Autowired
    private VipUserDAO userDAO;

    @Autowired
    private RegisterMailboxProducer mailboxProducer;

    @Autowired
    private BaseRedisService baseRedisService;

    @Value("${messages.queue}")
    private String MESSAGESQUEUE;

    @Override
    public ResponseBase findUser(Long id) {
        VipUserEntity user = userDAO.findByID(id);
        return  reSuccess(user);
    }

    public ResponseBase register(@RequestBody VipUserEntity user) {
        String passWord = user.getPassword();
        String newPassWord = MD5Util.MD5(passWord);
        user.setPassword(newPassWord);
        Integer insertUser = userDAO.insertUser(user);
        if (insertUser <= 0) {
            return reFail("注册失败!");
        }
        // 采用MQ异步发送邮件
        String email = user.getEmail();
        String messAageJson = message(email);
        log.info("######email:{},messAageJson:{}",email,messAageJson);
        sendMsg(messAageJson);
        return reSuccessNoData();
    }

    @Override
    public ResponseBase login(VipUserEntity user) {
        String username = user.getUsername();
        if (StringUtils.isEmpty(username)) {
            return reFail("用户名称不能为空!");
        }
        String password = user.getPassword();
        if (StringUtils.isEmpty(password)) {
            return reFail("密码不能为空!");
        }
        String newPassWord = MD5Util.MD5(password);
        VipUserEntity userEntity = userDAO.login(username, newPassWord);
        if (userEntity == null) {
            return reFail("账号或密码错误!");
        }
        // 生成token
        String token = TokenUtil.getToken();
        baseRedisService.setString(token, userEntity.getId()+"",null);
        JSONObject JSONObject = new JSONObject();
        JSONObject.put("token", token);
        return reSuccess(JSONObject);
    }

    @Override
    public ResponseBase finTokenByUser(String token) {
        if (StringUtils.isEmpty(token)) {
            return reFail("token不能为空.");
        }
        String userId = (String) baseRedisService.getString(token);
        if(StringUtils.isEmpty(userId)){
            return reFail("未查询到用户信息");
        }
        Long userIdl=Long.parseLong(userId);
        VipUserEntity userEntity = userDAO.findByID(userIdl);
        if (userEntity == null) {
            return reFail("未查询到用户信息");
        }
        userEntity.setPassword(null);
        return reSuccess(userEntity);
    }

    private String message(String mail) {
        JSONObject root = new JSONObject();
        JSONObject header = new JSONObject();
        header.put("interfaceType", "sms_mail");
        JSONObject content = new JSONObject();
        content.put("mail", mail);
        root.put("header", header);
        root.put("content", content);
        return root.toJSONString();
    }

    private void sendMsg(String json) {
        ActiveMQQueue activeMQQueue = new ActiveMQQueue(MESSAGESQUEUE);
        mailboxProducer.sendMsg(activeMQQueue, json);
    }
}
