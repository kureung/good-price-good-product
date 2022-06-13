package kr.co.gpgp.web.api.order;

import java.net.URI;
import javax.validation.Valid;
import kr.co.gpgp.domain.order.dto.OrderRequest;
import kr.co.gpgp.domain.order.dto.OrderResponse;
import kr.co.gpgp.domain.order.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

    @GetMapping("/{id}")
    public ResponseEntity<OrderResponse> findOne(@PathVariable Long id) {
        OrderResponse response = orderService.findOneToDto(id);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/{id}")
    public ResponseEntity<String> orderCancel(@PathVariable Long id) {
        orderService.cancel(id);
        return ResponseEntity.ok("주문 취소 완료");
    }

}