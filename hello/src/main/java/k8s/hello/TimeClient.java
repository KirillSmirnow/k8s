package k8s.hello;

import lombok.Builder;
import lombok.Data;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDateTime;

@FeignClient(name = "time", url = "time:8080")
public interface TimeClient {

    @GetMapping
    TimeView getTime();

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
