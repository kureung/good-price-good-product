package kr.co.gpgp.domain.orderline.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.time.LocalDate;
import java.util.List;
import kr.co.gpgp.domain.item.entity.Item;
import kr.co.gpgp.domain.item.entity.ItemInfo;
import kr.co.gpgp.domain.item.repository.ItemRepository;
import kr.co.gpgp.domain.orderline.dto.OrderLineRequest;
import kr.co.gpgp.domain.orderline.dto.OrderLineResponse;
import kr.co.gpgp.domain.orderline.entity.OrderLine;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class OrderLineDtoServiceTests {

    @Autowired
    private OrderLineDtoService orderLineDtoService;

    @Autowired
    private ItemRepository itemRepository;

    @Test
    void 엔티티를_dto로_변환_테스트() {

        // given
        Item item = Item.of(
                1000,
                30,
                ItemInfo.of(
                        "item1",
                        10,
                        "123",
                        LocalDate.now(),
                        "www.naver.com"));

        OrderLine orderLine = OrderLine.of(item, 10);
        List<OrderLine> orderLines = List.of(orderLine);

        //when
        List<OrderLineResponse> responses = orderLineDtoService.toDto(orderLines);
        OrderLineResponse response = responses.get(0);

        // then
        assertAll(
                () -> assertThat(responses.size()).isEqualTo(orderLines.size()),
                () -> assertThat(response.getItemCode()).isEqualTo(orderLine.getItem().getInfo().getCode()),
                () -> assertThat(response.getItemName()).isEqualTo(orderLine.getItem().getInfo().getName()),
                () -> assertThat(response.getItemPrice()).isEqualTo(orderLine.getItem().getPrice()),
                () -> assertThat(response.getItemQuantity()).isEqualTo(orderLine.getCount())
        );
    }

    @Test
    void dto를_엔티티로_변환_테스트() {

        // given

        String itemCode = "123";

        Item item = Item.of(
                1000,
                30,
                ItemInfo.of(
                        "item1",
                        10,
                        itemCode,
                        LocalDate.now(),
                        "www.naver.com"));

        itemRepository.save(item);

        OrderLineRequest request = OrderLineRequest.builder()
                .itemCode(itemCode)
                .itemQuantity(10)
                .build();

        List<OrderLineRequest> requests = List.of(request);

        // when
        List<OrderLine> orderLines = orderLineDtoService.toEntity(requests);
        OrderLine orderLine = orderLines.get(0);

        //then
        assertAll(
                () -> assertThat(requests.size()).isEqualTo(orderLines.size()),
                () -> assertThat(request.getItemCode()).isEqualTo(orderLine.getItem().getInfo().getCode()),
                () -> assertThat(request.getItemQuantity()).isEqualTo(orderLine.getCount())
        );
    }
}