package leonardo.ezio.personal;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@EnableFeignClients
@EnableDiscoveryClient
@SpringBootApplication
public class Oauth2AuthApplication {

    public static void main(String[] args) {
        SpringApplication.run(Oauth2AuthApplication.class, args);
    }

}
