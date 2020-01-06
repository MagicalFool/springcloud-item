package item.api.vip;

import item.api.vip.entity.VipUserEntity;
import item.common.base.ResponseBase;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/vip")
public interface VipUserInterface {

    @RequestMapping("/find")
    ResponseBase findUser(Long id);

    @RequestMapping("/register")
    ResponseBase register(@RequestBody VipUserEntity user);

    @RequestMapping("/login")
    ResponseBase login(@RequestBody VipUserEntity user);

    @RequestMapping("/findByToken")
    ResponseBase finTokenByUser(String token);
}
