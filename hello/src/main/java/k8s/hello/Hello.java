package k8s.hello;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class Hello {

    public static void main(String[] args) {
        SpringApplication.run(Hello.class);
    }
}
