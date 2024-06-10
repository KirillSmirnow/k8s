package k8s.time;

import jakarta.servlet.http.HttpServletRequest;
import lombok.Builder;
import lombok.Data;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.InetAddress;
import java.time.LocalDateTime;
import java.util.concurrent.atomic.AtomicInteger;

import static java.util.UUID.randomUUID;

@RestController
public class TimeController {

    private static final String INSTANCE = "time/%s".formatted(randomUUID());

    private final AtomicInteger requestNumber = new AtomicInteger();

    @Value("${namespace}")
    private String namespace;

    @GetMapping("/**")
    @SneakyThrows
    public TimeView getTime(HttpServletRequest request) {
        return TimeView.builder()
                .instance(INSTANCE)
                .instanceIpAddress(InetAddress.getLocalHost().getHostAddress())
                .namespace(namespace)
                .requestNumber(requestNumber.incrementAndGet())
                .dateTime(LocalDateTime.now())
                .path(request.getServletPath())
                .clientIpAddress(request.getRemoteAddr())
                .build();
    }

    @Data
    @Builder
    public static class TimeView {
        private final String instance;
        private final String instanceIpAddress;
        private final String namespace;
        private final int requestNumber;
        private final LocalDateTime dateTime;
        private final String path;
        private final String clientIpAddress;
    }
}
