package item.vip.service.impl;

import item.api.vip.TestInterface;
import item.common.base.BaseApiService;
import item.common.base.BaseRedisService;
import item.common.base.ResponseBase;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class TestServiceImpl extends BaseApiService implements TestInterface {

    @Autowired
    private BaseRedisService redisService;

    @Override
    public ResponseBase test(Integer userId, Integer password) {
        return reSuccess(userId);
    }

    @Override
    public ResponseBase testRedis(Integer userId, Integer password) {
        redisService.setString(String.valueOf(userId),String.valueOf(password),100L);
        System.out.println(redisService.getString(String.valueOf(userId)));
        return reSuccessNoData();
    }

}
