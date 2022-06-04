package kr.co.gpgp.domain.orderline.service;

import java.util.List;
import java.util.stream.Collectors;
import kr.co.gpgp.domain.item.service.ItemFindService;
import kr.co.gpgp.domain.orderline.dto.OrderLineRequest;
import kr.co.gpgp.domain.orderline.dto.OrderLineResponse;
import kr.co.gpgp.domain.orderline.entity.OrderLine;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderLineDtoService {

    private final ItemFindService itemFindService;

    public List<OrderLineResponse> toDto(List<OrderLine> orderLines) {
        return orderLines.stream()
                .map(orderLine -> OrderLineResponse.builder()
                        .itemName(orderLine.getItem().getInfo().getName())
                        .itemCode(orderLine.getItem().getInfo().getCode())
                        .itemPrice(orderLine.getPrice())
                        .itemQuantity(orderLine.getCount())
                        .build())
                .collect(Collectors.toList());
    }

    public List<OrderLine> toEntity(List<OrderLineRequest> orderLineRequests) {
        return orderLineRequests.stream()
                .map(orderLineRequest -> OrderLine.of(
                        itemFindService.findOne(orderLineRequest.getItemCode()),
                        orderLineRequest.getItemQuantity()))
                .collect(Collectors.toList());
    }
}
