package k8s.main;

import lombok.Builder;
import lombok.Data;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDateTime;

@FeignClient(name = "hello", url = "hello:8080")
public interface HelloClient {

    @GetMapping
    HelloView getHello();

    @Data
    @Builder
    class HelloView {
        private final String instance;
        private final String instanceIpAddress;
        private final String namespace;
        private final int requestNumber;
        private final String hello;
        private final String path;
        private final String clientIpAddress;
        private final TimeView timeResponse;
    }

    @Data
    @Builder
    class TimeView {
        private final String instance;
        private final String instanceIpAddress;
        private final String namespace;
        private final int requestNumber;
        private final LocalDateTime dateTime;
        private final String path;
        private final String clientIpAddress;
    }
}
