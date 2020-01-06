package item.api.vip;

import item.common.base.ResponseBase;
import org.springframework.web.bind.annotation.RequestMapping;
@RequestMapping("/vip1")
public interface TestInterface {

    @RequestMapping("/test")
    ResponseBase test(Integer userId, Integer password);

    @RequestMapping("/put")
    ResponseBase testRedis(Integer userId, Integer password);
}
