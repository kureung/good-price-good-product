package kr.co.gpgp.domain.order;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;
import kr.co.gpgp.domain.address.Address;
import kr.co.gpgp.domain.address.AddressRepository;
import kr.co.gpgp.domain.delivery.Delivery;
import kr.co.gpgp.domain.delivery.DeliveryRepository;
import kr.co.gpgp.domain.item.Item;
import kr.co.gpgp.domain.item.ItemInfo;
import kr.co.gpgp.domain.orderline.OrderLine;
import kr.co.gpgp.domain.requirement.Requirement;
import kr.co.gpgp.domain.requirement.RequirementRepository;
import kr.co.gpgp.domain.user.Role;
import kr.co.gpgp.domain.user.User;
import kr.co.gpgp.domain.user.UserRepository;
import kr.co.gpgp.repository.item.ItemJpaRepository;
import kr.co.gpgp.web.api.order.OrderRequest.OrderLineRequest;
import kr.co.gpgp.web.api.order.OrderResponse.OrderLineResponse;
import kr.co.gpgp.web.exception.ErrorCode;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class OrderServiceTest {

    @Autowired
    private OrderService sut;

    @Autowired
    private ItemJpaRepository itemJpaRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private DeliveryRepository deliveryRepository;

    @Autowired
    private RequirementRepository requirementRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Test
    void 상품_주문_테스트() {
        // given
        User user = User.of("name", "abc@naver.com", Role.USER);
        User savedUser = userRepository.save(user);

        Requirement requirement = new Requirement("요청사항");
        Address address = Address.of(user, "123456789", "12345", "주소", "주소주소");
        Delivery delivery = Delivery.of(requirement, address);

        ItemInfo info = ItemInfo.builder()
                .code("123")
                .weight(10)
                .build();

        Item item = Item.builder()
                .stockQuantity(11)
                .info(info)
                .price(1000)
                .build();
        itemJpaRepository.save(item);

        List<OrderLineRequest> orderLineRequests = List.of(
                OrderLineRequest.builder()
                        .itemCode(item.getInfo().getCode())
                        .orderQuantity(10)
                        .build());

        // when, then
        assertDoesNotThrow(() ->
                sut.order(
                        savedUser.getId(),
                        delivery,
                        orderLineRequests)
        );
    }

    @Test
    void 상품_주문시_재고수량을_초과하면_예외가_발생한다() {
        // given
        User user = User.of("name", "abc@naver.com", Role.USER);
        User savedUser = userRepository.save(user);

        Requirement requirement = new Requirement("요청사항");
        Address address = Address.of(user, "123456789", "12345", "주소", "주소주소");
        Delivery delivery = Delivery.of(requirement, address);

        ItemInfo info = ItemInfo.builder()
                .code("123")
                .weight(10)
                .build();

        Item item = Item.builder()
                .stockQuantity(11)
                .info(info)
                .price(1000)
                .build();
        itemJpaRepository.save(item);

        List<OrderLineRequest> orderLineRequests = List.of(
                OrderLineRequest.builder()
                        .itemCode(item.getInfo().getCode())
                        .orderQuantity(40)
                        .build());

        // then
        assertThatThrownBy(() -> sut.order(
                savedUser.getId(),
                delivery,
                orderLineRequests))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage(ErrorCode.STOCK_OUT_OF_RANGE.getMessage());
    }

    @Test
    void 결제완료_단계에서_주문취소시_예외가_발생하지_않는다() {
        // given
        User user = User.of("name", "abc@naver.com", Role.USER);
        User savedUser = userRepository.save(user);

        Requirement requirement = new Requirement("요청사항");
        Address address = Address.of(user, "123456789", "12345", "주소", "주소주소");
        Delivery delivery = Delivery.of(requirement, address);

        ItemInfo info = ItemInfo.builder()
                .code("123")
                .weight(10)
                .build();

        Item item = Item.builder()
                .stockQuantity(11)
                .info(info)
                .price(1000)
                .build();
        itemJpaRepository.save(item);

        List<OrderLineRequest> orderLineRequests = List.of(
                OrderLineRequest.builder()
                        .itemCode(item.getInfo().getCode())
                        .orderQuantity(10)
                        .build());

        Long orderId = sut.order(
                savedUser.getId(),
                delivery,
                orderLineRequests);

        // when, then
        assertDoesNotThrow(() -> sut.cancel(orderId));
    }

    @Test
    void 상품준비중_단계에서_주문취소시_예외가_발생하지_않는다() {
        // given
        User user = User.of("name", "abc@naver.com", Role.USER);
        User savedUser = userRepository.save(user);

        Requirement requirement = new Requirement("요청사항");
        Address address = Address.of(user, "123456789", "12345", "주소", "주소주소");
        Delivery delivery = Delivery.of(requirement, address);
        delivery.nextStepInstruct();

        ItemInfo info = ItemInfo.builder()
                .code("123")
                .weight(10)
                .build();

        Item item = Item.builder()
                .stockQuantity(11)
                .info(info)
                .price(1000)
                .build();
        itemJpaRepository.save(item);

        List<OrderLineRequest> orderLineRequests = List.of(
                OrderLineRequest.builder()
                        .itemCode(item.getInfo().getCode())
                        .orderQuantity(10)
                        .build());

        Long orderId = sut.order(
                savedUser.getId(),
                delivery,
                orderLineRequests);

        // when, then
        assertDoesNotThrow(() -> sut.cancel(orderId));
    }

    @Test
    void 엔티티리스트를_dto리스트로_변환_테스트() {
        // given
        ItemInfo info = ItemInfo.builder()
                .code("123")
                .weight(10)
                .build();

        Item item = Item.builder()
                .stockQuantity(11)
                .info(info)
                .price(1000)
                .build();

        OrderLine orderLine = OrderLine.of(item, 10);
        List<OrderLine> orderLines = List.of(orderLine);

        //when, then
        List<OrderLineResponse> responses = assertDoesNotThrow(() -> sut.toDtos(orderLines));
        assertNotNull(responses);
        assertInstanceOf(List.class, responses);
    }

    @Test
    void dto리스트를_엔티티리스트로_변환_테스트() {
        // given
        String itemCode = "123";
        ItemInfo info = ItemInfo.builder()
                .code(itemCode)
                .build();
        Item item = Item.builder()
                .info(info)
                .stockQuantity(11)
                .build();
        itemJpaRepository.save(item);

        OrderLineRequest request = OrderLineRequest.builder()
                .itemCode(itemCode)
                .orderQuantity(10)
                .build();
        List<OrderLineRequest> requests = List.of(request);

        // when, then
        List<OrderLine> responses = assertDoesNotThrow(() -> sut.toEntities(requests));
        assertNotNull(responses);
        assertInstanceOf(List.class, responses);
    }

    @Test
    void 주문_검색_테스트() {
        // given
        OrderSearchCondition condition = OrderSearchCondition.builder()
                .userId(1L)
                .build();

        User user = User.of("name1", "abc@naver.com", Role.USER);
        userRepository.save(user);

        Requirement requirement = new Requirement("요청사항");
        requirementRepository.save(requirement);

        Address address = Address.of(user, "123456789", "12345", "name1", "detailed");
        addressRepository.save(address);

        Delivery delivery = Delivery.of(requirement, address);
        deliveryRepository.save(delivery);

        for (int i = 0; i < 100; i++) {
            ItemInfo info = ItemInfo.builder()
                    .code("123")
                    .weight(20)
                    .build();

            Item item = Item.builder()
                    .info(info)
                    .price(1000)
                    .stockQuantity(100)
                    .build();
            itemJpaRepository.save(item);

            OrderLine orderLine = OrderLine.of(item, i);
            List<OrderLine> orderLines = List.of(orderLine);

            Order order = Order.of(user, delivery, orderLines);
            orderRepository.save(order);
        }

        // when
        int size = 5;
        PageRequest pageRequest = PageRequest.of(0, size);
        Page<OrderSearchResponse> searchResponses = sut.searchOrder(condition, pageRequest);
        List<OrderSearchResponse> content = searchResponses.getContent();

        // then
        assertThat(content.size()).isEqualTo(size);
    }

}