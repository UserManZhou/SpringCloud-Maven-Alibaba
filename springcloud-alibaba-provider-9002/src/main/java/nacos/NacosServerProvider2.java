package nacos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class NacosServerProvider2 {
    public static void main(String[] args) {
        SpringApplication.run(NacosServerProvider2.class, args);
    }
}