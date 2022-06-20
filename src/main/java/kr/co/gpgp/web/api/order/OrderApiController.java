package kr.co.gpgp.web.api.order;

import java.net.URI;
import java.util.List;
import javax.validation.Valid;
import kr.co.gpgp.domain.address.Address;
import kr.co.gpgp.domain.order.Order;
import kr.co.gpgp.domain.order.OrderSearchCondition;
import kr.co.gpgp.domain.order.OrderSearchResponse;
import kr.co.gpgp.domain.order.OrderService;
import kr.co.gpgp.domain.orderline.OrderLine;
import kr.co.gpgp.domain.requirement.Requirement;
import kr.co.gpgp.web.api.address.AddressAdapter;
import kr.co.gpgp.web.api.requirement.RequirementAdapter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
public class OrderApiController {

    private final OrderService orderService;
    private final OrderAdapter orderAdapter;
    private final AddressAdapter addressAdapter;
    private final RequirementAdapter requirementAdapter;

    @PostMapping
    public ResponseEntity<OrderResponse> register(
            @Valid @RequestBody OrderRequest request) {

        List<OrderLine> orderLines = orderAdapter.toEntities(request.getOrderLines());
        Requirement requirement = requirementAdapter.toEntity(request.getRequirement());
        Address address = addressAdapter.toEntity(request.getUserId(),
                request.getRoadName(),
                request.getZipCode(),
                request.getAddressName(),
                request.getDetailedAddress());

        Long orderId = orderService.order(request.getUserId(),
                address,
                requirement,
                orderLines);
        Order order = orderService.findOne(orderId);
        OrderResponse response = orderAdapter.toDto(order);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(orderId)
                .toUri();

        return ResponseEntity.created(location)
                .body(response);
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<OrderResponse> findOne(@PathVariable Long orderId) {
        Order order = orderService.findOne(orderId);
        OrderResponse response = orderAdapter.toDto(order);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/{orderId}")
    public ResponseEntity<String> orderCancel(@PathVariable Long orderId) {
        orderService.cancel(orderId);
        return ResponseEntity.ok("주문 취소 완료");
    }

    @PostMapping("/search")
    public ResponseEntity<Page<OrderSearchResponse>> searchResponse(OrderSearchCondition condition, Pageable pageable) {
        Page<OrderSearchResponse> orderSearchResponses = orderService.searchOrder(condition, pageable);
        return ResponseEntity.ok(orderSearchResponses);
    }

}