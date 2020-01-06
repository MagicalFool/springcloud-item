package item.vip;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication(scanBasePackages = {"item.**"})
@EnableEurekaClient
public class VipServer {

    public static void main(String[] args) {
        SpringApplication.run(VipServer.class,args);
    }
}
