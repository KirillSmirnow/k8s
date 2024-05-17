package k8s.hello;

import lombok.Builder;
import lombok.Data;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDateTime;

@FeignClient("time")
public interface TimeClient {

    @GetMapping
    TimeView getTime();

    @Data
    @Builder
    class TimeView {
        private final String instance;
        private final String instanceIpAddress;
        private final int requestNumber;
        private final LocalDateTime dateTime;
        private final String path;
        private final String clientIpAddress;
    }
}
