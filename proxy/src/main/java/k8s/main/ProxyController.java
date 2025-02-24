package k8s.main;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.atomic.AtomicInteger;

import static java.util.UUID.randomUUID;

@Slf4j
@RestController
@RequiredArgsConstructor
public class ProxyController {

    private static final String INSTANCE = "proxy/%s".formatted(randomUUID());

    private final AtomicInteger requestNumber = new AtomicInteger();

    @Value("${namespace}")
    private String namespace;

    @GetMapping("/**")
    public ResponseEntity<Void> authorize(HttpServletRequest request) {
        printHeaders(request);
        var permitHeader = request.getHeader("X-PERMIT");
        var allow = permitHeader != null && permitHeader.equalsIgnoreCase("true");
        return ResponseEntity.status(
                allow ? HttpStatus.OK : HttpStatus.UNAUTHORIZED
        ).build();
    }

    private void printHeaders(HttpServletRequest request) {
        var headers = new StringBuilder();
        request.getHeaderNames().asIterator().forEachRemaining(
                headerName -> headers.append(headerName).append(": ").append(request.getHeader(headerName)).append("\n")
        );
        log.info("Headers:\n{}===", headers);
    }
}
