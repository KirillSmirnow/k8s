package k8s.main;

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
public class MainController {

    private static final String INSTANCE = "main/%s".formatted(randomUUID());

    private final AtomicInteger requestNumber = new AtomicInteger();

    private final HelloClient helloClient;

    @GetMapping("/**")
    @SneakyThrows
    public MainView getMain(HttpServletRequest request) {
        var helloResponse = helloClient.getHello();
        return MainView.builder()
                .instance(INSTANCE)
                .instanceIpAddress(InetAddress.getLocalHost().getHostAddress())
                .requestNumber(requestNumber.incrementAndGet())
                .path(request.getServletPath())
                .clientIpAddress(request.getRemoteAddr())
                .helloResponse(helloResponse)
                .build();
    }

    @Data
    @Builder
    public static class MainView {
        private final String instance;
        private final String instanceIpAddress;
        private final int requestNumber;
        private final String path;
        private final String clientIpAddress;
        private final HelloClient.HelloView helloResponse;
    }
}
