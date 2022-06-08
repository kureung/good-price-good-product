package kr.co.gpgp.web.api;

import java.net.URI;
import javax.validation.Valid;
import kr.co.gpgp.domain.order.dto.OrderRequest;
import kr.co.gpgp.domain.order.dto.OrderResponse;
import kr.co.gpgp.domain.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/orders")
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<OrderResponse> register(
            Long userId,
            @Valid @RequestBody OrderRequest request) {

        OrderResponse response = orderService.register(userId, request);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(response.getId())
                .toUri();

        return ResponseEntity.created(location)
                .body(response);
    }
}