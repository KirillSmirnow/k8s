package k8s.hello;

import jakarta.servlet.http.HttpServletRequest;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.InetAddress;
import java.util.concurrent.atomic.AtomicInteger;

import static java.util.UUID.randomUUID;

@RestController
@RequiredArgsConstructor
public class HelloController {

    private static final String INSTANCE = "hello/%s".formatted(randomUUID());

    private final AtomicInteger requestNumber = new AtomicInteger();

    private final TimeClient timeClient;

    @GetMapping("/**")
    @SneakyThrows
    public HelloView getHello(HttpServletRequest request) {
        var timeResponse = timeClient.getTime();
        return HelloView.builder()
                .instance(INSTANCE)
                .instanceIpAddress(InetAddress.getLocalHost().getHostAddress())
                .requestNumber(requestNumber.incrementAndGet())
                .hello("Hello! It's %s now".formatted(timeResponse.getDateTime()))
                .path(request.getServletPath())
                .clientIpAddress(request.getRemoteAddr())
                .timeResponse(timeResponse)
                .build();
    }

    @Data
    @Builder
    public static class HelloView {
        private final String instance;
        private final String instanceIpAddress;
        private final int requestNumber;
        private final String hello;
        private final String path;
        private final String clientIpAddress;
        private final TimeClient.TimeView timeResponse;
    }
}
